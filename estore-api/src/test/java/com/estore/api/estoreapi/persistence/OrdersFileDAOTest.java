package com.estore.api.estoreapi.persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.estore.api.estoreapi.model.ShippingAddress;
import com.estore.api.estoreapi.model.Stock;
import com.estore.api.estoreapi.model.Cart;
import com.estore.api.estoreapi.model.InsufficientStockException;
import com.estore.api.estoreapi.model.Order;
import com.estore.api.estoreapi.model.Product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
/**
 * Test the Order File DAO class
 * 
 * @author Kanisha Agrawal
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
     * @throws InsufficientStockException
     */
    @BeforeEach
    public void setupOrderFileDAO() throws IOException, InsufficientStockException {
        mockObjectMapper = mock(ObjectMapper.class);
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

        CartsFileDAO cartsDao = mock(CartsFileDAO.class);
        Cart[] cart = new Cart[2];
        Map<Integer,Integer> productMap = new HashMap<>();
        productMap.put(101,1);
        productMap.put(103,2);

        cart[0] = new Cart(1,productMap);
        cart[1] = new Cart(2,productMap); 
        when(cartsDao.getCarts()).thenReturn(cart);
        when(cartsDao.getCart(1)).thenReturn(cart[0]);
        when(cartsDao.getCart(2)).thenReturn(cart[1]);
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
    public void testCreateOrder() throws IOException, InsufficientStockException {
        CartsFileDAO cartsDao =  mock(CartsFileDAO.class);
        Cart[] cart = new Cart[2];
        Map<Integer,Integer> productMap = new HashMap<>();
        productMap.put(101,1);
        productMap.put(103,2);

        cart[0] = new Cart(1,productMap);
        cart[1] = new Cart(2,productMap); 
        when(cartsDao.getCarts()).thenReturn(cart);
        when(cartsDao.getCart(1)).thenReturn(cart[0]);
        when(cartsDao.getCart(2)).thenReturn(cart[1]);
        // Setup
        
        Order order = new Order(4,"Rince","Wind", "02734613","rince@gmail.com", new ShippingAddress("United States of America", "New York","Rochester", 14623,"220 John Street","RIT"), cartsDao.getCart(1));

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

    @Test
    public void testGetProductsPurchased() throws IOException{
        // Setup
        int userId = 1;

        // Invoke
        int[] products = ordersFileDao.getProductsPurchased(userId);

        // Analyze
        assertEquals(0, products.length);        
    }

}
    
