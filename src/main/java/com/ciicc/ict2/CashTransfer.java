package com.ciicc.ict2;

import com.ciicc.ict2.Transactions.*;
import static com.ciicc.ict2.CheckBalance.*;

import java.sql.*;

public class CashTransfer {

    public static String cashTransfer(int acctID, double transferAmount, String rcvrName, int rcvrID) {
        UserAccount sender = CashAppDB.getUserByID(acctID);
        UserAccount receiver = CashAppDB.getUserByID(rcvrID);
        System.out.println(String.format("Account %s sending to: %s", sender.getId(), receiver.getId()));
        StringBuilder s = new StringBuilder();
        s.append("gc");
        s.append(String.valueOf(System.currentTimeMillis()));
        s.append("SD");
        s.append(String.valueOf(acctID));
        String txnID = s.toString();
        double senderBal = CheckBalance.checkBalance(sender.getId());

        if (transferAmount > 0 && transferAmount <= senderBal) {
            CashAppDB.createTransaction(txnID, transferAmount, rcvrName, acctID, rcvrID, acctID);
            System.out.println("Verifying transaction");
            // prompt in Main how?
            senderBal -= transferAmount;
            CashAppDB.updateUserBalance(sender.getId(), senderBal);
            System.out.println(String.format("Deducting php%s from account ID %s", transferAmount, sender.getId()));
        } else {
            System.out.println("Invalid amount or amount greater than balance");
        }
        return txnID;
    }

    public static boolean transferInDB(String trnID) {
        try {
            Transaction trn = CashAppDB.getTxnDetails(trnID);
//        Transaction txn = null;
            // find account using getTransferToID;
            if (trn == null) {
                System.err.println("Transaction ID not found: " + trnID);
                return false;
            }
            System.out.println("transaction: "+ trn.toString());
            // check balance of account_ID
            double currBal = CheckBalance.checkBalance(trn.getTransferToID());
            // add the deposit amount to transferToID
            currBal += trn.getAmount();
            // update the account balance
            CashAppDB.updateUserBalance(trn.getTransferToID(), currBal);
            CashAppDB.getUserBalance(trn.getTransferToID());
            return true;
        } catch (Exception e) {
            System.err.println("cashInDB: "+ e.getLocalizedMessage());
            return false;
        }
    }
}
    //    Transaction transfer = new Transaction();
//    public void cashTransfer(double txnAmount, int payorID, int payeeID) {
//        int sender = payorID;
//        int recipient = payeeID;
//        // verify sender balance
//        // prepare transfer details
//        // input payor details
//        String payeeName; // get payee name
//        String account_ID; // get payee account_ID
//        Date date = Date.now();
//        // generate transaction record
//        transfer = Transaction(txnAmount, payeeName, account_ID, date, recipient, sender);
//        // update sender and recipient balance - helper class
//        updateBalance(transfer);
//    }
//    void addMoney(double amount, int recipient){
////        int recipient = transfer.getTransferToID();
//        Balance bal = Balance(recipient);
//        double txnAmount = amount;
//        double currBalance = CheckBalance.checkBalance(recipient);
//        double newBalance = currBalance + txnAmount;
//        // setAmount(newBalance);
//
//    }
//    void deductMoney(double amount, int sender){
////        int sender = transfer.getTransferFromID();
//        double txnAmount = amount;
//        double currBalance = CheckBalance.checkBalance(sender);
//        double newBalance = currBalance - txnAmount;
//        // setAmount(newBalance);
//    }
//    void updateBalance(Transaction transfer) {
////        // get details of transaction ID from db
////        ResultSet rs = statement.executeQuery(where txnID);
////        rs.absolute(1);
////        // get transaction amounts and details
////        Transaction txn = (Transaction) rs;
//        double amount = transfer.getAmount();
//        int sender = transfer.getTransferFromID();
//        int recipient = transfer.getTransferToID();
//        addMoney(amount, recipient);
//        deductMoney(amount, sender);
//    }

