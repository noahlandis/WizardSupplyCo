package com.estore.api.estoreapi.model;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class is to store order of a customer
 * 
 * @author Kanisha Agrawal
 */

public class Order {

    private static final Logger LOG = Logger.getLogger(Order.class.getName());

    // Package private for tests
    static final String STRING_FORMAT = "Order [orderId=%d, firstname=%s, lastname=%s, phoneNumber=%s, emailAddress=%s]";

    @JsonProperty("orderId") private int orderId;
    @JsonProperty("firstname") private String firstname;
    @JsonProperty("lastname") private String lastname;
    @JsonProperty("phoneNumber") private String phoneNumber;
    @JsonProperty("emailAddress") private String emailAddress;
    private ShippingAddress shippingAddress;
    private Cart cart;


    /**
     * JSON contructor to create a order with the given firstname, lastname, phoneNumber, emailAddress, shipping Address,cart quantity
     * @param orderId the id of the order
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
    public Order( @JsonProperty("orderId") int orderId, @JsonProperty("firstname") String firstname, @JsonProperty("lastname") String lastname,  @JsonProperty("phoneNumber") String phoneNumber, @JsonProperty("emailAddress") String emailAddress, ShippingAddress shippingAddress, Cart cart){
        this.orderId = orderId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.shippingAddress = shippingAddress;
        this.cart = cart;
    }

    public int getOrderId(){
        return orderId;
    }
    
    /**
     * Retrieves the userId of the product
     * @return The userId of the product
     */
    public int getUserId(){
        return cart.getUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT,orderId, firstname,lastname,phoneNumber,emailAddress);
    } 

}
