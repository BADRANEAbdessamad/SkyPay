package com.skypay.hotel.model;

import java.util.Date;
import java.util.Objects;

public class User {
    private final int id;
    private int balance;
    private final Date createdAt;

    public User(int id, int balance) {
        if (balance < 0) throw new IllegalArgumentException("Balance cannot be negative");
        this.id = id;
        this.balance = balance;
        this.createdAt = new Date();
    }

    public int getId() {
        return id;
    }

    public int getBalance() {
        return balance;
    }

    public void debit(int amount) {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");
        if (amount > balance) throw new IllegalArgumentException("Insufficient balance");
        balance -= amount;
    }

    public void credit(int amount) {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");
        balance += amount;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
