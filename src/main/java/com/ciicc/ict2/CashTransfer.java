package com.ciicc.ict2;

import com.ciicc.ict2.Transactions.*;
import static com.ciicc.ict2.CheckBalance.*;

import java.sql.*;

public class CashTransfer {
    Transaction transfer = new Transaction();
    public void cashTransfer(double txnAmount, int payorID, int payeeID) {
        int sender = payorID;
        int recipient = payeeID;
        // verify sender balance
        // prepare transfer details
        // input payor details
        String payeeName; // get payee name
        String account_ID; // get payee account_ID
        Date date = Date.now();
        // generate transaction record
        transfer = Transaction(txnAmount, payeeName, account_ID, date, recipient, sender);
        // update sender and recipient balance - helper class
        updateBalance(transfer);
    }
    void addMoney(double amount, int recipient){
//        int recipient = transfer.getTransferToID();
        Balance bal = Balance(recipient);
        double txnAmount = amount;
        double currBalance = CheckBalance.checkBalance(recipient);
        double newBalance = currBalance + txnAmount;
        // setAmount(newBalance);

    }
    void deductMoney(double amount, int sender){
//        int sender = transfer.getTransferFromID();
        double txnAmount = amount;
        double currBalance = CheckBalance.checkBalance(sender);
        double newBalance = currBalance - txnAmount;
        // setAmount(newBalance);
    }
    void updateBalance(Transaction transfer) {
//        // get details of transaction ID from db
//        ResultSet rs = statement.executeQuery(where txnID);
//        rs.absolute(1);
//        // get transaction amounts and details
//        Transaction txn = (Transaction) rs;
        double amount = transfer.getAmount();
        int sender = transfer.getTransferFromID();
        int recipient = transfer.getTransferToID();
        addMoney(amount, recipient);
        deductMoney(amount, sender);
    }
}
