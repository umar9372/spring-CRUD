package com.example.springg.customer;

import java.util.List;
import java.util.Optional;

public interface CustomerDao {

    List<Customer> selectAllCustomers();
    Optional<Customer> selectCustomerById(Integer id);
    void insertCustomer(Customer customer);
    boolean exitsPersonWithEmail(String email);
    boolean exitsPersonWithId(Integer id);
    void deleteCustomerById(Integer customerId);

    void updateCustomer(Customer update);
}

