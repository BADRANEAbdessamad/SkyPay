package com.banking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Account implements AccountService {
    private final List<Transaction> transactions = new ArrayList<>();
    private int balance = 0;

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
        balance -= amount;
        transactions.add(new Transaction(date, -amount, balance));    }

    @Override
    public void printStatement() {
        System.out.println("Date       | Amount | Balance");
        transactions.stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        list -> {
                            Collections.reverse(list);
                            return list.stream();
                        }))
                .forEach(tx -> System.out.printf("%s | %d   | %d%n", tx.getDate(), tx.getAmount(), tx.getBalance()));


    }
}

