package com.bank.dao.impl;

import java.util.List;

import org.hibernate.Session;

import com.bank.dao.TransactionDao;
import com.bank.model.Transaction;
import com.bank.util.HibernateUtil;

public class TransactionDaoImpl implements TransactionDao {

    @Override
    public void save(Transaction transaction) {

        org.hibernate.Transaction hibernateTransaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            hibernateTransaction = session.beginTransaction();

            session.persist(transaction);

            hibernateTransaction.commit();

        } catch (Exception e) {

            if (hibernateTransaction != null
                    && hibernateTransaction.isActive()) {

                hibernateTransaction.rollback();
            }

            e.printStackTrace();
        }
    }

    @Override
    public Transaction findById(long transactionId) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            return session.find(Transaction.class, transactionId);
        }
    }

    @Override
    public List<Transaction> findByAccountNumber(long accountNumber) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            return session
                    .createQuery(
                            "FROM Transaction WHERE accountNumber = :accountNumber",
                            Transaction.class
                    )
                    .setParameter("accountNumber", accountNumber)
                    .getResultList();
        }
    }

    @Override
    public List<Transaction> findAll() {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            return session
                    .createQuery("FROM Transaction", Transaction.class)
                    .getResultList();
        }

    }
    @Override
    public void save(Session session, Transaction transaction) {

        session.persist(transaction);
    }
}