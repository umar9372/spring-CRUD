package com.example.springg.customer;

import com.example.springg.exception.DuplicateResourceException;
import com.example.springg.exception.RequestValidationException;
import com.example.springg.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    private CustomerService underTest;
    @Mock
    private CustomerDao customerDao;

    @BeforeEach
    void setUp() {
        underTest = new CustomerService(customerDao);
    }


    @Test
    void getAllCustomers() {

        //WHen
        underTest.getAllCustomers();

        //then
        verify(customerDao).selectAllCustomers();
    }

    @Test
    void canGetCustomer() {

        int id = 10;
        Customer customer = new Customer(
                id, "umar", "umar@gmail.com", 38
        );

        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));
        //selectCustomerById is an optional

        //when
        Customer actual = underTest.getCustomer(id);

        //then
        assertThat(actual).isEqualTo(customer);

    }

    @Test
    void willThrowWhenGetCustomerReturnsEmptyOptional() {

        int id = 10;

        when(customerDao.selectCustomerById(id)).thenReturn(Optional.empty());
        //selectCustomerById is an optional

        //when
        //then
        assertThatThrownBy(() -> underTest.getCustomer(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("customer  with the id [%s] is not found"
                        .formatted(id));
    }

    @Test
    void addCustomer() {

        //GIven
        String email = "umra0@gmail.com";

        //we return false to add customer otherwise it throws exception if it is true
        when(customerDao.exitsPersonWithEmail(email)).thenReturn(false);

        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
                "umar", email,29
        );

        //when
        underTest.addCustomer(request);

        //then

        //we have to take the argument from CustomerRegistrationRequest
        // to Customer class and then put it to the insertCustomer metod
        //thats why we use ArgumentCaptor class

        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);

        verify(customerDao).insertCustomer(customerArgumentCaptor.capture());

        Customer capturedCustomer = customerArgumentCaptor.getValue();

        assertThat(capturedCustomer.getId()).isNull();
        assertThat(capturedCustomer.getName()).isEqualTo(request.name());
        assertThat(capturedCustomer.getEmail()).isEqualTo(request.email());
        assertThat(capturedCustomer.getAge()).isEqualTo(request.age());
    }

    @Test
    void willThrowWhenEmailExistsWhileAddingCustomer() {
        String email = "umra0@gmail.com";

        when(customerDao.exitsPersonWithEmail(email)).thenReturn(true);

        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
                "umar", email,29
        );

        assertThatThrownBy(() -> underTest.addCustomer(request))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("customer  with the email [%s] is already taken, please try different one"
                        .formatted(email));

        verify(customerDao, never()).insertCustomer(any());
    }

    @Test
    void deleteCustomerById() {

        //given
        int id = 1;

        when(customerDao.exitsPersonWithId(id)).thenReturn(true);

        //when
        underTest.deleteCustomerById(id);

        //then
        verify(customerDao).deleteCustomerById(id);

    }

    @Test
    void wilLThrowWhenDeleteCustomerByIdNotExists() {
        int id = 1;

        when(customerDao.exitsPersonWithId(id)).thenReturn(false);

        assertThatThrownBy(() -> underTest.deleteCustomerById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("customer with id [%s] not found".formatted(id));

        verify(customerDao,never()).deleteCustomerById(any());
    }

    @Test
    void canUpdateAllCustomersProperties() {

        //Given
        int id = 10;

        Customer customer = new Customer(
                id, "umar", "umar@gmail.com", 38
        );

        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));


        //we providing all value to update it means all if statements execute
        String newEmail = "umarshaikh@gmail.com";

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(
                "shaikhBro", newEmail, 23
        );

        when(customerDao.exitsPersonWithEmail(newEmail)).thenReturn(false);

        //When
        underTest.updateCustomer(id, updateRequest);


        //Then
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);

        verify(customerDao).updateCustomer(customerArgumentCaptor.capture());
        Customer capturedValue = customerArgumentCaptor.getValue();

        assertThat(capturedValue.getName()).isEqualTo(updateRequest.name());
        assertThat(capturedValue.getEmail()).isEqualTo(updateRequest.email());
        assertThat(capturedValue.getAge()).isEqualTo(updateRequest.age());

    }

    @Test
    void canUpdateCustomerName() {

        //Given
        int id = 10;

        Customer customer = new Customer(
                id, "umar", "umar@gmail.com", 38
        );

        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));


        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(
                "shaikhBro", null, null
        );

        //When
        underTest.updateCustomer(id, updateRequest);


        //Then
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);

        verify(customerDao).updateCustomer(customerArgumentCaptor.capture());
        Customer capturedValue = customerArgumentCaptor.getValue();

        assertThat(capturedValue.getName()).isEqualTo(updateRequest.name());
        assertThat(capturedValue.getEmail()).isEqualTo(customer.getEmail());
        assertThat(capturedValue.getAge()).isEqualTo(customer.getAge());

    }

    @Test
    void canUpdateCustomerEmail() {

        //Given
        int id = 10;

        Customer customer = new Customer(
                id, "umar", "umar@gmail.com", 38
        );

        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));

        String newEmail = "umarshaikh@gmail.com";

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(
                null, newEmail, null
        );

        when(customerDao.exitsPersonWithEmail(newEmail)).thenReturn(false);

        //When
        underTest.updateCustomer(id, updateRequest);


        //Then
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);

        verify(customerDao).updateCustomer(customerArgumentCaptor.capture());
        Customer capturedValue = customerArgumentCaptor.getValue();

        assertThat(capturedValue.getName()).isEqualTo(customer.getName());
        assertThat(capturedValue.getEmail()).isEqualTo(updateRequest.email());
        assertThat(capturedValue.getAge()).isEqualTo(customer.getAge());

    }

    @Test
    void canUpdateCustomerAge() {

        //Given
        int id = 10;

        Customer customer = new Customer(
                id, "umar", "umar@gmail.com", 38
        );

        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));


        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(
                null, null, 23
        );


        //When
        underTest.updateCustomer(id, updateRequest);


        //Then
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);

        verify(customerDao).updateCustomer(customerArgumentCaptor.capture());
        Customer capturedValue = customerArgumentCaptor.getValue();

        assertThat(capturedValue.getName()).isEqualTo(customer.getName());
        assertThat(capturedValue.getEmail()).isEqualTo(customer.getEmail());
        assertThat(capturedValue.getAge()).isEqualTo(updateRequest.age());

    }

    @Test
    void willThrowWhenTryingToUpdateCustomerEmailWhenAlreadyTaken() {

        //Given
        int id = 10;

        Customer customer = new Customer(
                id, "umar", "umar@gmail.com", 38
        );

        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));

        String newEmail = "umarshaikh@gmail.com";

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(
                null, newEmail, null
        );

        when(customerDao.exitsPersonWithEmail(newEmail)).thenReturn(true);

        //When

        //Then
        assertThatThrownBy(() -> underTest.updateCustomer(id, updateRequest))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("email already taken");

        verify(customerDao, never()).updateCustomer(any());

    }

    @Test
    void willThrowWhenCustomerUdpateHasNoChanges() {

        //Given
        int id = 10;

        Customer customer = new Customer(
                id, "umar", "umar@gmail.com", 38
        );

        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));


        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(
                customer.getName(), customer.getEmail(), customer.getAge()
        );

        //when & then
        assertThatThrownBy(() -> underTest.updateCustomer(id, updateRequest))
                .isInstanceOf(RequestValidationException.class)
                .hasMessage("no data changes found");

        verify(customerDao, never()).updateCustomer(any());

    }
}