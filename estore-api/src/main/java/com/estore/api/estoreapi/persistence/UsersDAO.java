package com.estore.api.estoreapi.persistence;

import java.io.IOException;
import com.estore.api.estoreapi.model.User;

/**
 * Defines the User interface for User object persistence
 * 
 * @author Kanisha Agrawal
 */
public interface UsersDAO {
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
     * @param userName {@linkplain User user} object to be created and saved
     * <br>
     * The userId of the user object is ignored and a new unique userId is assigned
     * Checks if the user already exists by name and if so, returns null
     *
     * @return new {@link User user} if successful, null otherwise 
     * 
     * @throws IOException if an issue with underlying storage
     */
    User createUser(String userName) throws IOException;

    /**
     * Log a user out {@linkplain User user}
     * 
     * @param userName {@linkplain User user} object to be logged out
     * <br>
     * Checks if the user already exists by name and if so, returns null
     *
     * @return user {@link User user} if successful, null otherwise 
     * 
     * @throws IOException if an issue with underlying storage
     */
    User LogOutUser(String userName) throws IOException;

      /**
     * Log a user in the website {@linkplain User user}
     * 
     * @param userName {@linkplain User user} object to be logged in
     * <br>
     * Checks if the user already exists by name and if so, returns null
     *
     * @return user {@link User user} if successful, null otherwise 
     * 
     * @throws IOException if an issue with underlying storage
     */
    User LoginUser(String userName) throws IOException;

}
    