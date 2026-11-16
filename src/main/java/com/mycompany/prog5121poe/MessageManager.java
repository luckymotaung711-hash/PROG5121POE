/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.prog5121poe;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageManager {

    private Message[] sentMessages = new Message[0];
    private Message[] disregardedMessages = new Message[0];
    private Message[] storedMessages = new Message[0];
    private String[] messageHashes = new String[0];
    private String[] messageIDs = new String[0];

    private String[] senders = new String[0];

    private final ArrayList<Message> sentList = new ArrayList<>();
    private final ArrayList<Message> disregardedList = new ArrayList<>();
    private final ArrayList<String> senderList = new ArrayList<>();

    // Add a sent message
    public void addSentMessage(Message message, String sender) {
        sentList.add(message);
        senderList.add(sender);
        updateArrays();
    }

    // Add a disregarded message
    public void addDisregardedMessage(Message message) {
        disregardedList.add(message);
        updateArrays();
    }

    // Update the arrays
    private void updateArrays() {
        sentMessages = sentList.toArray(new Message[0]);
        disregardedMessages = disregardedList.toArray(new Message[0]);
        senders = senderList.toArray(new String[0]);

        ArrayList<String> hashes = new ArrayList<>();
        ArrayList<String> ids = new ArrayList<>();

        for (Message message : sentMessages) {
            hashes.add(message.getMessageHash());
            ids.add(message.getMessageID());
        }

        for (Message message : disregardedMessages) {
            hashes.add(message.getMessageHash());
            ids.add(message.getMessageID());
        }

        for (Message message : storedMessages) {
            hashes.add(message.getMessageHash());
            ids.add(message.getMessageID());
        }

        messageHashes = hashes.toArray(new String[0]);
        messageIDs = ids.toArray(new String[0]);
    }

    // Read stored messages from messages.json
    public String loadStoredMessages() {
        String fileName = System.getProperty("user.dir")
                + java.io.File.separator + "messages.json";

        try {
            if (!Files.exists(Paths.get(fileName))) {
                storedMessages = new Message[0];
                updateArrays();
                return "No stored messages file found.";
            }

            String json = new String(
                    Files.readAllBytes(Paths.get(fileName)),
                    StandardCharsets.UTF_8
            );

            ArrayList<Message> loaded = new ArrayList<>();

            Pattern objectPattern = Pattern.compile("\\{([^{}]*)\\}");
            Matcher objectMatcher = objectPattern.matcher(json);

            while (objectMatcher.find()) {
                String object = objectMatcher.group(1);

                String id = getJsonValue(object, "messageID");
                String hash = getJsonValue(object, "messageHash");
                String recipient = getJsonValue(object, "recipient");
                String messageText = getJsonValue(object, "message");

                if (id != null && hash != null
                        && recipient != null && messageText != null) {

                    Message message = new Message(
                            id, recipient, messageText, hash, loaded.size()
                    );

                    loaded.add(message);
                }
            }

            storedMessages = loaded.toArray(new Message[0]);
            updateArrays();

            return storedMessages.length
                    + " stored message(s) loaded successfully.";

        } catch (IOException e) {
            return "Could not load messages: " + e.getMessage();
        }
    }

    // Read a value from a JSON object
    private String getJsonValue(String object, String key) {
        Pattern pattern = Pattern.compile(
                "\"" + key + "\"\\s*:\\s*\"((?:\\\\.|[^\"\\\\])*)\""
        );

        Matcher matcher = pattern.matcher(object);

        if (matcher.find()) {
            return unescapeJson(matcher.group(1));
        }

        return null;
    }

    // Convert escaped JSON text back to normal text
    private String unescapeJson(String value) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);

            if (c == '\\' && i + 1 < value.length()) {
                char next = value.charAt(++i);

                switch (next) {
                    case 'n':
                        result.append('\n');
                        break;
                    case 'r':
                        result.append('\r');
                        break;
                    case 't':
                        result.append('\t');
                        break;
                    case '"':
                        result.append('"');
                        break;
                    case '\\':
                        result.append('\\');
                        break;
                    default:
                        result.append(next);
                }
            } else {
                result.append(c);
            }
        }

        return result.toString();
    }

    // Display sender and recipient
    public void displaySendersAndRecipients() {
        if (storedMessages.length == 0) {
            System.out.println("No stored messages available.");
            return;
        }

        for (int i = 0; i < storedMessages.length; i++) {
            System.out.println("Sender: Unknown");
            System.out.println("Recipient: "
                    + storedMessages[i].getRecipient());
            System.out.println("-------------------------");
        }
    }

    // Display the longest stored message
    public void displayLongestMessage() {
        if (storedMessages.length == 0) {
            System.out.println("No stored messages available.");
            return;
        }

        Message longest = storedMessages[0];

        for (Message message : storedMessages) {
            if (message.getMessage().length()
                    > longest.getMessage().length()) {
                longest = message;
            }
        }

        System.out.println("Longest stored message:");
        System.out.println(longest.getMessage());
    }

    // Search by message ID
    public void searchByMessageID(String id) {
        for (Message message : storedMessages) {
            if (message.getMessageID().equals(id)) {
                System.out.println("Recipient: "
                        + message.getRecipient());
                System.out.println("Message: "
                        + message.getMessage());
                return;
            }
        }

        System.out.println("Message ID not found.");
    }

    // Search messages by recipient
    public void searchByRecipient(String recipient) {
        boolean found = false;

        for (Message message : storedMessages) {
            if (message.getRecipient().equals(recipient)) {
                System.out.println(message.getMessage());
                found = true;
            }
        }

        for (Message message : sentMessages) {
            if (message.getRecipient().equals(recipient)) {
                System.out.println(message.getMessage());
                found = true;
            }
        }

        if (!found) {
            System.out.println("No messages found for this recipient.");
        }
    }

    // Delete a stored message using its hash
    public void deleteByHash(String hash) {
        ArrayList<Message> remaining = new ArrayList<>();

        boolean deleted = false;

        for (Message message : storedMessages) {
            if (!deleted && message.getMessageHash().equals(hash)) {
                System.out.println("Message: "
                        + message.getMessage() + " successfully deleted.");
                deleted = true;
            } else {
                remaining.add(message);
            }
        }

        if (!deleted) {
            System.out.println("Message hash not found.");
            return;
        }

        storedMessages = remaining.toArray(new Message[0]);

        try {
            saveStoredMessages();
            updateArrays();
        } catch (IOException e) {
            System.out.println("Could not update JSON file: "
                    + e.getMessage());
        }
    }

    // Save remaining stored messages to JSON
    private void saveStoredMessages() throws IOException {
        StringBuilder json = new StringBuilder("[");

        for (int i = 0; i < storedMessages.length; i++) {
            Message message = storedMessages[i];

            if (i > 0) {
                json.append(",");
            }

            json.append("{")
                    .append("\"messageID\":\"")
                    .append(escapeJson(message.getMessageID()))
                    .append("\",")
                    .append("\"messageHash\":\"")
                    .append(escapeJson(message.getMessageHash()))
                    .append("\",")
                    .append("\"recipient\":\"")
                    .append(escapeJson(message.getRecipient()))
                    .append("\",")
                    .append("\"message\":\"")
                    .append(escapeJson(message.getMessage()))
                    .append("\"")
                    .append("}");
        }

        json.append("]");

        String fileName = System.getProperty("user.dir")
                + java.io.File.separator + "messages.json";

        Files.write(
                Paths.get(fileName),
                json.toString().getBytes(StandardCharsets.UTF_8)
        );
    }

    private String escapeJson(String value) {
        return value.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    // Display the complete stored message report
    public void displayReport() {
        if (storedMessages.length == 0) {
            System.out.println("No stored messages available.");
            return;
        }

        System.out.println("\n========== STORED MESSAGE REPORT ==========");

        for (Message message : storedMessages) {
            System.out.println(message.printMessages());
            System.out.println("-------------------------------------------");
        }
    }

    // Getters for the required arrays
    public Message[] getSentMessages() {
        return sentMessages;
    }

    public Message[] getDisregardedMessages() {
        return disregardedMessages;
    }

    public Message[] getStoredMessages() {
        return storedMessages;
    }

    public String[] getMessageHashes() {
        return messageHashes;
    }

    public String[] getMessageIDs() {
        return messageIDs;
    }
}