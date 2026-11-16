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

        Login user = new Login(
                username, password, cellPhoneNumber, firstName, lastName
        );

        String registrationMessage = user.registerUser();
        System.out.println("\n" + registrationMessage);

        if (registrationMessage.equals("Registration successful.")) {

            System.out.println("\n--- LOGIN ---");

            System.out.print("Enter username: ");
            String loginUsername = input.nextLine();

            System.out.print("Enter password: ");
            String loginPassword = input.nextLine();

            user.setLoginDetails(loginUsername, loginPassword);
            user.loginUser();

            System.out.println(user.returnLoginStatus());

            if (user.loginUser()) {

                System.out.println("Welcome to QuickChat.");

                MessageManager manager = new MessageManager();
                System.out.println(manager.loadStoredMessages());

                int choice = 0;

                while (choice != 3) {

                    System.out.println("\n===== QUICKCHAT MENU =====");
                    System.out.println("1) Send Messages");
                    System.out.println("2) Show recently sent messages");
                    System.out.println("3) Quit");
                    System.out.println("4) Stored Messages");
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
                            System.out.print(
                                    "How many messages would you like to send? "
                            );

                            if (!input.hasNextInt()) {
                                System.out.println("Enter a valid number.");
                                input.nextLine();
                                break;
                            }

                            int numberOfMessages = input.nextInt();
                            input.nextLine();

                            if (numberOfMessages <= 0) {
                                System.out.println(
                                        "Enter a number greater than zero."
                                );
                                break;
                            }

                            for (int i = 0; i < numberOfMessages; i++) {

                                System.out.println("\nMessage " + (i + 1));

                                System.out.print("Enter recipient number: ");
                                String recipient = input.nextLine();

                                System.out.print("Enter your message: ");
                                String messageText = input.nextLine();

                                Message newMessage = new Message(
                                        recipient, messageText, i
                                );

                                if (!newMessage.checkMessageID()) {
                                    System.out.println("Invalid message ID.");
                                    continue;
                                }

                                String recipientResult =
                                        newMessage.checkRecipientCell();

                                System.out.println(recipientResult);

                                if (!recipientResult.equals(
                                        "Cell phone number successfully captured.")) {
                                    continue;
                                }

                                if (!newMessage.checkMessageLength()) {
                                    System.out.println(
                                            "Message exceeds 250 characters."
                                    );
                                    continue;
                                }

                                System.out.println(newMessage.printMessages());

                                String result = newMessage.SentMessage();
                                System.out.println(result);

                                if (result.equals(
                                        "Message successfully sent.")) {
                                    manager.addSentMessage(
                                            newMessage, username
                                    );
                                }

                                if (result.equals(
                                        "Press 0 to delete the message.")) {
                                    manager.addDisregardedMessage(newMessage);
                                }

                                if (result.equals(
                                        "Message successfully stored.")) {
                                    System.out.println(
                                            manager.loadStoredMessages()
                                    );
                                }
                            }
                            break;

                        case 2:
                            System.out.println("\n--- SENT MESSAGES ---");

                            for (Message message : manager.getSentMessages()) {
                                System.out.println(message.printMessages());
                                System.out.println("--------------------");
                            }

                            if (manager.getSentMessages().length == 0) {
                                System.out.println("No sent messages yet.");
                            }
                            break;

                        case 3:
                            System.out.println("Goodbye!");
                            break;

                        case 4:
                            storedMessagesMenu(manager, input);
                            break;

                        default:
                            System.out.println("Invalid option.");
                    }
                }
            }

        } else {
            System.out.println(
                    "Please correct your registration details."
            );
        }

        input.close();
    }

    public static void storedMessagesMenu(
            MessageManager manager, Scanner input) {

        int option = 0;

        while (option != 7) {

            System.out.println("\n===== STORED MESSAGES =====");
            System.out.println("1. Display senders and recipients");
            System.out.println("2. Display longest stored message");
            System.out.println("3. Search message by ID");
            System.out.println("4. Search messages by recipient");
            System.out.println("5. Delete message using hash");
            System.out.println("6. Display stored messages report");
            System.out.println("7. Return to main menu");
            System.out.print("Choose an option: ");

            if (!input.hasNextInt()) {
                System.out.println("Enter a valid number.");
                input.nextLine();
                continue;
            }

            option = input.nextInt();
            input.nextLine();

            switch (option) {

                case 1:
                    manager.displaySendersAndRecipients();
                    break;

                case 2:
                    manager.displayLongestMessage();
                    break;

                case 3:
                    System.out.print("Enter message ID: ");
                    String id = input.nextLine();
                    manager.searchByMessageID(id);
                    break;

                case 4:
                    System.out.print("Enter recipient number: ");
                    String recipient = input.nextLine();
                    manager.searchByRecipient(recipient);
                    break;

                case 5:
                    System.out.print("Enter message hash: ");
                    String hash = input.nextLine();
                    manager.deleteByHash(hash);
                    break;

                case 6:
                    manager.displayReport();
                    break;

                case 7:
                    System.out.println("Returning to main menu.");
                    break;

                default:
                    System.out.println("Invalid option.");
            }
        }
    }
}