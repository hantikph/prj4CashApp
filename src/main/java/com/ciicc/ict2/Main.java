package com.ciicc.ict2;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.*;


public class Main extends JFrame {

    private JPanel MainPanel;
//    private JLabel titleBar;
//    private JPanel userBarPanel;
    private JButton transferButton;
    private JButton getTransactionsButton;
    private JButton updateChangeButton;
//    private JPanel userInfoPanel;
    private JCheckBox toggleBalance;
    private JLabel acctBalanceLabel;
    private JLabel acctIDLabel;
    private JLabel userNameLabel;
    private JButton logOutButton;
    private JScrollPane infoScrollPane;
    private JPanel infoPanel;
    private JTable infoPaneTable;
    private JLabel infoPaneLabel;

    public static boolean isLoggedIn;
    public static int loggedAccountID;
    public static List<Transaction> transactions = new ArrayList<>();
    static JFrame appWin;

    public static boolean isIsLoggedIn() {
        return isLoggedIn;
    }

    public static void setIsLoggedIn(boolean isLoggedIn) {
        Main.isLoggedIn = isLoggedIn;
    }

    public static int getLoggedAccountID() {
        return loggedAccountID;
    }

    public static void setLoggedAccountID(int accountID) {
        Main.loggedAccountID = accountID;
    }

    public Main() {
        setTitle("Gee Cash - Cash pang-G");
        setVisible(true);
        setContentPane(MainPanel);
        setSize(720, 600);
        setLocationRelativeTo(null);
        infoScrollPane.setViewportView(infoPanel);

        List<UserAccount> data = new ArrayList<>(UserAuthentication.users);

        UserAccount user = CashAppDB.getUserByID(loggedAccountID);
//        System.out.println(user.toString());
        acctIDLabel.setText("Account ID: " + String.valueOf(user.getId()));
        userNameLabel.setText("Logged in as: " + user.getName());

        toggleBalance.addItemListener(e -> {
            if ((e.getStateChange() == ItemEvent.SELECTED)) {
                acctBalanceLabel.setText(String.format("%.2f", CheckBalance.checkBalance(loggedAccountID)));
            } else {
                acctBalanceLabel.setText("********");
            }
        });

        logOutButton.addActionListener(e -> {
            dispose();
            UserAuthentication.logout();
        });

        updateChangeButton.addActionListener(e -> new UserAuthentication(loggedAccountID));

        transferButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                depositAmount();
            }

            private void depositAmount() {
                // call deposits
//                System.out.println("Enter an amount");
                new CashIn();
            }
        });
        getTransactionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                infoPaneLabel.setText("Viewing Transactions");
                infoPaneTable.setVisible(true);
                setTableVal();
            }

            private void setTableVal() {
                // provision table data from db
                Transactions.viewUserAll(loggedAccountID);
//                System.out.println(Transactions.results.toString());
                DefaultTableModel tlist = (DefaultTableModel) infoPaneTable.getModel();
                tlist.setRowCount(0);
                // parse data into rows
                for (Transaction trn : Transactions.results) {
                    // check transaction type
                    String type;
                    int fromID = trn.getTransferFromID();
                    int toID = trn.getTransferToID();
                    if (fromID == 999999) {
                        type = "Cash In";
                    } else if (fromID == loggedAccountID) {
                        type = "Send";
                    } else if (toID == loggedAccountID) {
                        type = "Receive";
                    } else {
                        type = "Other";
                    }
                    Object[] data = { type, String.format("%.2f", trn.getAmount()), trn.getDate(), trn.getTxnID() };
                    tlist.addRow(data);
                }
                infoPaneTable.repaint();
            }
        });
        // transaction table data setting
        String[] colnames = {"Transaction", "Amount", "Date", "Transaction ID"};
        DefaultTableModel tableModel = new DefaultTableModel(colnames, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        infoPaneTable.setModel(tableModel);
    }

    public static void main(String[] args) {
        appWin = (!isIsLoggedIn()) ?  new UserAuthentication():  new Main();
    }
}
//    public Main(boolean t) {
//        this();
//        dispose();
//    }

//    private static int getLoggedAccountID(int acct) {
//        UserAccount user = UserAuthentication.findUserById(acct);
//        return user.getId();
//    }

//    public static void main(String[] args) {
//        isLoggedIn = false;
//        // start login sequence
//        if (!isLoggedIn) {
//            mainWindow = new UserAuthentication();
//        } else {
//            loggedAccountID = getLoggedAccountID();
//            mainWindow = new Main();
//        }
//    }