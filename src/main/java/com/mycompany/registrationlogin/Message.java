/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.registrationlogin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


/**
 * This Message class represents the chat message with all required fields for this second phase:
 * Unique Message ID with a 10-digit random number
 * Added auto-incremented message counter
 * Message text
 * Auto-generated Message Hash
 * Storage status (Sent, Stored, Disregarded)
 * And more.
 */
public class Message {
    
    private static int globalMessageCounter = 0;  // Auto-incremented across all messages
    private static List<Message> sentMessages = new ArrayList<>();  // Store all sent messages
    
    // Message fields
    private String messageId;        // A 10-digit random number
    private int messageNumber;       // The auto-incremented which starts at 1
    private String recipient;        // Cell number with the international code
    private String messageText;      // Max 250 characters
    private String messageHash;      // Auto hashing
    private String status;           // "Sent", "Stored", "Disregarded"
    
    /**
     * Constructor creates a new Message with auto-generated fields
     */
    public Message(String recipient, String messageText) {
        this.messageId = generateMessageId();
        this.messageNumber = ++globalMessageCounter;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageHash = createMessageHash();
        this.status = "Pending";
    }
    
    // Added Part 2 Message ID Generator
    /**
     * Generates a unique 10-digit random number for message tracking
     * Based on Apache Turbine's session ID generation pattern [citation:8]
     */
    public String generateMessageId() {
        Random random = new Random();
        // Generate number between 1,000,000,000 and 9,999,999,999
        long id = 1_000_000_000L + (long)(random.nextDouble() * 9_000_000_000L);
        return String.valueOf(id);
    }
    
    //Added the Message Hash Generation
    /**
     * Creates the Hash message in the format: [first 2 digits of ID]:[message number]:[first word][last word]
     * e.g 00:0:HITHANKS
     * All output are in UPPERCASE
     */
    public String createMessageHash() {
        // Gets first two numbers of Message ID
        String firstTwo = messageId.substring(0, 2);
        
        // Extract first and last words from message text
        String[] words = messageText.trim().split("\\s+");
        String firstWord = words.length > 0 ? words[0] : "";
        String lastWord = words.length > 1 ? words[words.length - 1] : firstWord;
        
        // Builds hash in appropriate format and convert string to uppercase
        String hash = firstTwo + ":" + messageNumber + ":" + firstWord + lastWord;
        return hash.toUpperCase();
    }
    
    // Implemented the Message Length Validation 
    /**
     * Which validates that the message does not exceed 250 characters
     * Returns appropriate message as specified in by requirements
     */
    public static String checkMessageLength(String message) {
        if (message.length() <= 250) {
            return "Message ready to send.";
        } else {
            int excess = message.length() - 250;
            return "Message exceeds 250 characters by " + excess + "; please reduce the size.";
        }
    }
    
    //Implemented the Recipient Cell number Validation 
    /**
     * Validates recipient cell number format (using the Login class validation)
     * Must have +27 with 9 digits
     */
    public static String checkRecipientCell(String cellNumber, Login loginSystem) {
        if (loginSystem.checkCellPhoneNumber(cellNumber)) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }
    
    //Implemented the Send/Store/Disregard Options
 
    public String sentMessage(Scanner scanner) {
        System.out.println("\nChoose an option:");
        System.out.println("1. Send Message");
        System.out.println("2. Store Message in JSON File");
        System.out.println("3. Disregard Message");
        System.out.print("Enter choice: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        switch (choice) {
            case 1:
                this.status = "Sent";
                sentMessages.add(this);
                System.out.println("\n=== Message Sent Successfully ===");
                printMessageDetails();
                return "Message successfully sent.";
                
            case 2:
                this.status = "Stored";
                storeMessageAsJson();
                return "Message successfully stored.";
                
            case 3:
                this.status = "Disregarded";
                return "Press 0 to delete the message.";
                
            default:
                return "Invalid option. Message not processed.";
        }
    }
    
    //Part 2. JSON Storage
    /**
     * Stores message in JSON file using Gson library
     * Reference: GitHub java-json-file-handling project [citation:2]
     * Uses pretty printing for human-readable JSON format
     */
    public void storeMessageAsJson() {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    // Read all existing messages from file
    List<Message> allMessages = readMessagesFromJson();
    
    // Add the current message to the list
    allMessages.add(this);
    
    // Write the entire array back to file (overwrites, doesn't append)
    try (FileWriter writer = new FileWriter("messages.json")) {
        gson.toJson(allMessages, writer);
        System.out.println("Message successfully stored to messages.json");
    } catch (IOException e) {
        System.out.println("Error storing message: " + e.getMessage());
    }
}
    private List<Message> readMessagesFromJson() {
    Gson gson = new Gson();
    File file = new File("messages.json");
    
    // If file doesn't exist, return empty list
    if (!file.exists()) {
        return new ArrayList<>();
    }
    
    // Try to read messages from file
    try (FileReader reader = new FileReader(file)) {
        // Read array of messages from JSON
        Message[] messages = gson.fromJson(reader, Message[].class);
        
        // Convert array to ArrayList and return
        if (messages != null) {
            return new ArrayList<>(Arrays.asList(messages));
        } else {
            return new ArrayList<>();
        }
    } catch (IOException e) {
        System.out.println("Warning: Could not read messages.json - " + e.getMessage());
        return new ArrayList<>();
    }
}
    
    // Shows message details.
    /**
     * Prints the full message details in this order, The:
     * Message ID, Message Hash, Recipient, Message
     */
    public void printMessageDetails() {
        System.out.println("Message ID: " + messageId);
        System.out.println("Message Hash: " + messageHash);
        System.out.println("Recipient: " + recipient);
        System.out.println("Message: " + messageText);
        System.out.println("Status: " + status);
    }
    
    // Implemted a Return of Total Messages Sent
    /**
     * Returns total number of messages sent during program execution
     */
    public static int returnTotalMessages() {
        return sentMessages.size();
    }
    
    /**
     * Prints all sent messages (for the "Coming Soon" feature)
     */
    public static String printMessages() {
        if (sentMessages.isEmpty()) {
            return "No messages sent yet.";
        }
        
        StringBuilder sb = new StringBuilder("\n=== All Sent Messages ===\n");
        for (int i = 0; i < sentMessages.size(); i++) {
            sb.append("\n--- Message ").append(i + 1).append(" ---\n");
            sb.append("ID: ").append(sentMessages.get(i).messageId).append("\n");
            sb.append("Hash: ").append(sentMessages.get(i).messageHash).append("\n");
            sb.append("To: ").append(sentMessages.get(i).recipient).append("\n");
            sb.append("Message: ").append(sentMessages.get(i).messageText).append("\n");
        }
        return sb.toString();
    }
    
    // Getters for testing purposes
    public String getMessageId() { return messageId; }
    public int getMessageNumber() { return messageNumber; }
    public String getRecipient() { return recipient; }
    public String getMessageText() { return messageText; }
    public String getMessageHash() { return messageHash; }
    public String getStatus() { return status; }
}

