
# Microservices Project

This project consists of two microservices: `account-service` and `customer-service`. These microservices are designed to manage accounts and customer information respectively.

## Microservices Overview

### Account Service
The `account-service` is responsible for managing account-related operations. This includes creating, updating, retrieving, and deleting accounts. It also handles transactions associated with these accounts.

### Customer Service
The `customer-service` is responsible for managing customer-related information. This includes creating, updating, retrieving, and deleting customer data.

## Minimum Requirements

- Docker: Ensure that Docker and Docker Compose are installed on your system.
- Java 17: Both microservices are built using Java 17.
- Maven: Ensure that Maven is installed to build the project.

## Getting Started

Follow the steps below to get the microservices up and running.

### Step 1: Clone the Repository

Clone the repository to your local machine:

```sh
git clone https://github.com/yourusername/microservices-project.git
cd microservices-project
```

### Step 2: Build the Project

Navigate to each microservice directory and build the project using Maven:

For `account-service`:
```sh
cd account-service
mvn clean package
```

For `customer-service`:
```sh
cd customer-service
mvn clean package
```

### Step 3: Create Docker Images

Build the Docker images for each microservice using Docker Compose:

```sh
docker-compose build
```

### Step 4: Start the Services

Start the services using Docker Compose:

```sh
docker-compose up
```

This command will start both the `account-service` and `customer-service` along with any dependencies they might have.

### Step 5: Verify the Services

Ensure that the services are running by accessing the following URLs in your browser or using `curl`:

- Account Service: `http://localhost:8081`
- Customer Service: `http://localhost:8080`

You can also check the logs to ensure there are no errors:

```sh
docker-compose logs
```

### Step 6: Test the Endpoints

Use Postman, curl, or any other HTTP client to test the endpoints exposed by each microservice.

For example, to get all accounts:

```sh
curl http://localhost:8081/cuentas
```

To get a customer by ID:

```sh
curl http://localhost:8080/clientes/1
```

## Environment Variables

The following environment variables are used in the `docker-compose.yml` file:

- `SPRING_DATASOURCE_PASSWORD`: The password for the datasource.
- `CUSTOMER_SERVICE_URL`: The URL of the customer service.

These variables can be configured in the `docker-compose.yml` file or passed at runtime.

## Directory Structure

The project has the following structure:

```
.
├── account-service
│   ├── src
│   │   └── main
│   │       └── java
│   │           └── com.transactions.devsu
│   │               ├── controller
│   │               ├── dto
│   │               ├── entities
│   │               ├── exceptions
│   │               ├── repository
│   │               └── services
│   ├── Dockerfile
│   ├── pom.xml
│   └── application.properties
└── customer-service
    ├── src
    │   └── main
    │       └── java
    │           └── com.clients.devsu
    │               ├── controller
    │               ├── dto
    │               ├── entities
    │               ├── exceptions
    │               ├── repository
    │               └── services
    ├── Dockerfile
    ├── pom.xml
    └── application.properties
└── docker-compose.yml
```

## Conclusion

You now have both microservices running in Docker containers, communicating with each other. Follow the steps outlined above to build, deploy, and test the services. Feel free to modify and extend the functionality as needed. If you encounter any issues, check the logs for troubleshooting or reach out for support.
