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

import com.estore.api.estoreapi.persistence.InventoryFileDAO;

/**
 * The unit test suite for the Cart class
 * 
 * @author Ryan Webb
 */
@Tag("Model-tier")
public class CartTest {
    private static final Logger LOG = Logger.getLogger(Cart.class.getName());
    private Cart testCart;
    

     /**
     * Before each test, we will create and inject an InventoryFileDAO to
     * isolate the tests from the underlying file, and create a test cart
     * @throws IOException
     */
    @BeforeEach
    public void setupInventoryFileDAO() throws IOException {
        // set up mock inventory dao with mock products
        InventoryFileDAO inventoryDao = mock(InventoryFileDAO.class);
        Product[] mockProducts = new Product[3];
        mockProducts[0] = new Product(101, "Newt Lungs (10 pack)", 15, new Stock(10));
        mockProducts[1] = new Product(102, "Frostwing Dragon Egg", 20, new Stock(10));
        mockProducts[2] = new Product(103, "Malachite Heartstones (3 pack)", 50, new Stock(10));

        // Mock the inventory dao methods
        when(inventoryDao.getProducts()).thenReturn(mockProducts);
        when(inventoryDao.getProduct(101)).thenReturn(mockProducts[0]);
        when(inventoryDao.getProduct(102)).thenReturn(mockProducts[1]);
        when(inventoryDao.getProduct(103)).thenReturn(mockProducts[2]);
        when(inventoryDao.getProduct(104)).thenReturn(null);

        // Setup the cart product map
        Map<Integer, Integer> productsMap = new HashMap<Integer, Integer>();
        productsMap.put(101, 5);
        productsMap.put(102, 5);

        // create the test cart
        testCart = new Cart(1, productsMap);
        // inject the mock inventory dao
        testCart.setInventoryDao(inventoryDao);
    }

    /**
     * Test the cart constructor that takes a user id
     */
    @Test
    public void testCartConstructor() {
        // Invoke
        Cart cart = new Cart(1);

        // Analyze
        assertNotNull(cart);
        assertEquals(1, cart.getUserId());
        assertEquals(0, cart.getCount());
    }

    /**
     * Test the cart constructor that takes a user id and a product map
     */
    @Test
    public void testCartConstructorWithProductMap() {
        // Setup
        Map<Integer, Integer> productsMap = new HashMap<Integer, Integer>();
        productsMap.put(101, 1);
        productsMap.put(102, 1);
        
        // Invoke
        Cart cart = new Cart(1, productsMap);
        
        // Analyze
        assertNotNull(cart);
        assertEquals(1, cart.getUserId());
        assertEquals(2, cart.getCount());
    }

    /**
     * Test the containsProduct method
     */
    @Test
    public void testContainsProduct() {
        // Analyze
        assertTrue(testCart.containsProduct(101));
        assertFalse(testCart.containsProduct(103));
    }

    /**
     * Test the addProduct method
     */
    @Test
    public void testAddProduct() {
        // Invoke
        try{
            testCart.addProduct(103, 5);
        }
        catch (InsufficientStockException e){
            fail(e.getMessage());
        }
        // Analyze
        assertTrue(testCart.containsProduct(103));
        assertEquals(5, testCart.getProductCount(103));
    }

    /**
     * Test the addProduct method, passing in a quantity that is greater than the
     * quantity in the inventory
     */
    @Test
    public void testAddProductQuantityGreaterThanInventoryQuantity() {
        //Invoke and Analyze
        assertThrows(InsufficientStockException.class, () -> testCart.addProduct(101,6));
    }

    /**
     * Test the addProduct method with an invalid sku
     */
    @Test
    public void testAddProductInvalidSku() {
        // Analyze
        try{
            assertFalse(testCart.addProduct(104, 5));
        }
        catch (InsufficientStockException e) {
            fail(e.getMessage());
        }
        assertFalse(testCart.containsProduct(104));
    }

    /**
     * Test the removeProduct method that takes a quantity
     */
    @Test
    public void testRemoveProductQuantity() throws IOException {
        // Invoke
        testCart.removeProduct(101, 3);
        
        // Analyze
        assertTrue(testCart.containsProduct(101));
        assertEquals(2, testCart.getProductCount(101));
    }

    @Test
    public void testRemoveExactProductQuantity() throws IOException {
        // Invoke
        testCart.removeProduct(101, 5);
        
        // Analyze
        assertFalse(testCart.containsProduct(101));
    }

    /**
     * Test the removeProduct method that takes a quantity, passing in a quantity
     * that is greater than the quantity in the cart
     * @throws IOException
     */
    @Test
    public void testRemoveProductQuantityGreaterThanCartQuantity() throws IOException {
        // Invoke
        testCart.removeProduct(101, 6);
        
        // Analyze
        assertFalse(testCart.containsProduct(101));
    }

    /**
     * Test the removeProduct method that does not take a quantity
     * @throws IOException
     */
    @Test
    public void testRemoveProduct() throws IOException {
        // Invoke
        testCart.removeProduct(101);
        
        // Analyze
        assertFalse(testCart.containsProduct(101));
    }

    /**
     * Test the removeProduct method, passing in an invalid sku
     * @throws IOException
     */
    @Test
    public void testRemoveProductInvalidSku() throws IOException {
        // Analyze
        assertFalse(testCart.removeProduct(104));
        assertFalse(testCart.containsProduct(104));
    }

    /**
     * Test the getProductCount method
     */
    @Test
    public void testGetProductCount() {
        // Analyze
        assertEquals(5, testCart.getProductCount(101));
    }

    /**
     * Test the getCount method
     */
    @Test
    public void testGetCount() {
        // Analyze
        assertEquals(2, testCart.getCount());
    }

    /**
     * Test the clear method
     */
    @Test
    public void testClear() {
        // Invoke
        testCart.clear();
        
        // Analyze
        assertEquals(0, testCart.getCount());
    }

    /**
     * Test the getTotalPrice method
     */
    @Test
    public void testGetTotalPrice() {
        // Analyze
        try {
            assertEquals(175f, testCart.updateTotalPrice());
        } catch (IOException e) {
            LOG.severe("Error getting inventory while testing. Weird, because it's mocked.");
        }
    }
}