# Spring-CRUD

# Customer Management API

A Spring Boot application for managing customers.

## Description

This is a simple Spring Boot application that provides RESTful APIs for managing customer information. The application supports creating, updating, retrieving, and deleting customers.

## Features

- Create a new customer
- Retrieve customer details by ID
- Update customer information
- Delete a customer
- List all customers

## Prerequisites

- Java 11 or higher
- Maven 3.6.0 or higher

## Installation

1. Clone the repository:

    ```sh
    git clone https://github.com/your-username/customer-management-api.git
    ```

2. Navigate to the project directory:

    ```sh
    cd customer-management-api
    ```

3. Build the project using Maven:

    ```sh
    mvn clean install
    ```

## Usage

1. Run the application:

    ```sh
    mvn spring-boot:run
    ```

2. The application will start and be accessible at `http://localhost:8080`.

## API Endpoints

### Create a new customer

- **URL:** `/customer`
- **Method:** `POST`
- **Request Body:**

    ```json
    {
        "name": "John Doe",
        "email": "john.doe@example.com"
    }
    ```

### Retrieve customer details by ID

- **URL:** `/customer/{id}`
- **Method:** `GET`

### Update customer information

- **URL:** `/customer/update/{id}`
- **Method:** `PUT`
- **Request Body:**

    ```json
    {
        "name": "Jane Doe",
        "email": "jane.doe@example.com"
    }
    ```

### Delete a customer

- **URL:** `/customer/{id}`
- **Method:** `DELETE`

### List all customers

- **URL:** `/customers`
- **Method:** `GET`

## Contributing

Contributions are welcome! Please open an issue or submit a pull request for any changes or improvements.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
