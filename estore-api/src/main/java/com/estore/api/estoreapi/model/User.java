package com.estore.api.estoreapi.model;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * This abstract class is made to represent a user
 * A user can be a customer or the estore owner
 * 
 * @author Priyank Patel
 */

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = Customer.class, name = "customer"),
    @JsonSubTypes.Type(value = Admin.class, name = "admin")
})
public abstract class User {
    private static final Logger LOG = Logger.getLogger(User.class.getName());

    static final String STRING_FORMAT = "User [userId=%d, username=%s]";

    // TODO: investigate all the fields that are being serialized (there are some duplicates)
    @JsonProperty("userId") private int userId;
    @JsonProperty("username") private String username;
    @JsonProperty("loggedIn") private boolean loggedIn;
    @JsonProperty("isAdmin") private boolean isAdmin;

    /**
     * Create a User with the given userID, username and the default
     * state of loggedIn as true
     * @param userId The User ID of the user
     * @param username The Username of the user
     */
    public User(@JsonProperty("userId") int userId, @JsonProperty("username") String username){
        this.userId = userId;
        this.username = username;
        this.loggedIn = true;
        if(userId == 0){isAdmin = true;}    else isAdmin = false;
    }
    
    /**
     * Changes the state of the boolean to represent 
     * the user is logged out
     */
    public void logIn() {this.loggedIn = true;}

    /**
     * Changes the state of the boolean to represent 
     * the user is logged in
     */
    public void logOut() {this.loggedIn = false;}

    /**
     * Returns the logged in state of the user
     * @return loggedIn
     */
    public boolean isLoggedIn(){return loggedIn;}

    /**
     * Returns the username of the user
     * @return username
     */
    public String getUsername() {return username;}

    /**
     * Returns the userId of the user
     * @return userId
     */
    public int getUserId() {return userId;}

    /**
     * Checks if the username of the user is the same as the name of the
     * user passed in.
     * @param usernameToCheck The name to check
     * @return True if the name of the username is the same as the username passed in
     */
    public boolean usernameEquals(String usernameToCheck){
        return this.username.equals(usernameToCheck);
    }

    /**
     * Returns the value of isAdmin
     * @return isAdmin
     */
        public boolean isAdmin() {return isAdmin;}

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString(){
        return String.format(STRING_FORMAT, userId, username);
    }
}
