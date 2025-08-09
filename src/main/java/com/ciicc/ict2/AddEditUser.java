package com.ciicc.ict2;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.Objects;

public class AddEditUser extends JFrame {
    private JPanel addUserPanel;
    private JPanel dataEntryPanel;
    private JTextField fullNameField;
    private JTextField emailAddressField;
    private JTextField mobileNumberField;
    private JTextField pIdNumField;
    private JTextField reenterPINField;
    private JButton registerButton;
    private JButton clearFormButton;
    private JPanel noticePanel;
    private JLabel noticeLabel;

    public AddEditUser() {
        setTitle("Gee Cash - Cash pang-G");
        setVisible(true);
        setContentPane(addUserPanel);
        setSize(400, 600);
        setLocationRelativeTo(null);

        registerButton.addActionListener(e -> {
            String userName = fullNameField.getText();
            String emailAddr = emailAddressField.getText();
            String mobileNum = mobileNumberField.getText();
            int pIdNumber = Integer.parseInt(pIdNumField.getText());
            int verfiyPIN = Integer.parseInt(reenterPINField.getText());

            if (!Objects.equals(pIdNumber, verfiyPIN)) {
                noticeLabel.setText("PIN must match.");
                return;
            }

            // add new user account
//                UserAuthentication.userRegistration(userName, emailAddr, mobileNum, pIdNumber); // for List
            CashAppDB.addUser(userName, emailAddr, mobileNum, pIdNumber);
            dispose();
            new UserAuthentication();
        });

        clearFormButton.addActionListener(e -> {
            fullNameField.setText("");
            emailAddressField.setText("");
            mobileNumberField.setText("");
            pIdNumField.setText("");
            reenterPINField.setText("");
        });

        pIdNumField.getDocument().addDocumentListener(new DocumentListener() {
            private void verifyNumbers() {
                String input1 = pIdNumField.getText().trim();
                String input2 = reenterPINField.getText().trim();
                try {
                    int inputPin = Integer.parseInt(input1);
                    int reenterPin = Integer.parseInt(input2);
                    if (input1.length() != 6) {
                        noticeLabel.setText("PIN must be exactly 6 digits");
                    } else if (Objects.deepEquals(inputPin, reenterPin)) {
                        noticeLabel.setText("PIN entered does not match");
                        noticeLabel.setForeground(Color.ORANGE);
                    } else {
                        noticeLabel.setText("PIN verified");
                        noticeLabel.setForeground(Color.GREEN.darker());
                    }
                } catch (NumberFormatException e) {
                    noticeLabel.setText("Complete your registration");
                    noticeLabel.setForeground(Color.GRAY);
                }
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                verifyNumbers();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                verifyNumbers();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                verifyNumbers();
            }
        });
    }

    // edit for existing user
    public AddEditUser(int acctID) {
        this();
        fullNameField.setEditable(false);
        emailAddressField.setEditable(false);
        mobileNumberField.setEditable(false);
        registerButton.setText("Change PIN");

        registerButton.addActionListener(e -> {
//                String userName = fullNameField.getText();
//                String emailAddr = emailAddressField.getText();
//                String mobileNum = mobileNumberField.getText();
            int pIdNumber = Integer.parseInt(pIdNumField.getText());
            int verfiyPIN = Integer.parseInt(reenterPINField.getText());

            if (!Objects.equals(pIdNumber, verfiyPIN)) {
                noticeLabel.setText("PIN must match.");
                return;
            }

            // add new user account
//                UserAuthentication.userRegistration(userName, emailAddr, mobileNum, pIdNumber); // for List
            CashAppDB.editPIN(acctID, pIdNumber);
            dispose();
        });
    }

    public AddEditUser(String str) {
        this();
        UserAccount usr = CashAppDB.getUserByID(Main.loggedAccountID);
        mobileNumberField.setText(String.valueOf(usr.getNumber()));
        mobileNumberField.setEditable(false);
        pIdNumField.setVisible(false);
        reenterPINField.setVisible(false);
        registerButton.setText(str);

        registerButton.addActionListener(e -> {
            String newUserName = fullNameField.getText();
            String currUserName = usr.getName();
            String updateName;
            String emailAddr = emailAddressField.getText();
            String currEmailAddr = usr.getEmail();
            String updateEmail = "";

//            String mobileNum = mobileNumberField.getText();
            if (Objects.equals(currUserName, newUserName)) {
                updateName = currUserName;
            } else {
                updateName = newUserName;
            }
            if (Objects.equals(currEmailAddr, emailAddr)) {
                updateEmail = currEmailAddr;
            } else {
                updateEmail = emailAddr;
            }

            CashAppDB.editUserInfo(usr.getNumber(), updateName, updateEmail);
        });
    }
}
