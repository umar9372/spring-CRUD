# Spring Master Class - Unit and Integration Testing

Welcome to my repository for the Spring Master Class! This project contains my progress and work related to mastering Spring Framework concepts, with a focus on unit and integration testing using JUnit 5 and Mockito.

## Table of Contents

- [Introduction](#introduction)
- [Technologies Used](#technologies-used)
- [Project Structure](#project-structure)
- [Testing Overview](#testing-overview)
  - [Unit Testing with JUnit 5](#unit-testing-with-junit-5)
  - [Mocking with Mockito](#mocking-with-mockito)
  - [Integration Testing](#integration-testing)
- [How to Run the Tests](#how-to-run-the-tests)
- [References](#references)

## Introduction

This repository is part of my journey to mastering Spring Framework. The focus here is on writing effective and maintainable unit and integration tests, ensuring that the code is robust, reliable, and well-tested.

## Technologies Used

- **Java 17**
- **Spring Boot**
- **JUnit 5**
- **Mockito**
- **Maven**
- **H2 Database (for testing purposes)**
- **Flyway (for database migrations)**

## Project Structure

src/
├── main/
│ ├── java/
│ │ └── com/
│ │ └── example/
│ │ └── springmasterclass/
│ └── resources/
├── test/
│ ├── java/
│ │ └── com/
│ │ └── example/
│ │ └── springmasterclass/
│ └── resources/
└── pom.xml



## Testing Overview

### Unit Testing with JUnit 5

JUnit 5 is used extensively in this project to write unit tests. These tests focus on individual components (e.g., services, controllers) to ensure they function as expected in isolation.

**Examples:**
- Testing service methods for business logic.
- Validating controller behavior with mock HTTP requests.

### Mocking with Mockito

Mockito is utilized to mock dependencies in unit tests. This ensures that we can test components in isolation without relying on their dependencies (like databases or external services).

**Examples:**
- Mocking repositories in service tests.
- Capturing arguments passed to methods for verification.

### Integration Testing

Integration tests are also a significant focus of this project. These tests verify that different parts of the application work together as expected. The H2 in-memory database is used for testing data access layers without affecting the real database.

**Examples:**
- Testing REST endpoints with full context loading.
- Verifying database queries and persistence.

## How to Run the Tests

To run the tests in this project, you can use the following Maven command:

```bash
mvn test
