package com.estore.api.estoreapi.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;


import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.estore.api.estoreapi.model.Customer;
import com.estore.api.estoreapi.model.User;
import com.estore.api.estoreapi.persistence.UsersDAO;

/**
 * Tests for the UsersController class.
 * 
 * @author Priyank Patel
 */
@Tag("Controller-tier")
public class UsersControllerTest {
    private UsersController usersController;
    private UsersDAO mockUsersDAO;

    /**
     * Before each test, create a new UsersController object and inject
     * a mock UserDAO
     */
    @BeforeEach
    public void setupUsersController() {
        mockUsersDAO = mock(UsersDAO.class);
        usersController = new UsersController(mockUsersDAO);
    }

    @Test
    public void testGetUserSuccessful() throws IOException {
        // Arrange
        Customer customer = new Customer(1, "JohnDoe");
        when(mockUsersDAO.getUser(customer.getUserId())).thenReturn(customer);
        
        // Act
        ResponseEntity<User> response = usersController.getUser(customer.getUserId());

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetUserNull() throws IOException {
        // Arrange
        when(mockUsersDAO.getUser(1)).thenReturn(null);
        
        // Act
        ResponseEntity<User> response = usersController.getUser(1);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetUsersException() throws IOException {
        // Arrange
        when(mockUsersDAO.getUser(1)).thenThrow(new IOException());
        
        // Act
        ResponseEntity<User> response = usersController.getUser(1);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testCreateUserSuccessful() throws IOException {
        String userName = "JohnMcClane";
        // Arrange
        when(mockUsersDAO.createUser(userName)).thenReturn(new Customer(12, userName));
        
        // Act
        ResponseEntity<User> response = usersController.createUser(userName);

        // Analyze
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testCreateUserNUll() throws IOException {
        // Arrange
        when(mockUsersDAO.createUser(null)).thenReturn(null);
        
        // Act
        ResponseEntity<User> response = usersController.createUser(null);

        // Analyze
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void testCreateUserException() throws IOException {
        // Arrange
        when(mockUsersDAO.createUser(null)).thenThrow(new IOException());
        
        // Act
        ResponseEntity<User> response = usersController.createUser(null);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testLogOutUser() throws IOException{
        // Arrange
        String userName = "MichaelScott";
        when(mockUsersDAO.LogOutUser(userName)).thenReturn(new Customer(12, userName));
        
        // Act
        ResponseEntity<User> response = usersController.LogOutUser(userName);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testLogOutUserNull() throws IOException {
        // Arrange
        when(mockUsersDAO.LogOutUser(null)).thenReturn(null);
        
        // Act
        ResponseEntity<User> response = usersController.LogOutUser(null);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testLogOutUserException() throws IOException {
        // Arrange
        when(mockUsersDAO.LogOutUser(null)).thenThrow(new IOException());
        
        // Act
        ResponseEntity<User> response = usersController.LogOutUser(null);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testLogInUser() throws IOException{
        // Arrange
        String userName = "MichaelScott";
        when(mockUsersDAO.LoginUser(userName)).thenReturn(new Customer(12, userName));
        
        // Act
        ResponseEntity<User> response = usersController.LoginUser(userName);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testLogInUserNull() throws IOException {
        // Arrange
        when(mockUsersDAO.LoginUser(null)).thenReturn(null);
        
        // Act
        ResponseEntity<User> response = usersController.LoginUser(null);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testLogInUserException() throws IOException {
        // Arrange
        when(mockUsersDAO.LoginUser(null)).thenThrow(new IOException());
        
        // Act
        ResponseEntity<User> response = usersController.LoginUser(null);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
