package com.bank.service.impl;

import java.util.List;

import com.bank.dao.TransactionDao;
import com.bank.model.Transaction;
import com.bank.service.TransactionService;

public class TransactionServiceImpl implements TransactionService {

    private final TransactionDao transactionDao;

    public TransactionServiceImpl(TransactionDao transactionDao) {
        this.transactionDao = transactionDao;
    }

    @Override
    public List<Transaction> getTransactions(long accountNumber) {
        return transactionDao.findByAccountNumber(accountNumber);
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionDao.findAll();
    }
}