package com.estore.api.estoreapi.model;

/**
 * This class is made for the owner of the 
 */
public class Admin extends User {

    private static final String ADMIN_USER_NAME = "admin";
    private static final int ADMIN_USER_ID = 1;
    public Admin(){
        super(ADMIN_USER_ID,ADMIN_USER_NAME);
    }
    
}
