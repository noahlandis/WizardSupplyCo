package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Tests for the User class and its Child classes
 * @author Kanisha Agrawal
 */

@Tag("Model-tier")
public class CartTest {
    @Test
    public void testConstructor()   {
        //Setup
        int userId = 7;
        Map<Integer,Integer> productsMap = new HashMap<>();
        productsMap.put(1,1);
        productsMap.put(2,2);
        productsMap.put(3,2);

        //Invoke
        Cart cart = new Cart(userId);

        // Analyze
        assertEquals(userId, cart.getUserId());
  }

  @Test
  public void testCartConstructor()   {
      //Setup
      int userId = 7;
      Map<Integer,Integer> productsMap = new HashMap<>();
      productsMap.put(1,1);
      productsMap.put(2,2);
      productsMap.put(3,2);

      //Invoke
      Cart cart = new Cart(userId,productsMap);

      // Analyze
      assertEquals(userId, cart.getUserId());
    }

  @Test
  public void testGetCount()   {
      //Setup
      int userId = 7;
      Map<Integer,Integer> productsMap = new HashMap<>();
      productsMap.put(1,1);
      productsMap.put(2,2);
      productsMap.put(3,2);

      //Invoke
      Cart cart = new Cart(userId,productsMap);

      // Analyze
      assertEquals(cart.getCount(),3);
    }

  @Test
  public void testContainsProduct()   {
        //Setup
        int userId = 7;
        Map<Integer,Integer> productsMap = new HashMap<>();
        productsMap.put(1,1);
        productsMap.put(2,2);
        productsMap.put(3,2);
  
        //Invoke
        Cart cart = new Cart(userId,productsMap);
  
        // Analyze
        assertEquals(cart.containsProduct(1), true);
      }  

  @Test
  public void testGetProductCount()   {
      //Setup
      int userId = 7;
      Map<Integer,Integer> productsMap = new HashMap<>();
      productsMap.put(1,1);
      productsMap.put(2,2);
      productsMap.put(3,2);

      //Invoke
      Cart cart = new Cart(userId,productsMap);

      // Analyze
      assertEquals(cart.getProductCount(1),1);
    }

    // @Test
    // public void testAddProduct()   {
    //     //Setup
    //     int userId = 7;
    //     Map<Integer,Integer> productsMap = new HashMap<>();
    //     productsMap.put(1,1);
    //     productsMap.put(2,2);
    //     productsMap.put(3,2);
  
    //     //Invoke
    //     Cart cart = new Cart(userId,productsMap);
    //     cart.addProduct(4, 3);
  
    //     // Analyze
    //     assertEquals(cart.containsProduct(4),true);
    //  }
     
     @Test
     public void testRemoveProduct()   {
         //Setup
         int userId = 7;
         Map<Integer,Integer> productsMap = new HashMap<>();
         productsMap.put(1,1);
         productsMap.put(2,2);
         productsMap.put(3,2);
   
         //Invoke
         Cart cart = new Cart(userId,productsMap);
         cart.removeProduct(3,1);
         // Analyze
         assertEquals(cart.getProductCount(3),1);
       } 

       @Test
       public void testRemovePro()   {
           //Setup
           int userId = 7;
           Map<Integer,Integer> productsMap = new HashMap<>();
           productsMap.put(1,1);
           productsMap.put(2,2);
           productsMap.put(3,2);
     
           //Invoke
           Cart cart = new Cart(userId,productsMap);
         
           // Analyze
           assertEquals(cart.removeProduct(1,1),true);
         } 

         @Test
         public void testRemoveNonexistentProduct()   {
             //Setup
             int userId = 7;
             Map<Integer,Integer> productsMap = new HashMap<>();
             productsMap.put(1,1);
             productsMap.put(2,2);
             productsMap.put(3,2);
       
             //Invoke
             Cart cart = new Cart(userId,productsMap);
           
             // Analyze
             assertEquals(cart.removeProduct(5,1),false);
           } 

     @Test
     public void testRemoveProducts()   {
         //Setup
         int userId = 7;
         Map<Integer,Integer> productsMap = new HashMap<>();
         productsMap.put(1,1);
         productsMap.put(2,2);
         productsMap.put(3,2);
   
         //Invoke
         Cart cart = new Cart(userId,productsMap);
   
         // Analyze
         assertEquals(cart.removeProduct(1),true);
       }

     @Test
     public void testclear()   {
           //Setup
           int userId = 7;
           Map<Integer,Integer> productsMap = new HashMap<>();
           productsMap.put(1,1);
           productsMap.put(2,2);
           productsMap.put(3,2);
     
           //Invoke
           Cart cart = new Cart(userId,productsMap);
           cart.clear();
           // Analyze
           assertEquals(0, cart.getCount());
         }

    //  @Test
    //  public void getTotalPrice()   {
    //            //Setup
    //            int userId = 7;
    //            Map<Integer,Integer> productsMap = new HashMap<>();
    //            productsMap.put(1,1);
    //            productsMap.put(2,2);
    //            productsMap.put(3,2);
         
    //            //Invoke
    //            Cart cart = new Cart(userId,productsMap);
            
    //            // Analyze
    //            assertEquals(cart.getTotalPrice(),);
    //          }
    
        
}