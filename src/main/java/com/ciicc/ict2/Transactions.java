package com.ciicc.ict2;

import java.sql.Date;
import java.util.*;

public class Transactions {
    public static List<Transaction> results = new ArrayList<>();

    // viewAll (must be admin?) method that returns all Transactions from the db table
    public static void viewAll(boolean isAdmin) {

    }
    // viewUserAll method that will return all user transactions
    public static void viewUserAll(int userID) {
//        System.out.println("Called from Transactions");
        CashAppDB.getUserTransactions(userID);
        Main.transactions = results;
    }
    // viewTransaction method using transaction ID as the parameter
    public static void viewTransaction(String txnID) {
        CashAppDB.getTxnDetails(txnID);
    }

//    public static void main(String[] args) {
//        viewUserAll(1020);
//        System.out.println(result.toString());
//    }

}
