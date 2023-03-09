package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        String userName = new String("Voldemort");

        //Invoke
        Customer customer = new Customer(userName, userId);

        //Analyze
        assertEquals(userName, customer.getUserName());
        assertEquals(userId, customer.getUserId());
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
    public void testUserNameEquals(){
         //Setup
         int userId = 16;
         String userName1 = new String("Mack");
         String userName2 = new String( "Mack");

         //Invoke
         Customer customer = new Customer(userName1,userId);

         //Analyze
         assertEquals(customer.userNameEquals(userName2),true);
         
    }  
    
    @Test
    public void testUserNameNotEquals(){
        //Setup
        int userId = 16;
        String userName1 = new String("Mack");
        String userName2 = new String( "Mk");

        //Invoke
        Customer customer = new Customer(userName1,userId);

        //Analyze
        assertEquals(customer.userNameEquals(userName2),false);
        
   }  
   
    
    @Test
    public void testIsAdmin(){
        //Setup
        Admin admin = new Admin();

        //Analyze
        assertEquals(admin.isAdmin(), true);
    }

    @Test
    public void testIsNotAdmin(){
        //Setup
        int userId = 11;
        String userName1 = new String("ka");

        //Invoke
        Customer customer = new Customer(userName1,userId);

        //Analyze
        assertEquals(customer.isAdmin(), false);
    }

    @Test
    public void adminTestConstructor(){
        //Invoke
        Admin admin = new Admin();
        
        //Analyze
        assertEquals(admin.getUserName(), "admin");
    }

    @Test
    public void testToString() {
        //Setup
        int userId = 22;
        String userName = new String("Mackenzie");

        //Invoke
        Customer customer = new Customer(userName, userId);

        String expectedString = String.format(User.STRING_FORMAT, userId, userName);

        // Invoke
        String actualString = customer.toString();

        // Analyze
        assertEquals(expectedString, actualString);
    }
}
