package com.estore.api.estoreapi.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.estore.api.estoreapi.model.Customer;
import com.estore.api.estoreapi.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Test the Users File DAO class
 * @author Priyank Patel
 */
public class UsersFileDAOTest {
    UsersFileDAO usersFileDAO;
    ObjectMapper mockObjectMapper;
    Customer[] mockCustomers; 
    CartsDAO mockCartsDAO;

    @BeforeEach
    public void setupUsersFileDAOTest() throws IOException {
        mockCartsDAO = mock(CartsDAO.class);
        mockObjectMapper = mock(ObjectMapper.class);
        mockCustomers = new Customer[3];
        mockCustomers[0] = new Customer(1, "johnWick");
        mockCustomers[1] = new Customer(2, "jamesBond");
        mockCustomers[2] = new Customer(3, "jasonBourne");
        
        /* When the object mapper is supposed to read from the file
            the mock object mapper will return the users array above */
        when(mockObjectMapper
            .readValue((new File("Users_file.txt")),User[].class))
                .thenReturn(mockCustomers);

        usersFileDAO = new UsersFileDAO("Users_file.txt", mockObjectMapper, mockCartsDAO);
    }

    @Test
    public void testGetUsers() {
        // Invoke
        User[] users = usersFileDAO.getUsers();

        for(int i = 0; i < mockCustomers.length; i++)
        {
            assertEquals( i+1, users[i].getUserId());
        }    
    }

    @Test
    public void testGetUser() {
        // Invoke
        User user = usersFileDAO.getUser(1);

        // Analyze
        assertEquals(user.getUserId(), 1);
    }

    @Test 
    public void testGetUserNull(){
        // Invoke
        User user = usersFileDAO.getUser(100);

        // Analyze
        assertEquals(user, null);
    }

    @Test
    public void testCreateUser() throws IOException {

        usersFileDAO.createUser("ethanHunt");
        
        // Analyze
        assertEquals(usersFileDAO.getUser(4).getUsername(), "ethanHunt");
    }

    @Test
    public void testCreateUserAlreadyExists() throws IOException {
        // Invoke
        User user = usersFileDAO.createUser(mockCustomers[1].getUsername());

        // Analyze
        assertNull(user);
    }

    @Test
    public void testLoginUser() throws IOException {
        // Setup
        usersFileDAO.LogOutUser(mockCustomers[0].getUsername());
        // Invoke
        User user = usersFileDAO.LoginUser(mockCustomers[0].getUsername());

        // Analyze
        assertTrue(user.isLoggedIn());
    }

    @Test
    public void testLoginUserNull() throws IOException {
        //Invoke
        User user = usersFileDAO.LoginUser("fakeUser");

        // Analyze
        assertNull(user);
    }

    @Test
    public void testLogoutUser() throws IOException {
        //Setup
        usersFileDAO.LoginUser(mockCustomers[0].getUsername());

        // Invoke
        User user = usersFileDAO.LogOutUser(mockCustomers[0].getUsername());

        // Analyze
        assertFalse(user.isLoggedIn());
    }

    @Test
    public void testLogoutUserNull() throws IOException {
        //Invoke
        User user = usersFileDAO.LogOutUser("fakeUser");

        // Analyze
        assertNull(user);
    }

    @Test
    public void testLoginUserAlreadyLoggedIn() throws IOException {
        // Invoke
        User user = usersFileDAO.LoginUser(mockCustomers[0].getUsername());

        // Analyze
        assertTrue(user.isLoggedIn());
    }

    @Test
    public void testLogoutUserAlreadyLoggedOut() throws IOException {
        // Setup
        usersFileDAO.LogOutUser(mockCustomers[0].getUsername());
        // Invoke
        User user = usersFileDAO.LogOutUser(mockCustomers[0].getUsername());

        // Analyze
        assertTrue(!user.isLoggedIn());
    }
}
