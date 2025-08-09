package com.ciicc.ict2;

public class CheckBalance {
    // create a checkBalance method that will
    // return the user balance using the userID
    public static double checkBalance(int userID) {
        // table key userID
        if (!CashAppDB.findUserBalance(userID)) {
            CashAppDB.initUserBalance(userID);
            return 0;
        } else {
            // retrieve db data getUserID
            // return amount getAmount
            // display results
            return CashAppDB.getUserBalance(userID);
        }
    }
//    Balance myBal = new Balance(1111);
}