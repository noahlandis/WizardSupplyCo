package com.estore.api.estoreapi.model;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * This class is to store order of a customer
 * 
 * @author Kanisha Agrawal
 */
@JsonPropertyOrder({"orderNumber", "firstName", "lastName", "phoneNumber", "emailAddress", "shippingAddress", "cart"})
public class Order {

    private static final Logger LOG = Logger.getLogger(Order.class.getName());

    // Package private for tests
    static final String STRING_FORMAT = "Order [orderNumber=%d, firstName=%s, lastName=%s, phoneNumber=%s, emailAddress=%s]";

    @JsonProperty("orderNumber") private int orderNumber;
    @JsonProperty("firstName") private String firstName;
    @JsonProperty("lastName") private String lastName;
    @JsonProperty("phoneNumber") private String phoneNumber;
    @JsonProperty("emailAddress") private String emailAddress;
    @JsonProperty("shippingAddress") private ShippingAddress shippingAddress;
    @JsonProperty("cart")private Cart cart;


    /**
     * JSON contructor to create an order with the given orderNumber, first name, last name, phoneNumber, emailAddress, shipping Address, cart
     * @param orderNumber the number of the order
     * @param firstName The first name of the customer
     * @param lastName The last name of the customer
     * @param phoneNumber the phone number of the customer
     * @param emailAddress the email address of the customer
     * @param shippingAddress the shipping address object of the customer
     * @param cart the cart object of the customer
     * 
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If a field
     * is not provided in the JSON object, the Java field gets the default Java
     * value, i.e. 0 for int
     */
    @JsonCreator
    public Order(
        @JsonProperty("orderNumber") int orderNumber,
        @JsonProperty("firstName") String firstName,
        @JsonProperty("lastName") String lastName,
        @JsonProperty("phoneNumber") String phoneNumber,
        @JsonProperty("emailAddress") String emailAddress,
        @JsonProperty("shippingAddress") ShippingAddress shippingAddress,
        @JsonProperty("cart") Cart cart
    ){
        this.orderNumber = orderNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.shippingAddress = shippingAddress;
        this.cart = cart;
    }

    /**
     * JSON contructor to create an order with the given first name, last name, phoneNumber, emailAddress, shipping Address,cart quantity
     * @param firstName The first name of the customer
     * @param lastName The last name of the customer
     * @param phoneNumber the phone number of the customer
     * @param emailAddress the email address of the customer
     * @param shippingAddress the shipping address object of the customer
     * @param cart the cart object of the customer
     * 
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If a field
     * is not provided in the JSON object, the Java field gets the default Java
     * value, i.e. 0 for int
     */
    public Order(
        @JsonProperty("firstName") String firstName,
        @JsonProperty("lastName") String lastName,
        @JsonProperty("phoneNumber") String phoneNumber,
        @JsonProperty("emailAddress") String emailAddress,
        @JsonProperty("shippingAddress") ShippingAddress shippingAddress,
        @JsonProperty("cart") Cart cart
    ){
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.shippingAddress = shippingAddress;
        this.cart = cart;
    }

    
    /**
     * Retrieves the first name of the person who placed the order
     * @return The first name of the person who placed theorder
     */
    public String getFirstName(){
        return firstName;
    }

    /**
     * Retrieves the lastname of the person who placed the order
     * @return The lastname of the person who placed theorder
     */
    public String getLastName(){
        return lastName;
    }

    /**
     * Retrieves the phoneNumber of the person who placed the order
     * @return The phoneNumber of the person who placed theorder
     */
    public String getPhoneNumber(){
        return phoneNumber;
    }

    /**
     * Retrieves the emailAddress of the person who placed the order
     * @return The emailAddress of the person who placed theorder
     */
    public String getEmailAddress(){
        return emailAddress;
    }

    /**
     * Retrieves the ShippingAddress of the person who placed the order
     * @return The shippingAddress of the person who placed theorder
     */
    public ShippingAddress getShippingAddress(){
        return shippingAddress;
    }

    /**
     * Retrieves the orderNumber of the person who placed the order
     * @return The orderNumber of the person who placed theorder
     */
    public int getOrderNumber(){
        return orderNumber;
    }
    
    /**
     * Retrieves the userId of the order
     * @return The userId of the order
     */
    @JsonIgnore
    public int getUserId(){
        return cart.getUserId();
    }

    /**
     * Retrieves the productSkus of the cart
     * @return The productSkus of the cart
     */
    @JsonIgnore
    public int[] getProductSkus(){
        return cart.getSkuArray();
    }

    /**
     * Retrieves the cart object of the cart
     * @return The cart object of the cart
     */ 
    public Cart getCart(){
        return cart;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT,orderNumber, firstName,lastName,phoneNumber,emailAddress,shippingAddress, cart);
    } 

}
