/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.prog5121poe;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class LoginTest {

    @Test
    public void testUsernameCorrect() {

        Login user = new Login(
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976",
                "Kyle",
                "Smith"
        );

        assertTrue(user.checkUserName());
    }

    @Test
    public void testUsernameIncorrect() {

        Login user = new Login(
                "kyle!!!!!!!",
                "Ch&&sec@ke99!",
                "+27838968976",
                "Kyle",
                "Smith"
        );

        assertFalse(user.checkUserName());
    }

    @Test
    public void testPasswordCorrect() {

        Login user = new Login(
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976",
                "Kyle",
                "Smith"
        );

        assertTrue(user.checkPasswordComplexity());
    }

    @Test
    public void testPasswordIncorrect() {

        Login user = new Login(
                "kyl_1",
                "password",
                "+27838968976",
                "Kyle",
                "Smith"
        );

        assertFalse(user.checkPasswordComplexity());
    }

    @Test
    public void testCellPhoneCorrect() {

        Login user = new Login(
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976",
                "Kyle",
                "Smith"
        );

        assertTrue(user.checkCellPhoneNumber());
    }

    @Test
    public void testCellPhoneIncorrect() {

        Login user = new Login(
                "kyl_1",
                "Ch&&sec@ke99!",
                "08966553",
                "Kyle",
                "Smith"
        );

        assertFalse(user.checkCellPhoneNumber());
    }

    @Test
    public void testLoginSuccessful() {

        Login user = new Login(
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976",
                "Kyle",
                "Smith"
        );

        user.registerUser();

        user.setLoginDetails(
                "kyl_1",
                "Ch&&sec@ke99!"
        );

        assertTrue(user.loginUser());
    }

    @Test
    public void testLoginFailed() {

        Login user = new Login(
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976",
                "Kyle",
                "Smith"
        );

        user.registerUser();

        user.setLoginDetails(
                "kyl_1",
                "wrongPassword"
        );

        assertFalse(user.loginUser());
    }
}