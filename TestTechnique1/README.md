# SkyPay – Simple Banking Service

This is a technical test implementation for a simple banking system, following Test-Driven Development (TDD) principles.

## ✅ Features

The application supports core banking operations:

- 💰 Deposit money
- 💸 Withdraw money
- 🧾 Print bank statement (with date, amount, and balance)

Example output:
```bash 
Date | Amount | Balance
14-01-2012 | -500 | 2500 
13-01-2012 | 2000 | 3000
10-01-2012 | 1000 | 1000
```



## 📦 Technologies

- Java 17
- Maven (build tool)
- JUnit 5 (unit testing)

## 🚀 How to Run

### ✅ Run the tests

```bash
mvn clean test
```

All business use cases are tested in the AccountTest.java class.

##🖥️ Run manually
You can run the Main.java class (if present) to manually test behavior from the command line.

#📁 Project Structure

```bash
src
├── main
│   └── java
│       └── com.banking
│           ├── Account.java              # Main implementation class
│           ├── AccountService.java       # Interface
│           ├── Transaction.java          # Represents a bank transaction
│           └── StatementPrinter.java     # Responsible for printing statements
└── test
    └── java
        └── com.banking
            └── AccountTest.java          # Main test class
            └── MockStatementPrinter.java # Prepared class for testing call printing statement class

```

#🎯 Design Principles
- Written using TDD: Test-first approach with clear acceptance criteria.

- Separation of concerns: printing logic is handled in a dedicated StatementPrinter.

- Dependency Injection: Account receives StatementPrinter via constructor to allow easier testing and decoupling.

- Exception handling and input validation can be extended as needed.

#✍️ Author
Abdessamad Badrane