package com.ciicc.ict2;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserAuthenticationTest {

    @Test
    @DisplayName("Valid login should return a value")
    void testValidLogin() {
        String validUserNumber = "09999999999";
        int validPIN = 999999;
        System.out.println("Sending test payload");
        int num = UserAuthentication.logIn(validUserNumber, validPIN);
        boolean result = (num > 0) ? true : false;
        assertTrue(result, "Valid login should return a value");
    }

    @Test
    @DisplayName("Invalid login should fail authentication")
    void testInvalidLogin() {
        String validUserNumber = "01987654321";
        int validPIN = 000000;
        System.out.println("Sent invalid payload");
        int result = UserAuthentication.logIn(validUserNumber, validPIN);

        assertEquals(-1, result, "Expected -1 for invalid login attempt");
    }

}
