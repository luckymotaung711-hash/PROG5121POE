/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.prog5121poe;

/**
 *
 * @author tumelo motaung
 */
import java.util.Scanner;

public class QuickChat {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        System.out.println("=================================");
        System.out.println("        WELCOME TO QUICKCHAT");
        System.out.println("=================================");

        // Registration
        System.out.println("\n--- REGISTRATION ---");

        System.out.print("Enter your first name: ");
        String firstName = input.nextLine();

        System.out.print("Enter your last name: ");
        String lastName = input.nextLine();

        System.out.print("Enter your username: ");
        String username = input.nextLine();

        System.out.print("Enter your password: ");
        String password = input.nextLine();

        System.out.print("Enter your South African cell phone number: ");
        String cellPhoneNumber = input.nextLine();

        // Create the Login object
        Login user = new Login(
                username,
                password,
                cellPhoneNumber,
                firstName,
                lastName
        );

        // Register the user
        String registrationMessage = user.registerUser();

        System.out.println("\n" + registrationMessage);

        // Continue only if registration was successful
if (registrationMessage.equals("Registration successful.")) {

    System.out.println("Username successfully captured.");
    System.out.println("Password successfully captured.");
    System.out.println("Cell phone number successfully added.");

    System.out.println("\n--- LOGIN ---");
    System.out.print("Enter username: ");
    String loginUsername = input.nextLine();

    System.out.print("Enter password: ");
    String loginPassword = input.nextLine();

            // Store the login details
            user.setLoginDetails(loginUsername, loginPassword);

            // Check login
            user.loginUser();

            // Display login status
            System.out.println(user.returnLoginStatus());

         } else {

            System.out.println(
                    "Please correct your registration details before trying to log in."
            );
        }

        input.close();
        
    }
}