package com.example.springg.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

class CustomerRowMapperTest {


    @Test
    void mapRow() throws SQLException {

        //Given
        CustomerRowMapper customerRowMapper = new CustomerRowMapper();

        ResultSet resultSet = mock(ResultSet.class);

        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getInt("age")).thenReturn(29);
        when(resultSet.getString("name")).thenReturn("shaikh");
        when(resultSet.getString("email")).thenReturn("shaikh@gmail.com");

        //when
        Customer actual = customerRowMapper.mapRow(resultSet, 1);

        //then
        Customer expected = new Customer(
                1, "shaikh", "shaikh@gmail.com", 29
        );
        assertThat(actual).isEqualTo(expected);
    }
}