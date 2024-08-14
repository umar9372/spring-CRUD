package com.example.springg.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)  //this will do the same as autoclosable
class CustomerJPADataAccessServiceTest {

    private CustomerJPADataAccessService underTest;

    //This allows you to close the mocks after each test
//    private AutoCloseable autoCloseable;

    @Mock
    private CustomerRepository customerRepository;


    @BeforeEach
    void setUp() {
//        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new CustomerJPADataAccessService(customerRepository);
    }

//    @AfterEach
//        //after each test we have fresh mock
//    void tearDown() throws Exception {
//        autoCloseable.close();
//    }

    @Test
    void selectAllCustomers() {

        //when
        underTest.selectAllCustomers();

        //then
        verify(customerRepository)
                .findAll();
    }

    @Test
    void selectCustomerById() {

        //given
        int id = -1;

        //when
        underTest.selectCustomerById(id);

        //then
        verify(customerRepository).findById(id);
    }

    @Test
    void insertCustomer() {

        //given
        Customer customer = new Customer(
                1, "shaikh", "shaikh@gmail.com", 20
        );

        //when
        underTest.insertCustomer(customer);

        //then
        verify(customerRepository).save(customer);

    }

    @Test
    void exitsPersonWithEmail() {

        //Given
        String email = "shaikhking@gmail.com";

        //When
        underTest.exitsPersonWithEmail(email);

        //then
        verify(customerRepository).existsCustomerByEmail(email);


    }

    @Test
    void exitsPersonWithId() {

        //given
        int id = 0;

        //when
        underTest.exitsPersonWithId(id);

        //then
        verify(customerRepository).existsCustomerById(id);


    }

    @Test
    void deleteCustomerById() {

        //given
        int id = 0;

        //when
        underTest.deleteCustomerById(id);

        //then
        verify(customerRepository).deleteById(id);

    }

    @Test
    void updateCustomer() {

        //GIven
        Customer customer = new Customer(
                1, "shaikh", "shaikh@gmail.com", 23
        );

        //when
        underTest.updateCustomer(customer);

        //then

        verify(customerRepository).save(customer);

    }
}