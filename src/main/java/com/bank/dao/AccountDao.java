package com.bank.dao;

import java.util.List;

import org.hibernate.Session;

import com.bank.model.Account;

public interface AccountDao {

    void save(Account account);

    Account findByAccountNumber(long accountNumber);

    List<Account> findByCustomerId(long customerId);

    List<Account> findAll();

    void update(Account account);

    void delete(long accountNumber);

    Account findByAccountNumber(Session session, long accountNumber);

    void update(Session session, Account account);
}