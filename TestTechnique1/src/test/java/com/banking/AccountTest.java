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
        account = new Account(new StatementPrinter());
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
    @Test
    public void cannotDepositZeroAmount() {
        IllegalArgumentException exception =
                org.junit.jupiter.api.Assertions.assertThrows(
                        IllegalArgumentException.class,
                        () -> account.deposit(0, "16-01-2012")
                );

        assertEquals("Deposit amount must be positive", exception.getMessage());
    }

    @Test
    public void cannotWithdrawZeroAmount() {
        IllegalArgumentException exception =
                org.junit.jupiter.api.Assertions.assertThrows(
                        IllegalArgumentException.class,
                        () -> account.withdraw(0, "17-01-2012")
                );

        assertEquals("Withdrawal amount must be positive", exception.getMessage());
    }
    @Test
    public void withdrawExactBalance() {
        account.deposit(1000, "18-01-2012");
        account.withdraw(1000, "19-01-2012");

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        account.printStatement();

        String actual = output.toString().trim().replace("\r\n", "\n");

        String expected =
                "Date       | Amount | Balance\n" +
                        "19-01-2012 | -1000   | 0\n" +
                        "18-01-2012 | 1000   | 1000";

        assertEquals(expected, actual);
    }
    @Test
    public void printStatementWhenNoTransaction() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        account.printStatement();

        String actual = output.toString().trim().replace("\r\n", "\n");

        String expected = "Date       | Amount | Balance";

        assertEquals(expected, actual);
    }

    @Test
    public void fullScenarioPrintsCorrectStatement() {
        account.deposit(1000, "10-01-2012");
        account.deposit(2000, "13-01-2012");
        account.withdraw(500, "14-01-2012");

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        account.printStatement();

        String actual = output.toString().trim().replace("\r\n", "\n");

        String expected =
                "Date       | Amount | Balance\n" +
                        "14-01-2012 | -500   | 2500\n" +
                        "13-01-2012 | 2000   | 3000\n" +
                        "10-01-2012 | 1000   | 1000";

        assertEquals(expected, actual);
    }



}
