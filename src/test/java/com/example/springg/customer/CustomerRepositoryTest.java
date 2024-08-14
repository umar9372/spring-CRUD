package com.example.springg.customer;

import com.example.springg.AbstractTestContainers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContext;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomerRepositoryTest extends AbstractTestContainers {

    @Autowired
    private CustomerRepository underTest;

    @Autowired
    private ApplicationContext applicationContext;

    @BeforeEach
    void setUp() {
        System.out.println(applicationContext.getBeanDefinitionCount());

    }

    @Test
    void existsCustomerByEmail() {

        //Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        String name = FAKER.name().fullName();
        Customer customer = new Customer(name, email, 20);


        underTest.save(customer);


        //When
        var actual = underTest.existsCustomerByEmail(email);

        //THen
        assertThat(actual).isTrue();

        }

    @Test
    void existsCustomerByEmailWhenNotPresent() {

        //Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();

        //When
        var actual = underTest.existsCustomerByEmail(email);

        //THen
        assertThat(actual).isFalse();

    }


    @Test
    void existsCustomerById() {
//Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        String name = FAKER.name().fullName();
        Customer customer = new Customer(name, email, 20);


        underTest.save(customer);

        //we need ID for that we can filter the customer by email and get the id

        Integer id = underTest.findAll()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        //When
        var actual = underTest.existsCustomerById(id);

        //THen
        assertThat(actual).isTrue();

    }


    @Test
    void existsCustomerByIdWhenNotPresent() {

        //we need ID for that we can filter the customer by email and get the id

        Integer id = -1;

        //When
        var actual = underTest.existsCustomerById(id);

        //THen
        assertThat(actual).isFalse();

    }


}