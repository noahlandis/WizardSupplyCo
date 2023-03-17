package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Tests for the User class and its Child classes
 * @author Priyank Patel
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
    }
    
    @Test
    public void testlogIn(){
        //Setup
        int userId = 10;
        String username = new String("Oz");

        //Invoke
        Customer customer = new Customer(userId, username);

        //Analyze
        assertEquals(customer.isLoggedIn(), true);
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
        assertEquals(customer.isLoggedIn(), false);
    }

    @Test
    public void adminTestConstructor(){
        //Invoke
        Admin admin = new Admin();
        
        //Analyze
        assertEquals(admin.getUsername(), "admin");
    }
}
