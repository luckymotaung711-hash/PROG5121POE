/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.prog5121poe;

public class Login {

    // Registration details
    private String username;
    private String password;
    private String cellPhoneNumber;
    private String firstName;
    private String lastName;

    // Details saved after successful registration
    private String registeredUsername;
    private String registeredPassword;
    private String registeredCellPhoneNumber;

    // Details entered during login
    private String loginUsername;
    private String loginPassword;

    // Stores whether the login was successful
    private boolean loginSuccessful;

    // Constructor
    public Login(String username, String password, String cellPhoneNumber,
                 String firstName, String lastName) {

        this.username = username;
        this.password = password;
        this.cellPhoneNumber = cellPhoneNumber;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // Checks that the username contains an underscore
    // and is no more than five characters long.
    public boolean checkUserName() {

        return username.contains("_") && username.length() <= 5;
    }

    // Checks the password requirements.
    public boolean checkPasswordComplexity() {

        boolean hasEightCharacters = password.length() >= 8;
        boolean hasCapitalLetter = password.matches(".*[A-Z].*");
        boolean hasNumber = password.matches(".*[0-9].*");
        boolean hasSpecialCharacter =
                password.matches(".*[^a-zA-Z0-9].*");

        return hasEightCharacters
                && hasCapitalLetter
                && hasNumber
                && hasSpecialCharacter;
    }

    // Checks the South African cell phone number.
    public boolean checkCellPhoneNumber() {

        String regex = "^\\+27\\d{9}$";

        return cellPhoneNumber.matches(regex);
    }

    // Registers the user if all details are valid.
    public String registerUser() {

        if (!checkUserName()) {

            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        }

        if (!checkPasswordComplexity()) {

            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }

        if (!checkCellPhoneNumber()) {

            return "Cell phone number incorrectly formatted or does not contain international code.";
        }

        // Save the registration details.
        registeredUsername = username;
        registeredPassword = password;
        registeredCellPhoneNumber = cellPhoneNumber;

        return "Registration successful.";
    }

    // Stores the username and password entered during login.
    public void setLoginDetails(String loginUsername, String loginPassword) {

        this.loginUsername = loginUsername;
        this.loginPassword = loginPassword;
    }

    // Checks whether the login details match the
    // details stored during registration.
    public boolean loginUser() {

        loginSuccessful = loginUsername != null
                && loginPassword != null
                && loginUsername.equals(registeredUsername)
                && loginPassword.equals(registeredPassword);

        return loginSuccessful;
    }

    // Returns the appropriate login status message.
    public String returnLoginStatus() {

        if (loginSuccessful) {

            return "Welcome " + firstName + " " + lastName
                    + " it is great to see you again.";
        }

        return "Username or password incorrect, please try again.";
    }
}