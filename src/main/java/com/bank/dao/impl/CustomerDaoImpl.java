package com.bank.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.bank.dao.CustomerDao;
import com.bank.model.Customer;
import com.bank.util.HibernateUtil;

public class CustomerDaoImpl implements CustomerDao {

    @Override
    public void save(Customer customer) {

        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            session.persist(customer);

            transaction.commit();

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();
        }
    }

    @Override
    public Customer findById(long customerId) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            return session.find(Customer.class, customerId);
        }
    }

    @Override
    public List<Customer> findAll() {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            return session
                    .createQuery("FROM Customer", Customer.class)
                    .getResultList();
        }
    }

    @Override
    public void update(Customer customer) {

        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            session.merge(customer);

            transaction.commit();

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();
        }
    }

    @Override
    public void delete(long customerId) {

        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            Customer customer = session.find(Customer.class, customerId);

            if (customer != null) {
                session.remove(customer);
            }

            transaction.commit();

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();
        }
    }
}