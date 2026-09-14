package com.bank.util;


import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.bank.model.Account;
import com.bank.model.Customer;
import com.bank.model.Transaction;

public class HibernateUtil {

    private static final SessionFactory SESSION_FACTORY;

    static {
        try {
            SESSION_FACTORY = new Configuration()
                    .configure("hibernate.cfg.xml")
                    .addAnnotatedClass(Customer.class)
                    .addAnnotatedClass(Account.class)
                    .addAnnotatedClass(Transaction.class)
                    .buildSessionFactory();

        } catch (Throwable ex) {
            System.err.println("SessionFactory creation failed: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    private HibernateUtil() {
    }

    public static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}