package com.example.springg;

import com.example.springg.customer.Customer;
import com.example.springg.customer.CustomerRepository;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Random;

@SpringBootApplication
public class SpringgApplication {

    public static void main(String[] args) {

        SpringApplication.run(SpringgApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(CustomerRepository customerRepository){
        return args -> {

            var faker = new Faker();
            Random random = new Random();
            Name name = faker.name();  //to instanciate the name

            String firstName = name.firstName(); //just to ge the same name to get same email
            String lastName = name.lastName();


            Customer customer = new Customer(
                    firstName+ " " + lastName,
                    firstName.toLowerCase() + "."
                            + lastName.toLowerCase()
                            + "@amigoscode.com",
                    random.nextInt(16, 99) //to get the age randomly
            );



            // Save the customer to the database
            customerRepository.save(customer);
        };
    }
}
