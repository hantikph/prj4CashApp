package com.ciicc.ict2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionsTest {

//    @BeforeEach
//    void clearBeforeTest() {
//        Main.transactions = null;
//        Transactions.results = null;
//    }

    @Test
    @DisplayName("Display Transactions from viewUserAll method")
    void testViewUserAllLoadsTransactionsFromDatabase() {
        int userID = 999;

        // Act: call the real method to fetch from DB and assign to Main.transactions
        Transactions.viewUserAll(userID);

        // Assert: check if Main.transactions was populated
        assertNotNull(Main.transactions, "Main.transactions should not be null after DB load");
        assertFalse(Main.transactions.isEmpty(), "Main.transactions should contain at least one transaction");

        // Optional: Print or verify one transaction
        Transaction txn = Main.transactions.get(0);
        System.out.println("Printing Array index 0");
        System.out.print("Transaction ID: " + txn.getTxnID() +", ");
        System.out.println("Amount: " + txn.getAmount() +", ");
        System.out.print("From: " + txn.getTransferFromID() +", ");
        System.out.print("To: " + txn.getTransferToID());

        // Sample assertion — adjust based on known DB contents
        assertTrue(txn.getTxnID().startsWith("gc"), "Transaction ID should follow expected format");
    }
}