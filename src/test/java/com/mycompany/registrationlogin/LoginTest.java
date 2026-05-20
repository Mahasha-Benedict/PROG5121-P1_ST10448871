package com.mycompany.registrationlogin;

import org.junit.Test;
import static org.junit.Assert.*;

public class LoginTest {
    
    private Login loginSystem = new Login();
    
    // ========== assertEquals TESTS ==========
    
    @Test
    public void testRegisterUser_UsernameValid() {
        System.out.println("Testing: Username correctly formatted");
        
        String result = loginSystem.registerUser("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        
        String expected = "Username successfully captured.\nPassword successfully captured.\nCellphone number successfully added.\nRegistration has been captured!";
        
        assertEquals(expected, result);
    }
    
    @Test
    public void testRegisterUser_UsernameInvalid() {
        System.out.println("Testing: Username incorrectly formatted");
        
        String result = loginSystem.registerUser("Kyle", "Smith", "kyle!!!!!!!", "Ch&&sec@ke99!", "+27838968976");
        
        String expected = "Incorrect Username format,username MUST HAVE an underscore and is no more than five characters in length.";
        
        assertEquals(expected, result);
    }
    
    @Test
    public void testRegisterUser_PasswordValid() {
        System.out.println("Testing: Password meets complexity requirements");
        
        String result = loginSystem.registerUser("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        
        assertTrue(result.contains("Password successfully captured"));
    }
    
    @Test
    public void testRegisterUser_PasswordInvalid() {
        System.out.println("Testing: Password does not meet complexity requirements");
        
        String result = loginSystem.registerUser("Kyle", "Smith", "kyl_1", "password", "+27838968976");
        
        String expected = "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        
        assertEquals(expected, result);
    }
    
    @Test
    public void testRegisterUser_CellphoneValid() {
        System.out.println("Testing: Cell phone correctly formatted");
        
        String result = loginSystem.registerUser("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        
        assertTrue(result.contains("Cellphone number successfully added"));
    }
    
    @Test
    public void testRegisterUser_CellphoneInvalid() {
        System.out.println("Testing: Cell phone number incorrectly formatted");
        
        String result = loginSystem.registerUser("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "08966553");
        
        String expected = "Cellphone number incorrectly formatted or does not contain international code.";
        
        assertEquals(expected, result);
    }
    
    // ========== assertTrue/assertFalse TESTS ==========
    
    @Test
    public void testLoginUser_Successful() {
        System.out.println("Testing: Login Successful");
        
        loginSystem.registerUser("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        
        boolean result = loginSystem.loginUser("kyl_1", "Ch&&sec@ke99!");
        
        assertTrue(result);
    }
    
    @Test
    public void testLoginUser_Failed() {
        System.out.println("Testing: Login Failed");
        
        loginSystem.registerUser("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        
        boolean result = loginSystem.loginUser("kyl_1", "wrongpassword");
        
        assertFalse(result);
    }
    
    @Test
    public void testCheckUserName_Valid() {
        System.out.println("Testing: Username correctly formatted");
        
        boolean result = loginSystem.checkUserName("kyl_1");
        
        assertTrue(result);
    }
    
    @Test
    public void testCheckUserName_Invalid() {
        System.out.println("Testing: Username incorrectly formatted");
        
        boolean result = loginSystem.checkUserName("kyle!!!!!!!");
        
        assertFalse(result);
    }
    
    @Test
    public void testCheckPasswordComplexity_Valid() {
        System.out.println("Testing: Password meets complexity requirements");
        
        boolean result = loginSystem.checkPasswordComplexity("Ch&&sec@ke99!");
        
        assertTrue(result);
    }
    
    @Test
    public void testCheckPasswordComplexity_Invalid() {
        System.out.println("Testing: Password does not meet complexity requirements");
        
        boolean result = loginSystem.checkPasswordComplexity("password");
        
        assertFalse(result);
    }
    
    @Test
    public void testCheckCellPhoneNumber_Valid() {
        System.out.println("Testing: Cell phone number correctly formatted");
        
        boolean result = loginSystem.checkCellPhoneNumber("+27838968976");
        
        assertTrue(result);
    }
    
    @Test
    public void testCheckCellPhoneNumber_Invalid() {
        System.out.println("Testing: Cell phone number incorrectly formatted");
        
        boolean result = loginSystem.checkCellPhoneNumber("08966553");
        
        assertFalse(result);
    }
    
    @Test
    public void testReturnLoginStatus_Success() {
        System.out.println("Testing: Login success message");
        
        loginSystem.registerUser("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        loginSystem.loginUser("kyl_1", "Ch&&sec@ke99!");
        
        String result = loginSystem.returnLoginStatus(true);
        String expected = "Welcome Kyle, Smith it is great to see you again.";
        
        assertEquals(expected, result);
    }
    
    @Test
    public void testReturnLoginStatus_Failure() {
        System.out.println("Testing: Login failure message");
        
        String result = loginSystem.returnLoginStatus(false);
        String expected = "Username or password incorrect, please try again.";
        
        assertEquals(expected, result);
    }
}
