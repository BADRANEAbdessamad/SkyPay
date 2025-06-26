package com.banking;

import com.banking.Account;
import com.banking.AccountService;
import com.banking.StatementPrinter;

public class Main {
    public static void main(String[] args) {
        AccountService account = new Account(new StatementPrinter());
        account.deposit(1000, "10-01-2012");
        account.deposit(2000, "13-01-2012");
        account.withdraw(500, "14-01-2012");
        account.printStatement();
    }
}
