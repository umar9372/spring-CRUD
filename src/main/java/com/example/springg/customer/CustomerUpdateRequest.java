package com.example.springg.customer;


public record CustomerUpdateRequest(
        String name,
        String email,
        Integer age
) {
}
