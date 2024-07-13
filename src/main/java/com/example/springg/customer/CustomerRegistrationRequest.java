package com.example.springg.customer;

import jakarta.persistence.Id;

public record CustomerRegistrationRequest(
        String name,
        String email,
        Integer age
) {
}
