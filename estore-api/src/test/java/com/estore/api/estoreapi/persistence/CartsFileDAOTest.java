package com.estore.api.estoreapi.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.naming.InsufficientResourcesException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.estore.api.estoreapi.model.Cart;
import com.estore.api.estoreapi.model.InsufficientStockException;
import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.model.Stock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Null;

/**
 * Test the Carts File DAO class
 * 
 * @author Ryan Webb
 */
@Tag("Persistence-tier")
public class CartsFileDAOTest {
    CartsFileDAO cartsFileDao;
    Cart[] testCarts;
    ObjectMapper mockObjectMapper;

    /**
     * Before each test, we will create and inject a Mock Object Mapper to
     * isolate the tests from the underlying file
     * @throws IOException
     */
    @BeforeEach
    public void setupCartsFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testCarts = new Cart[3];
        testCarts[0] = new Cart(1);
        testCarts[1] = new Cart(2);
        testCarts[2] = new Cart(3);

        // Set up mock inventory dao with mock products
        InventoryFileDAO inventoryDao = mock(InventoryFileDAO.class);
        Product[] mockProducts = new Product[3];
        mockProducts[0] = new Product(101, "Newt Lungs (10 pack)", 14.99f, new Stock(100));
        mockProducts[1] = new Product(102, "Frostwing Dragon Egg", 20.99f, new Stock(100));
        mockProducts[2] = new Product(103, "Malachite Heartstones (3 pack)", 50.99f, new Stock(100));


        // Mock the inventory dao methods
        when(inventoryDao.getProducts()).thenReturn(mockProducts);
        when(inventoryDao.getProduct(101)).thenReturn(mockProducts[0]);
        when(inventoryDao.getProduct(102)).thenReturn(mockProducts[1]);
        when(inventoryDao.getProduct(103)).thenReturn(mockProducts[2]);
        when(inventoryDao.getProduct(104)).thenReturn(null);

        // When the object mapper is supposed to read from the file
        // the mock object mapper will return the product array above
        when(mockObjectMapper
            .readValue(new File("Carts_File.txt"),Cart[].class))
                .thenReturn(testCarts);
        cartsFileDao = new CartsFileDAO("Carts_File.txt", mockObjectMapper, inventoryDao);
    }

    @Test
    public void testGetCarts() {
        // Invoke
        Cart[] carts = cartsFileDao.getCarts();

        // Analyze
        assertTrue(carts instanceof Cart[]);
        assertEquals(3, carts.length);
        assertEquals(1, carts[0].getUserId());
        assertEquals(2, carts[1].getUserId());
        assertEquals(3, carts[2].getUserId());
    }

    @Test
    public void testGetCart() {
        // Invoke
        Cart cart = cartsFileDao.getCart(2);

        // Analyze
        assertTrue(cart instanceof Cart);
        assertEquals(2, cart.getUserId());
    }

    @Test
    public void testGetCartNotFound() {
        // Invoke
        Cart cart = cartsFileDao.getCart(4);

        // Analyze
        assertNull(cart);
    }

    @Test
    public void testCreateCart() {
        // Invoke
        Cart cart = cartsFileDao.createCart(4);

        // Analyze
        assertTrue(cart instanceof Cart);
        assertEquals(4, cart.getUserId());
    }


    @Test
    public void testCreateCartAlreadyExists() {
        // Invoke
        Cart cart = cartsFileDao.createCart(2);

        // Analyze
        assertNull(cart);
    }

    @Test
    public void testAddProductToCart() {
        
        try{
            // Invoke
            Cart cart = cartsFileDao.addProductToCart(1, 101, 1);

            // Analyze
            assertTrue(cart instanceof Cart);
            assertEquals(1, cart.getUserId());
            assertEquals(1, cart.getCount());
            assertTrue(cart.containsProduct(101));
            assertEquals(1, cart.getProductCount(101)); // ensure count is 1
        }
        catch (InsufficientStockException e){
            fail(e.getMessage());
        }
    }

    @Test
    public void testAddProductToCartNotFound() {
        try{
            // Invoke
            Cart cart = cartsFileDao.addProductToCart(1, 104, 1);
            
            // Analyze
            assertNull(cart);
        }
        catch (InsufficientStockException e){
            fail(e.getMessage());
        }
    }

    @Test
    public void testAddProductToCartNotEnoughStock() {
        // Invoke and Analyze
        assertThrows(InsufficientStockException.class, () -> cartsFileDao.addProductToCart(1, 101, 101));
    }

    @Test
    public void testRemoveProductFromCart() {
        try{
            // Setup
            cartsFileDao.addProductToCart(1, 101, 10);

            // Invoke
            Cart cart = cartsFileDao.removeProductFromCart(1, 101, 1);

            // Analyze
            assertTrue(cart instanceof Cart);
            assertEquals(1, cart.getUserId());
            assertEquals(1, cart.getCount());
            assertTrue(cart.containsProduct(101));
            assertEquals(9, cart.getProductCount(101)); // ensure count is 9
        }
        catch (InsufficientStockException e){
            fail(e.getMessage());
        }
    }

    @Test
    public void testAddProductToCartNull() {
        try{
            // Setup
            Cart cart = cartsFileDao.addProductToCart(4, 101, 10);

            // Analyze
            assertNull(cart);
        }
        catch (InsufficientStockException e){
            fail(e.getMessage());
        }
        catch (NullPointerException e){
            fail(e.getMessage());
        }
    }

    @Test
    public void testRemoveProductFromCartNull() {
        try{
            // Setup
            Cart cart = cartsFileDao.removeProductFromCart(4, 101, 10);

            // Analyze
            assertNull(cart);
        }
        catch (NullPointerException e){
            fail(e.getMessage());
        }
    }

    @Test
    public void testRemoveAllProductsFromCartNull() {
        try{
            // Setup
            Cart cart = cartsFileDao.removeProductFromCart(4,101);

            // Analyze
            assertNull(cart);
        }
        catch (NullPointerException e){
            fail(e.getMessage());
        }
    }

    @Test
    public void testRemoveProductFromCartNoQuantity() {
        try{
        // Setup
        cartsFileDao.addProductToCart(1, 101, 10);

        // Invoke
        Cart cart = cartsFileDao.removeProductFromCart(1, 101);

        // Analyze
        assertTrue(cart instanceof Cart);
        assertEquals(1, cart.getUserId());
        assertEquals(0, cart.getCount());
        assertFalse(cart.containsProduct(101));
        }
        catch (InsufficientStockException e){
            fail(e.getMessage());
        }
    }

    @Test
    public void testRemoveProductFromCartNotFound() {
        // Invoke
        Cart cart = cartsFileDao.removeProductFromCart(1, 104, 1);

        // Analyze
        assertNull(cart);
    }

    @Test
    public void testClearCart() {
        try{
        // Setup
        cartsFileDao.addProductToCart(1, 101, 10);

        // Invoke
        Cart cart = cartsFileDao.clearCart(1);

        // Analyze
        assertTrue(cart instanceof Cart);
        assertEquals(1, cart.getUserId());
        assertEquals(0, cart.getCount());
        assertFalse(cart.containsProduct(101));
        }
        catch (InsufficientStockException e){
            fail(e.getMessage());
        }
    }

    // delete cart
    @Test
    public void testDeleteCart() {
        // Invoke
        boolean result = cartsFileDao.deleteCart(2);

        // Analyze
        assertTrue(result);
        assertNull(cartsFileDao.getCart(2));
    }

    @Test
    public void testDeleteCartNotFound() {
        // Invoke
        boolean result = cartsFileDao.deleteCart(4);

        // Analyze
        assertFalse(result);

    }

}