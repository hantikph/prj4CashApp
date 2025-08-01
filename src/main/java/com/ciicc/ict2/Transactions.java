package com.ciicc.ict2;

import java.sql.Date;

class Transaction {
    private final int txnID;
    private final double amount;
    private final String name;
    private final String account_ID;
    private final Date date;
    private final int transferToID;
    private final int transferFromID;

    // getter
    public int getTxnID() {
        return txnID;
    }
    public double getAmount() {
        return amount;
    }
    public String getName() {
        return name;
    }
    public String getAccount_ID() {
        return account_ID;
    }
    public Date getDate() {
        return date;
    }
    public int getTransferToID() {
        return transferToID;
    }
    public int getTransferFromID() {
        return transferFromID;
    }
    // constructor

    public Transaction(double amount, String name, String account_ID,
                       Date date, int transferToID, int transferFromID) {
        this.amount = amount;
        this.name = name;
        this.account_ID = account_ID;
        this.date = date;
        this.transferToID = transferToID;
        this.transferFromID = transferFromID;
    }
}

public class Transactions {
    // viewAll (must be admin?) method that returns all Transactions from the db table
    public void viewAll(boolean isAdmin) {

    }
    // viewUserAll method that will return all user transactions
    public void viewUserAll(int userID) {

    }
    // viewTransaction method using transaction ID as the parameter
    public void viewTransaction(int txnID) {

    }

}
