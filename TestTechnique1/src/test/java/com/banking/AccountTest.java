package com.banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountTest {

    private AccountService account;

    @BeforeEach
    public void setUp() {
        account = new Account();
    }

    @Test
    public void depositThenPrintStatement() {
        account.deposit(1000, "10-01-2012");

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        account.printStatement();
        String ln = System.lineSeparator();

        String expected =
                "Date       | Amount | Balance" +ln+
                        "10-01-2012 | 1000   | 1000";
        String actual = output.toString().trim();

        assertEquals(expected.trim(), actual);
    }
}
