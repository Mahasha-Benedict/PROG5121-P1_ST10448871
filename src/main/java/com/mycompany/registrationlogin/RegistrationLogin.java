package com.mycompany.registrationlogin;

import java.util.Scanner;

public class RegistrationLogin {
    
    private static Scanner scanner = new Scanner(System.in);
    private static Login loginSystem = new Login();
    
    public static void main(String[] args) {
        
        while (true) {
            System.out.println("\n       ===============    ");
            System.out.println("      REGISTRATION LOGIN    ");
            System.out.println("       ===============      ");
            System.out.println("        1. Register          ");
            System.out.println("        2. Login             ");
            System.out.println("        3. Exit              ");
            System.out.println("       ==============");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1":
                    registerNewUser();
                    break;
                case "2":
                    loginUser();
                    break;
                case "3":
                    System.out.println("\nExiting... Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid input. Please choose 1, 2, or 3.");
            }
        }
    }
    
    private static void registerNewUser() {
        System.out.println("\n==REGISTRATION ==\n");
        
        // First Name
        String firstName = "";
        while (true) {
            System.out.print("Enter your first name: ");
            firstName = scanner.nextLine().trim();
            if (firstName.isEmpty()) {
                System.out.println("First name cannot be empty.\n");
            } else if (!firstName.matches("[a-zA-Z]+")) {
                System.out.println("First name must contain only letters.\n");
            } else {
                System.out.println("First name accepted!\n");
                break;
            }
        }
        
        // Last Name
        String lastName = "";
        while (true) {
            System.out.print("Enter your last name: ");
            lastName = scanner.nextLine().trim();
            if (lastName.isEmpty()) {
                System.out.println("Last name cannot be empty.\n");
            } else if (!lastName.matches("[a-zA-Z]+")) {
                System.out.println("Last name must contain only letters.\n");
            } else {
                System.out.println("Last name accepted!\n");
                break;
            }
        }
        
        // Username
        String username = "";
        while (true) {
            System.out.print("Enter username (must contain '_' and be ? 5 characters): ");
            username = scanner.nextLine().trim();
            if (loginSystem.checkUserName(username)) {
                System.out.println("Username accepted!\n");
                break;
            } else {
                System.out.println("Incorrect username format!");
                System.out.println("   - Must contain an underscore (_)");
                System.out.println("   - Must be 5 characters or less\n");
            }
        }
        
        // Password
        String password = "";
        while (true) {
            System.out.print("Enter password (min 8 chars, 1 uppercase, 1 digit, 1 special char): ");
            password = scanner.nextLine();
            if (loginSystem.checkPasswordComplexity(password)) {
                System.out.println("Password accepted!\n");
                break;
            } else {
                System.out.println("Password requirements not met!\n");
            }
        }
        
        // Cellphone
        String cellphone = "";
        while (true) {
            System.out.print("Enter cellphone number ( start with '+27' followed by 9 digits): ");
            cellphone = scanner.nextLine().trim();
            if (loginSystem.checkCellPhoneNumber(cellphone)) {
                System.out.println("Cellphone accepted!\n");
                break;
            } else {
                System.out.println("Incorrect format! Use +27 then 9 digits\n");
            }
        }
        
        String result = loginSystem.registerUser(firstName, lastName, username, password, cellphone);
        System.out.println(result);
    }
    
    private static void loginUser() {
        System.out.println("\nLOGIN \n");
        
        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();
        
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        
        boolean success = loginSystem.loginUser(username, password);
        System.out.println("\n" + loginSystem.returnLoginStatus(success));
        
        if (success) {
            // Part 2: After successful login, displays QuickChat menu
            showQuickChatMenu();
        }
    }
    
    // Part 2 QuickChat Messaging Menu 
    /**
     * Displays the QuickChat messaging menu after login
     * Users can only send messages if logged in
     */
    private static void showQuickChatMenu() {
        boolean quitQuickChat = false;
        
        while (!quitQuickChat) {
            System.out.println("\n    ==============        ");
            System.out.println("    Welcome to QuickChat    ");
            System.out.println("      ==============        ");
            System.out.println("        1. Send Messages          ");
            System.out.println("        2. Show recently sent messages (Coming Soon)");
            System.out.println("        3. Quit              ");
            System.out.println("      ==============        ");
            System.out.print("Choose an option: ");
            
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1":
                    // Send Messages - Now ask how many
                    sendMessagesFlow();
                    break;
                    
                case "2":
                    // Coming Soon feature
                    System.out.println("\n      ==============        ");
                    System.out.println("         COMING SOON!        ");
                    System.out.println("      ==============        ");
                    break;
                    
                case "3":
                    // Quit - return to main menu
                    quitQuickChat = true;
                    System.out.println("\nReturning to main menu...");
                    break;
                    
                default:
                    System.out.println("Invalid option. Please choose 1, 2, or 3.");
            }
        }
    }
    
    /**
     * Handles the message sending flow after user selects Option 1
     */
    private static void sendMessagesFlow() {
        // User defines how many messages they wish to enter
        System.out.print("\nHow many messages do you want to send? ");
        int maxMessages = scanner.nextInt();
        scanner.nextLine(); 
        
        int messageCount = 0;
        
        while (messageCount < maxMessages) {
            System.out.println("\n--- Message " + (messageCount + 1) + " of " + maxMessages + " ---");
            
            // Get recipient cell number (from part 1 validation)
            String recipient = "";
            while (true) {
                System.out.print("Enter recipient cell number (+27 followed by 9 digits): ");
                recipient = scanner.nextLine().trim();
                String validationResult = Message.checkRecipientCell(recipient, loginSystem);
                if (validationResult.equals("Cell phone number successfully captured.")) {
                    System.out.println(validationResult);
                    break;
                } else {
                    System.out.println(validationResult);
                }
            }
            
            // Gets the message text with length validation
            String messageText = "";
            while (true) {
                System.out.print("Enter your message (max 250 characters): ");
                messageText = scanner.nextLine();
                String validationResult = Message.checkMessageLength(messageText);
                if (validationResult.equals("Message ready to send.")) {
                    System.out.println(validationResult);
                    break;
                } else {
                    System.out.println(validationResult);
                }
            }
            
            // Create and process message
            Message message = new Message(recipient, messageText);
            String result = message.sentMessage(scanner);
            System.out.println("\n" + result);
            
            messageCount++;
        }
        
        // Displays the total messages sent
        System.out.println("\n      ============         ");
        System.out.println("Total messages sent: " + Message.returnTotalMessages());
        System.out.println("        ============        ");
    }
}
