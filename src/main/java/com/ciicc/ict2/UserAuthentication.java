package com.ciicc.ict2;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;


public class UserAuthentication extends JFrame {

    private JPanel loginPanel;
    private JButton registerButton;
    private JButton logInButton;
    private JTextField inputNumberTextField;
    private JPasswordField inputPINPasswordField;
    private JTextArea notification;
    private JLabel pinLabel;
    private JLabel numberLabel;

    public static List<UserAccount> users = new ArrayList<>();
//    static JFrame gUI;

    public UserAuthentication() {
        setTitle("Gee Cash");
        setVisible(true);
        setContentPane(loginPanel);
        setSize(600, 500);
        setLocationRelativeTo(null);
        notification.setText("Register new users or Login");

        // built in user information
        users.add(new UserAccount(1001,"Charles Mijares", "hantikdigital@techie.com", "09185248999", 168888));
        users.add(new UserAccount(1005, "Michael Jackstone", "kingofpapis@email.kuno", "09112223456", 123456));
        users.add(new UserAccount(1020, "Pedro Parker", "gagamba@email.kuno", "09123456789", 654321));

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser();
                dispose();
            }
        });

        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String  number = inputNumberTextField.getText();
                char[] pw = inputPINPasswordField.getPassword();
                String s = new String(pw);
                int pin = Integer.parseInt(s);

                try {
//                    UserAccount userFile = findUserByNumber(number);  // for List
                    UserAccount userFile = CashAppDB.getUserByNumber(number);

                    if (userFile.getNumber().equals(number) && (userFile.getPIdNum() == pin)) {
                        System.out.println("Success!");
                        notification.setText(("Login successful"));
                        Main.setIsLoggedIn(true);
                        Main.setLoggedAccountID(logIn(number, pin));
                        logInUser();
                    } else if (!userFile.getNumber().equals(number)) {
                        System.out.println("No such user");
                        notification.setText("User number not on file");
                    } else {
                        System.out.println("Invalid login!");
                        notification.setText("Number and PIN do not match");
                    }
                } catch (Exception x) {
                    System.err.println(x.getLocalizedMessage());
                }
                dispose();
            }
        });
    }

    public UserAuthentication(int acctID) {
        this();
        inputNumberTextField.setText(String.valueOf(acctID));
        inputNumberTextField.setEditable(false);
        numberLabel.setText("Account Id: ");
        pinLabel.setText("Logged in: ");
        inputPINPasswordField.setVisible(false);
        registerButton.setText("Update info");
        logInButton.setText("Change PIN");
        notification.setText("What would you like to do?");

        UserAccount user = CashAppDB.getUserByID(acctID);
//        JTextField userNameField = new JTextField(user.getName());

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateUserInfo();
            }

        });

        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updatePIN();
                dispose();
            }
        });

    }
    private void logInUser() {
        new Main();
    }

    private void registerUser() {
        new AddEditUser();
    }

    private void updatePIN() {
        new AddEditUser(Main.getLoggedAccountID());
    }

    private void updateUserInfo() {
        new AddEditUser("email");
    }

    // create a Registration method that adds a new user
    // adding validation for each field
    public static void userRegistration(String name, String email, String number, int pIN) {
    /*  cli input and verification
        List<String> newUser = new ArrayList<String>();
        Scanner input = new Scanner(System.in);
        System.out.println("Enter your name (at least 2 characters):");
        while (!input.hasNext("[A-Za-z]+")) {
            System.out.println("Please enter name using letters only");
            input.next();
        }
        newUser.add(input.next());
        System.out.println("Enter your email address:");
        Pattern simpleEmail = Pattern.compile("^[a-z0-9+_.-]+@(.+)$");
        while (!input.hasNext(simpleEmail)) {
            System.out.println("Please enter valid email address");
            input.next();
        }
        newUser.add(input.next());
        System.out.println("Enter your mobile number (10 digits):");
        while (!input.hasNext("^[0-9]+")) {
            System.out.println("Please enter numbers only");
            input.next();
        }
        newUser.add(input.next());
        System.out.println("Select your 6-digit PIN:");
        Pattern sixDigitPin = Pattern.compile("\\d{6}");
        while (!input.hasNext(sixDigitPin)) {
            System.out.println("Please use a number with 6 digits");
            input.next();
        }
        newUser.add(input.next());
        System.out.println("The following details: "+ newUser);
        input.close();
    */
        // create user account
//        users.add(new UserAccount(name, email, number, pIN));
    }

    // create a Login method to check registered user
    // and return the ID for online banking transactions
    public static int logIn(String number, int pIN) {
        // start validation
        String suppliedNumber;
        int suppliedPIN, accountId;
    /*  CLI change input streams
        Scanner in = new Scanner(System.in);
        System.out.println("Enter mobile number (10 digits)");
        while (!in.hasNext("^[0-9]+")) {
            System.out.println("Please enter numbers only");
            in.next();
        }
        suppliedNumber = Integer.parseInt(in.next());
        System.out.println("Enter your 6-digit PIN");
        while (!in.hasNext("\\d{6}")) {
            System.out.println("Please enter numbers only");
            in.next();
        }
        suppliedPIN = Integer.parseInt(in.next());
        in.close();
     */
        // find user number
    /*  done SQL integration
        passed to class CashAppDB
        try {
            queryResult = sql.find(suppliedNumber);
            accountId = queryResult.getId();
            // accept input and authenticate
            if(logInHelper(suppliedNumber, suppliedPIN)) {
                System.out.println("You are now logged in");
            } else {
                System.out.println("Incorrect number or PIN");
                accountId = 0;
            }
        } catch (Exception e) {
        }

     */
        suppliedNumber = number;
        suppliedPIN = pIN;
        // find user number in datasource
//        UserAccount result = findUserByNumber(suppliedNumber);  // for List
        UserAccount result = CashAppDB.getUserByNumber(suppliedNumber);
        accountId = (logInHelper(suppliedNumber, suppliedPIN))? result.getId() : -1;

        return accountId;
    }
    static UserAccount findUserByNumber(String number) {
        // access records
        List<UserAccount> records = new ArrayList<>(UserAuthentication.users);
        UserAccount foundUser = null;
        for (UserAccount r : records) {
            if(r.getNumber().equals(number))
                foundUser = r;
        }
        return foundUser;
    }

    public static UserAccount findUserById(int id, List<UserAccount> data) {
        // access records
//        List<UserAccount> records = data;
        UserAccount foundUser = null;
        for (UserAccount r : data) {
            if(r.getNumber().equals(id))
                foundUser = r;
        }
        return foundUser;
    }

    public static boolean logInHelper(String number, int pin) {
        String inputNumber = number;
        int inputPIN = pin;
        boolean result;
//        UserAccount userFile = findUserByNumber(inputNumber);
        UserAccount userFile = CashAppDB.getUserByNumber(number);
        // compare input values
        result = (inputNumber.equals(userFile.getNumber()) && inputPIN==userFile.getPIdNum());
        return result;

    /* done SQL integration
        passed to class CashAppDB
        try {
            // find relevant mobile number
            UserAccount result = findNumber(inputNumber);
            // compare values
            return inputNumber == result.getNumber() && inputPIN == result.getPIdNum();
        } catch (SQLDataException q) {

        }
    */

    }
    public boolean changePIdNum(int oldPIN, int newPIN) throws NullPointerException {
        // retrieve correct account details
        try {
//            UserAccount account = findUserById(Main.getLoggedAccountID(), users);
            UserAccount account = CashAppDB.getUserByID(Main.loggedAccountID);
            if (!(account == null)) {
                // compare old PIN
                if(oldPIN == account.getPIdNum()) {
                    System.out.println("Updating PIN...");
                    account.setPIdNum(newPIN);
                    return true;
                // write to database, update record
//                     try{
//                            isUpdated = true;
//                    } catch (Exception ignored){
//                            System.out.println("Unable to change PIN");
//                    }
                } else {
                    System.out.println("PIN updating failed.");
                    return false;
                }
            }
        } catch (NullPointerException e) {
            System.out.println("No user logged in");
            System.err.println("Error: " + e);
            return false;
        }
        return true;
    }
    public static void logout() {
        // close the session
        /* suggested usage:
        request.getSession().invalidate(); // Http session
            SecurityContextHolder.clearContext() or
            SecurityContextLogoutHandler  // when using Spring Security
         */
        Main.setLoggedAccountID(0);
        Main.setIsLoggedIn(false);
//        System.out.println("Logged ID: "+ Main.loggedAccountID);
        new UserAuthentication();
    }

//    public static void main(String[] args) {
//        gUI = !Main.isIsLoggedIn() ? new UserAuthentication() : new Main();
//    }
}


/*  FOR REFERENCE: Using Pattern-Matcher
    public boolean isValidEmail(String email) {
        // regular expression for matching valid email addresses
        String emailRegex = "^[a-zA-z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@"+"(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        // compile the regex
        Pattern p = Pattern.compile(emailRegex);
        // check that email matches pattern
        return email != null && p.matcher(email).matches();
    }
*/
