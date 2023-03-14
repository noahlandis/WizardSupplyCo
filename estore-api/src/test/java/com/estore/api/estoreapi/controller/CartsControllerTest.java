package com.estore.api.estoreapi.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;


import java.io.IOException;
import java.net.http.HttpTimeoutException;

import org.apache.catalina.connector.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.estore.api.estoreapi.persistence.CartsDAO;
import com.estore.api.estoreapi.model.Cart;
import com.estore.api.estoreapi.model.Customer;
import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.model.Stock;


/**
 * Tests for the Carts Controller Class
 * 
 * @author Priyank Patel
 */
@Tag("Controller-tier")
public class CartsControllerTest {
    private CartsController cartsController;
    private CartsDAO mockCartsDAO;

     /**
     * Before each test, create a new CartsController object and inject
     * a mock Cart DAO
     */
    @BeforeEach
    public void setupInventoryController() {
        mockCartsDAO = mock(CartsDAO.class);
        cartsController = new CartsController(mockCartsDAO);
    }

    @Test
    public void testGetCart() throws IOException {
        // Setup
        Customer customer = new Customer("Andromeda", 11);
        Cart cart = new Cart(customer.getUserId());
        //When the same UserId is passed in, our mock Carts DAO will return the Cart object
        when(mockCartsDAO.getCart(cart.getUserId())).thenReturn(cart);

        // Invoke
        ResponseEntity<Cart> response = cartsController.getCart(cart.getUserId());

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetCartNotFound() throws IOException {
        // Setup
        Customer customer = new Customer("Andromeda", 11);
        //When the same UserId is passed in, our mock Carts DAO will return null, simulating
        // no cart found
        when(mockCartsDAO.getCart(customer.getUserId())).thenReturn(null);

        // Invoke
        ResponseEntity<Cart> response = cartsController.getCart(customer.getUserId());

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testaddProductToCart() throws IOException {
        // Setup
        int userId = 10;
        int sku = 45;
        int quantity = 2;

        Cart cart = new Cart(userId);
        // when addProductToCart is called, return true simulating successful
        // addition
        when(mockCartsDAO.addProductToCart(userId,sku,quantity)).thenReturn(cart);

        //Invoke
        ResponseEntity<Cart> response = cartsController.addProductToCart(userId, sku, quantity);

        //Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testRemoveProductFromCart() throws IOException {
        // Setup
        int userId = 11;
        Product[] products = new Product[3];
        products[0] = new Product(41,"Glitter Powder",24.99f, new Stock(50));
        products[1] = new Product(42,"Sand Powder", 20.99f, new Stock(25));
        products[2] = new Product(43,"Bone Powder",19.99f, new Stock(20));
        
        Cart cart = new Cart(userId);
        
        for(Product product: products){
            cartsController.addProductToCart(userId, product.getSku(),1);
        }
        when(mockCartsDAO.removeProductFromCart(userId, products[1].getSku(), products[1].getStockQuantity())).thenReturn(cart);

        //Invoke
        ResponseEntity<Cart> response = cartsController.removeProductFromCart(userId, 42,1);
         
        //Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        


    }
}
