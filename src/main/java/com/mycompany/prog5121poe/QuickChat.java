/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.prog5121poe;

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

            // Login
            System.out.println("\n--- LOGIN ---");

            System.out.print("Enter username: ");
            String loginUsername = input.nextLine();

            System.out.print("Enter password: ");
            String loginPassword = input.nextLine();

            user.setLoginDetails(loginUsername, loginPassword);
            user.loginUser();

            System.out.println(user.returnLoginStatus());

            // Show menu only after successful login
            if (user.loginUser()) {

                System.out.println("Welcome to QuickChat.");

                int choice = 0;

                while (choice != 3) {

                    System.out.println("\n1) Send Messages");
                    System.out.println("2) Show recently sent messages");
                    System.out.println("3) Quit");
                    System.out.print("Choose an option: ");

                    if (!input.hasNextInt()) {
                        System.out.println("Please enter a number.");
                        input.nextLine();
                        continue;
                    }

                    choice = input.nextInt();
                    input.nextLine();

                    switch (choice) {

                        case 1:
                            System.out.println("Send Messages selected.");

                            System.out.print(
                                    "How many messages would you like to send? "
                            );

                            if (!input.hasNextInt()) {
                                System.out.println("Please enter a valid number.");
                                input.nextLine();
                                break;
                            }

                            int numberOfMessages = input.nextInt();
                            input.nextLine();

                            if (numberOfMessages <= 0) {
                                System.out.println(
                                        "Please enter a number greater than zero."
                                );
                                break;
                            }
                             int totalSent = 0; 
                            for (int i = 0; i < numberOfMessages; i++) {

                                System.out.println("\nMessage " + (i + 1));

                                System.out.print(
                                        "Enter recipient cell number with international code: "
                                );
                                String recipient = input.nextLine();

                                System.out.print(
                                        "Enter your message (maximum 250 characters): "
                                );
                                String messageText = input.nextLine();

                                Message newMessage =
                                        new Message(recipient, messageText, i);

                                if (!newMessage.checkMessageID()) {
                                    System.out.println("Invalid message ID.");
                                    continue;
                                }

                                String recipientResult = newMessage.checkRecipientCell();
System.out.println(recipientResult);

if (!recipientResult.equals(
        "Cell phone number successfully captured.")) {
    continue;
}                                

                                if (!newMessage.checkMessageLength()) {
    int excess = newMessage.getMessage().length() - 250;

    System.out.println("Message exceeds 250 characters by "
            + excess + "; please reduce the size.");
    continue;
}

System.out.println("Message ready to send.");

                                System.out.println(newMessage.printMessages());
                                String result = newMessage.SentMessage();
System.out.println(result);

if (result.equals("Message successfully sent.")) {
    totalSent++;
}
System.out.println("\nTotal messages sent: " + totalSent);
                            }
                            break;

                        case 2:
                            System.out.println("Coming Soon.");
                            break;

                        case 3:
                            System.out.println("Goodbye!");
                            break;

                        default:
                            System.out.println(
                                    "Invalid option. Please try again."
                            );
                    }
                }
            }

        } else {
            System.out.println(
                    "Please correct your registration details before trying to log in."
            );
        }

        input.close();
    }
}