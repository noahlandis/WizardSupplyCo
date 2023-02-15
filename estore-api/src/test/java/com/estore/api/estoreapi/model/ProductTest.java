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
    public void testCtor() {
        // Setup
        int expected_id = 99;
        String expected_name = "Binmus 2000 Racing Broom";

        // Invoke
        Product product = new Product(expected_id,expected_name);

        // Analyze
        assertEquals(expected_id,product.getId());
        assertEquals(expected_name,product.getName());
    }

    @Test
    public void testName() {
        // Setup
        int id = 99;
        String name = "Binmus 2000 Racing Broom";
        Product product = new Product(id,name);

        String expected_name = "Starshot v3 Sports Broom";

        // Invoke
        product.setName(expected_name);

        // Analyze
        assertEquals(expected_name,product.getName());
    }

    @Test
    public void testToString() {
        // Setup
        int id = 99;
        String name = "The Vruum Broom by BroomCorp";
        String expected_string = String.format(Product.STRING_FORMAT,id,name);
        Product product = new Product(id,name);

        // Invoke
        String actual_string = product.toString();

        // Analyze
        assertEquals(expected_string,actual_string);
    }
}