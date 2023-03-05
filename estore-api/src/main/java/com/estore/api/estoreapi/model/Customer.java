package com.estore.api.estoreapi.model;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

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
    public Customer(String userName, int userId){
        super(userId, userName);
    }
}