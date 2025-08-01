package com.ciicc.ict2;

class Balance {
    // using ID, amount, userID
    private final int id;
    private double amount;
    private int userID;

    // setter
    public void setAmount(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        } else this.amount = amount;
    }
    public void setUserID(int userID) {
        this.userID = userID;
    }

    // getter
    public int getId() {
        return id;
    }
    public double getAmount() {
        return amount;
    }
    public int getUserID() {
        return userID;
    }

    // constructor
    public Balance(int userID) {
        this.userID = userID;
        this.amount = 0;
    }
}

public class CheckBalance {
    // create a checkBalance method that will
    // return the user balance using the userID
    public static double checkBalance(int userID) {
        // table key userID
        // retrieve db data getUserID
        // return amount getAmount
        // display results
        return this.amount;
    }
    public static Balance getByUserID(int userID) {

    }
}
