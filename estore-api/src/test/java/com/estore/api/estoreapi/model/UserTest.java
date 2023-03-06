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
        String userName = new String("Voldemort");

        //Invoke
        Customer customer = new Customer(userName, userId);

        //Analyze
        assertEquals(userName, customer.getUserName());
    }
    
    @Test
    public void testlogIn(){
        //Setup
        int userId = 10;
        String userName = new String("Oz");

        //Invoke
        Customer customer = new Customer(userName,userId);

        //Analyze
        assertEquals(customer.isLoggedIn(), true);
    }

    @Test
    public void testlogOut(){
        //Setup
        int userId = 12;
        String userName = new String("Wizrd");

        //Invoke
        Customer customer = new Customer(userName,userId);
        customer.logOut();

        //Analyze
        assertEquals(customer.isLoggedIn(), false);
    }

    @Test
    public void adminTestConstructor(){
        //Invoke
        Admin admin = new Admin();
        
        //Analyze
        assertEquals(admin.getUserName(), "admin");
    }
}
