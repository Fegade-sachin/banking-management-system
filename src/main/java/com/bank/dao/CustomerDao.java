package com.bank.dao;

import java.util.List;
import com.bank.model.Customer;

public interface CustomerDao {

    void save(Customer customer);

    Customer findById(long customerId);

    List<Customer> findAll();

    void update(Customer customer);

    void delete(long customerId);
}