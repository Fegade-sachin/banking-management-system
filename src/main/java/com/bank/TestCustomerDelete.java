package com.bank;

import com.bank.dao.CustomerDao;
import com.bank.dao.impl.CustomerDaoImpl;
import com.bank.model.Customer;

public class TestCustomerDelete {

    public static void main(String[] args) {

        CustomerDao customerDao = new CustomerDaoImpl();

        // Find customer before delete
        Customer customer = customerDao.findById(10);

        System.out.println("Before Delete:");
        System.out.println(customer);

        // Delete customer
        customerDao.delete(10);

        System.out.println("\nCustomer deleted successfully!");

        // Verify deletion
        Customer deletedCustomer = customerDao.findById(10);

        System.out.println("\nAfter Delete:");
        System.out.println(deletedCustomer);
    }
}