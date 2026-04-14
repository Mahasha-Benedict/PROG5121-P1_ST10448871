/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */


package com.mycompany.registrationlogin;

import java.util.Scanner;

/**
 *
 * @author bened
 */
public class RegistrationLogin {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Login loginSystem = new Login();

        while (true) {
            System.out.println("\n1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> {
                    System.out.print("Enter your first name: ");
                    String firstName = scanner.nextLine();
                    System.out.print("Enter your last name: ");
                    String lastName = scanner.nextLine();

                    System.out.print("Enter username (must have an underscore and no more than 5 characters): ");
                    String username = scanner.nextLine();
                    System.out.print("Enter password (min 8 chars, 1 uppercase, 1 digit, 1 special char): ");
                    String password = scanner.nextLine();
                    System.out.print("Enter cellphone number (e.g. +27123456789): ");
                    String cellphone = scanner.nextLine();

                    String registrationMessage = loginSystem.registerUser(firstName, lastName, username, password, cellphone);
                    System.out.println(registrationMessage);
                }

                case "2" -> {
                    System.out.print("Enter username: ");
                    String loginUsername = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String loginPassword = scanner.nextLine();

                    boolean success = loginSystem.loginUser(loginUsername, loginPassword);
                    System.out.println(loginSystem.returnLoginStatus(success));
                }

                case "3" -> {
                    System.out.println("Exiting... Goodbye!");
                    scanner.close();
                    return;
                }

                default -> System.out.println("Invalid option. Please choose 1, 2, or 3.");
            }
        }
    }
}
