# Product Catalogue Service

A Spring Boot application for managing product catalogues.

## Project Structure

This project is organized as a multi-module Gradle project:

- **product-catalogue** (root): The main Spring Boot application
- **product-catalogue-dto**: A separate module containing DTOs for the API

## Features

- RESTful API for product management
- CRUD operations for products
- H2 in-memory database for development
- DTO pattern for API contracts

## Getting Started

### Prerequisites

- Java 17 or higher
- Gradle 7.6 or higher

### Building the Project

```bash
./gradlew build
```

### Running the Application

```bash
./gradlew bootRun
```

The application will start on port 8080 by default.

## API Endpoints

| Method | URL                   | Description                |
|--------|------------------------|----------------------------|
| GET    | /api/products          | Get all products           |
| GET    | /api/products/{id}     | Get product by ID          |
| POST   | /api/products          | Create a new product       |
| PUT    | /api/products/{id}     | Update an existing product |
| DELETE | /api/products/{id}     | Delete a product           |

## DTO Module

The `product-catalogue-dto` module contains all the DTOs used by the API. This module can be published separately and used by other services that need to interact with the Product Catalogue Service.

For more information, see the [DTO Module README](product-catalogue-dto/README.md).

## Development

### Adding a New DTO

1. Create a new DTO class in the `product-catalogue-dto` module
2. Update the mapper interface if needed
3. Implement the mapper in the main application
4. Update the service and controller layers to use the new DTO

### Publishing the DTO Module

```bash
./gradlew :product-catalogue-dto:publish
``` 