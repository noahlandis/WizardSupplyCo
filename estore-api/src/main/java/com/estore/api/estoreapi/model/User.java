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

    static final String STRING_FORMAT = "User [userId=%d, userName=%s]";

    // TODO: investigate all the fields that are being serialized (there are some duplicates)
    @JsonProperty("userId") private int userId;
    @JsonProperty("userName") private String userName;
    @JsonProperty("loggedIn") private boolean loggedIn;
    @JsonProperty("isAdmin") private boolean isAdmin;

    /**
     * Create a User with the given userID, userName and the default
     * state of loggedIn as true
     * @param userId The User ID of the user
     * @param userName The Username of the user
     */
    public User(@JsonProperty("userId") int userId, @JsonProperty("userName") String userName){
        this.userId = userId;
        this.userName = userName;
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
     * @return userName
     */
    public String getUserName() {return userName;}

    /**
     * Returns the userId of the user
     * @return userId
     */
    public int getUserId() {return userId;}

    /**
     * Checks if the userName of the user is the same as the name of the
     * user passed in.
     * @param userNameToCheck The name to check
     * @return True if the name of the userName is the same as the userName passed in
     */
    public boolean userNameEquals(String userNameToCheck){
        return this.userName.equals(userNameToCheck);
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
        return String.format(STRING_FORMAT, userId, userName);
    }
}
