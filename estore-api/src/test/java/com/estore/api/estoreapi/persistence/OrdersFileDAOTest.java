package com.estore.api.estoreapi.persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.estore.api.estoreapi.model.ShippingAddress;
import com.estore.api.estoreapi.model.Order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Test the Inventory File DAO class
 * 
 * @author SWEN Faculty
 */
@Tag("Persistence-tier")
public class OrdersFileDAOTest {
    OrdersFileDAOTest ordersFileDAOTest;
    Order[] testOrders;
    ObjectMapper mockObjectMapper;

    /**
     * Before each test, we will create and inject a Mock Object Mapper to
     * isolate the tests from the underlying file
     * @throws IOException
     */
    @BeforeEach
    public void setupOrderFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testOrders = new Order[3];
        testOrders[0] = new Order(2,"Rince","Wind", "02734613","rince@gmail.com", new ShippingAddress("United States of America", "New York","Rochester", 14623,"220 John Street","RIT"), new Cart(3));
        testOrders[1] = new Order(3,"Rince","Wind", "02734613","rince@gmail.com", new ShippingAddress("United States of America", "New York","Rochester", 14623,"220 John Street","RIT"), new Cart(2));
        testOrders[2] = new Order(1,"Rince","Wind", "02734613","rince@gmail.com", new ShippingAddress("United States of America", "New York","Rochester", 14623,"220 John Street","RIT"), new Cart(5));

        // When the object mapper is supposed to read from the file
        // the mock object mapper will return the Order array above
        when(mockObjectMapper
            .readValue(new File("Order_File.txt"),Order[].class))
                .thenReturn(testOrders);
        orderFileDAO = new OrderFileDAO("Order_File.txt",mockObjectMapper);
    }

    @Test
    public void testGetOrders() {
        // Invoke
        Order[] orders = orderFileDAO.getOrders();

        // Analyze
        assertEquals(orders.length,testOrders.length);
        for (int i = 0; i < testOrders.length;++i)
            assertEquals(orders[i],testOrders[i]);
    }

    @Test
    public void testGetOrder() {
        // Invoke
        Order order = orderFleDAO.getOrder(3);

        // Analzye
        assertEquals(order,testOrders[1]);
    }

    @Test
    public void testCreateOrder() {
        // Setup
        Order order = new Order(5,"Rince","Wind", "02734613","rince@gmail.com", new ShippingAddress("United States of America", "New York","Rochester", 14623,"220 John Street","RIT"), new Cart(5));

        // Invoke
        Order result = assertDoesNotThrow(() -> orderFileDAO.createOrder(order),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        Order actual = orderFileDAO.getOrder(order.getOrderNumber());
        assertEquals(actual.getOrderNumber(),order.getOrderNumber());
        assertEquals(actual.getFirstname(),order.getFirstname());
        assertEquals(actual.getLastname(),order.getLastname());
        assertEquals(actual.getPhoneNumber(),order.getPhoneNumber());
        assertEquals(actual.getemailAddress(),order.getemailAddress());
        assertEquals(actual.getShippingAddress(),order.getShippingAddress());
        assertEquals(actual.getCart(),order.getCart());        

    }

}