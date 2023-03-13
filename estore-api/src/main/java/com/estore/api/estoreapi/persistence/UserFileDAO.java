package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

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
    Map<Integer,User> users;   // Provides a local cache of the product objects
    // so that we don't need to read from the file
    // each time
    private ObjectMapper objectMapper;  // Provides conversion between Products
            // objects and JSON text format written
            // to the file
    private static int nextUserId;  // The next sku to assign to a new product
    private String filename;    // Filename to read from and write to

       /**
     * Creates an Inventory File Data Access Object
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
        load();  // load the products from the file
    }

    /**
     * Generates the next sku for a new {@linkplain Product product}
     * 
     * @return The next sku
     */
    private synchronized static int nextUserId() {
        int userId = nextUserId;
        ++nextUserId;
        return userId;
    }

     /**
     * Generates an array of {@linkplain Product products} from the tree map
     * 
     * @return  The array of {@link Product products}, may be empty
     */
    private User[] getUsersArray() {
        return getUsersArray(null);
    }

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

    private boolean save() throws IOException {
        LOG.info("Saving users to file: " + filename);
        User[] userArray = getUsersArray();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename),userArray);
        return true;
    }

    private boolean load() throws IOException {
        LOG.info("Loading products from file: " + filename);
        users = new TreeMap<>();
        nextUserId = 0;

        // Deserializes the JSON objects from the file into an array of products
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        User[] userArray = objectMapper.readValue(new File(filename),User[].class);

        // Add each product to the tree map and keep track of the greatest Sku
        for (User user : userArray) {
            users.put(user.getUserId(),user);
            if (user.getUserId() > nextUserId)
                nextUserId = user.getUserId();
        }
        // Make the next sku one greater than the maximum from the file
        ++nextUserId;
        return true;
    }

    @Override
    public User[] getUsers() {
        synchronized(users) {
            return getUsersArray();
        }
    }

    @Override
    public User getUser(int userId) {
        synchronized(users) {
            if (users.containsKey(userId))
                return users.get(userId);
            else
                return null;
        }
    }

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
            User newUser = new User(nextUserId(), user.getUserName());
            users.put(newUser.getUserId(),newUser);
            save(); // may throw an IOException
            return newUser;
        }
    }


}


