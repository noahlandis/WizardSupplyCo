package com.estore.api.estoreapi.model;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonProperty("images") private String[] images;
    @JsonProperty("description") private Description description;
    private Stock stock;

    /**
     * JSON contructor to create a product with the given SKU, name, price and stock quantity
     * @param sku The sku of the product
     * @param name The name of the product
     * @param price the price of the product
     * @param stock the stock quantity of the product
     * 
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If a field
     * is not provided in the JSON object, the Java field gets the default Java
     * value, i.e. 0 for int
     */
    @JsonCreator
    public Product(@JsonProperty("sku") int sku, @JsonProperty("name") String name, @JsonProperty("price") float price, @JsonProperty("stockQuantity") int stockQuantity) {
        this.sku = sku;
        this.name = name;
        this.price = price;
        this.stock = new Stock(stockQuantity);
    }

    /**
     * Creates a product with the given SKU, name, price and stock object
     * @param sku The sku of the product
     * @param name The name of the product
     * @param price the price of the product
     * @param stock the stock object of the product
     */
    public Product(int sku, String name, float price, Stock stock) {
        this.sku = sku;
        this.name = name;
        this.price = price;
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
     * 
     * @JsonIgnore is used to ignore the stock object when serializing the product to JSON
     */
    @JsonIgnore
    public Stock getStock() {
        return stock;
    }

    /**
     * Gets the stock quantity of the product.
     * 
     * This method is used to serialize the stock quantity to JSON
     * 
     * @return The stock quantity of the product
     */
    @JsonProperty("stockQuantity")
    public int getStockQuantity() {
        return stock.getQuantity();
    }

    /**
     * Checks if there is enough stock for the quantity passed in
     * @param quantity The quantity to check
     * @return True if there is enough stock for the quantity passed in
     */
    public boolean hasEnoughStockFor(int quantity) {
        return stock.getQuantity() >= quantity;
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
