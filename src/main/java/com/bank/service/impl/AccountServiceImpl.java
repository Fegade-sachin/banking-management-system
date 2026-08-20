package com.bank.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

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

public class AccountServiceImpl implements AccountService {

    private final AccountDao accountDao;
    private final CustomerDao customerDao;
    private final TransactionDao transactionDao;

    private static final AtomicLong ACCOUNT_SEQUENCE =
            new AtomicLong(100000);

    private static final AtomicLong TRANSACTION_SEQUENCE =
            new AtomicLong(1);

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

        long accountNumber = ACCOUNT_SEQUENCE.incrementAndGet();

        Account account = new Account(
                accountNumber,
                customerId,
                accountType,
                BigDecimal.ZERO
        );

        accountDao.save(account);

        System.out.println(
                "Account created successfully. Account Number: "
                        + accountNumber);
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
                TRANSACTION_SEQUENCE.incrementAndGet(),
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
                TRANSACTION_SEQUENCE.incrementAndGet(),
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

        Account source = getAccount(fromAccount);
        Account destination = getAccount(toAccount);

        validateActiveAccount(source);
        validateActiveAccount(destination);

        if (source.getBalance().compareTo(amount) < 0) {

            throw new InsufficientBalanceException(
                    "Insufficient balance for transfer");
        }

        BigDecimal sourceBalance =
                source.getBalance().subtract(amount);

        BigDecimal destinationBalance =
                destination.getBalance().add(amount);

        source.setBalance(sourceBalance);
        destination.setBalance(destinationBalance);

        accountDao.update(source);
        accountDao.update(destination);

        Transaction debitTransaction = new Transaction(
                TRANSACTION_SEQUENCE.incrementAndGet(),
                fromAccount,
                TransactionType.TRANSFER_DEBIT,
                amount,
                sourceBalance,
                "Transfer to account " + toAccount
        );

        Transaction creditTransaction = new Transaction(
                TRANSACTION_SEQUENCE.incrementAndGet(),
                toAccount,
                TransactionType.TRANSFER_CREDIT,
                amount,
                destinationBalance,
                "Transfer from account " + fromAccount
        );

        transactionDao.save(debitTransaction);
        transactionDao.save(creditTransaction);
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