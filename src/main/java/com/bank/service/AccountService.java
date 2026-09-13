package com.bank.service;

import java.math.BigDecimal;
import java.util.List;

import com.bank.constants.AccountType;
import com.bank.dao.AccountDao;
import com.bank.model.Account;

public interface AccountService {

    void createAccount(long customerId, AccountType accountType);

    Account getAccount(long accountNumber);

    List<Account> getAccountsByCustomer(long customerId);

    void deposit(long accountNumber, BigDecimal amount);

    void withdraw(long accountNumber, BigDecimal amount);

    void transfer(long fromAccount,
                  long toAccount,
                  BigDecimal amount);

    BigDecimal getBalance(long accountNumber);

    void closeAccount(long accountNumber);
}