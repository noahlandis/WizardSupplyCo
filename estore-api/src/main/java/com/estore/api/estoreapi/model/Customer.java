package com.estore.api.estoreapi.model;

/**
 * This class represents a Customer and it extends the abstract
 * class "User"
 * 
 * @author Priyank Patel
 */
public class Customer extends User{
    /**
     * This creates a user with the given username and userId
     * 
     * @param username - username for the customer
     * @param userId - userId of the 
     */
    public Customer(int userId, String username){
        super(userId, username);
    }
}