# Banking Management System

A console-based Banking Management System developed using **Core Java, Maven, OOP, DAO, Service Layer, Exception Handling, and Docker**.

The project demonstrates a layered backend architecture and provides basic banking operations such as customer creation, account management, deposits, withdrawals, transfers, balance checking, and transaction history.

##  Features

* Create Customer
* Create Bank Account
* Deposit Money
* Withdraw Money
* Transfer Money
* Check Account Balance
* View Transaction History
* View Customers
* View Accounts
* Close Account
* Custom Exception Handling
* Account Status Management
* Transaction Management
* Dockerized Java Application

## 🏗️ Project Architecture

```text
com.bank
│
├── model
│   ├── Customer.java
│   ├── Account.java
│   └── Transaction.java
│
├── constants
│   ├── AccountType.java
│   ├── AccountStatus.java
│   └── TransactionType.java
│
├── exception
│   ├── CustomerNotFoundException.java
│   ├── AccountNotFoundException.java
│   ├── InsufficientBalanceException.java
│   ├── InvalidAmountException.java
│   └── AccountBlockedException.java
│
├── dao
│   ├── CustomerDao.java
│   ├── AccountDao.java
│   └── TransactionDao.java
│
├── dao.impl
│   ├── CustomerDaoImpl.java
│   ├── AccountDaoImpl.java
│   └── TransactionDaoImpl.java
│
├── service
│   ├── CustomerService.java
│   ├── AccountService.java
│   └── TransactionService.java
│
├── service.impl
│   ├── CustomerServiceImpl.java
│   ├── AccountServiceImpl.java
│   └── TransactionServiceImpl.java
│
└── Main.java
```

## 🛠️ Technologies

* Java 17
* Maven
* OOP
* DAO Design Pattern
* Service Layer Architecture
* Exception Handling
* BigDecimal for monetary calculations
* Docker
* Docker Hub

## 💰 Banking Operations

### Create Customer

Creates a new customer in the banking system.

### Create Account

Creates an account for an existing customer.

Supported account types can include:

* Savings
* Current

### Deposit

Adds money to an active account.

### Withdraw

Withdraws money after validating:

* Account existence
* Account status
* Valid amount
* Available balance

### Transfer

Transfers money between two accounts.

The operation creates corresponding debit and credit transaction records.

### Check Balance

Displays the current account balance.

### Transaction History

Displays transactions associated with an account.

### Close Account

Changes the account status so that further banking operations are prevented.

## 🧱 Layered Architecture

```text
User
 │
 ▼
Main / Console
 │
 ▼
Service Layer
 │
 ▼
DAO Layer
 │
 ▼
In-Memory Data
```

### Service Layer

Contains business logic and validation.

### DAO Layer

Handles data access operations.

### Model Layer

Contains domain objects such as Customer, Account, and Transaction.

### Exception Layer

Contains custom exceptions for business validation failures.

## ▶️ Run Locally

Make sure Java 17 and Maven are installed.

Build the project:

```powershell
mvn clean package
```

Run the application:

```powershell
java -cp target/banking-management-system-1.0-SNAPSHOT.jar com.bank.Main
```

## 🐳 Run Using Docker

Build the Docker image:

```powershell
mvn clean package
docker build -t banking-management-system:1.0 .
```

Run the application:

```powershell
docker run -it --name banking-app banking-management-system:1.0
```

## 🐳 Docker Hub

Docker image:

```text
fegadesachin/banking-management-system:1.0
```

Pull the image:

```powershell
docker pull fegadesachin/banking-management-system:1.0
```

Run directly from Docker Hub:

```powershell
docker run -it --name banking-app fegadesachin/banking-management-system:1.0
```

## 📋 Application Menu

```text
1. Create Customer
2. Create Account
3. Deposit
4. Withdraw
5. Transfer
6. Check Balance
7. Transaction History
8. View Customers
9. View Accounts
10. Close Account
11. Exit
```

## ⚠️ Current Limitation

The current version uses **in-memory storage** through Java collections.

Therefore, data is lost when the application or Docker container is stopped.

## 🔮 Future Improvements

Planned improvements include:

* JDBC integration
* Oracle Database integration
* Database transactions and rollback
* Spring Boot REST APIs
* Spring Data JPA / Hibernate
* JWT Authentication
* Global Exception Handling
* API validation
* Swagger/OpenAPI documentation
* Unit testing with JUnit and Mockito
* Docker Compose
* CI/CD using GitHub Actions
* Microservices architecture

## 👨‍💻 Author

**Sachin Fegade**

Java Backend Developer

## 📌 Project Purpose

This project was developed to demonstrate practical backend development concepts including **Java, OOP, layered architecture, DAO, service-layer business logic, exception handling, Maven, and Docker**.
