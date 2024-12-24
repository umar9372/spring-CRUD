package com.example.springg.customer;

public record CustomerRegistrationRequest(
        String name,
        String email,
        Integer age
) {
}
