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
    @Test
    public void depositAndWithdrawThenPrintStatement() {
        account.deposit(1000, "10-01-2012");
        account.withdraw(200, "11-01-2012");

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        account.printStatement();

        String actual = output.toString().trim().replace("\r\n", "\n");
        String expected =
                "Date       | Amount | Balance\n" +
                        "11-01-2012 | -200   | 800\n" +
                        "10-01-2012 | 1000   | 1000";

        assertEquals(expected, actual);
    }
    @Test
    public void cannotWithdrawNegativeAmount() {
        IllegalArgumentException exception =
                org.junit.jupiter.api.Assertions.assertThrows(
                        IllegalArgumentException.class,
                        () -> account.withdraw(-500, "12-01-2012")
                );

        assertEquals("Withdrawal amount must be positive", exception.getMessage());
    }
    @Test
    public void cannotDepositNegativeAmount() {
        IllegalArgumentException exception =
                org.junit.jupiter.api.Assertions.assertThrows(
                        IllegalArgumentException.class,
                        () -> account.deposit(-1000, "13-01-2012")
                );

        assertEquals("Deposit amount must be positive", exception.getMessage());
    }
    @Test
    public void cannotWithdrawMoreThanBalance() {
        account.deposit(500, "14-01-2012");

        IllegalArgumentException exception =
                org.junit.jupiter.api.Assertions.assertThrows(
                        IllegalArgumentException.class,
                        () -> account.withdraw(1000, "15-01-2012")
                );

        assertEquals("Insufficient balance", exception.getMessage());
    }



}
