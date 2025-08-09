package com.ciicc.ict2;

public class Balance {
    // using ID, amount, userID
    private int id;
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
    }

//    public static double getBalance(int acctID) {
//
//    }

    public static void updateBalance(int acctID, double amount) {

    }
}
