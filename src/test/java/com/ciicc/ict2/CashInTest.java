package com.ciicc.ict2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CashInTest {
    @Test
    @DisplayName("Cash In test for updated balances")
    void testCashInUpdatesBalance() {
        int acctID = 999;
        double cashInAmount = 100.0;

        // Step 1: Get initial balance
        double initialBalance = CheckBalance.checkBalance(acctID);
        assertTrue(initialBalance >= 0, "Initial balance should be non-negative");

        // Step 2: Call cashIn (register transaction)
        String txnID = CashIn.cashIn(acctID, cashInAmount);
        assertNotNull(txnID, "Transaction ID should not be null");

        // Step 3: Apply transaction (update balance)
        boolean result = CashIn.cashInDB(txnID);
        assertTrue(result, "cashInDB should return true for valid transaction");

        // Step 4: Get updated balance
        double updatedBalance = CheckBalance.checkBalance(acctID);

        // Step 5: Assert that the balance increased by cashInAmount
        assertEquals(initialBalance + cashInAmount, updatedBalance, 0.0001,
                "Updated balance should match initial + cashInAmount");
    }
}
