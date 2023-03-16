package com.estore.api.estoreapi.persistence;

import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.estore.api.estoreapi.model.Customer;
import com.estore.api.estoreapi.persistence.CartsFileDAO;
import com.estore.api.estoreapi.persistence.CartsDAO;
import com.estore.api.estoreapi.model.User;

/**
 * Implements the functionality for JSON file-based peristance for Users
 * 
 * {@literal @}Component Spring annotation instantiates a single instance of this
 * class and injects the instance into other classes as needed
 * 
 * @author Kanisha Agrawal
 */
@Repository
public class UsersFileDAO implements UserDAO{
    private static final Logger LOG = Logger.getLogger(UsersFileDAO.class.getName()); 
    private Map<Integer,User> users;   // Provides a local cache of the users objects
                               // so that we don't need to read from the file
                               // each time
    private ObjectMapper objectMapper;  // Provides conversion between PUsers
                                        // objects and JSON text format written
                                        // to the file
    private static int nextUserId;  // The next Id to assign to a new user
    private String filename;    // Filename to read from and write to
    private CartsDAO cartsDAO;
    /**
     * Creates an User File Data Access Object
     * 
     * @param filename Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public UsersFileDAO(@Value("${users.file}") String filename, ObjectMapper objectMapper, CartsDAO cartsDAO) throws IOException {
        LOG.info("userFileDAO created");
        this.filename = filename;
        this.objectMapper = objectMapper;
        this.cartsDAO = cartsDAO;
        load();  // load the users from the file
    }

    /**
     * Generates the next Id for a new {@linkplain User user}
     * 
     * @return The next Id
     */
    private synchronized static int nextUserId() {
        int userId = nextUserId;
        ++nextUserId;
        return userId;
    }

    
    /**
     * Generates an array of {@linkplain User users} from the tree map
     * 
     * @return  The array of {@link User users}, may be empty
     */
    private User[] getUsersArray() {
        return getUsersArray(null);
    }

    /**
     * Generates an array of {@linkplain User users} from the tree map for any
     * {@linkplain User users} that contains the text specified by containsText
     * <br>
     * If containsText is null, the array contains all of the {@linkplain User users}
     * in the tree map
     * 
     * @return  The array of {@link User users}, may be empty
     */
    private User[] getUsersArray(String containsText) { // if containsText == null, no filter
        ArrayList<User> userArrayList = new ArrayList<>();
        /*This for loop goes through each product in the inventory product map */
        for (User user : users.values()) {
            if (containsText == null || user.getUserName().contains(containsText)) {
                userArrayList.add(user);
            }
        }

        User[] userArray = new User[userArrayList.size()];
        userArrayList.toArray(userArray);
        return userArray;
    }


    /**
     * Saves the {@linkplain User users} from the map into the file as an array of JSON objects
     * 
     * @return true if the {@link User users} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */    
    private boolean save() throws IOException {
        LOG.info("Saving users to file: " + filename);
        User[] userArray = getUsersArray();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename), userArray);
        return true;
    }

    /**
     * Loads {@linkplain User users} from the JSON file into the map
     * <br>
     * Also sets next Id to one more than the greatest if found in the file
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        LOG.info("Loading products from file: " + filename);
        users = new TreeMap<>();
        nextUserId = 0;

        // Deserializes the JSON objects from the file into an array of products
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        User[] userArray = objectMapper.readValue(new File(filename),User[].class);

        // Add each user to the tree map and keep track of the greatest Id
        for (User user : userArray) {
            users.put(user.getUserId(),user);
            if (user.getUserId() > nextUserId)
                nextUserId = user.getUserId();
        }
        // Make the next id one greater than the maximum from the file
        ++nextUserId;
        return true;
    }

   /**
    ** {@inheritDoc}
    */
    @Override
    public User[] getUsers() {
        synchronized(users) {
            return getUsersArray();
        }
    }

    
    /**
    ** {@inheritDoc}
     */
    @Override
    public User getUser(int userId) {
        synchronized(users) {
            if (users.containsKey(userId))
                return users.get(userId);
            else
                return null;
        }
    }

    
    /**
    ** {@inheritDoc}
     */
    @Override
    public User createUser(String username) throws IOException {
        synchronized(users) {
            // // check if an user with the same name already exists. If so, return null
            for (User user : users.values()) {
                 if (user.userNameEquals(username))
                     return null;
             }

            // We create a new customer object because the id field is immutable
            // and we need to assign the next unique id
            //and we assign a cart to the customer
            Customer customer = new Customer(nextUserId(), username);
            users.put(customer.getUserId(), customer);
            cartsDAO.createCart(customer.getUserId());

            save(); // may throw an IOException
            return customer;
        }
    }

  /**
  ** {@inheritDoc}
   */
    @Override
    public User LoginUser(String username) throws IOException {
        synchronized(users) {
            //check if user with given username exists
            //if it does then log the user in
            for (User user : users.values()) {
                if (user.userNameEquals(username)){
                    if(!user.isLoggedIn()){
                        user.logIn();
                        return user; 
                    }
                    break;       
                }
            }
        }

        return null;
    }

  /**
  ** {@inheritDoc}
   */
    @Override
    public User LogOutUser(String username) throws IOException {
        synchronized(users) {

            //check if user with given username exists
            //if it does then log the user out
            for (User user : users.values()) {
                if (user.userNameEquals(username)){
                    if(user.isLoggedIn() == true){
                        user.logOut();
                        return user; 
                    }
                    break;                
                }
            }  
        }

        return null;
    }

}