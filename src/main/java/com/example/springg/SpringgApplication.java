package com.example.springg;

import com.example.springg.customer.Customer;
import com.example.springg.customer.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.beans.BeanProperty;
import java.util.List;

@SpringBootApplication
public class SpringgApplication {

    public static void main(String[] args) {

        SpringApplication.run(SpringgApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(CustomerRepository customerRepository){
        return args -> {
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

            List<Customer> customers = List.of(umar, zeeshan);
            customerRepository.saveAll(customers);
        };
    }
}
