Internal Transfers System

This is a Spring Boot application that handles money transfers between accounts. It provides REST APIs to create accounts, check balances, and process transactions.

Quick Start

You need Java 17 or higher and Maven. You can use the Maven wrapper that comes with the project.

To run the application:
./mvnw spring-boot:run

The app will start on http://localhost:8080. I am using H2 in-memory database, so there is no database setup needed. It just works out of the box.

What It Does

The app has three endpoints:

1. POST /accounts - Create a new account with an initial balance

Request body example:
account_id: 123
initial_balance: 100.23344

2. GET /accounts/account_id - Get the current balance of an account

Response example:
account_id: 123
balance: 100.23344

3. POST /transactions - Transfer money from one account to another

Request body example:
source_account_id: 123
destination_account_id: 456
amount: 100.12345

How It Works

I structured the code in layers. The controller handles HTTP requests and responses. The service layer has the business logic. The repositories talk to the database.

All transactions are wrapped in a transaction so if anything goes wrong, everything rolls back and you do not end up with inconsistent data.

The service validates inputs, checks if accounts exist, makes sure there is enough balance before transferring, and prevents things like transferring to the same account or creating duplicate accounts.

Example Usage

Here is a quick example of how to use it:

Create two accounts:
curl -X POST http://localhost:8080/accounts -H "Content-Type: application/json" -d '{"account_id": 123, "initial_balance": "100.00"}'
curl -X POST http://localhost:8080/accounts -H "Content-Type: application/json" -d '{"account_id": 456, "initial_balance": "50.00"}'

Check balances:
curl http://localhost:8080/accounts/123
curl http://localhost:8080/accounts/456

Transfer 25 from account 123 to 456:
curl -X POST http://localhost:8080/transactions -H "Content-Type: application/json" -d '{"source_account_id": 123, "destination_account_id": 456, "amount": "25.00"}'

Verify the transfer worked:
curl http://localhost:8080/accounts/123
curl http://localhost:8080/accounts/456

After the transfer, account 123 should show 75.00 and account 456 should show 75.00.




Use these settings:
- JDBC URL: jdbc:h2:mem:transfer_system
- Username: root
- Password: root
