/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.prog5121poe;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.Random;
import java.util.Scanner;

public class Message {

    private String messageID;
    private String recipient;
    private String message;
    private String messageHash;
    private int messageNumber;

    private static int totalMessages = 0;

    public Message(String recipient, String message, int messageNumber) {
        this.messageID = generateMessageID();
        this.recipient = recipient;
        this.message = message;
        this.messageNumber = messageNumber;
        this.messageHash = createMessageHash();
    }

    // Generate a random 10-digit message ID
    private String generateMessageID() {
        Random random = new Random();

        long number = 1_000_000_000L
                + (long) (random.nextDouble() * 9_000_000_000L);

        return String.valueOf(number);
    }

    // Check that the message ID has no more than 10 characters
    public boolean checkMessageID() {
        return messageID != null && messageID.length() <= 10;
    }

    // Validate the recipient's international cell number
    public String checkRecipientCell() {
        if (recipient != null
                && recipient.matches("^\\+[1-9]\\d{1,12}$")) {
            return "Cell phone number successfully captured.";
        }

        return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
    }

    // Check the message length
    public boolean checkMessageLength() {
        return message != null && message.length() <= 250;
    }

    // Create the message hash
    public String createMessageHash() {
        String cleanedMessage = message.trim()
                .replaceAll("[^a-zA-Z0-9\\s]", "");

        String[] words = cleanedMessage.isEmpty()
                ? new String[0] : cleanedMessage.split("\\s+");

        String firstWord = words.length > 0 ? words[0] : "";
        String lastWord = words.length > 0
                ? words[words.length - 1] : "";

        String firstTwoDigits = messageID.substring(0, 2);

        return (firstTwoDigits + ":" + messageNumber + ":"
                + firstWord + lastWord).toUpperCase();
    }

    // Allow the user to send, disregard or store a message
    public String SentMessage() {
        Scanner input = new Scanner(System.in);

        System.out.println("Choose an option:");
        System.out.println("1. Send Message");
        System.out.println("2. Disregard Message");
        System.out.println("3. Store Message");

        String choice = input.nextLine();

        switch (choice) {
            case "1":
                totalMessages++;
                return "Message successfully sent.";

            case "2":
                return "Press 0 to delete the message.";

            case "3":
    return storeMessage();

            default:
                return "Invalid option.";
        }
    }

    // Display the message details
    public String printMessages() {
        return "Message ID: " + messageID
                + "\nMessage Hash: " + messageHash
                + "\nRecipient: " + recipient
                + "\nMessage: " + message;
    }

    // Return the total number of messages sent
    public int returnTotalMessagess() {
        return totalMessages;
    }

    // Getter methods
    public String getMessageID() {
        return messageID;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessage() {
        return message;
    }

    public String getMessageHash() {
        return messageHash;
    }
    public String storeMessage() {
    String fileName = System.getProperty("user.dir")
        + java.io.File.separator + "messages.json"; 

    try {
        String existingData = "[]";

        if (Files.exists(Paths.get(fileName))) {
            existingData = new String(
                    Files.readAllBytes(Paths.get(fileName)),
                    StandardCharsets.UTF_8
            ).trim();

            if (existingData.isEmpty()) {
                existingData = "[]";
            }
        }

        // Prepare the message as JSON
        String jsonMessage = "{"
                + "\"messageID\":\"" + escapeJson(messageID) + "\","
                + "\"messageHash\":\"" + escapeJson(messageHash) + "\","
                + "\"recipient\":\"" + escapeJson(recipient) + "\","
                + "\"message\":\"" + escapeJson(message) + "\""
                + "}";

        // Add the new message to the JSON array
        if (existingData.equals("[]")) {
            existingData = "[" + jsonMessage + "]";
        } else if (existingData.startsWith("[")
                && existingData.endsWith("]")) {
            String content = existingData.substring(
                    1, existingData.length() - 1
            ).trim();

            existingData = "[" + content + ","
                    + jsonMessage + "]";
        } else {
            return "Could not store message: invalid JSON file.";
        }

        Files.write(
                Paths.get(fileName),
                existingData.getBytes(StandardCharsets.UTF_8)
        );
        System.out.println("JSON file location: "
        + Paths.get(fileName).toAbsolutePath());

        return "Message successfully stored.";

    } catch (IOException e) {
        return "Could not store message: " + e.getMessage();
    }
}

private String escapeJson(String value) {
    if (value == null) {
        return "";
    }

    return value.replace("\\", "\\\\")
            .replace("\"", "\\\"")
            .replace("\n", "\\n")
            .replace("\r", "\\r")
            .replace("\t", "\\t");
}
}
