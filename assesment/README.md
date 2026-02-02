Internal Transfers System

This is a Spring Boot application that handles money transfers between accounts. It provides REST APIs to create accounts, check balances, and process transactions.

Quick Start

You need Java 17 or higher and Maven. You can use the Maven wrapper that comes with the project.

To run the application:
./mvnw spring-boot:run

The app will start on http://localhost:8080. I'm using H2 in-memory database, so there's no database setup needed. It just works out of the box.

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

All transactions are wrapped in a transaction so if anything goes wrong, everything rolls back and you don't end up with inconsistent data.

The service validates inputs, checks if accounts exist, makes sure there's enough balance before transferring, and prevents things like transferring to the same account or creating duplicate accounts.


Assumptions

- All accounts use the same currency. No conversion logic needed.
- Using H2 in-memory database for simplicity
- No authentication required as per the requirements
- Balances can have up to 8 decimal places
- Account IDs need to be unique
- Cannot have negative balances
- Cannot transfer money to the same account

Building

To build a JAR file:
./mvnw clean package

Then run it:
java -jar target/assesment-0.0.1-SNAPSHOT.jar

H2 Console

If you want to look at the database while testing, H2 console is enabled at http://localhost:8080/h2-console

Use these settings:
- JDBC URL: jdbc:h2:mem:transfer_system
- Username: sa
- Password: leave empty
