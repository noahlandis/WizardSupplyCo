package com.estore.api.estoreapi.model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.estore.api.estoreapi.persistence.InventoryDAO;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a cart for a user
 * 
 * @author Ryan Webb
 */
public class Cart {
    private static final Logger LOG = Logger.getLogger(Cart.class.getName());
    @JsonProperty("userId") private int userId;
    @JsonProperty("productsMap") private Map<Integer, Integer> productsMap;

    private InventoryDAO inventoryDao;

    /**
     * Creates a cart for a given user
     * @param userId The id of the user who owns the cart
     */
    public Cart(int userId) {
        productsMap = new HashMap<>();
        this.userId = userId;
    }

    /**
     * Sets the inventoryDao for the cart
     * @param inventoryDao The inventoryDao for the cart
     */
    public void setInventoryDao(InventoryDAO inventoryDao) {
        this.inventoryDao = inventoryDao;
    }
    
    /**
     * Creates an array to get the skus of theproducts in the cart
     * @return the skus of the products in the cart
     */
    public int[] getSkuArray() {
        int[] skus = new int[productsMap.size()];
        int i = 0;
        for (Map.Entry<Integer, Integer> entry : productsMap.entrySet()) {
            skus[i] = entry.getKey();
            i++;
        }
        return skus;
    }
    
    /**
     * Creates a cart with the given products
     * @param productsMap The products in the cart
     * 
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If a field
     * is not provided in the JSON object, the Java field gets the default Java
     * value, i.e. 0 for int
     */
    @JsonCreator // @JsonCreator specifies this constructor to be used to 
                 // create the Java object from the JSON object
    public Cart(@JsonProperty("userId") int userId, @JsonProperty("productsMap") Map<Integer, Integer> productsMap) {
        this.productsMap = productsMap;
        this.userId = userId;
    }

    /**
     * Returns the id of the user who owns the cart
     * @return The id of the user who owns the cart
     */
    @JsonIgnore // @JsonIgnore specifies that this field should not be serialized
    public int getUserId() { return userId; }

    /**
     * Retrieves the number of products in the cart
     * @return The number of products in the cart
     */
    @JsonProperty("count")
    public int getCount() { return productsMap.size(); }

    /**
     * Checks if the product with the given sku is in the cart
     * @param sku
     * @return true if the product is in the cart, false otherwise
     */
    public boolean containsProduct(int sku) {
        return productsMap.containsKey(sku);
    }

    /**
     * Retrieves the count of a product in the cart
     * 
     * @param sku The sku of the product to retrieve
     * @return The count of the product in the cart
     */
    public int getProductCount(int sku) {
        return productsMap.getOrDefault(sku, 0);
    }


    /**
     * Adds a product to the cart
     * @param sku The sku of the product to add
     * @param quantity The quantity of the product to add
     * 
     * @return true if the product was added to the cart, false if the sku is not found in the inventory
     * @throws InsufficientStockException when there is insufficient stock of the product
     */
    public boolean addProduct(int sku, int quantity) throws InsufficientStockException {
        LOG.info("Adding product with sku " + sku + " and quantity " + quantity + " to cart for user " + userId);
        int currentQuantity = productsMap.getOrDefault(sku, 0);
        int newQuantity = currentQuantity + quantity;
        System.out.println("newQuantity: " + newQuantity);

        // check the inventory to see if the product is available at the new quantity
        try {
            Product product = inventoryDao.getProduct(sku);
            if (product == null) {
                LOG.warning("Product with sku " + sku + " not found in inventory");
                return false;
            }
            if (!product.hasEnoughStockFor(newQuantity)) {
                LOG.warning("Product with sku " + sku + " does not have enough stock for quantity " + newQuantity);
                throw new InsufficientStockException(sku, newQuantity);
            }
            
            productsMap.put(sku, newQuantity);
            LOG.info("Product with sku " + sku + " added to cart for user " + userId);
            return true;

        } catch (IOException e) {
            LOG.severe("Error getting inventory for sku " + sku);
            return false;
        }
    }

    /**
     * Removes a product from the cart
     * @param sku The sku of the product to remove
     * @param quantity The quantity of the product to remove
     * 
     * @return true if the product was removed from the cart, false if the sku is not found in the inventory
     */
    public boolean removeProduct(int sku, int quantity) {
        LOG.info("Removing " + quantity + " of product with sku " + sku + " from cart for user " + userId);

        // If the product is not in the cart, return false
        if (!productsMap.containsKey(sku)) {
            LOG.warning("Product with sku " + sku + " is not in the cart for user " + userId);
            return false;
        }

        int currentQuantity = productsMap.get(sku);

        // If the quantity to remove is greater than the quantity in the cart,
        // remove the product from the cart
        int newQuantity = currentQuantity - quantity;
        if (newQuantity <= 0) {
            productsMap.remove(sku);
            LOG.info("Product with sku " + sku + " removed from cart for user " + userId);
        } else {
            productsMap.put(sku, newQuantity);
            LOG.info("Product with sku " + sku + " quantity reduced to " + newQuantity + " in cart for user " + userId);
        }

        return true;
    }

    /**
     * Removes all of a product from the cart
     * @param sku The sku of the product to remove
     * 
     * @return true if the product was removed from the cart, false if the sku is not found in the inventory
     */
    public boolean removeProduct(int sku) {
        LOG.info("Removing all of product with sku " + sku + " from cart for user " + userId);

        return removeProduct(sku, getProductCount(sku));
    }

    /**
     * Clears the cart
     * @param sku The sku of the product to remove
     * @param quantity The quantity of the product to remove
     */
    public void clear() { 
        productsMap.clear();
        LOG.info("Cart for user " + userId + " cleared");
    }

    /**
     * Calculates the total price of the cart
     * @return The total price of the cart
     * @throws IOException If there is an error reading from the inventory
     */
    @JsonProperty("totalPrice")
    public float getTotalPrice() throws IOException {
        float total = 0;

        // Iterate through the products in the cart and calculate the total
        for (Map.Entry<Integer, Integer> entry : productsMap.entrySet()) {
            int sku = entry.getKey();
            int quantity = entry.getValue();
            Product product = inventoryDao.getProduct(sku);

            // If the product is not in the inventory, it is not added to the total
            if (product != null) {
                total += product.getPrice() * quantity;
            }
        }

        // Round the total to 2 decimal places
        total = (float) Math.round(total * 100) / 100;

        LOG.info("Total price for cart for user " + userId + " is " + total);
        return total;
    }
}