package com.bank;

import com.bank.util.HibernateUtil;

public class TestHibernate {

    public static void main(String[] args) {

        System.out.println("Starting Hibernate...");

        HibernateUtil.getSessionFactory();

        System.out.println("Hibernate started successfully!");
        System.out.println("Oracle connection configuration loaded.");

        HibernateUtil.shutdown();

        System.out.println("Hibernate shutdown successfully!");
    }
}