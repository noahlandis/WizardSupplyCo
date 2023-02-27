package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * The unit test suite for the Stock class
 * 
 * @author Noah Landis
 */
@Tag("Model-tier")
public class StockTest {
    @Test
    public void testConstructor() {
        // Setup
        int expectedQuantity = 10;

        // Invoke
        Stock stock = new Stock(expectedQuantity);

        // Analyze
        assertEquals(expectedQuantity, stock.getQuantity());
    }

    @Test
    public void testStatusInStock() {
        // Setup
        boolean expectedIsLowStock = false;
        boolean expectedIsOutOfStock = false;
        int quantity = 11;

        // Invoke
        Stock stock = new Stock(quantity);

        // Analyze
        assertEquals(expectedIsLowStock, stock.isLowStock());
        assertEquals(expectedIsOutOfStock, stock.isOutOfStock());
    }

    @Test
    public void testStatusLowStock() {
        // Setup
        boolean expectedIsLowStock = true;
        boolean expectedIsOutOfStock = false;
        int quantity = 10;

        // Invoke
        Stock stock = new Stock(quantity);

        // Analyze
        assertEquals(expectedIsLowStock, stock.isLowStock());
        assertEquals(expectedIsOutOfStock, stock.isOutOfStock());
    }

    @Test
    public void testStatusOutOfStock() {
        // Setup
        boolean expectedIsLowStock = true;
        boolean expectedIsOutOfStock = false;
        int quantity = 10;

        // Invoke
        Stock stock = new Stock(quantity);

        // Analyze
        assertEquals(expectedIsLowStock, stock.isLowStock());
        assertEquals(expectedIsOutOfStock, stock.isOutOfStock());
    }


  
    
}
