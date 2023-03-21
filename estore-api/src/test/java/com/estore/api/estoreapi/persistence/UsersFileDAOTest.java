package com.estore.api.estoreapi.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
            the mock object mapper will return the product array above */
        when(mockObjectMapper
            .readValue((new File("Users_file.txt")),Customer[].class))
                .thenReturn(mockCustomers);

        usersFileDAO = new UsersFileDAO("Users_file.txt", mockObjectMapper, mockCartsDAO);

        // when(usersFileDAO.getUsers()).thenReturn(mockCustomers);
        // when(usersFileDAO.getUser(1)).thenReturn(mockCustomers[0]);
        // when(usersFileDAO.getUser(2)).thenReturn(mockCustomers[1]);
        // when(usersFileDAO.getUser(3)).thenReturn(mockCustomers[2]);
    }

    @Test
    public void testGetUsers() {
        // Invoke
        User[] users = usersFileDAO.getUsers();

        for(int i = 0; i < mockCustomers.length; i++)
        {
            assertEquals(users[i].getUserId(), i);
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
    public void testCreateUser() throws IOException {

        usersFileDAO.createUser("ethanHunt");
        
        // Analyze
        assertEquals(usersFileDAO.getUser(4).getUserId(), 4);
    }
}
