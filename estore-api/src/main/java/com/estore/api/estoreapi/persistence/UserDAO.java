package com.estore.api.estoreapi.persistence;

import java.io.IOException;
import com.estore.api.estoreapi.model.User;

/**
 * Defines the User interface for User object persistence
 * 
 * @author Kanisha Agrawal
 */
public interface UserDAO {
   /**
     * Retrieves all {@linkplain User users}
     * 
     * @return An array of {@link User user} objects, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    User[] getUsers() throws IOException;

     /**
     * Retrieves a {@linkplain User user} with the given userId
     * 
     * @param userId The userId of the {@link User user} to get
     * 
     * @return a {@link User user} object with the matching userId
     * <br>
     * null if no {@link User user} with a matching userId is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    User getUser(int userId) throws IOException;


    /**
     * Creates and saves a {@linkplain User user}
     * 
     * @param userId {@linkplain User user} object to be created and saved
     * <br>
     * The userId of the user object is ignored and a new uniqe userId is assigned
     * Checks if the user already exists by name and if so, returns null
     *
     * @return new {@link User user} if successful, null otherwise 
     * 
     * @throws IOException if an issue with underlying storage
     */
    User createUser(User user) throws IOException;

}
    
