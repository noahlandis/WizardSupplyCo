package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.estore.api.estoreapi.model.Stock.Status;

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
        int quantity = 11;
        Stock stock = new Stock(quantity);

        // Invoke
        boolean actualIsLowStock = stock.isLowStock();
        boolean actualIsOutOfStock = stock.isOutOfStock();

        // Analyze
        assertFalse(actualIsLowStock);
        assertFalse(actualIsOutOfStock);
    }

    @Test
    public void testStatusLowStock() {
        // Setup
        int quantity = 10;
        Stock stock = new Stock(quantity);

        // Invoke
        boolean actualIsLowStock = stock.isLowStock();
        boolean actualIsOutOfStock = stock.isOutOfStock();

        // Analyze
        assertTrue(actualIsLowStock);
        assertFalse(actualIsOutOfStock);
    }

    @Test
    public void testStatusOutOfStock() {
        // Setup
        int quantity = 0;
        Stock stock = new Stock(quantity);

        // Invoke
        boolean actualIsLowStock = stock.isLowStock();
        boolean actualIsOutOfStock = stock.isOutOfStock();

        // Analyze
        assertFalse(actualIsLowStock);
        assertTrue(actualIsOutOfStock);
    }

    @Test
    public void testAddStock() {
        // Setup
        Stock stock = new Stock(0);
        int stockToAdd = 11;
        int expectedNewQuantity = 11;
     
        // Invoke
        stock.addStock(stockToAdd);

        // Analyze
        assertEquals(expectedNewQuantity, stock.getQuantity());
        assertFalse(stock.isLowStock());
        assertFalse(stock.isOutOfStock());
    }

    @Test
    public void testRemoveStockValid() {
        // Setup
        Stock stock = new Stock(11);
        int stockToRemove = 1;
        int expectedNewQuantity = 10;

        // Invoke
        stock.removeStock(stockToRemove);

        // Analyze
        assertEquals(expectedNewQuantity, stock.getQuantity());
        assertTrue(stock.isLowStock());
        assertFalse(stock.isOutOfStock());
    }

    @Test
    public void testRemoveStockMoreThanAvailable() {
        // Setup
        Stock stock = new Stock(11);
        int stockToRemove = 100;
        int expectedNewQuantity = 0;

        // Invoke
        stock.removeStock(stockToRemove);

        // Analyze
        assertEquals(expectedNewQuantity, stock.getQuantity());
        assertFalse(stock.isLowStock());
        assertTrue(stock.isOutOfStock());
    }

    @Test
    public void testToString() {
        // Setup
        int quantity = 0;
        Stock stock = new Stock(quantity);

        String expectedString = String.format(Stock.STRING_FORMAT, quantity, Status.OUT_OF_STOCK);

        // Invoke
        String actualString = stock.toString();

        // Analyze
        assertEquals(expectedString, actualString);
    }
}
