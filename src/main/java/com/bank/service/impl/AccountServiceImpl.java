
        package com.bank.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Session;

import com.bank.constants.AccountStatus;
import com.bank.constants.AccountType;
import com.bank.constants.TransactionType;
import com.bank.dao.AccountDao;
import com.bank.dao.CustomerDao;
import com.bank.dao.TransactionDao;
import com.bank.exception.AccountBlockedException;
import com.bank.exception.AccountNotFoundException;
import com.bank.exception.CustomerNotFoundException;
import com.bank.exception.InsufficientBalanceException;
import com.bank.exception.InvalidAmountException;
import com.bank.model.Account;
import com.bank.model.Customer;
import com.bank.model.Transaction;
import com.bank.service.AccountService;
import com.bank.util.HibernateUtil;

public class AccountServiceImpl implements AccountService {

    private final AccountDao accountDao;
    private final CustomerDao customerDao;
    private final TransactionDao transactionDao;

    public AccountServiceImpl(AccountDao accountDao,
                              CustomerDao customerDao,
                              TransactionDao transactionDao) {

        this.accountDao = accountDao;
        this.customerDao = customerDao;
        this.transactionDao = transactionDao;
    }

    @Override
    public void createAccount(long customerId, AccountType accountType) {

        Customer customer = customerDao.findById(customerId);

        if (customer == null) {
            throw new CustomerNotFoundException(
                    "Customer not found with ID: " + customerId);
        }

        if (accountType == null) {
            throw new IllegalArgumentException(
                    "Account type cannot be null");
        }

        Account account = new Account(
                0,
                customerId,
                accountType,
                BigDecimal.ZERO
        );

        accountDao.save(account);

        System.out.println(
                "Account created successfully. Account Number: "
                        + account.getAccountNumber());
    }

    @Override
    public Account getAccount(long accountNumber) {

        Account account =
                accountDao.findByAccountNumber(accountNumber);

        if (account == null) {
            throw new AccountNotFoundException(
                    "Account not found: " + accountNumber);
        }

        return account;
    }

    @Override
    public List<Account> getAccountsByCustomer(long customerId) {

        Customer customer = customerDao.findById(customerId);

        if (customer == null) {
            throw new CustomerNotFoundException(
                    "Customer not found with ID: " + customerId);
        }

        return accountDao.findByCustomerId(customerId);
    }

    @Override
    public void deposit(long accountNumber, BigDecimal amount) {

        validateAmount(amount);

        Account account = getAccount(accountNumber);

        validateActiveAccount(account);

        BigDecimal newBalance =
                account.getBalance().add(amount);

        account.setBalance(newBalance);

        accountDao.update(account);

        Transaction transaction = new Transaction(
                0,
                accountNumber,
                TransactionType.DEPOSIT,
                amount,
                newBalance,
                "Cash deposit"
        );

        transactionDao.save(transaction);
    }

    @Override
    public void withdraw(long accountNumber, BigDecimal amount) {

        validateAmount(amount);

        Account account = getAccount(accountNumber);

        validateActiveAccount(account);

        if (account.getBalance().compareTo(amount) < 0) {
            throw new InsufficientBalanceException(
                    "Insufficient balance. Available: "
                            + account.getBalance()
                            + ", Requested: " + amount);
        }

        BigDecimal newBalance =
                account.getBalance().subtract(amount);

        account.setBalance(newBalance);

        accountDao.update(account);

        Transaction transaction = new Transaction(
                0,
                accountNumber,
                TransactionType.WITHDRAW,
                amount,
                newBalance,
                "Cash withdrawal"
        );

        transactionDao.save(transaction);
    }

    @Override
    public void transfer(long fromAccount,
                         long toAccount,
                         BigDecimal amount) {

        validateAmount(amount);

        if (fromAccount == toAccount) {
            throw new IllegalArgumentException(
                    "Source and destination accounts cannot be same");
        }

        try (Session session =
                     HibernateUtil.getSessionFactory().openSession()) {

            org.hibernate.Transaction dbTransaction =
                    session.beginTransaction();

            try {

                // 1. Load source account
                Account source =
                        accountDao.findByAccountNumber(
                                session,
                                fromAccount
                        );

                // 2. Load destination account
                Account destination =
                        accountDao.findByAccountNumber(
                                session,
                                toAccount
                        );

                // 3. Validate accounts
                if (source == null) {
                    throw new AccountNotFoundException(
                            "Account not found: " + fromAccount);
                }

                if (destination == null) {
                    throw new AccountNotFoundException(
                            "Account not found: " + toAccount);
                }

                // 4. Validate account status
                validateActiveAccount(source);
                validateActiveAccount(destination);

                // 5. Check balance
                if (source.getBalance().compareTo(amount) < 0) {
                    throw new InsufficientBalanceException(
                            "Insufficient balance for transfer");
                }

                // 6. Calculate new balances
                BigDecimal sourceBalance =
                        source.getBalance().subtract(amount);

                BigDecimal destinationBalance =
                        destination.getBalance().add(amount);

                // 7. Update balances
                source.setBalance(sourceBalance);
                destination.setBalance(destinationBalance);

                accountDao.update(session, source);
                accountDao.update(session, destination);

                // 8. Create debit transaction
                Transaction debitTransaction =
                        new Transaction(
                                0,
                                fromAccount,
                                TransactionType.TRANSFER_DEBIT,
                                amount,
                                sourceBalance,
                                "Transfer to account " + toAccount
                        );

                // 9. Create credit transaction
                Transaction creditTransaction =
                        new Transaction(
                                0,
                                toAccount,
                                TransactionType.TRANSFER_CREDIT,
                                amount,
                                destinationBalance,
                                "Transfer from account " + fromAccount
                        );

                // 10. Save both transaction records
                transactionDao.save(
                        session,
                        debitTransaction
                );

                transactionDao.save(
                        session,
                        creditTransaction
                );

                // 11. Commit everything together
                dbTransaction.commit();

            } catch (Exception e) {

                // Rollback everything if any operation fails
                if (dbTransaction.isActive()) {
                    dbTransaction.rollback();
                }

                throw e;
            }
        }
    }

    @Override
    public BigDecimal getBalance(long accountNumber) {

        Account account = getAccount(accountNumber);

        return account.getBalance();
    }

    @Override
    public void closeAccount(long accountNumber) {

        Account account = getAccount(accountNumber);

        if (account.getBalance()
                .compareTo(BigDecimal.ZERO) != 0) {

            throw new IllegalStateException(
                    "Account cannot be closed while balance is not zero");
        }

        account.setStatus(AccountStatus.CLOSED);

        accountDao.update(account);
    }

    private void validateAmount(BigDecimal amount) {

        if (amount == null ||
                amount.compareTo(BigDecimal.ZERO) <= 0) {

            throw new InvalidAmountException(
                    "Amount must be greater than zero");
        }
    }

    private void validateActiveAccount(Account account) {

        if (account.getStatus() == AccountStatus.BLOCKED) {

            throw new AccountBlockedException(
                    "Account is blocked: "
                            + account.getAccountNumber());
        }

        if (account.getStatus() == AccountStatus.CLOSED) {

            throw new IllegalStateException(
                    "Account is closed: "
                            + account.getAccountNumber());
        }
    }
}

