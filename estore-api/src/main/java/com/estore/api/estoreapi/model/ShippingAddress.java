package com.estore.api.estoreapi.model;
import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * This class is to store shipping address of a customer
 * 
 * @author Kanisha Agrawal
 */

public class ShippingAddress {

    private static final Logger LOG = Logger.getLogger(ShippingAddress.class.getName());

    // Package private for tests
    static final String STRING_FORMAT = "ShippingAddress [country=%s, state=%s, city=%s, zipcode=%d addressLine1=%s]";

    @JsonProperty("country") private String country;
    @JsonProperty("state") private String state;
    @JsonProperty("city") private String city;
    @JsonProperty("zipCode") private int zipCode;
    @JsonProperty("addressLine1") private String addressLine1;
    @JsonProperty("addressLine2") private String addressLine2;
    @JsonProperty("apartmentNumber") private String apartmentNumber;    

     /**
     * Create a shipping adress with the country,state,city,zipCode,addressLine1 fields given as the default
     * @param country The country to be shipped
     * @param state The state to be shipped
     * @param city The city to be shipped
     * @param zipCode The zipCode to be shipped
     * @param addressLine1 The line 1 of address to be shipped at
     */
    public ShippingAddress (@JsonProperty("country") String country, @JsonProperty("state") String state, @JsonProperty("city") String city, @JsonProperty("zipCode") int zipCode, @JsonProperty("addressLine1") String addressLine1){
        this.country = country;
        this.state = state;
        this.city = city;
        this.zipCode = zipCode;
        this.addressLine1 = addressLine1;
    }

    /**
     * Create a shipping adress with the country,state,city,zipCode,addressLine1,addressLine2 fields given as the default
     * @param country The country to be shipped
     * @param state The state to be shipped
     * @param city The city to be shipped
     * @param zipCode The zipCode to be shipped
     * @param addressLine1 The line 1 of address to be shipped at
     * @param addressLine2 The line 2 of address to be shipped at
     */
    public ShippingAddress (@JsonProperty("country") String country, @JsonProperty("state") String state, @JsonProperty("city") String city, @JsonProperty("zipCode") int zipCode, @JsonProperty("addressLine1") String addressLine1,@JsonProperty("addressLine2") String addressLine2){
        this.country = country;
        this.state = state;
        this.city = city;
        this.zipCode = zipCode;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
    }

    /**
     * Create a shipping adress with the country,state,city,zipCode,addressLine1,addressLine2,apartmentNumber fields given as the default
     * @param country The country to be shipped
     * @param state The state to be shipped
     * @param city The city to be shipped
     * @param zipCode The zipCode to be shipped
     * @param addressLine1 The line 1 of address to be shipped at
     * @param addressLine2 The line 2 of address to be shipped at
     * @param apartmentNumber the apartment number of the user to be shipped at
     */
    public ShippingAddress (@JsonProperty("country") String country, @JsonProperty("state") String state, @JsonProperty("city") String city, @JsonProperty("zipCode") int zipCode, @JsonProperty("addressLine1") String addressLine1,@JsonProperty("addressLine2") String addressLine2, @JsonProperty("apartmentNumber") String apartmentNumber ){
        this.country = country;
        this.state = state;
        this.city = city;
        this.zipCode = zipCode;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.apartmentNumber = apartmentNumber;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public String toString(){
        return String.format(STRING_FORMAT, country, state, city, zipCode, addressLine1);
    }
}
