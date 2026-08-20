package com.bank.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.bank.dao.AccountDao;
import com.bank.model.Account;

public class AccountDaoImpl implements AccountDao {

    private final List<Account> accounts = new ArrayList<>();

    @Override
    public void save(Account account) {
        accounts.add(account);
    }

    @Override
    public Account findByAccountNumber(long accountNumber) {

        for (Account account : accounts) {

            if (account.getAccountNumber() == accountNumber) {
                return account;
            }
        }

        return null;
    }

    @Override
    public List<Account> findByCustomerId(long customerId) {

        List<Account> result = new ArrayList<>();

        for (Account account : accounts) {

            if (account.getCustomerId() == customerId) {
                result.add(account);
            }
        }

        return result;
    }

    @Override
    public List<Account> findAll() {
        return new ArrayList<>(accounts);
    }

    @Override
    public void update(Account account) {

        for (int i = 0; i < accounts.size(); i++) {

            if (accounts.get(i).getAccountNumber()
                    == account.getAccountNumber()) {

                accounts.set(i, account);
                return;
            }
        }
    }

    @Override
    public void delete(long accountNumber) {

        for (int i = 0; i < accounts.size(); i++) {

            if (accounts.get(i).getAccountNumber() == accountNumber) {
                accounts.remove(i);
                return;
            }
        }
    }
}