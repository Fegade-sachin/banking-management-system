package com.bank.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.bank.dao.CustomerDao;
import com.bank.model.Customer;

public class CustomerDaoImpl implements CustomerDao {

    private final List<Customer> customers = new ArrayList<>();

    @Override
    public void save(Customer customer) {
        customers.add(customer);
    }

    @Override
    public Customer findById(long customerId) {

        for (Customer customer : customers) {

            if (customer.getCustomerId() == customerId) {
                return customer;
            }
        }

        return null;
    }

    @Override
    public List<Customer> findAll() {
        return new ArrayList<>(customers);
    }

    @Override
    public void update(Customer customer) {

        for (int i = 0; i < customers.size(); i++) {

            if (customers.get(i).getCustomerId()
                    == customer.getCustomerId()) {

                customers.set(i, customer);
                return;
            }
        }
    }

    @Override
    public void delete(long customerId) {

        for (int i = 0; i < customers.size(); i++) {

            if (customers.get(i).getCustomerId() == customerId) {
                customers.remove(i);
                return;
            }
        }
    }
}