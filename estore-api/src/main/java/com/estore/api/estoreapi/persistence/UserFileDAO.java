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
import com.estore.api.estoreapi.model.Cart;
import com.estore.api.estoreapi.model.User;

/**
 * Implements the functionality for JSON file-based peristance for Products
 * 
 * {@literal @}Component Spring annotation instantiates a single instance of this
 * class and injects the instance into other classes as needed
 * 
 * @author Kanisha Agrawal
 */
@Repository
public class UserFileDAO implements UserDAO{
    private static final Logger LOG = Logger.getLogger(UserFileDAO.class.getName()); 
    Map<Integer,User> users;   // Provides a local cache of the users objects
                               // so that we don't need to read from the file
                               // each time
    private ObjectMapper objectMapper;  // Provides conversion between PUsers
                                        // objects and JSON text format written
                                        // to the file
    private static int nextUserId;  // The next Id to assign to a new user
    private String filename;    // Filename to read from and write to

    /**
     * Creates an User File Data Access Object
     * 
     * @param filename Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public UserFileDAO(@Value("${users.file}") String filename,ObjectMapper objectMapper) throws IOException {
        LOG.info("userFileDAO created");
        this.filename = filename;
        this.objectMapper = objectMapper;
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

    //  /**
    //  * Generates an array of {@linkplain Product products} from the tree map
    //  * 
    //  * @return  The array of {@link Product products}, may be empty
    //  */
    // private User[] getUsersArray() {
    //     return getUsersArray(null);
    // }

    // private User[] getUsersArray(String containsText) { // if containsText == null, no filter
    //     ArrayList<User> userArrayList = new ArrayList<>();
    //     /*This for loop goes through each product in the inventory product map */
    //     for (User user : users.values()) {
    //         if (containsText == null || user.getUserName().contains(containsText)) {
    //             userArrayList.add(user);
    //         }
    //     }

    //     User[] userArray = new User[userArrayList.size()];
    //     userArrayList.toArray(userArray);
    //     return userArray;
    // }

    /**
     * Saves the {@linkplain User users} from the map into the file as an array of JSON objects
     * 
     * @return true if the {@link User users} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */    
    private boolean save() throws IOException {
        LOG.info("Saving users to file: " + filename);
        // User[] userArray = getUsersArray();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename), User[].class);
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
        // Make the next sku one greater than the maximum from the file
        ++nextUserId;
        return true;
    }

    // @Override
    // public User[] getUsers() {
    //     synchronized(users) {
    //         return getUsersArray();
    //     }
    // }

    
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
    public User createUser(User user) throws IOException {
        synchronized(users) {
            // // check if an product with the same name already exists. If so, return null
            // for (User u : users.values()) {
            //     if (u.userId(user.getUserName()))
            //         return null;
            // }

            // We create a new product object because the sku field is immutable
            // and we need to assign the next unique sku
            Customer customer = new Customer(nextUserId(),user.getUserName());
            users.put(customer.getUserId(), customer);
            
            Cart cart = new Cart(customer.getUserId());
            
            save(); // may throw an IOException
            return customer;
        }
    }

    @Override
    public User LoginUser(int userId) throws IOException {
        synchronized(users) {
            if (users.containsKey(userId)){
                if(users.get(userId).isLoggedIn() == false){
                    users.get(userId).logIn();
                    return users.get(userId);
                }
                
            }
            else {
                return null;
            }
           
        }
    }

    @Override
    public User LogOutUser(int userId) throws IOException {
        synchronized(users) {
            if (users.containsKey(userId)){
                if(users.get(userId).isLoggedIn() == true){
                    users.get(userId).logOut();
                    users.get(userId);
                }
                
            }
            else {
                return null;
            }
        }
    }


}