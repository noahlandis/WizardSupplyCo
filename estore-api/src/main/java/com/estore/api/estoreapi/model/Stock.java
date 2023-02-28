package com.estore.api.estoreapi.model;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Represents a Stock entity
 * 
 * @author SWEN Faculty
 * @author Kanisha Agrawal
 */
public class Stock {

    public enum Status{
        IN_STOCK,
        OUT_OF_STOCK,
        LOW_STOCK
    }

    // Package private for tests
    static final String STRING_FORMAT = "Stock [quantity=%d, status=%s]";
    
    static final int QUANTITY_LOW_STOCK = 10;
    static final int QUANTITY_OUT_OF_STOCK = 0; 

    @JsonProperty("quantity") private int quantity;
    private Status status;

    /**
     * Create a product with the given id and name
     * @param quantity The quantity of the stock
     * @param status The status of the stock
     * 
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If a field
     * is not provided in the JSON object, the Java field gets the default Java
     * value, i.e. 0 for int
     */
    @JsonCreator
    public Stock(@JsonProperty("quantity") int quantity) {
        this.quantity = quantity;

        updateStatus();
    }

    private void updateStatus() {
        if (quantity == QUANTITY_OUT_OF_STOCK) {
            status = Status.OUT_OF_STOCK;
        }
        else if (quantity <= QUANTITY_LOW_STOCK) {
            status = Status.LOW_STOCK;
        }
        else {
            status = Status.IN_STOCK;
        }
    }

     /**
     * Retrieves the quantity of the product
     * @return The quantity of the product
     */
    public int getQuantity() { return this.quantity; }

     /**
     * Retrives if the product is out of stock 
     * @return a boolean value 
     */
    public boolean isOutOfStock() {
        return status == Status.OUT_OF_STOCK;
    }

     /**
     * Retrives if the product is low in stock 
     * @return a boolean value 
     */
    public boolean isLowStock() {
        return status == Status.LOW_STOCK;
    }

    /**
     * Sets the status of the product after some stocks are added
     * @param amount The quantity of the product
     */
    public void addStock(int amount) {
        this.quantity += amount;

        updateStatus();
    }

    /**
     * Sets the status for the product after some stocks is removed
     * @param amount The quantity of the product
     */
    public void removeStock(int amount) {    
        quantity -= amount;

        // floor the quantity at 0
        if (quantity < 0) {
            quantity = 0;
        }
        
        updateStatus();
    }


    /**
     * {@inheritDoc}
     */

    @Override
    public String toString() {
        return String.format(STRING_FORMAT,quantity,status);
    }
}