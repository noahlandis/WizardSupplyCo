package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ShippingAddressTest {
    @Test
    public void testConstructor1() {
        // Setup
        String expectedcountry = "United States of America";
        String expectedState = "New York";
        String expectedcity = "Rochester";
        int zipCode = 14623;
        String addressLine1 = "220 John Street";

        // Invoke
        ShippingAddress shippingAddress = new ShippingAddress(expectedcountry, expectedState, expectedcity, zipCode, addressLine1);

        // Analyze
        assertEquals(expectedcountry, shippingAddress.getCountry());
        assertEquals(expectedState, shippingAddress.getState());
        assertEquals(expectedcity, shippingAddress.getCity());
        assertEquals(zipCode, shippingAddress.getZipCode());
        assertEquals(addressLine1, shippingAddress.getAddressLine1());
        assertEquals("", shippingAddress.getAddressLine2());
    }

    @Test
    public void testConstructor2() {
        // Setup
        String expectedcountry = "United States of America";
        String expectedState = "New York";
        String expectedcity = "Rochester";
        int zipCode = 14623;
        String addressLine1 = "220 John Street";
        String addressLine2 = "Apt 2";

        // Invoke
        ShippingAddress shippingAddress = new ShippingAddress(expectedcountry, expectedState, expectedcity, zipCode, addressLine1, addressLine2);

        // Analyze
        assertEquals(expectedcountry, shippingAddress.getCountry());
        assertEquals(expectedState, shippingAddress.getState());
        assertEquals(expectedcity, shippingAddress.getCity());
        assertEquals(zipCode, shippingAddress.getZipCode());
        assertEquals(addressLine1, shippingAddress.getAddressLine1());
        assertEquals(addressLine2, shippingAddress.getAddressLine2());
        assertEquals("", shippingAddress.getApartmentNumber());
    }

    @Test
    public void testConstructor3() {
        // Setup
        String expectedcountry = "United States of America";
        String expectedState = "New York";
        String expectedcity = "Rochester";
        int zipCode = 14623;
        String addressLine1 = "220 John Street";
        String addressLine2 = "Apt 2";
        String apartmentNumber = "2";

        // Invoke
        ShippingAddress shippingAddress = new ShippingAddress(expectedcountry, expectedState, expectedcity, zipCode, addressLine1, addressLine2, apartmentNumber);

        // Analyze
        assertEquals(expectedcountry, shippingAddress.getCountry());
        assertEquals(expectedState, shippingAddress.getState());
        assertEquals(expectedcity, shippingAddress.getCity());
        assertEquals(zipCode, shippingAddress.getZipCode());
        assertEquals(addressLine1, shippingAddress.getAddressLine1());
        assertEquals(addressLine2, shippingAddress.getAddressLine2());
        assertEquals(apartmentNumber, shippingAddress.getApartmentNumber());
    }

}
