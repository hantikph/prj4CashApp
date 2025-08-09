package com.ciicc.ict2;

import java.sql.Date;
import java.sql.Timestamp;

public class Transaction {
    private String txnID;
    private double amount;
    private String name;
    private int account_ID;
    private Timestamp date;
    private int transferToID;
    private int transferFromID;

    // constructors
    public Transaction() {
    }

    public Transaction(String txnID, double amount, String name, int account_ID, int transferToID, int transferFromID) {
        this.txnID = txnID;
        this.amount = amount;
        this.name = name;
        this.account_ID = account_ID;
//        this.date = date;
        this.transferToID = transferToID;
        this.transferFromID = transferFromID;
    }

    public Transaction(String txnID, double amount, String name, int account_ID, Timestamp date, int transferToID, int transferFromID) {
        this.txnID = txnID;
        this.amount = amount;
        this.name = name;
        this.account_ID = account_ID;
        this.date = date;
        this.transferToID = transferToID;
        this.transferFromID = transferFromID;
    }

    // setters
    public void setTxnID(String txnID) {
        this.txnID = txnID;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAccount_ID(int account_ID) {
        this.account_ID = account_ID;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public void setTransferToID(int transferToID) {
        this.transferToID = transferToID;
    }

    public void setTransferFromID(int transferFromID) {
        this.transferFromID = transferFromID;
    }

    // getters
    public String getTxnID() {
        return txnID;
    }
    public double getAmount() {
        return amount;
    }
    public String getName() {
        return name;
    }
    public int getAccount_ID() {
        return account_ID;
    }
    public Timestamp getDate() {
        return date;
    }
    public int getTransferToID() {
        return transferToID;
    }
    public int getTransferFromID() {
        return transferFromID;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "txnID='" + txnID + '\'' +
                ", amount=" + amount +
                ", name='" + name + '\'' +
                ", account_ID='" + account_ID + '\'' +
                ", date=" + date +
                ", transferToID=" + transferToID +
                ", transferFromID=" + transferFromID +
                '}';
    }

}
