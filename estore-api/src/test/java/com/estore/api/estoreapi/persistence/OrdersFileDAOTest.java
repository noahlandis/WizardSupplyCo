package com.estore.api.estoreapi.persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import javax.naming.InsufficientResourcesException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.estore.api.estoreapi.model.ShippingAddress;
import com.estore.api.estoreapi.model.Cart;
import com.estore.api.estoreapi.model.Order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
/**
 * Test the Order File DAO class
 * 
 * @author SWEN Faculty
 */
@Tag("Persistence-tier")
public class OrdersFileDAOTest {
    OrdersFileDAO ordersFileDao;
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
        InventoryFileDAO inventoryDao = mock(InventoryFileDAO.class);
        CartsFileDAO cartsDao = mock(CartsFileDAO.class);
        testOrders = new Order[3];
        testOrders[0] = new Order(2,"Rince","Wind", "02734613","rince@gmail.com", new ShippingAddress("United States of America", "New York","Rochester", 14623,"220 John Street","RIT"), new Cart(1));
        testOrders[1] = new Order(3,"Rince","Wind", "02734613","rince@gmail.com", new ShippingAddress("United States of America", "New York","Rochester", 14623,"220 John Street","RIT"), new Cart(2));
        testOrders[2] = new Order(1,"Rince","Wind", "02734613","rince@gmail.com", new ShippingAddress("United States of America", "New York","Rochester", 14623,"220 John Street","RIT"), new Cart(3));

        
        // When the object mapper is supposed to read from the file
        // the mock object mapper will return the Order array above
        when(mockObjectMapper
            .readValue(new File("Orders_File.txt"),Order[].class))
                .thenReturn(testOrders);
        ordersFileDao = new OrdersFileDAO("Orders_File.txt",mockObjectMapper, inventoryDao, cartsDao);
    }

    @Test
    public void testGetOrders() {
        // Invoke
        Order[] orders = ordersFileDao.getOrders();

        // Analyze
        assertTrue(orders instanceof Order[]);
        assertEquals(3, testOrders.length);
        assertEquals(2, testOrders[0].getOrderNumber());
        assertEquals(3, testOrders[1].getOrderNumber());
        assertEquals(1, testOrders[2].getOrderNumber());
     
    }

    @Test
    public void testGetOrder() {
        // Invoke
        Order order = ordersFileDao.getOrder(3);

        // Analzye
        assertTrue(order instanceof Order);
        assertEquals(3,testOrders[1].getOrderNumber());
    }

    @Test
    public void testCreateOrder() throws IOException {
        // Setup
        Order order = new Order(4,"Rince","Wind", "02734613","rince@gmail.com", new ShippingAddress("United States of America", "New York","Rochester", 14623,"220 John Street","RIT"), new Cart(1));

        // Invoke
        Order result = assertDoesNotThrow(() -> ordersFileDao.createOrder(order),
        "Unexpected exception thrown");

        // Analyze
        Order actual = ordersFileDao.getOrder(order.getOrderNumber());
        assertNotNull(result);
        assertEquals(actual.getOrderNumber(),order.getOrderNumber());
        assertEquals(actual.getFirstName(),order.getFirstName());
        assertEquals(actual.getLastName(),order.getLastName());
        assertEquals(actual.getPhoneNumber(),order.getPhoneNumber());
        assertEquals(actual.getEmailAddress(),order.getEmailAddress());
        assertEquals(actual.getShippingAddress(),order.getShippingAddress());
        assertEquals(actual.getCart(),order.getCart());        

    }

}
    
