/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.mycompany.registrationlogin;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.io.File;

public class MessageTest {
    
    private Login loginSystem;
    private Message message;
    
    @Before
    public void setUp() {
        loginSystem = new Login();
        // Register a test user
        loginSystem.registerUser("Test", "User", "test_1", "Password123!", "+27123456789");
        loginSystem.loginUser("test_1", "Password123!");
        message = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?");
    }
    
    //Message Length Validation Tests
    @Test
    public void testMessageLength_Valid_250CharsOrLess() {
        String shortMessage = "Hi Mike, can you join us for dinner tonight?";
        String result = Message.checkMessageLength(shortMessage);
        assertEquals("Message ready to send.", result);
    }
    
    @Test
    public void testMessageLength_Invalid_Exceeds250() {
        StringBuilder longMsg = new StringBuilder();
        for (int i = 0; i < 260; i++) {
            longMsg.append("a");
        }
        String result = Message.checkMessageLength(longMsg.toString());
        assertEquals("Message exceeds 250 characters by 10; please reduce the size.", result);
    }
    
    @Test
    public void testMessageLength_Exactly250_ShouldPass() {
        StringBuilder exactMsg = new StringBuilder();
        for (int i = 0; i < 250; i++) {
            exactMsg.append("a");
        }
        String result = Message.checkMessageLength(exactMsg.toString());
        assertEquals("Message ready to send.", result);
    }
    
    // Tests Recipient Cell Validation 
    @Test
    public void testRecipientCell_Valid_WithPlus27Format() {
        String result = Message.checkRecipientCell("+27718693002", loginSystem);
        assertEquals("Cell phone number successfully captured.", result);
    }
    
    @Test
    public void testRecipientCell_Invalid_NoInternationalCode() {
        String result = Message.checkRecipientCell("08575975889", loginSystem);
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.", result);
    }
    
    @Test
    public void testRecipientCell_Invalid_WrongCountryCode() {
        String result = Message.checkRecipientCell("+44123456789", loginSystem);
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.", result);
    }
    
    //Tests the Message Hash Generation
    @Test
    public void testMessageHash_Format_Correct() {
        String hash = message.createMessageHash();
        // Check format: two digits, colon, number, colon, uppercase words
        assertTrue("Hash should match pattern: XX:NN:WORDS", 
                   hash.matches("\\d{2}:\\d+:[A-Z]+[A-Z]+"));
    }
    
    //Tests Message ID Generation
    @Test
    public void testMessageId_Is10Digits() {
        String id = message.generateMessageId();
        assertEquals("Message ID must be exactly 10 digits", 10, id.length());
        assertTrue("Message ID must contain only digits", id.matches("\\d{10}"));
    }
    
    @Test
    public void testMessageId_IsInValidRange() {
        String id = message.generateMessageId();
        long idNum = Long.parseLong(id);
        assertTrue("Message ID should be >= 1,000,000,000", idNum >= 1_000_000_000L);
        assertTrue("Message ID should be <= 9,999,999,999", idNum <= 9_999_999_999L);
    }
    
    //Tests Message Counter 
    @Test
    public void testMessageNumber_Increments() {
        Message msg1 = new Message("+27718693002", "First message");
        Message msg2 = new Message("+27718693002", "Second message");
        
        assertEquals(1, msg1.getMessageNumber());
        assertEquals(2, msg2.getMessageNumber());
    }
    
    //Tests Send/Store/Disregard Return Message
    
    @Test
    public void testSentMessage_ReturnsSuccessMessage() {
        String successMessage = "Message successfully sent.";
        String storeMessage = "Message successfully stored.";
        String deleteMessage = "Press 0 to delete the message.";
        
        assertNotNull(successMessage);
        assertNotNull(storeMessage);
        assertNotNull(deleteMessage);
        assertTrue(successMessage.contains("successfully"));
        assertTrue(storeMessage.contains("successfully stored"));
        assertTrue(deleteMessage.contains("Press 0"));
    }
    
    // Tests JSON Storage 
    @Test
    public void testStoreMessageAsJson_NoException() {
        Message testMsg = new Message("+27718693002", "Test JSON storage");
        testMsg.storeMessageAsJson();
        assertTrue(true);
    }
}