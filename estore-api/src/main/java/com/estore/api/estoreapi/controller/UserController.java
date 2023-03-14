package com.estore.api.estoreapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.estore.api.estoreapi.persistence.UserDAO;
import com.estore.api.estoreapi.model.User;

/**
 * Handles the REST API requests for the Users
 * <p>
 * {@literal @}RestController Spring annotation identifies this class as a REST API
 * method handler to the Spring framework
 * 
 * @author SWEN Faculty
 * @author Kanisha Agrawal
 */

 @RestController
 @RequestMapping("User")
 public class UserController {
     private static final Logger LOG = Logger.getLogger(UserController.class.getName());
     private UserDAO userDao;
 
     /**
      * Creates a REST API controller to respond to requests
      * 
      * @param userDao The {@link userDao User Data Access Object} to perform CRUD operations
      * <br>
      * This dependency is injected by the Spring Framework
      */
     public UserController(UserDAO userDao) {
         this.userDao = userDao;
     }
 
     /**
      * Responds to the GET request for a {@linkplain User user} for the given userId
      * 
      * @param userId The userId used to locate the {@link User user}
      * 
      * @return ResponseEntity with {@link User user} object and HTTP status of OK if found<br>
      * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
      * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
      * 
      * Example: Get customer with userId 1
      * GET http://localhost:8080/user/1
      */
     @GetMapping("/{userId}")
     public ResponseEntity<User> getUser(@PathVariable int userId) {
         LOG.info("GET /user/" + userId);
 
         try {
             User user = userDao.getUser(userId);
             if (user != null)
                 return new ResponseEntity<User>(user,HttpStatus.OK);
             else
                 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
         }
         catch(IOException e) {
             LOG.log(Level.SEVERE,e.getLocalizedMessage());
             return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
         }
     }
 
 
     /**
      * Creates a {@linkplain User user} with the provided user object
      * 
      * @param user - The {@link User user} to create
      * 
      * @return ResponseEntity with created {@link User user} object and HTTP status of CREATED<br>
      * ResponseEntity with HTTP status of CONFLICT if {@link User user} object already exists<br>
      * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
      * 
      * Example: Create a user
      * POST http://localhost:8080/user/
      * Body: user object to create
      */
     @PostMapping("")
     public ResponseEntity<User> createUser(@PathVariable String username) {
         LOG.info("POST /user " + username);
 
         try {
             User createdCustomer = userDao.createUser(username);
             
             // Check if Product exists
             if (createdCustomer != null)
                 return new ResponseEntity<User>(createdCustomer, HttpStatus.CREATED);
                 
             // Throw conflict since hero already exists
             return new ResponseEntity<>(HttpStatus.CONFLICT);
         }
         catch(IOException e) {
             LOG.log(Level.SEVERE,e.getLocalizedMessage());
             return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
         }
     }
 
    /**
      * Responds to the PUT for a {@linkplain User user} for the given userId
      * 
      * @param userId The userId used to locate the {@link User user}
      * 
      * @return ResponseEntity with {@link User user} object and HTTP status of OK if found<br>
      * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
      * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
      * 
      * Example: Put to log out user
      * PUT http://localhost:8080/user/username
      */
     @PutMapping("")
     public ResponseEntity<User> LogOutUser(@PathVariable String username) {
         LOG.info("PUT /user " + username);
 
         try {
             User updateUser = userDao.LogOutUser(username);
             // Update the user by logging it out if it exists
             if (updateUser != null)
                 return new ResponseEntity<User>(updateUser,HttpStatus.OK);
             
             // Throw not found if user does not exist
             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
         }
         catch(IOException e) {
             LOG.log(Level.SEVERE,e.getLocalizedMessage());
             return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
         }
     }

    /**
      * Responds to the PUT for a {@linkplain User user} for the given userId
      * 
      * @param userId The userId used to locate the {@link User user}
      * 
      * @return ResponseEntity with {@link User user} object and HTTP status of OK if found<br>
      * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
      * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
      * 
      * Example: Put to log in user
      * PUT http://localhost:8080/user/username
      */
 
     @PutMapping("")
     public ResponseEntity<User> LoginUser(@PathVariable String username) {
         LOG.info("PUT /user " + username);
 
         try {
             User updateUser = userDao.LoginUser(username);
             // Update the product if it exists
             if (updateUser != null)
                 return new ResponseEntity<User>(updateUser,HttpStatus.OK);
             
             // Throw not found if product does not exist
             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
         }
         catch(IOException e) {
             LOG.log(Level.SEVERE,e.getLocalizedMessage());
             return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
         }
     }
 
 }
 