package com.ciicc.ict2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserBalanceTest {
    @Test
    @DisplayName("Test the balance of existing user ID")
    void testCheckBalance_ExistingUser() {
        int userID = 999; // Known existing user ID
        double expectedBalance = CashAppDB.getUserBalance(userID);  // Fetch expected balance

        double actualBalance = CheckBalance.checkBalance(userID);  // Method under test

        System.out.println("Expected: " + expectedBalance + ", Actual: " + actualBalance);

        assertEquals(expectedBalance, actualBalance, 0.0001,
                "Balance should match the value in the database");
    }

    @Test
    @DisplayName("Test for nonexistent user ID")
    void testCheckBalance_NonexistentUser() {
        int newUserID = 800; // Assume this ID does not exist yet

        assertFalse(CashAppDB.findUserBalance(newUserID),
                "Precondition failed: user should not exist");

        double balance = CheckBalance.checkBalance(newUserID);

        assertEquals(-1.0, balance, 0.0001,
                "Nonexistent user's balance should be 0 or less");
    }
}
