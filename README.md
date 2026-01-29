# Banking App

## About

This project is a command-line Java application that simulates a basic personal banking system. It allows users to log in, manage savings and investment accounts, perform transactions, and interact with a simple investment fund system. The application is designed to be run in a terminal and demonstrates core Java concepts like object-oriented programming, data structures, and exception handling.

## Features

- **User Authentication**: A simple login system with a predefined set of users.
- **Account Management**:
  - View detailed balances for savings and investment accounts.
  - See a breakdown of invested funds and their current value after simulated growth.
  - Deposit money from a user's "cash on hand" into their savings account.
  - Withdraw money from a savings account back to cash.
- **Transactions**:
  - Send money from one user's savings account to another's.
  - Transfer funds between a user's own savings and investment accounts.
- **Investment System**:
  - Invest available funds into `LOW_RISK`, `MEDIUM_RISK`, or `HIGH_RISK` funds.
  - Simulate investment growth based on each fund's specific rate.
  - Liquidate all investments, moving their value back into the main investment account balance.

## How to Run

1.  **Navigate to the Source Directory**:
    Open your terminal and change the directory to where the `.java` files are located.

2.  **Compile the Code**:
    Run the Java compiler to compile all source files.
    ```bash
    javac *.java
    ```

3.  **Run the Application**:
    Execute the main class to start the program.
    ```bash
    java BankingApp
    ```

4.  **Follow On-Screen Prompts**:
    The application will prompt you to log in with a user's name (e.g., `Alice`, `Bob`) and then present a menu of options.

## Project Structure

- `BankingApp.java`: The main entry point for the application.
- `BankingService.java`: Contains the core application logic, including the user menu, session handling, and orchestrating all banking operations.
- `User.java`: Represents a user, holding their cash balance, savings account, and investment account.
- `Account.java`: An abstract base class defining common properties and behaviors for different account types.
- `SavingsAccount.java`: A concrete account type that accrues interest on its balance.
- `InvestmentAccount.java`: A specialized account for managing a portfolio of different investment `Fund`s as well as a general non-invested balance.
- `Fund.java`: An enum representing the available investment funds (`LOW_RISK`, `MEDIUM_RISK`, `HIGH_RISK`) and their respective growth rates.
- `InvalidAmountException.java`: A custom exception used for handling invalid transaction amounts (e.g., negative values, insufficient funds).

## Contributor:
**Aleksei Vassimov** 