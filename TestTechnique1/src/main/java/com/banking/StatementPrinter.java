package com.banking;

import java.util.List;

public class StatementPrinter {

    private static final String HEADER = "Date       | Amount | Balance";

    public void print(List<Transaction> transactions) {
        System.out.println(HEADER);
        for (Transaction tx : transactions) {
            System.out.printf("%s | %s   | %d%n", tx.getDate(), formatAmount(tx.getAmount()), tx.getBalance());
        }
    }

    private String formatAmount(int amount) {
        // Aligne les montants négatifs comme les positifs
        return String.format("%d", amount);
    }
}
