package com.bank.service.impl;

import java.util.List;

import com.bank.dao.CustomerDao;
import com.bank.exception.CustomerNotFoundException;
import com.bank.model.Customer;
import com.bank.service.CustomerService;

public class CustomerServiceImpl implements CustomerService {

    private final CustomerDao customerDao;

    public CustomerServiceImpl(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Override
    public void createCustomer(Customer customer) {

        if (customer == null) {
            throw new IllegalArgumentException(
                    "Customer cannot be null");
        }

        customerDao.save(customer);
    }

    @Override
    public Customer getCustomer(long customerId) {

        Customer customer = customerDao.findById(customerId);

        if (customer == null) {
            throw new CustomerNotFoundException(
                    "Customer not found with ID: " + customerId);
        }

        return customer;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerDao.findAll();
    }

    @Override
    public void updateCustomer(Customer customer) {

        getCustomer(customer.getCustomerId());

        customerDao.update(customer);
    }

    @Override
    public void deleteCustomer(long customerId) {

        getCustomer(customerId);

        customerDao.delete(customerId);
    }
}