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

        // Invoke
        Product product = new Product(expectedSku, expectedName, expectedPrice);

        // Analyze
        assertEquals(expectedSku, product.getSku());
        assertEquals(expectedName, product.getName());
        assertEquals(expectedPrice, product.getPrice());
    }

    @Test
    public void testName() {
        // Setup
        int expectedSku = 99;
        String name = "Binmus 2000 Racing Broom";
        float expectedPrice = 35.00f;
        String expectedName = "Starshot v3 Sports Broom";
        Product product = new Product(expectedSku, name, expectedPrice);

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

        String expected_string = String.format(Product.STRING_FORMAT, sku, name, price);
        Product product = new Product(sku, name, price);

        // Invoke
        String actual_string = product.toString();

        // Analyze
        assertEquals(expected_string,actual_string);
    }
}