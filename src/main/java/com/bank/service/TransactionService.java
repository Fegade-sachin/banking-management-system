package com.bank.service;

import java.util.List;

import com.bank.model.Transaction;

public interface TransactionService {

    List<Transaction> getTransactions(long accountNumber);

    List<Transaction> getAllTransactions();
}