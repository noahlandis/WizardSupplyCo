package com.estore.api.estoreapi.model;

import java.util.logging.Logger;

import javax.swing.event.CaretEvent;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class is to store order of a customer
 * 
 * @author Kanisha Agrawal
 */

public class Order {

    private static final Logger LOG = Logger.getLogger(Order.class.getName());

    // Package private for tests
    static final String STRING_FORMAT = "Order [orderNumber=%d, firstname=%s, lastname=%s, phoneNumber=%s, emailAddress=%s]";

    @JsonProperty("orderNumber") private int orderNumber;
    @JsonProperty("firstname") private String firstname;
    @JsonProperty("lastname") private String lastname;
    @JsonProperty("phoneNumber") private String phoneNumber;
    @JsonProperty("emailAddress") private String emailAddress;
    private ShippingAddress shippingAddress;
    private Cart cart;


    /**
     * JSON contructor to create an order with the given orderNumber, firstname, lastname, phoneNumber, emailAddress, shipping Address,cart quantity
     * @param orderNumber the number of the order
     * @param firstname The firstname of the customer
     * @param lastname The lastname of the customer
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
    public Order( @JsonProperty("orderNumber") int orderNumber, @JsonProperty("firstname") String firstname, @JsonProperty("lastname") String lastname,  @JsonProperty("phoneNumber") String phoneNumber, @JsonProperty("emailAddress") String emailAddress, ShippingAddress shippingAddress, Cart cart){
        this.orderNumber = orderNumber;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.shippingAddress = shippingAddress;
        this.cart = cart;
    }

    /**
     * JSON contructor to create an order with the given firstname, lastname, phoneNumber, emailAddress, shipping Address,cart quantity
     * @param firstname The firstname of the customer
     * @param lastname The lastname of the customer
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
    public Order(@JsonProperty("firstname") String firstname, @JsonProperty("lastname") String lastname,  @JsonProperty("phoneNumber") String phoneNumber, @JsonProperty("emailAddress") String emailAddress, ShippingAddress shippingAddress, Cart cart){
        this.firstname = firstname;
        this.lastname = lastname;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.shippingAddress = shippingAddress;
        this.cart = cart;
    }

    
    /**
     * Retrieves the firstname of the person who placed the order
     * @return The firstname of the person who placed theorder
     */
    public String getFirstName(){
        return firstname;
    }

    /**
     * Retrieves the lastname of the person who placed the order
     * @return The lastname of the person who placed theorder
     */
    public String getLastName(){
        return lastname;
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
     * Sets the orderNumber of the person who placed the order
     * @param orderNumber sets orderNumber of the person who placed theorder
     */
    public void setOrderNumber(int orderNumber){
        this.orderNumber = orderNumber;
    }
    
    /**
     * Retrieves the userId of the order
     * @return The userId of the order
     */
    public int getUserId(){
        return cart.getUserId();
    }

    /**
     * Retrieves the productSkus of the cart
     * @return The productSkus of the cart
     */    
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
        return String.format(STRING_FORMAT,orderNumber, firstname,lastname,phoneNumber,emailAddress,shippingAddress, cart);
    } 

}
