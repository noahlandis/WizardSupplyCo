package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.estore.api.estoreapi.persistence.OrdersFileDAO;

public class OrderTest {

    @Test
    public void testConstructor() {
        // Setup
        int expectedOrderNumber = 2;
        String expectedFirstname = "Rincewind";
        String expectedLastname = "rince";
        String expectedPhoneNumber = "02734613";
        String expectedemailAddress = "rince@gmail.com";
        ShippingAddress shippingAddress = new ShippingAddress( "United States of America", "New York","Rochester", 14623,"220 John Street","RIT");        // Invoke
        Cart cart = new Cart(2);

        Order order = new Order(expectedOrderNumber, expectedFirstname, expectedLastname, expectedPhoneNumber, expectedemailAddress, shippingAddress, cart);
        // Analyze
        assertEquals(expectedOrderNumber, order.getOrderNumber());
        assertEquals(expectedFirstname, order.getFirstName());
        assertEquals(expectedLastname, order.getLastName());
        assertEquals(expectedPhoneNumber, order.getPhoneNumber());
        assertEquals(expectedemailAddress, order.getEmailAddress());
        assertEquals(shippingAddress, order.getShippingAddress());
        assertEquals(cart, order.getCart());
    }

    @Test
    public void testConstructorWithoutOrderNumber() {
        // Setup
        String expectedFirstname = "Rincewind";
        String expectedLastname = "rince";
        String expectedPhoneNumber = "02734613";
        String expectedemailAddress = "rince@gmail.com";
        ShippingAddress shippingAddress = new ShippingAddress( "United States of America", "New York","Rochester", 14623,"220 John Street","RIT");        // Invoke
        Cart cart = new Cart(2);

        Order order = new Order(expectedFirstname, expectedLastname, expectedPhoneNumber, expectedemailAddress, shippingAddress, cart);
        // Analyze
        assertEquals(expectedFirstname, order.getFirstName());
        assertEquals(expectedLastname, order.getLastName());
        assertEquals(expectedPhoneNumber, order.getPhoneNumber());
        assertEquals(expectedemailAddress, order.getEmailAddress());
        assertEquals(shippingAddress, order.getShippingAddress());
        assertEquals(cart, order.getCart());
    }

    @Test
    public void testOrderNumber() {
         // Setup
        int orderNumber = 5; 
        int expectedOrderNumber = 5;
        String firstname = "Rincewind";
        String lastname = "rince";
        String phoneNumber = "02734613";
        String emailAddress = "rince@gmail.com";
        ShippingAddress shippingAddress = new ShippingAddress( "United States of America", "New York","Rochester", 14623,"220 John Street","RIT");        // Invoke
        Cart cart = new Cart(2);
        Order order = new Order(firstname, lastname, phoneNumber, emailAddress, shippingAddress, cart);

        // Invoke
        order.setOrderNumber(orderNumber);

        // Analyze
        assertEquals(expectedOrderNumber, order.getOrderNumber());
    }

    @Test
    public void testToString() {
        // Setup
        int orderNumber = 2;
        String firstname = "Rincewind";
        String lastname = "rince";
        String phoneNumber = "02734613";
        String emailAddress = "rince@gmail.com";
        ShippingAddress shippingAddress = new ShippingAddress( "United States of America", "New York","Rochester", 14623,"220 John Street","RIT");        // Invoke
        Cart cart = new Cart(2);

        Order order = new Order(orderNumber, firstname, lastname, phoneNumber, emailAddress, shippingAddress, cart);
        String expected_string = String.format(Order.STRING_FORMAT, orderNumber, firstname,lastname,phoneNumber,emailAddress,shippingAddress, cart);

        // Invoke
        String actual_string = order.toString();

        // Analyze
        assertEquals(expected_string,actual_string);
    }

    
}
