package com.banking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Account implements AccountService {

    private final List<Transaction> transactions = new ArrayList<>();
    private final StatementPrinter printer;
    private int balance = 0;

    public Account(StatementPrinter printer) {
        this.printer = printer;
    }

    @Override
    public void deposit(int amount, String date) {
        validateAmountPositive(amount, "Deposit");
        balance += amount;
        transactions.add(new Transaction(date, amount, balance));
    }

    @Override
    public void withdraw(int amount, String date) {
        validateAmountPositive(amount, "Withdrawal");

        if (amount > balance) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        balance -= amount;
        transactions.add(new Transaction(date, -amount, balance));
    }

    @Override
    public void printStatement() {
        // Send a copy of the transactions in reverse order to the printer
        List<Transaction> reversed = new ArrayList<>(transactions);
        Collections.reverse(reversed);
        printer.print(reversed);
    }

    private void validateAmountPositive(int amount, String operationType) {
        if (amount <= 0) {
            throw new IllegalArgumentException(operationType + " amount must be positive");
        }
    }
}
