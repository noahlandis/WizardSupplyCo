package com.estore.api.estoreapi.model;

/**
 * This class represents a Customer and it extends the abstract
 * class "User"
 * 
 * @author Priyank Patel
 */
public class Customer extends User{
    /**
     * This creates a user with the given userName and userId
     * 
     * @param userName - userName for the customer
     * @param userId - userId of the 
     */
    public Customer(int userId, String userName){
        super(userId, userName);
    }
}