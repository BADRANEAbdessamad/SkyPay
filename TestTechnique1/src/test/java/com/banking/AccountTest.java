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

    }

    @Test
    public void depositThenPrintStatement() {
        account.deposit(1000, "10-01-2012");

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        account.printStatement();

        String expected =
                "Date       | Amount | Balance\n" +
                        "10-01-2012 | 1000   | 1000";

        assertEquals(expected.trim(), output.toString().trim());
    }
}
