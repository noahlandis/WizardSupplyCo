package com.estore.api.estoreapi.persistence;

import java.io.IOException;

import com.estore.api.estoreapi.model.Order;
import com.estore.api.estoreapi.model.InsufficientStockException;

/**
 * Defines the interface for Orders object persistence
 * 
 * @author Kanisha Agrawal
 */
public interface OrdersDAO {
     /**
     * Retrieves all {@linkplain Order orders}
     * 
     * @param orderNumber The order ID associated with the {@link Order}
     * 
     * @return An array of {@link Order order} objects, may be empty
     * 
     * @throws IOException
     */
    Order[] getOrders() throws IOException;


    /**
     * Retrieves a {@link Order} object for the given order number
     * 
     * @param orderNumber The order number associated with the {@link Order}
     * 
     * @return The {@link Order} object for the given order number
     * <br>
     * null if no {@link Order} object exists for the given order number
     * 
     * @throws IOException if an issue with underlying storage
     */
    Order getOrder(int orderNumber) throws IOException;

    /**
     * Creates and saves a new {@link Order} object for the given order number
     * 
     * @param orderNumber The order number associated with the new {@link Order} object
     * 
     * @return The newly created {@link Order} object
     * <br>
     * null if a {@link Order} object already exists for the given order number
     * 
     * @throws IOException if an issue with underlying storage
     */
    Order createOrder(Order order) throws IOException;

}
