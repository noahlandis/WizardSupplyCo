package com.estore.api.estoreapi.model;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Represents a Product entity
 */
public class Product {
    private static final Logger LOG = Logger.getLogger(Product.class.getName());

    // Package private for tests
    static final String STRING_FORMAT = "Product [sku=%d, name=%s, price=%f]";

    @JsonProperty("sku") private int sku;
    @JsonProperty("name") private String name;
    @JsonProperty("price") private float price;
    @JsonProperty("stock") private Stock stock;
    @JsonProperty("images") private String[] images;
    @JsonProperty("description") private Description description;

    /**
     * Create a product with the given SKU, name, price and stock
     * @param sku The sku of the product
     * @param name The name of the product
     * @param price the price of the product
     * @param stock the stock of the product
     * 
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If a field
     * is not provided in the JSON object, the Java field gets the default Java
     * value, i.e. 0 for int
     */
    public Product(@JsonProperty("sku") int sku, @JsonProperty("name") String name, @JsonProperty("price") float price, @JsonProperty("stock") Stock stock) {
        this.sku = sku;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    /**
     * Retrieves the sku of the product
     * @return The sku of the product
     */
    public int getSku() {return sku;}

    /**
     * Sets the name of the product - necessary for JSON object to Java object deserialization
     * @param name The name of the product
     */
    public void setName(String name) {this.name = name;}

    /**
     * Retrieves the name of the product
     * @return The name of the product
     */
    public String getName() {return name;}

    /**
     * Sets the price of the product - necessary for JSON object to Java object deserialization
     * @param price the price of the product
     */
    public void setPrice(float price) {this.price = price;}

    /**
     * Retrieves the price of the product
     * @return The price of the product
     */
    public float getPrice() {return price;}

    /**
     * Retrieves the images of the product
     * in string form
     * @return the array of strings containing image data
     */
    public String[] getImages() {return images;}

    /**
     * Retrieves the product's stock object
     * @return The stock object of the product
     */
    public Stock getStock() {
        return stock;
    }

    /**
     * Retrieves the description of the product
     * @return Description object
     */
    public Description getDescription(){return description;}

    /**
     * Checks if the name of the product is the same as the name passed in.
     * Design Principle: Information Expert
     * @param nameToCheck The name to check
     * @return True if the name of the product is the same as the name passed in
     */
    public boolean nameEquals(String nameToCheck) {
        return this.name.equals(nameToCheck);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT,sku,name,price);
    }
}
