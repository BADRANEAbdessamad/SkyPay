package com.banking;

import java.util.List;

public class StatementPrinter {

    private static final String HEADER = "Date       | Amount | Balance";

    public void print(List<Transaction> transactions) {
        System.out.println(HEADER);

        transactions.stream()
                .sorted((t1, t2) -> t2.getDate().compareTo(t1.getDate())) // ordre inverse
                .forEach(tx ->
                        System.out.printf("%s | %d   | %d%n", tx.getDate(), tx.getAmount(), tx.getBalance()));
    }
}
