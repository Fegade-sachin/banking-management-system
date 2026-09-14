package com.bank.dao;

import java.util.List;
import com.bank.model.Transaction;
import org.hibernate.Session;

public interface TransactionDao {

    void save(Transaction transaction);

    Transaction findById(long transactionId);

    List<Transaction> findByAccountNumber(long accountNumber);

    List<Transaction> findAll();
    void save(Session session, Transaction transaction);
}