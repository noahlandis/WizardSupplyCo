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
import com.estore.api.estoreapi.model.Product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Test the Inventory File DAO class
 * 
 * @author SWEN Faculty
 */
@Tag("Persistence-tier")
public class InventoryFileDAOTest {
    InventoryFileDAO inventoryFileDAO;
    Product[] testProducts;
    ObjectMapper mockObjectMapper;

    /**
     * Before each test, we will create and inject a Mock Object Mapper to
     * isolate the tests from the underlying file
     * @throws IOException
     */
    @BeforeEach
    public void setupInventoryFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testProducts = new Product[3];
        testProducts[0] = new Product(99,"Newt Lungs (10 pack)",14.99f);
        testProducts[1] = new Product(100,"Frostwing Dragon Egg",20.99f);
        testProducts[2] = new Product(101,"Malachite Heartstones (3 pack)",50.99f);

        // When the object mapper is supposed to read from the file
        // the mock object mapper will return the product array above
        when(mockObjectMapper
            .readValue(new File("Inventory_File.txt"),Product[].class))
                .thenReturn(testProducts);
        inventoryFileDAO = new InventoryFileDAO("Inventory_File.txt",mockObjectMapper);
    }

    @Test
    public void testGetProducts() {
        // Invoke
        Product[] products = inventoryFileDAO.getProducts();

        // Analyze
        assertEquals(products.length,testProducts.length);
        for (int i = 0; i < testProducts.length;++i)
            assertEquals(products[i],testProducts[i]);
    }

    @Test
    public void testFindProducts() {
        // Invoke
        Product[] products = inventoryFileDAO.findProducts("Mal");

        // Analyze
        assertEquals(products.length,1);
        assertEquals(products[0],testProducts[2]);
    }

    @Test
    public void testGetProduct() {
        // Invoke
        Product product = inventoryFileDAO.getProduct(99);

        // Analzye
        assertEquals(product,testProducts[0]);
    }

    @Test
    public void testDeleteProduct() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> inventoryFileDAO.deleteProduct(99),
                            "Unexpected exception thrown");

        // Analzye
        assertEquals(result,true);
        // We check the internal tree map size against the length
        // of the test products array - 1 (because of the delete)
        // Because products attribute of InventoryFileDAO is package private
        // we can access it directly
        assertEquals(inventoryFileDAO.products.size(),testProducts.length-1);
    }

    @Test
    public void testCreateProduct() {
        // Setup
        Product product = new Product(102,"Nightmare Elyxir",20.99f);

        // Invoke
        Product result = assertDoesNotThrow(() -> inventoryFileDAO.createProduct(product),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        Product actual = inventoryFileDAO.getProduct(product.getSku());
        assertEquals(actual.getSku(),product.getSku());
        assertEquals(actual.getName(),product.getName());
    }

    @Test
    public void testUpdateProduct() {
        // Setup
        Product product = new Product(99,"Shadow Cloak",25.99f);

        // Invoke
        Product result = assertDoesNotThrow(() -> inventoryFileDAO.createProduct(product),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        Product actual = inventoryFileDAO.getProduct(product.getSku());
        assertEquals(actual,product);
    }

    @Test
    public void testSaveException() throws IOException{
        doThrow(new IOException())
            .when(mockObjectMapper)
                .writeValue(any(File.class),any(Product[].class));

        Product product = new Product(102,"Frog Legs (100 pack)",5.99f);

        assertThrows(IOException.class,
                        () -> inventoryFileDAO.createProduct(product),
                        "IOException not thrown");
    }

    @Test
    public void testGetProductNotFound() {
        // Invoke
        Product product = inventoryFileDAO.getProduct(98);

        // Analyze
        assertEquals(product,null);
    }

    @Test
    public void testDeleteProductNotFound() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> inventoryFileDAO.deleteProduct(98),
                                                "Unexpected exception thrown");

        // Analyze
        assertEquals(result,false);
        assertEquals(inventoryFileDAO.products.size(),testProducts.length);
    }

    @Test
    public void testUpdateProductNotFound() {
        // Setup
        Product product = new Product(98,"Flightstick 9000 Racing Broom",10);

        // Invoke
        Product result = assertDoesNotThrow(() -> inventoryFileDAO.createProduct(product),
                                                "Unexpected exception thrown");

        // Analyze
        assertNull(result);
    }

    @Test
    public void testConstructorException() throws IOException {
        // Setup
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        // We want to simulate with a Mock Object Mapper that an
        // exception was raised during JSON object deseerialization
        // into Java objects
        // When the Mock Object Mapper readValue method is called
        // from the InventoryFileDAO load method, an IOException is
        // raised
        doThrow(new IOException())
            .when(mockObjectMapper)
                .readValue(new File("doesnt_matter.txt"),Product[].class);

        // Invoke & Analyze
        assertThrows(IOException.class,
                        () -> new InventoryFileDAO("doesnt_matter.txt",mockObjectMapper),
                        "IOException not thrown");
    }
}
