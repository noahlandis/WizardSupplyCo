package com.estore.api.estoreapi.model;

/**
 * This class is made for the owner of the 
 */
public class Admin extends User {

    private static final String ADMIN_USERNAME = "admin";
    private static final int ADMIN_USER_ID = 0;
    
    /**
     * Creates a user which has a username and userId unique
     * to the estore Owner 
     */
    public Admin(){
        super(ADMIN_USER_ID, ADMIN_USERNAME);
    }
    
}
