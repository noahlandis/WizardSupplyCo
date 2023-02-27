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

    /**
     * Creates a cart for a given user
     * @param userId The id of the user who owns the cart
     */
    public Cart(int userId) {
        itemsMap = new HashMap<>();
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
     */
    public void addProduct(int sku, int quantity) {
        int currentQuantity = itemsMap.getOrDefault(sku, 0);
        itemsMap.put(sku, currentQuantity + quantity);
    }

    /**
     * Removes a product from the cart
     * @param sku The sku of the product to remove
     * @param quantity The quantity of the product to remove
     */
    public void removeProduct(int sku, int quantity) {
        int currentQuantity = itemsMap.getOrDefault(sku, 0);

        // If the quantity is somehow zero or negative, remove the product from the cart
        if (currentQuantity <= 0) {
            // remove the product from the cart
            itemsMap.remove(sku);
            return;
        }

        // If the quantity to remove is greater than the quantity in the cart,
        // remove the product from the cart
        int newQuantity = currentQuantity - quantity;
        if (newQuantity <= 0) {
            itemsMap.remove(sku);
        } else {
            itemsMap.put(sku, newQuantity);
        }
    }

    /**
     * Clears the cart
     * @param sku The sku of the product to remove
     * @param quantity The quantity of the product to remove
     */
    public void clear() { itemsMap.clear(); }

    /**
     * Calculates the total price of the cart
     * @param inventory The inventory to use to calculate the total
     * @return The total price of the cart
     * @throws IOException If there is an error reading from the inventory
     */
    public float getTotalPrice(InventoryDAO inventory) throws IOException {
        float total = 0;

        // Iterate through the products in the cart and calculate the total
        for (Map.Entry<Integer, Integer> entry : itemsMap.entrySet()) {
            int sku = entry.getKey();
            int quantity = entry.getValue();
            Product product = inventory.getProduct(sku);

            // If the product is not in the inventory, it is not added to the total
            if (product != null) {
                total += product.getPrice() * quantity;
            }
        }

        return total;
    }
}