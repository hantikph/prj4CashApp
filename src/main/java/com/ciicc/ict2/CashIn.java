package com.ciicc.ict2;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;


public class CashIn extends JFrame {
    int acctID = Main.getLoggedAccountID();
    UserAccount user = CashAppDB.getUserByID(acctID);
    static int cashInID = 999999;
    private JPanel CashInPanel;
    private JLabel transactionLabel;
    private JButton cashInButton;
    private JButton transferButton;
    private JLabel acctIDLabel;
    private JLabel loggedUserLabel;
    private JPanel cashButtonPanel;
    private JFormattedTextField cashAmountField;
    private JLabel cashAmountLabel;
    private JTextField receiverNameField;
    private JTextField receiverAcctField;
    private JLabel receiverNameLabel;
    private JLabel receiverAcctLabel;
    private JPanel transferInfoPanel;
    private JPanel notifsPanel;
    private JScrollPane notifsScrollPane;
    private JLabel noticeLabel;


    public CashIn() {
        setTitle("Gee Cash - Cash pang-G");
        setVisible(true);
        setContentPane(CashInPanel);
        setSize(600, 400);
        setLocationRelativeTo(null);
        receiverAcctLabel.setText(" ");
        receiverAcctField.setVisible(false);
        receiverNameLabel.setText(" ");
        receiverNameField.setVisible(false);
        cashAmountLabel.setText(" ");
        cashAmountField.setVisible(false);
        acctIDLabel.setText("Account ID: "+ String.valueOf(acctID));
        loggedUserLabel.setText("Logged in as: "+ user.getName());
        transactionLabel.setText("What would you like to do?");
        noticeLabel.setText("Cash In/Add money or Transfer/Send money");

        cashInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new CashIn(true);
            }
        });
        transferButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new CashIn(Main.loggedAccountID);
            }
        });
    }

    public CashIn(boolean f) {
        setTitle("Gee Cash - Cash pang-G");
        setVisible(true);
        setContentPane(CashInPanel);
        setSize(600, 400);
        setLocationRelativeTo(null);
        receiverAcctLabel.setText(" ");
        receiverAcctField.setVisible(false);
        receiverNameLabel.setText(" ");
        receiverNameField.setVisible(false);
        acctIDLabel.setText("Account ID: "+ String.valueOf(acctID));
        loggedUserLabel.setText("Logged in as: "+ user.getName());
        transactionLabel.setText("Cash In to Account");
        cashInButton.setText("Cash In");
        transferButton.setText("Cancel");
        notifsScrollPane.setViewportView(notifsPanel);
        noticeLabel.setText("Enter amount to add to your account");

        cashInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double cashAmount = Double.parseDouble(cashAmountField.getText());
                System.out.println("Process amount: "+ cashAmount);
                String transact = cashIn(Main.loggedAccountID, cashAmount);
                dispose();
                cashInDB(transact);
            }
        });
        transferButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    public CashIn(int acctID) {
        setTitle("Gee Cash - Cash pang-G");
        setVisible(true);
        setContentPane(CashInPanel);
        setSize(600, 400);
        setLocationRelativeTo(null);
        acctIDLabel.setText("Account ID: "+ String.valueOf(acctID));
        loggedUserLabel.setText("Logged in as: "+ user.getName());
        transactionLabel.setText("Transfer to another Account");
        cashAmountLabel.setText("Transfer amount");
        cashInButton.setText("Transfer to");
        transferButton.setText("Cancel");
        notifsScrollPane.setViewportView(notifsPanel);

        cashInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double cashAmount = Double.parseDouble(cashAmountField.getText().trim());
                double senderBal = CheckBalance.checkBalance(Main.loggedAccountID);
                String rcvrName = receiverNameField.getText();
                int rcvrID = Integer.parseInt(receiverAcctField.getText());
                String transact = "";
                System.out.println("Transfer amount: "+ cashAmount);
                if (cashAmount > senderBal) {
                    System.out.println("Amount larger than balance");

                } else {
                    System.out.println("Processing transfer");
                    transact = CashTransfer.cashTransfer(Main.loggedAccountID, cashAmount, rcvrName, rcvrID);
                }
                dispose();
                CashTransfer.transferInDB(transact);
            }
        });
        transferButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        cashAmountField.getDocument().addDocumentListener(new DocumentListener() {
            private void validateAmount() {
                String input = cashAmountField.getText().trim();
                double currBal = CheckBalance.checkBalance(Main.loggedAccountID);
                try {
                    double inputAmt = Double.parseDouble(input);
                    if (inputAmt > currBal) {
                        noticeLabel.setText("Input amount is greater than balance");
                        noticeLabel.setForeground(Color.ORANGE);
                    } else {
                        noticeLabel.setText("Verifying transfer amount");
                        noticeLabel.setForeground(Color.GREEN.darker());
                    }
                } catch (NumberFormatException e) {
                    noticeLabel.setText("Amount is invalid");
                    noticeLabel.setForeground(Color.GRAY);
                }
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                validateAmount();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                validateAmount();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                validateAmount();
            }
        });
    }

    public static String cashIn(int acctID, double cashInAmount) {
        UserAccount user = CashAppDB.getUserByID(acctID);
        System.out.println("Current ID: "+ acctID);
        StringBuilder s = new StringBuilder();
        s.append("gc");
        s.append(String.valueOf(System.currentTimeMillis()));
        s.append("CI");
        s.append(String.valueOf(acctID));
        String txnID = s.toString();

        if (cashInAmount > 0) {
            CashAppDB.createTransaction(txnID, cashInAmount, user.getName(), acctID, acctID, cashInID);
            System.out.println("Verifying transaction");
            // prompt in Main how?
        } else {
            System.out.println("Invalid amount");
        }

        return txnID;
    }

    public static boolean cashInDB(String trnID) {
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
            System.out.println(String.format("Success! Received %s to your account", trn.getAmount()));
            return true;
        } catch (Exception e) {
            System.err.println("cashInDB: "+ e.getLocalizedMessage());
            return false;
        }
    }

    // test transactions: first +200, second +300
//    public static void main(String[] args) {
//        List<Transaction> txnLog = new ArrayList<>();
//        cashInRequest(1020, 200);  // initial 200
////        cashIn(Main.loggedAccountID, 300);  // second 300
//        cashInDB("gc1754452147309CI1005");
//    }
}
