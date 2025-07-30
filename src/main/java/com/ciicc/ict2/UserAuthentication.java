package com.ciicc.ict2;

import java.util.*;
import java.util.regex.*;

class UserAccount {
    private final int id;
    private String name;
    private String email;
    private int number;
    private int pIdNum;

    // setters, id is final and cannot be changed
    // value id is randomly generated
    public void setName(String name) {
        this.name = name;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setNumber(int number) {
        this.number = number;
    }
    public void setPIdNum(int pIdNum) {
        this.pIdNum = pIdNum;
    }
    // getters for all fields
    public int getPIdNum() {
        return pIdNum;
    }
    public int getNumber() {
        return number;
    }
    public String getEmail() {
        return email;
    }
    public String getName() {
        return name;
    }
    public int getId() {
        return id;
    }

    // random id generator
    public static int generateId() {
        int idLength = 6;
        StringBuilder nums = new StringBuilder();
        for(int i=0; i<idLength; i++) {
            nums.append((int) Math.floor(Math.random()*10)); // number from 0 - 9
        }
        int acct = Integer.parseInt(String.valueOf(nums));
        System.out.println(acct);
        return acct;
    }

    public UserAccount(String name, String email, int number, int pIdNum) {
        this.id = generateId();
        this.name = name;
        this.email = email;
        this.number = number;
        this.pIdNum = pIdNum;
    }
}
public class UserAuthentication {

    // create a Registration method that adds a new user
    // adding validation for each field
    public static UserAccount userRegistration() {
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
        // create user account
        return new UserAccount(newUser.get(0),
                                    newUser.get(1),
                                    Integer.parseInt(newUser.get(2)),
                                    Integer.parseInt(newUser.get(3)));
    }

    // create a Login method to check registered user
    // and return the ID for online banking transactions
    public static int logIn() {
        // start validation
        int suppliedNumber, suppliedPIN, accountId;
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
        // find user number
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
        return accountId;
    }
    public static boolean logInHelper(int number, int pin) {
        int inputNumber = number;
        int inputPIN = pin;
        // find relevant mobile number
        UserAccount result = findNumber(inputNumber);
        // compare values
        return inputNumber == result.getNumber() && inputPIN == result.getPIdNum();
    }
    public boolean changePIdNum(int oldPIN, int newPIN) throws Exception {
        boolean updated;
        // retrieve correct account details
        UserAccount account = query.find(loggedEmail);
        // compare old PIN
        if(oldPIN == account.getPIdNum()) {
            System.out.println("Updating PIN...");
            account.setPIdNum(newPIN);
            // write to database, update record
            try{
                updated = true;
            } catch (Exception ignored){
                System.out.println("Unable to change PIN");
            }
        } else {
            System.out.println("PIN updating failed.");
            updated = false;
        }
        return updated;
    }
    public void logout() {
        // close the session
        /* suggested usage:
        request.getSession().invalidate(); // Http session
            SecurityContextHolder.clearContext() or
            SecurityContextLogoutHandler  // when using Spring Security
         */
    }

    public static void main(String[] args) {
        // start login sequence
        Scanner cli = new Scanner(System.in);
        System.out.println("Already a registered user? (Y/N)");
        String reply = cli.next();
        cli.close();
        boolean isRegistered= (reply.toLowerCase()=="y")? true: false;
        if (isRegistered) logIn();
        else {
            userRegistration();
        }
    }
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
