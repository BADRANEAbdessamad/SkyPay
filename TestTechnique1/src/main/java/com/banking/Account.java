package com.banking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Account implements AccountService {
    private final List<Transaction> transactions = new ArrayList<>();
    private int balance = 0;
    private final StatementPrinter statementPrinter;

    public Account(StatementPrinter statementPrinter) {
        this.statementPrinter = statementPrinter;
    }

    @Override
    public void deposit(int amount, String date) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }

        balance += amount;
        transactions.add(new Transaction(date, amount, balance));
    }

    @Override
    public void withdraw(int amount, String date) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        if (amount > balance) {
            throw new IllegalArgumentException("Insufficient balance");
        }
        balance -= amount;
        transactions.add(new Transaction(date, -amount, balance));    }

    @Override
    public void printStatement() {
        statementPrinter.print(transactions);
    }
}

