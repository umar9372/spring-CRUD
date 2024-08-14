package com.example.springg.customer;

import com.example.springg.AbstractTestContainers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


class CustomerJDBCDataAccessServiceTest extends AbstractTestContainers {

    private CustomerJDBCDataAccessService underTest;
    private final CustomerRowMapper customerRowMapper = new CustomerRowMapper();


    @BeforeEach
    void setUp() {

        //Instanciate the CustomerJDBCDataAccessService
        underTest = new CustomerJDBCDataAccessService(getJdbcTemplate(),
                customerRowMapper);

    }

    @Test
    void selectAllCustomers() {
        // Given
        String name = FAKER.name().fullName();
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();

        Customer customer = new Customer(name,
                email,
                20);
        underTest.insertCustomer(customer);

        // When

        List<Customer> actual = underTest.selectAllCustomers();

        //then
        assertThat(actual).isNotEmpty();
    }


    @Test
    void selectCustomerById() {

       //Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        String name = FAKER.name().fullName();
        Customer customer = new Customer(name, email, 20);


        underTest.insertCustomer(customer);

        //we need ID for that we can filter the customer by email and get the id

        Integer id = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        //When
        Optional<Customer> actual = underTest.selectCustomerById(id);

        //THen
        assertThat(actual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getName()).isEqualTo(customer.getName());
            assertThat(c.getEmail()).isEqualTo(customer.getEmail());
            assertThat(c.getAge()).isEqualTo(customer.getAge());

        });
    }


    @Test
    void willReturnEmptySelectCustomerById() {

        //given
        Integer id = 0;

        //When
        var actual = underTest.selectCustomerById(id);

        //then
        assertThat(actual).isEmpty();

    }


    @Test
    void exitsCustomerWithEmail() {
        //Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        String name = FAKER.name().fullName();

        Customer customer = new Customer(name, email, 20);

        underTest.insertCustomer(customer);

        //When
        boolean actual = underTest.exitsPersonWithEmail(email);

        //Then
        assertThat(actual).isTrue();
    }

    @Test
    void existCustomerWithEmailReturnsFalseWhenDOesNotExist() {
        //Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();

        //When
        boolean actual = underTest.exitsPersonWithEmail(email);

        //Then
        assertThat(actual).isFalse();

    }

    @Test
    void exitsCustomerWithId() {

        //GIven
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        String name = FAKER.name().fullName();

        Customer customer = new Customer(name, email, 39);

        underTest.insertCustomer(customer);

        Integer id = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        //When
        boolean actual = underTest.exitsPersonWithId(id);

        //Then

        assertThat(actual).isTrue();


    }

    @Test
    void existsPersonWithIdWillReturnsFalseWhenDoesNotPresent() {

        //Given
        Integer id = -1;

        //When
        boolean actual = underTest.exitsPersonWithId(id);

        //Then

        assertThat(actual).isFalse();

    }

    @Test
    void deleteCustomerById() {

        //GIven
        String name = FAKER.name().fullName();
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();

        Customer customer = new Customer(
                name, email, 30
        );

        underTest.insertCustomer(customer);

        Integer id = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        //when

        underTest.deleteCustomerById(id);   //delete the pass id

        //when we select the passed id which is actually deleted
        //it shows no id present

        Optional<Customer> actual = underTest.selectCustomerById(id);

        //then
        assertThat(actual).isNotPresent();


    }

    @Test
    void updateCustomerName() {

        //given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        String name = FAKER.name().fullName();

        Customer customer = new Customer(
                name, email, 56
        );

        underTest.insertCustomer(customer);

        Integer id = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        var newName = "foo";

        //when
        Customer update = new Customer();
        update.setId(id);
        update.setName(newName);

        underTest.updateCustomer(update);

        //then

        Optional<Customer> actual = underTest.selectCustomerById(id);

        assertThat(actual).isPresent().hasValueSatisfying(
                c -> {
                    assertThat(c.getId()).isEqualTo(id);
                    assertThat(c.getName()).isEqualTo(newName);  //changed
                    assertThat(c.getEmail()).isEqualTo(customer.getEmail());
                    assertThat(c.getAge()).isEqualTo(customer.getAge());
                });

    }

    @Test
    void updateCustomerEmail() {
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        String name = FAKER.name().fullName();

        Customer customer = new Customer(
                name, email, 45
        );

        underTest.insertCustomer(customer);

        Integer id = underTest.selectAllCustomers()
                .stream()
                .filter(customer1 -> customer1.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        //new email
        var newEmail = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();

        Customer update = new Customer();
        update.setId(id);
        update.setEmail(newEmail);

        underTest.updateCustomer(update); //now we update the customer with new email

        Optional<Customer> actual = underTest.selectCustomerById(id);

        assertThat(actual).isPresent().hasValueSatisfying(
                c -> {
                    assertThat(c.getId()).isEqualTo(id);
                    assertThat(c.getName()).isEqualTo(customer.getName());
                    assertThat(c.getEmail()).isEqualTo(newEmail);
                    assertThat(c.getAge()).isEqualTo(customer.getAge());
                });
    }


    @Test
    void updateCustomerAge() {

        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        String name = FAKER.name().fullName();

        Customer customer = new Customer(
                name, email, 56
        );

        underTest.insertCustomer(customer);

        Integer id = underTest.selectAllCustomers()
                .stream()
                .filter(customer1 -> customer1.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();


        var newAge = 23;

        Customer update = new Customer();

        update.setId(id);
        update.setAge(newAge);

        underTest.updateCustomer(update);

        Optional<Customer> actual = underTest.selectCustomerById(id);

        assertThat(actual).isPresent().hasValueSatisfying(
                c -> {
                    assertThat(c.getId()).isEqualTo(id);
                    assertThat(c.getName()).isEqualTo(customer.getName());
                    assertThat(c.getEmail()).isEqualTo(customer.getEmail());
                    assertThat(c.getAge()).isEqualTo(newAge);

                });
    }

    @Test
    void willUpdateAllPropertiesOfCustomer() {

        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        String name = FAKER.name().fullName();

        Customer customer = new Customer(
                name, email, 78
        );

        underTest.insertCustomer(customer);

        Integer id = underTest.selectAllCustomers()
                .stream()
                .filter(customer1 -> customer1.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        Customer update = new Customer();
        update.setId(id);
        update.setName("bar");
        update.setEmail(UUID.randomUUID().toString());
        update.setAge(60);

        underTest.updateCustomer(update);

        Optional<Customer> actual = underTest.selectCustomerById(id);

        assertThat(actual).isPresent().hasValue(update);
    }

    @Test
    void willNotUpdateWhenNothingToUpdate() {

        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        String name = FAKER.name().fullName();

        Customer customer = new Customer(
                name, email, 20

        );

        underTest.insertCustomer(customer);

        Integer id = underTest.selectAllCustomers()
                .stream()
                .filter(customer1 -> customer1.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        //update when no changes
        Customer update = new Customer();
        update.setId(id);

        underTest.updateCustomer(update);

        Optional<Customer> actual = underTest.selectCustomerById(id);

        assertThat(actual).isPresent().hasValueSatisfying(
                c -> {
                    assertThat(c.getId()).isEqualTo(id);
                    assertThat(c.getName()).isEqualTo(customer.getName());
                    assertThat(c.getEmail()).isEqualTo(customer.getEmail());
                    assertThat(c.getAge()).isEqualTo(customer.getAge());
                }
        );
    }
}



























