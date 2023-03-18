package com.estore.api.estoreapi.model;


/**
 * Exception thrown when there is insufficient stock of a product
 * in the inventory
 * 
 * @author Ryan Webb
 */
public class InsufficientStockException extends Exception {

    /**
     * Creates an InsufficientStockException with the given sku and quantity
     * @param sku The sku of the product
     * @param newQuantity The quantity of the product
     */
    public InsufficientStockException(int sku, int newQuantity) {
        super("Product with sku " + sku + " does not have enough stock for quantity " + newQuantity);
    }
}
