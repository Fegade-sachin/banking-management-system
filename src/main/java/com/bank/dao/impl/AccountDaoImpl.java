package com.bank.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.bank.dao.AccountDao;
import com.bank.model.Account;
import com.bank.util.HibernateUtil;

public class AccountDaoImpl implements AccountDao {

    @Override
    public void save(Account account) {

        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            session.persist(account);

            transaction.commit();

        } catch (Exception e) {

            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }

            e.printStackTrace();
        }
    }

    @Override
    public Account findByAccountNumber(long accountNumber) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            return session.find(Account.class, accountNumber);
        }
    }

    @Override
    public List<Account> findByCustomerId(long customerId) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            return session
                    .createQuery(
                            "FROM Account WHERE customerId = :customerId",
                            Account.class
                    )
                    .setParameter("customerId", customerId)
                    .getResultList();
        }
    }

    @Override
    public List<Account> findAll() {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            return session
                    .createQuery("FROM Account", Account.class)
                    .getResultList();
        }
    }

    @Override
    public void update(Account account) {

        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            session.merge(account);

            transaction.commit();

        } catch (Exception e) {

            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }

            e.printStackTrace();
        }
    }

    @Override
    public void delete(long accountNumber) {

        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            Account account = session.find(Account.class, accountNumber);

            if (account != null) {
                session.remove(account);
            }

            transaction.commit();

        } catch (Exception e) {

            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }

            e.printStackTrace();
        }
    }

    @Override
    public Account findByAccountNumber(Session session, long accountNumber) {

        return session.find(Account.class, accountNumber);
    }

    @Override
    public void update(Session session, Account account) {

        session.merge(account);
    }
}