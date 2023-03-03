package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * The unit test suite for the Product class
 * 
 * @author SWEN Faculty
 */
@Tag("Model-tier")
public class ProductTest {
    @Test
    public void testConstructor() {
        // Setup
        int expectedSku = 99;
        String expectedName = "Binmus 2000 Racing Broom";
        float expectedPrice = 35.00f;
        Stock expectedStock = new Stock(0);

        // Invoke
        Product product = new Product(expectedSku, expectedName, expectedPrice, expectedStock);

        // Analyze
        assertEquals(expectedSku, product.getSku());
        assertEquals(expectedName, product.getName());
        assertEquals(expectedPrice, product.getPrice());
        assertEquals(expectedStock, product.getStock());
    }

    @Test
    public void testName() {
        // Setup
        int expectedSku = 99;
        String name = "Binmus 2000 Racing Broom";
        float expectedPrice = 35.00f;
        String expectedName = "Starshot v3 Sports Broom";
        Stock expectedStock = new Stock(0);

        Product product = new Product(expectedSku, name, expectedPrice, expectedStock);

        // Invoke
        product.setName(expectedName);

        // Analyze
        assertEquals(expectedName, product.getName());
    }

    @Test
    public void testToString() {
        // Setup
        int sku = 99;
        String name = "The Vruum Broom by BroomCorp";
        float price = 35.00f;
        Stock stock = new Stock(0);

        String expected_string = String.format(Product.STRING_FORMAT, sku, name, price);
        Product product = new Product(sku, name, price, stock);

        // Invoke
        String actual_string = product.toString();

        // Analyze
        assertEquals(expected_string,actual_string);
    }

    // @Test
    // public void testEquals() {
    //     // Setup
    //     int sku = 99;
    //     String name = "The Vruum Broom by BroomCorp";
    //     float price = 35.00f;
    //     Stock stock = new Stock(0);

    //     Product product1 = new Product(sku, name, price, stock);
    //     Product product2 = new Product(sku, name, price, stock);

    //     // Invoke
    //     boolean actual = product1.equals(product2);

    //     // Analyze
    //     assertEquals(true, actual);
    // }

    @Test
    public void testHasEnoughStockFor() {
        // Setup
        int sku = 99;
        String name = "The Vruum Broom by BroomCorp";
        float price = 35.00f;
        Stock stock = new Stock(10);

        Product product = new Product(sku, name, price, stock);

        // Invoke
        boolean actual = product.hasEnoughStockFor(5);

        // Analyze
        assertEquals(true, actual);
    }
}