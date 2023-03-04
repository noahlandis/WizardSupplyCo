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
    //@JsonIgnore("cart") private Cart cart;

    public Customer(String userName, int userId){
        super(userId,userName);
        //cart = new Cart(userId);
    }

    // public Cart getCart(){
    //     return this.cart;
    // }
}