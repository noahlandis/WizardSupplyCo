package com.estore.api.estoreapi.model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.estore.api.estoreapi.persistence.InventoryDAO;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Cart {
    private static final Logger LOG = Logger.getLogger(Cart.class.getName());
    private Map<Integer, Integer> itemsMap;
    private int userId;

    private InventoryDAO inventoryDao;

    /**
     * Creates a cart for a given user
     * @param userId The id of the user who owns the cart
     */
    public Cart(int userId) {
        itemsMap = new HashMap<>();
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
     * Creates a cart with the given products
     * @param itemsMap The products in the cart
     * 
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If a field
     * is not provided in the JSON object, the Java field gets the default Java
     * value, i.e. 0 for int
     */
    @JsonCreator // @JsonCreator specifies this constructor to be used to 
                 // create the Java object from the JSON object
    public Cart(@JsonProperty("itemsMap") Map<Integer, Integer> itemsMap) {
        this.itemsMap = itemsMap;
    }

    /**
     * Returns the id of the user who owns the cart
     * @return The id of the user who owns the cart
     */
    public int getUserId() { return userId; }

    /**
     * Retrieves the number of products in the cart
     * @return The number of products in the cart
     */
    public int getCount() { return itemsMap.size(); }

    /**
     * Adds a product to the cart
     * @param sku The sku of the product to add
     * @param quantity The quantity of the product to add
     * 
     * @return true if the product was added to the cart, false otherwise
     */
    public boolean addProduct(int sku, int quantity) {
        LOG.info("Adding product with sku " + sku + " and quantity " + quantity + " to cart for user " + userId);
        int currentQuantity = itemsMap.getOrDefault(sku, 0);
        int newQuantity = currentQuantity + quantity;

        // check the inventory to see if the product is available at the new quantity
        try {
            if (inventoryDao.getProduct(sku).hasEnoughStockFor(newQuantity)) {
                itemsMap.put(sku, newQuantity);
                LOG.info("Product with sku " + sku + " added to cart for user " + userId);
                return true;
            }
        } catch (IOException e) {
            LOG.severe("Error getting inventory for sku " + sku);
            return false;
        }

        return false;
    }

    /**
     * Removes a product from the cart
     * @param sku The sku of the product to remove
     * @param quantity The quantity of the product to remove
     * 
     * @return true if the product was removed from the cart, false otherwise
     */
    public boolean removeProduct(int sku, int quantity) {
        LOG.info("Removing product with sku " + sku + " and quantity " + quantity + " from cart for user " + userId);

        // If the product is not in the cart, return false
        if (!itemsMap.containsKey(sku)) {
            LOG.warning("Product with sku " + sku + " is not in the cart for user " + userId);
            return false;
        }

        int currentQuantity = itemsMap.get(sku);

        // If the quantity to remove is greater than the quantity in the cart,
        // remove the product from the cart
        int newQuantity = currentQuantity - quantity;
        if (newQuantity <= 0) {
            itemsMap.remove(sku);
            LOG.info("Product with sku " + sku + " removed from cart for user " + userId);
        } else {
            itemsMap.put(sku, newQuantity);
            LOG.info("Product with sku " + sku + " quantity reduced to " + newQuantity + " in cart for user " + userId);
        }

        return true;
    }

    /**
     * Clears the cart
     * @param sku The sku of the product to remove
     * @param quantity The quantity of the product to remove
     */
    public void clear() { 
        itemsMap.clear();
        LOG.info("Cart for user " + userId + " cleared");
    }

    /**
     * Calculates the total price of the cart
     * @return The total price of the cart
     * @throws IOException If there is an error reading from the inventory
     */
    public float getTotalPrice() throws IOException {
        float total = 0;

        // Iterate through the products in the cart and calculate the total
        for (Map.Entry<Integer, Integer> entry : itemsMap.entrySet()) {
            int sku = entry.getKey();
            int quantity = entry.getValue();
            Product product = inventoryDao.getProduct(sku);

            // If the product is not in the inventory, it is not added to the total
            if (product != null) {
                total += product.getPrice() * quantity;
            }
        }

        LOG.info("Total price for cart for user " + userId + " is " + total);
        return total;
    }
}