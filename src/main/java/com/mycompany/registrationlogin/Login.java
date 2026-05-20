/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.registrationlogin;

import java.util.HashMap;
import java.util.regex.Pattern;

/**
 *
 * @author bened
 */
class Login {
    private final HashMap<String, User> users = new HashMap<>();
    private User loggedInUser;

    public boolean checkUserName(String username) {
        return username.contains("_") && username.length() <= 5;
    }

    public boolean checkPasswordComplexity(String password) {
        return password.length() >= 8 &&
                password.chars().anyMatch(Character::isUpperCase) &&
                password.chars().anyMatch(Character::isDigit) &&
                Pattern.compile("[!@#$%^&*(),.?\":{}|<>]").matcher(password).find();
    }

    public boolean checkCellPhoneNumber(String cellphone) {
        return cellphone.matches("^\\+27[0-9]{9}$");
    }

    public String registerUser(String firstName, String lastName, String username, String password, String cellphone) {
        boolean validUsername = checkUserName(username);
        boolean validPassword = checkPasswordComplexity(password);
        boolean validCellphone = checkCellPhoneNumber(cellphone);

        if (!validUsername) {
            return "Incorrect Username format,username MUST HAVE an underscore and is no more than five characters in length.";
        }
        if (!validPassword) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
        if (!validCellphone) {
            return "Cellphone number incorrectly formatted or does not contain international code.";
        }

        users.put(username, new User(firstName, lastName, username, password, cellphone));
        return "Username successfully captured.\nPassword successfully captured.\nCellphone number successfully added.\nRegistration has been captured!";
    }

    public boolean loginUser(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            loggedInUser = user;
            return true;
        }
        return false;  
    }
    
     public boolean isLoggedIn() {
        return loggedInUser != null;
    }
     public String getCurrentUsername() {
        return loggedInUser != null ? loggedInUser.getUsername() : null;
    }

    public String returnLoginStatus(boolean loginSuccess) {
        if (loginSuccess) {
            return "Welcome " + loggedInUser.getFirstName() + ", " + loggedInUser.getLastName() + " it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
}