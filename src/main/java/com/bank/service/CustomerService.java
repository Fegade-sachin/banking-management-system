package com.bank.service;

import java.util.List;

import com.bank.model.Customer;

public interface CustomerService {

    void createCustomer(Customer customer);

    Customer getCustomer(long customerId);

    List<Customer> getAllCustomers();

    void updateCustomer(Customer customer);

    void deleteCustomer(long customerId);
}