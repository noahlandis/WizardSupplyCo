package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.estore.api.estoreapi.model.Stock.Status;

@Tag("Model-tier")
public class CustomerTest {
    @Test
    public void testConstructor()   {
        //Setup
        int userId = 7;
        String userName = new String("Voldemort");

        //Invoke
        Customer customer = new Customer(userName, userId);

        //Analyze
        assertEquals(userName, customer.getUserName());
    }
}
