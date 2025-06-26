package com.banking;

import java.util.List;

public class MockStatementPrinter extends StatementPrinter {
    private boolean printCalled = false;
    private List<Transaction> receivedTransactions = null;

    @Override
    public void print(List<Transaction> transactions) {
        this.printCalled = true;
        this.receivedTransactions = transactions;
    }

    public boolean isPrintCalled() {
        return printCalled;
    }

    public List<Transaction> getReceivedTransactions() {
        return receivedTransactions;
    }
}
