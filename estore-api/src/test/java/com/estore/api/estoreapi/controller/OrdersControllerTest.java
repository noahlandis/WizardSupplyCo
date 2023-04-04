package com.estore.api.estoreapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import com.estore.api.estoreapi.persistence.OrdersDAO;
import com.estore.api.estoreapi.model.Cart;
import com.estore.api.estoreapi.model.InsufficientStockException;
import com.estore.api.estoreapi.model.Order;
import com.estore.api.estoreapi.model.ShippingAddress;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Test the Inventory Controller class
 * 
 * @author SWEN Faculty
 */
@Tag("Controller-tier")
public class OrdersControllerTest {
    private OrdersController ordersController;
    private OrdersDAO mockOrdersDAO;

    /**
     * Before each test, create a new OrdersController object and inject
     * a mock Inventory DAO
     */
    @BeforeEach
    public void setupOrdersController() {
        mockOrdersDAO = mock(OrdersDAO.class);
        ordersController = new OrdersController(mockOrdersDAO);
    }

    @Test
    public void testGetOrder() throws IOException {  // getOrder may throw IOException
        // Setup
        Order order = new Order(2,"Rince","Wind", "02734613","rince@gmail.com", new ShippingAddress("United States of America", "New York","Rochester", 14623,"220 John Street","RIT"), new Cart(1));
       
        // When the same sku is passed in, our mock Inventory DAO will return the Order object
        when(mockOrdersDAO.getOrder(order.getOrderNumber())).thenReturn(order);

        // Invoke
        ResponseEntity<Order> response = ordersController.getOrder(order.getOrderNumber());

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(order,response.getBody());
    }

    @Test
    public void testGetOrderNotFound() throws Exception { // createOrder may throw IOException
        // Setup
        int orderNumber= 99;
        // When the same sku is passed in, our mock Inventory DAO will return null, simulating
        // no Order found
        when(mockOrdersDAO.getOrder(orderNumber)).thenReturn(null);

        // Invoke
        ResponseEntity<Order> response = ordersController.getOrder(orderNumber);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testGetOrderHandleException() throws Exception { // createOrder may throw IOException
        // Setup
        int orderNumber = 99;
        // When getorder is called on the Mock Inventory DAO, throw an IOException
        doThrow(new IOException()).when(mockOrdersDAO).getOrder(orderNumber);

        // Invoke
        ResponseEntity<Order> response = ordersController.getOrder(orderNumber);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    /*****************************************************************
     * The following tests will fail until all ordersController methods
     * are implemented.
     ****************************************************************/

    @Test
    public void testCreateOrder() throws IOException {  // createOrder may throw IOException
        // Setup
          Order order = new Order(2,"Rince","Wind", "02734613","rince@gmail.com", new ShippingAddress("United States of America", "New York","Rochester", 14623,"220 John Street","RIT"), new Cart(1));
        // when createOrder is called, return true simulating successful
        // creation and save
        try {
            when(mockOrdersDAO.createOrder(order)).thenReturn(order);
        } catch (InsufficientStockException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Invoke
        ResponseEntity<Order> response = ordersController.createOrder(order);

        // Analyze
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(order,response.getBody());
    }
    
}
