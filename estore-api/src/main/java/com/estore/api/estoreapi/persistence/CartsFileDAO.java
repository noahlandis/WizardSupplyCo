package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.estore.api.estoreapi.model.Cart;
import com.estore.api.estoreapi.model.InsufficientStockException;

/**
 * Implements the functionality for JSON file-based persistence for Carts
 * 
 * {@literal @}Component Spring annotation instantiates a single instance of this
 * class and injects the instance into other classes as needed
 * 
 * @author Ryan Webb
 */
@Repository
public class CartsFileDAO implements CartsDAO {

    private static final Logger LOG = Logger.getLogger(CartsFileDAO.class.getName());

    private Map<Integer,Cart> carts;   // Provides a local cache of the cart objects
                                    // so that we don't need to read from the file
                                   // each time
    private ObjectMapper objectMapper;  // Provides conversion between Cart
                                       // objects and JSON text format written
                                      // to the file
    private String filename;    // Filename to read from and write to

    private InventoryDAO inventoryDao;

    public CartsFileDAO(@Value("${carts.file}") String filename, ObjectMapper objectMapper, InventoryDAO inventoryDao) throws IOException {
        LOG.info("CartsFileDAO created");
        this.filename = filename;
        this.objectMapper = objectMapper;
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        this.inventoryDao = inventoryDao;
        load(); // load the carts from the file
    }

    /**
     * Saves the carts to the file
     * 
     * @return true if the carts were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        LOG.info("Saving carts to file: " + filename);
        Cart[] cartsArray = getCarts();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename), cartsArray);

        return true;
    }

    /**
     * Loads the carts from the file
     * 
     * @return true if the carts were read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        LOG.info("Loading carts from file: " + filename);
        carts = new TreeMap<>();

        // readValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        Cart[] cartsArray = objectMapper.readValue(new File(filename), Cart[].class);

        // Load the carts into the local cache
        for (Cart cart : cartsArray) {
            LOG.info("Loaded cart for user: " + cart.getUserId());
            cart.setInventoryDao(inventoryDao);
            carts.put(cart.getUserId(), cart);
        }

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Cart[] getCarts() {
        synchronized (carts) {
            return carts.values().toArray(new Cart[0]);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Cart getCart(int userId) {
        synchronized (carts) {
            return carts.get(userId);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Cart createCart(int userId) {
        // If the cart already exists, return null
        if (carts.containsKey(userId)) {
            return null;
        }

        synchronized (carts) {
            Cart cart = new Cart(userId);
            cart.setInventoryDao(inventoryDao);
            carts.put(cart.getUserId(), cart);
            try {
                save();
            } catch (IOException e) {
                LOG.warning("Failed to save cart for user " + userId + ". " + e.getMessage());
            }

            return cart;
        }
    }

    /**
     * {@inheritDoc}
     * @throws InsufficientStockException
     */
    @Override
    public Cart addProductToCart(int userId, int sku, int quantity) throws InsufficientStockException {
        synchronized (carts) {
            if (!carts.containsKey(userId)) {
                LOG.warning("User with id " + userId + " does not have a cart!");
                return null;
            }
            
            Cart cart = carts.get(userId);
            boolean success = cart.addProduct(sku, quantity);

            // If the product could not be added to the cart, return null
            if (!success) {
                LOG.warning("User with id " + userId + " does not have product with sku " + sku + " in their cart!");
                return null;
            }

            try {
                save();
            } catch (IOException e) {
                LOG.warning("Failed to save cart for user " + userId + ". " + e.getMessage());
            }

            return cart;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Cart removeProductFromCart(int userId, int sku, int quantity) throws IOException {
        synchronized (carts) {
            if (!carts.containsKey(userId)) {
                LOG.warning("User with id " + userId + " does not have a cart!");
                return null;
            }

            Cart cart = carts.get(userId);
            boolean success = cart.removeProduct(sku, quantity);

            if (!success) {
                LOG.warning("User with id " + userId + " does not have product with sku " + sku + " in their cart!");
                return null;
            }          

            try {
                save();
            } catch (IOException e) {
                LOG.warning("Failed to save cart for user " + userId + ". " + e.getMessage());
            }

            return cart;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Cart removeProductFromCart(int userId, int sku) throws IOException {
        synchronized (carts) {
            if (!carts.containsKey(userId)) {
                LOG.warning("User with id " + userId + " does not have a cart!");
                return null;
            }

            Cart cart = carts.get(userId);
            boolean success = cart.removeProduct(sku);

            if (!success) {
                LOG.warning("User with id " + userId + " does not have product with sku " + sku + " in their cart!");
                return null;
            }          

            try {
                save();
            } catch (IOException e) {
                LOG.warning("Failed to save cart for user " + userId + ". " + e.getMessage());
            }

            return cart;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Cart clearCart(int userId) {
        synchronized (carts) {
            Cart cart = carts.get(userId);
            if (cart != null) {
                cart.clear();
                try {
                    save();
                } catch (IOException e) {
                    LOG.warning("Failed to save cart for user " + userId + ". " + e.getMessage());
                }
            }

            return cart;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteCart(int userId) {
        synchronized (carts) {
            for (Cart cart : carts.values()) {
                if (cart.getUserId() != userId)
                    continue;

                carts.remove(cart.getUserId());
                try {
                    save();
                } catch (IOException e) {
                    LOG.warning("Failed to save cart for user " + userId + ". " + e.getMessage());
                }
                return true;
            }
        }

        return false;
    }
}