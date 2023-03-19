package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Tests for the User class and its Child classes
 * @author Priyank Patel, Kanisha Agrawal
 */

@Tag("Model-tier")
public class UserTest {
    @Test
    public void testConstructor()   {
        //Setup
        int userId = 7;
        String username = new String("Voldemort");

        //Invoke
        Customer customer = new Customer(userId, username);

        //Analyze
        assertEquals(username, customer.getUsername());
        assertEquals(userId, customer.getUserId());
    }
    
    @Test
    public void testlogIn(){
        //Setup
        int userId = 10;
        String username = new String("Oz");

        //Invoke
        Customer customer = new Customer(userId, username);

        //Analyze
        assertTrue(customer.isLoggedIn());
    }

    @Test
    public void testlogOut(){
        //Setup
        int userId = 12;
        String username = new String("Wizrd");

        //Invoke
        Customer customer = new Customer(userId, username);
        customer.logOut();

        //Analyze
        assertFalse(customer.isLoggedIn());
    }

    @Test
    public void testUserNameEquals(){
         //Setup
         int userId = 16;
         String username1 = new String("Mack");
         String username2 = new String( "Mack");

         //Invoke
         Customer customer = new Customer(userId, username1);

         //Analyze
         assertTrue(customer.usernameEquals(username2));
         
    }  
    
    @Test
    public void testUserNameNotEquals(){
        //Setup
        int userId = 16;
        String username1 = new String("Mack");
        String username2 = new String( "Mk");

        //Invoke
        Customer customer =new Customer(userId, username1);

        //Analyze
        assertFalse(customer.usernameEquals(username2));
        
   }  
   
    
    @Test
    public void testIsAdmin(){
        //Setup
        Admin admin = new Admin();

        //Analyze
        assertTrue(admin.isAdmin());
    }

    @Test
    public void testIsNotAdmin(){
        //Setup
        int userId = 11;
        String username1 = new String("ka");

        //Invoke
        Customer customer = new Customer(userId, username1);

        //Analyze
        assertFalse(customer.isAdmin());
    }

    @Test
    public void adminTestConstructor(){
        //Invoke
        Admin admin = new Admin();
        
        //Analyze
        assertEquals(admin.getUsername(), "admin");
    }

    @Test
    public void testToString() {
        //Setup
        int userId = 22;
        String username = new String("Mackenzie");

        //Invoke
        Customer customer = new Customer(userId, username);

        String expectedString = String.format(User.STRING_FORMAT, userId, username);

        // Invoke
        String actualString = customer.toString();

        // Analyze
        assertEquals(expectedString, actualString);
    }
}
