package com.ciicc.ict2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CashTransferTest {
    @Test
    @DisplayName("Transfer transaction full test")
    void testCashTransferBetweenTwoUsers() {
        int senderID = 999;
        int receiverID = 900;
        double transferAmount = 100.0;

        // Precondition: Ensure sender has enough balance (at least 100.0)
        double senderInitialBalance = CheckBalance.checkBalance(senderID);
        assertTrue(senderInitialBalance >= transferAmount, "Sender should have at least 100 balance");

        double receiverInitialBalance = CheckBalance.checkBalance(receiverID);

        // Step 1: Initiate transfer (deducts from sender, creates transaction)
        String txnID = CashTransfer.cashTransfer(senderID, transferAmount, "Othertest", receiverID);
        assertNotNull(txnID, "Transaction ID should not be null");

        // Step 2: Apply the transfer (adds to receiver)
        boolean applied = CashTransfer.transferInDB(txnID);
        assertTrue(applied, "Transaction should be successfully applied");

        // Step 3: Check updated balances
        double senderFinalBalance = CheckBalance.checkBalance(senderID);
        double receiverFinalBalance = CheckBalance.checkBalance(receiverID);

        // Step 4: Assert balances updated correctly
        assertEquals(senderInitialBalance - transferAmount, senderFinalBalance, 0.0001,
                "Sender's balance should be reduced by the transfer amount");

        assertEquals(receiverInitialBalance + transferAmount, receiverFinalBalance, 0.0001,
                "Receiver's balance should be increased by the transfer amount");
    }
}
