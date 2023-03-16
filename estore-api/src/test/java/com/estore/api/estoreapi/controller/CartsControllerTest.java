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

import com.estore.api.estoreapi.persistence.CartsDAO;
import com.estore.api.estoreapi.model.Cart;
import com.estore.api.estoreapi.model.Customer;
import com.estore.api.estoreapi.model.InsufficientStockException;


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
    public void testGetCartIOException() throws IOException{

        //Setup
        Customer customer = new Customer("Andromeda", 11);
        //When using getCart, throw an IOException simulating IO error
        when(mockCartsDAO.getCart(customer.getUserId())).thenThrow(new IOException());

        ResponseEntity<Cart> response = cartsController.getCart(customer.getUserId());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testGetCarts() throws IOException {
        Cart[] carts = null;
        //When the same UserId is passed in, our mock Carts DAO will return all the Carts objects
        when(mockCartsDAO.getCarts()).thenReturn(carts);

        // Invoke
        ResponseEntity<Cart[]> response = cartsController.getCarts();

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetCartsIOException() throws IOException {
        //When the same UserId is passed in, our mock Carts DAO will throw IOException simulating
        //IO error
        when(mockCartsDAO.getCarts()).thenThrow(new IOException());

        // Invoke
        ResponseEntity<Cart[]> response = cartsController.getCarts();

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testaddProductToCartSuccessful() throws IOException, InsufficientStockException {
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
    public void testaddProductToCartNotFound() throws IOException, InsufficientStockException {
        // Setup
        int userId = 10;
        int sku = 45;
        int quantity = 2;

        // when addProductToCart is called, return false simulating unsuccessful
        // addition
        when(mockCartsDAO.addProductToCart(userId,sku,quantity)).thenReturn(null);

        //Invoke
        ResponseEntity<Cart> response = cartsController.addProductToCart(userId, sku, quantity);

        //Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testAddProductToCartIOException() throws IOException, InsufficientStockException{
        // Setup
        int userId = 10;
        int sku = 45;
        int quantity = 2;

        // when addProductToCart is called, throw an IOException error simulating IO error
        when(mockCartsDAO.addProductToCart(userId,sku,quantity)).thenThrow(new IOException());

        //Invoke
        ResponseEntity<Cart> response = cartsController.addProductToCart(userId, sku, quantity);

        //Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }


    @Test
    public void testaddProductToCartInvalidQuantity() throws IOException, InsufficientStockException {
        // Setup
        int userId = 10;
        int sku = 45;
        int quantity = -1;
        
        //Invoke
        ResponseEntity<Cart> response = cartsController.addProductToCart(userId, sku, quantity);

        //Analyze
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testaddProductToCartInsufficientStock() throws IOException, InsufficientStockException {
        // Setup
        int userId = 10;
        int sku = 45;
        int quantity = 2;

        // when addProductToCart is called, throw InsufficientStockException
        doThrow(InsufficientStockException.class).when(mockCartsDAO).addProductToCart(userId,sku,quantity);

        //Invoke
        ResponseEntity<Cart> response = cartsController.addProductToCart(userId, sku, quantity);

        //Analyze
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testRemoveProductFromCartSuccessful() throws IOException {
        // Setup
        int userId = 10;
        int sku = 45;
        int quantity = 2;

        Cart cart = new Cart(userId);
        // when removeProductFromCart is called, return true simulating successful
        // removal
        when(mockCartsDAO.removeProductFromCart(userId,sku,quantity)).thenReturn(cart);

        //Invoke
        ResponseEntity<Cart> response = cartsController.removeProductFromCart(userId, sku, quantity);

        //Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testRemoveProductFromCartNotFound() throws IOException {
        // Setup
        int userId = 10;
        int sku = 45;
        int quantity = 2;

        // when removeProductFromCart is called, return false simulating unsuccessful
        // removal
        when(mockCartsDAO.removeProductFromCart(userId,sku,quantity)).thenReturn(null);

        //Invoke
        ResponseEntity<Cart> response = cartsController.removeProductFromCart(userId, sku, quantity);

        //Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testRemoveProductFromCartIOException() throws IOException {
        // Setup
        int userId = 10;
        int sku = 45;
        int quantity = 2;

        // when removeProductFromCart is called, throw IOException simulating IO error
        when(mockCartsDAO.removeProductFromCart(userId,sku,quantity)).thenThrow(new IOException());

        //Invoke
        ResponseEntity<Cart> response = cartsController.removeProductFromCart(userId, sku, quantity);

        //Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testRemoveProductFromCartInvalidQuantity() throws IOException {
        // Setup
        int userId = 10;
        int sku = 45;
        int quantity = -1;
        
        //Invoke
        ResponseEntity<Cart> response = cartsController.removeProductFromCart(userId, sku, quantity);

        //Analyze
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testRemoveProductFromCartAllSuccessful() throws IOException {
        // Setup
        int userId = 10;
        int sku = 45;

        Cart cart = new Cart(userId);
        // when removeProductFromCart is called, return true simulating successful
        // removal
        when(mockCartsDAO.removeProductFromCart(userId,sku)).thenReturn(cart);

        //Invoke
        ResponseEntity<Cart> response = cartsController.removeProductFromCart(userId, sku);

        //Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testRemoveProductFromCartAllNull() throws IOException {
        // Setup
        int userId = 10;
        int sku = 45;

        // when removeProductFromCart is called, return null simulating cart not found
        when(mockCartsDAO.removeProductFromCart(userId,sku)).thenReturn(null);

        //Invoke
        ResponseEntity<Cart> response = cartsController.removeProductFromCart(userId, sku);

        //Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testRemoveProductFromCartAllIOException() throws IOException {
        // Setup
        int userId = 10;
        int sku = 45;

        // when removeProductFromCart is called, throw IO exception simulating IO error
        when(mockCartsDAO.removeProductFromCart(userId,sku)).thenThrow(new IOException());

        //Invoke
        ResponseEntity<Cart> response = cartsController.removeProductFromCart(userId, sku);

        //Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testClearCartSuccessful() throws IOException {
        //Setup
        int userId = 10;
        Cart cart = new Cart(userId);

        // when clearCart is called, return the cart, simulating success
        when(mockCartsDAO.clearCart(userId)).thenReturn(cart);

        //Invoke
        ResponseEntity<Cart> response = cartsController.clearCart(userId);

        //Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testClearCartFailure() throws IOException {
        //Setup
        int userId = 10;

        // when clearCart is called, return null, simulating failure
        when(mockCartsDAO.clearCart(userId)).thenReturn(null);

        //Invoke
        ResponseEntity<Cart> response = cartsController.clearCart(userId);

        //Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testClearCartIOException() throws IOException {
        //Setup
        int userId = 10;

        // when clearCart is called, throw IOException, simulating IO error
        when(mockCartsDAO.clearCart(userId)).thenThrow(new IOException());

        //Invoke
        ResponseEntity<Cart> response = cartsController.clearCart(userId);

        //Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}