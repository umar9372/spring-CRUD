package com.example.springg.customer;

import com.example.springg.exception.DuplicateResourcException;
import com.example.springg.exception.RequestValidationException;
import com.example.springg.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(@Qualifier("jpa") CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public List<Customer> getAllCustomers() {

        return customerDao.selectAllCustomers();
    }

    public Customer getCustomer(Integer id) {

        return customerDao.selectCustomerById(id).orElseThrow(
                () -> new ResourceNotFoundException("customer  with the id [%s] is not found"
                        .formatted(id)));
    }

    public void addCustomer(CustomerRegistrationRequest customerRegistrationRequest) {
        //check  if emai exist
        //if not then add
        String email = customerRegistrationRequest.email();
        if (customerDao.exitsPersonWithEmail(email)) {
            throw new DuplicateResourcException
                    ("customer  with the email [%s] is already taken, please try different one"
                            .formatted(email));
        }
        //add
        Customer customer = new Customer(
                customerRegistrationRequest.name(),
                customerRegistrationRequest.email(),
                customerRegistrationRequest.age()
        );
        customerDao.InsertCustomer(customer);
    }

    public void deleteCustomerById(Integer customerId) {
        if (!customerDao.exitsPersonWithId(customerId)) {
            throw new ResourceNotFoundException(
                    "customer with id [%s] not found".formatted(customerId)
            );
        }
        customerDao.deleteCustomerById(customerId);
    }

    public void updateCustomer(Integer customerId,
                               CustomerUpdateRequest updateRequest) {

        Customer customer = getCustomer(customerId);
        boolean changes = false;

        if (updateRequest.name() != null && !updateRequest.name().equals(customer.getName())) {
            customer.setName(updateRequest.name());
            changes = true;
        }
        if (updateRequest.age() != null && !updateRequest.age().equals(customer.getAge())) {
            customer.setAge(updateRequest.age());
            changes = true;
        }

        if (updateRequest.email() != null && !updateRequest.email().equals(customer.getEmail())) {
            if (customerDao.exitsPersonWithEmail(updateRequest.email())) {
                throw new DuplicateResourcException(
                        "email already taken"
                );
            }

            customer.setEmail(updateRequest.email());
            changes = true;
        }

        if (!changes) {
            throw new RequestValidationException("no data changes found");
        }

        customerDao.updateCustomer(customer);
    }




}

