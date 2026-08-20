package com.bank.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.bank.dao.TransactionDao;
import com.bank.model.Transaction;

public class TransactionDaoImpl implements TransactionDao {

    private final List<Transaction> transactions = new ArrayList<>();

    @Override
    public void save(Transaction transaction) {
        transactions.add(transaction);
    }

    @Override
    public Transaction findById(long transactionId) {

        for (Transaction transaction : transactions) {

            if (transaction.getTransactionId() == transactionId) {
                return transaction;
            }
        }

        return null;
    }

    @Override
    public List<Transaction> findByAccountNumber(long accountNumber) {

        List<Transaction> result = new ArrayList<>();

        for (Transaction transaction : transactions) {

            if (transaction.getAccountNumber() == accountNumber) {
                result.add(transaction);
            }
        }

        return result;
    }

    @Override
    public List<Transaction> findAll() {
        return new ArrayList<>(transactions);
    }
}