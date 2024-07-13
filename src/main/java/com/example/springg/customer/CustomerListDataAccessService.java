package com.example.springg.customer;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository("list")


public class CustomerListDataAccessService implements CustomerDao {


    private static List<Customer> customers;

    static {
        customers = new ArrayList<>();
        Customer umar = new Customer(
                "Umar",
                "umar@gmail.com",
                23
        );

        Customer zeeshan = new Customer(
                "Zeeshan",
                "zeeshan@gmail.com",
                17
        );

        customers.add(umar);
        customers.add(zeeshan);
    }


    @Override
    public List<Customer> selectAllCustomers() {
        return customers;
    }

    @Override
    public Optional<Customer> selectCustomerById(Integer id) {
        return customers.stream().
                filter(c -> c.getId().equals(id))
                .findFirst();
    }

    @Override
    public void InsertCustomer(Customer customer) {
        customers.add(customer);
    }

    @Override
    public boolean exitsPersonWithEmail(String email) {
        return customers.stream().anyMatch(c -> c.getEmail().equals(email));
    }

    @Override
    public boolean exitsPersonWithId(Integer id) {
        return customers.stream()
                .anyMatch(c -> c.getId().equals(id));
    }

    @Override
    public void deleteCustomerById(Integer customerId) {
        customers.stream()
                .filter(c -> c.getId().equals(customerId))
                .findFirst()
                .ifPresent(c -> customers.remove(c));

    }

    @Override
    public void updateCustomer(Customer customer) {
        customers.add(customer);
    }
}
