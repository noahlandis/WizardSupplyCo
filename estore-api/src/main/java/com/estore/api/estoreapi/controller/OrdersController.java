package com.estore.api.estoreapi.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;


import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.estore.api.estoreapi.persistence.OrdersDAO;
import com.estore.api.estoreapi.model.InsufficientStockException;
import com.estore.api.estoreapi.model.Order;

/**
 * Handles the REST API requests for the Orders resource
 * <p>
 * {@literal @}RestController Spring annotation identifies this class as a REST API
 * method handler to the Spring framework
 * 
 * @author Kanisha Agrawal 
 */

@RestController
@RequestMapping("orders")
public class OrdersController {
    private static final Logger LOG = Logger.getLogger(OrdersController.class.getName());
    private OrdersDAO ordersDao;

    /**
     * Creates a REST API controller to respond to requests
     * 
     * @param ordersDao The {@link OrdersDAO Orders Data Access Object} to perform CRUD operations
     * <br>
     * This dependency is injected by the Spring Framework
     */
    public OrdersController(OrdersDAO ordersDao) {
        this.ordersDao = ordersDao;
    }

    /**
     * Responds to the GET request for a {@linkplain Order order} for the user with the given userId
     * 
     * @param orderNumber The order number of the {@link Order order} who owns the order
     * 
     * @return ResponseEntity with {@link order order} object and HTTP status of OK<br>
     * ResponseEntity with HTTP status of NOT_FOUND if the order does not exist<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * 
     * Example: Get order with order number 1
     * GET http://localhost:8080/orders/1
     */
    @GetMapping("/{orderNumber}")
    public ResponseEntity<Order> getOrder(@PathVariable int orderNumber) {
        LOG.info("GET /orders/" + orderNumber);

        try {
            Order order = ordersDao.getOrder(orderNumber);
            if (order == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<Order>(order, HttpStatus.OK);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

        /**
     * Responds to the GET request for all {@linkplain Order orders}
     * 
     * @return ResponseEntity with array of {@link Order order} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * 
     * Example: Get all orders
     * GET http://localhost:8080/order/
     */
    @GetMapping("")
    public ResponseEntity<Order[]> getOrders() {
        LOG.info("GET /orders");

        try {
            Order[] orders = ordersDao.getOrders();
            return new ResponseEntity<Order[]>(orders,HttpStatus.OK);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

        /**
     * Creates a {@linkplain Order order} with the provided order object
     * 
     * @param order - The {@link Order order} to create
     * 
     * @return ResponseEntity with created {@link Order order} object and HTTP status of CREATED<br>
     * ResponseEntity with HTTP status of CONFLICT if {@link Order order} object already exists<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * 
     * Example: Create a order
     * POST http://localhost:8080/order/
     * Body: order object to create
     */
    @PostMapping("")
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        LOG.info("POST /orders " + order);

        try {
            Order createdOrder = ordersDao.createOrder(order);
            
            // Check if order exists
            if (createdOrder != null)
                return new ResponseEntity<Order>(createdOrder, HttpStatus.CREATED);
                
            // Throw conflict since order already exists
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        catch(InsufficientStockException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Gets the list of all {@linkplain Product products} purchased by the user with the given userId
     * 
     * @param userId The user id of the {@link User user} who owns the order
     * 
     * @return ResponseEntity with array of {@link Product product} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * 
     * Example: Get all products purchased by user with user id 1
     * GET http://localhost:8080/orders/user/1
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<int[]> getProductsPurchased(@PathVariable int userId) {
        LOG.info("GET /orders/user/" + userId);

        try {
            int[] products = ordersDao.getProductsPurchased(userId);
            return new ResponseEntity<int[]>(products, HttpStatus.OK);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    

}
