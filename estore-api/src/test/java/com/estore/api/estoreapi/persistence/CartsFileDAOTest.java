package com.estore.api.estoreapi.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.estore.api.estoreapi.model.Cart;
import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.model.Stock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

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

        // set up mock inventory dao with mock products
        InventoryFileDAO inventoryDao = mock(InventoryFileDAO.class);
        Product[] mockProducts = new Product[3];
        mockProducts[0] = new Product(101,"Newt Lungs (10 pack)",14.99f, new Stock(100));
        mockProducts[1] = new Product(102,"Frostwing Dragon Egg",20.99f, new Stock(100));
        mockProducts[2] = new Product(103,"Malachite Heartstones (3 pack)",50.99f, new Stock(100));
        when(inventoryDao.getProducts()).thenReturn(mockProducts);

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

        // Assert
        assertNotNull(carts);
        assertEquals(3, carts.length);
        assertEquals(1, carts[0].getUserId());
        assertEquals(2, carts[1].getUserId());
        assertEquals(3, carts[2].getUserId());
    }

    @Test
    public void testGetCart() {
        // Invoke
        Cart cart = cartsFileDao.getCart(2);

        // Assert
        assertNotNull(cart);
        assertEquals(2, cart.getUserId());
    }

    @Test
    public void testGetCartNotFound() {
        // Invoke
        Cart cart = cartsFileDao.getCart(4);

        // Assert
        assertNull(cart);
    }

    @Test
    public void testCreateCart() {
        // Invoke
        Cart cart = cartsFileDao.createCart(4);

        // Assert
        assertNotNull(cart);
        assertEquals(4, cart.getUserId());
    }

    @Test
    public void testCreateCartAlreadyExists() {
        // Invoke
        Cart cart = cartsFileDao.createCart(2);

        // Assert
        assertNull(cart);
    }

    // @Test
    // public void testAddProductToCart() {
    //     // Invoke
    //     Cart cart = cartsFileDao.addProductToCart(1, 101, 1);

    //     // Assert
    //     assertNotNull(cart);
    //     assertEquals(1, cart.getUserId());
    //     assertEquals(1, cart.getCount());
    //     assertTrue(cart.getProducts().containsKey(101));
    //     assertEquals(1, cart.getProducts().get(101)); // ensure count is 1
    // }

    // @Test
    // public void testAddProductToCartNotFound() {
    //     // Invoke
    //     Cart cart = cartsFileDao.addProductToCart(1, 104, 1);

    //     // Assert
    //     assertNull(cart);
    // }

    // @Test
    // public void testAddProductToCartNotEnoughStock() {
    //     // Invoke
    //     Cart cart = cartsFileDao.addProductToCart(1, 101, 101);

    //     // Assert
    //     assertNull(cart);
    // }

    // @Test
    // public void testRemoveProductFromCart() {
    //     // Setup
    //     cartsFileDao.addProductToCart(1, 101, 10);

    //     // Invoke
    //     Cart cart = cartsFileDao.removeProductFromCart(1, 101, 1);

    //     // Assert
    //     assertNotNull(cart);
    //     assertEquals(1, cart.getUserId());
    //     assertEquals(9, cart.getCount());
    //     assertEquals(true, cart.getProducts().containsKey(101));
    //     assertEquals(9, cart.getProducts().get(0)); // ensure count is 9
    // }
}
