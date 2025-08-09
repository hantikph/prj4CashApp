package com.ciicc.ict2;

public class CheckBalance {
    // create a checkBalance method that will
    // return the user balance using the userID
    private static final double USER_NOT_FOUND = -1.0;

    public static double checkBalance(int userID) {
        // check if user exists on database
        if (CashAppDB.getUserByID(userID)==null) {
            System.out.println("No user in database with ID: "+ userID);
            return USER_NOT_FOUND;
        }
        // table key userID
        if (!CashAppDB.findUserBalance(userID)) {
            CashAppDB.initUserBalance(userID);
            return 0.0;
        }
        // retrieve db data getUserID
        // return amount getAmount
        // display results
        return CashAppDB.getUserBalance(userID);
    }
//    Balance myBal = new Balance(1111);
}