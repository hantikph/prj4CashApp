package com.ciicc.ict2;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // start login sequence
        Scanner cli = new Scanner(System.in);
        System.out.println("Already a registered user? (Y/N)");
        String reply = cli.next();
        cli.close();
        boolean isRegistered= (reply.toLowerCase()=="y")? true: false;
        if (isRegistered) UserAuthentication.logIn();
        else {
            UserAuthentication.userRegistration();
        }
    }
}