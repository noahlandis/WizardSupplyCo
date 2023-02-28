package com.estore.api.estoreapi.persistence;

import java.io.IOException;
import java.util.Map;

import com.estore.api.estoreapi.model.Cart;

/**
 * Defines the interface for Cart object persistence
 * 
 * @author Ryan Webb
 */
public interface CartsDAO {

    /**
     * Retrieves all {@linkplain Cart carts}
     * 
     * @param userId The user ID associated with the {@link Cart}
     * 
     * @return An array of {@link Cart cart} objects, may be empty
     * 
     * @throws IOException
     */
    Cart[] getCarts() throws IOException;

    /**
     * Retrieves a {@link Cart} object for the given user ID
     * 
     * @param userId The user ID associated with the {@link Cart}
     * 
     * @return The {@link Cart} object for the given user ID
     * <br>
     * null if no {@link Cart} object exists for the given user ID
     * 
     * @throws IOException if an issue with underlying storage
     */
    Cart getCart(int userId) throws IOException;

    /**
     * Creates and saves a new {@link Cart} object for the given user ID
     * 
     * @param userId The user ID associated with the new {@link Cart} object
     * 
     * @return The newly created {@link Cart} object
     * <br>
     * null if a {@link Cart} object already exists for the given user ID
     * 
     * @throws IOException if an issue with underlying storage
     */
    Cart createCart(int userId) throws IOException;

    /**
     * Adds a product to the {@link Cart} object for the given user ID
     * 
     * @param userId The user ID associated with the {@link Cart} object to update
     * @param sku The SKU of the product to add
     * @param quantity The quantity of the product to add
     * 
     * @return The updated {@link Cart} object
     * <br>
     * null if no {@link Cart} object exists for the given user ID
     * 
     * @throws IOException if an issue with underlying storage
     */
    Cart addProductToCart(int userId, int sku, int quantity) throws IOException;

    /**
     * Removes a product from the {@link Cart} object for the given user ID
     * 
     * @param userId The user ID associated with the {@link Cart} object to update
     * @param sku The SKU of the product to remove
     * @param quantity The quantity of the product to remove
     * 
     * @return The updated {@link Cart} object
     * <br>
     * null if no {@link Cart} object exists for the given user ID
     * 
     * @throws IOException if an issue with underlying storage
     */
    Cart removeProductFromCart(int userId, int sku, int quantity) throws IOException;

    /**
     * Clears the {@link Cart} object for the given user ID
     * 
     * @param userId The user ID associated with the {@link Cart} object to clear
     * 
     * @return The updated {@link Cart} object
     * 
     * @throws IOException if an issue with underlying storage
     */
    Cart clearCart(int userId) throws IOException;

    /**
     * Deletes a {@link Cart} object for the given user ID
     * 
     * @param userId The user ID associated with the {@link Cart} object to delete
     * 
     * @return true if the {@link Cart} object was deleted
     * <br>
     * false if no {@link Cart} object exists for the given user ID
     * 
     * @throws IOException if an issue with underlying storage
     */
    boolean deleteCart(int userId) throws IOException;

    /**
     * Retrieves the total for the {@link Cart} object for the given user ID
     * 
     * @param userId The user ID associated with the {@link Cart} object to retrieve the total for
     * 
     * @return The total for the {@link Cart} object for the given user ID
     * <br>
     * 0 if no {@link Cart} object exists for the given user ID
     * 
     * @throws IOException if an issue with underlying storage
     */
    double getCartTotal(int userId) throws IOException;
}