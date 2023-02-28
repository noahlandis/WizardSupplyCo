package com.estore.api.estoreapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.estore.api.estoreapi.persistence.CartsDAO;
import com.estore.api.estoreapi.model.Cart;

/**
 * Handles the REST API requests for the Carts resource
 * <p>
 * {@literal @}RestController Spring annotation identifies this class as a REST API
 * method handler to the Spring framework
 * 
 * @author Ryan Webb (rfw5762)
 */

@RestController
@RequestMapping("carts")
public class CartsController {
    private static final Logger LOG = Logger.getLogger(InventoryController.class.getName());
    private CartsDAO cartsDao;

    /**
     * Creates a REST API controller to respond to requests
     * 
     * @param cartsDao The {@link CartsDAO Carts Data Access Object} to perform CRUD operations
     * <br>
     * This dependency is injected by the Spring Framework
     */
    public CartsController(CartsDAO cartsDao) {
        this.cartsDao = cartsDao;
    }

    /**
     * Responds to the GET request for a {@linkplain Cart cart} for the user with the given userId
     * 
     * @param userId The id of the {@link User user} who owns the cart
     * 
     * @return ResponseEntity with {@link Cart cart} object and HTTP status of OK<br>
     * ResponseEntity with HTTP status of NOT_FOUND if the cart does not exist<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * 
     * Example: Get cart for user with id 1
     * GET http://localhost:8080/carts/1
     */
    @GetMapping("/{userId}")
    public ResponseEntity<Cart> getCart(@PathVariable int userId) {
        LOG.info("GET /carts/" + userId);

        try {
            Cart cart = cartsDao.getCart(userId);
            if (cart == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }


            return new ResponseEntity<Cart>(cart, HttpStatus.OK);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all {@linkplain Cart carts}
     * 
     * @return ResponseEntity with array of {@link Cart cart} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * 
     * Example: Get all carts
     * GET http://localhost:8080/carts/
     */
    @GetMapping("")
    public ResponseEntity<Cart[]> getCarts() {
        LOG.info("GET /carts/");

        try {
            Cart[] carts = cartsDao.getCarts();
            return new ResponseEntity<Cart[]>(carts, HttpStatus.OK);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates a {@linkplain Cart cart} for the user with the given userId
     * 
     * @param cart - The {@link Cart cart} to create
     * 
     * @return ResponseEntity with created {@link Cart cart} object and HTTP status of CREATED<br>
     * ResponseEntity with HTTP status of CONFLICT if the cart already exists<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * 
     * Example: Create a cart for user with id 1
     * POST http://localhost:8080/carts/1
     */
    @PostMapping("/{userId}")
    public ResponseEntity<Cart> createCart(@PathVariable int userId) {
        LOG.info("POST /carts/" + userId);

        try {
            Cart cart = cartsDao.createCart(userId);
            if (cart == null) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }

            return new ResponseEntity<Cart>(cart, HttpStatus.CREATED);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Adds the given quantity of the {@linkplain Product product} with the given sku to the
     * {@linkplain Cart cart} for the user with the given userId
     * 
     * @param userId The id of the {@link User user} who owns the cart
     * @param sku The sku of the {@link Product product} to add to the cart
     * @param quantity The quantity of the {@link Product product} to add to the cart
     * 
     * @return ResponseEntity with updated {@link Cart cart} object and HTTP status of OK<br>
     * ResponseEntity with HTTP status of NOT_FOUND if the cart or product does not exist<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * 
     * Example: Add 2 items with sku 3 to the cart for user with id 1
     * PUT http://localhost:8080/carts/1/items/3?quantity=2
     */
    @PutMapping("/{userId}/items/{sku}")
    public ResponseEntity<Cart> addProductToCart(@PathVariable int userId, @PathVariable int sku, @RequestParam int quantity) {
        LOG.info("PUT /carts/" + userId + "/items/" + sku + "?quantity=" + quantity);

        try {
            Cart cart = cartsDao.addProductToCart(userId, sku, quantity);
            if (cart == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<Cart>(cart, HttpStatus.OK);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Removes the given quantity of the {@linkplain Product product} with the given sku from the
     * {@linkplain Cart cart} for the user with the given userId
     * 
     * @param userId The id of the {@link User user} who owns the cart
     * @param sku The sku of the {@link Product product} to remove from the cart
     * @param quantity The quantity of the {@link Product product} to remove from the cart
     * 
     * @return ResponseEntity with updated {@link Cart cart} object and HTTP status of OK<br>
     * ResponseEntity with HTTP status of NOT_FOUND if the cart or product does not exist<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * 
     * Example: Remove 2 items with sku 3 from the cart for user with id 1
     * DELETE http://localhost:8080/carts/1/items/3?quantity=2
     */
    @DeleteMapping("/{userId}/items/{sku}")
    public ResponseEntity<Cart> removeProductFromCart(@PathVariable int userId, @PathVariable int sku, @RequestParam int quantity) {
        LOG.info("DELETE /carts/" + userId + "/items/" + sku + "?quantity=" + quantity);

        try {
            Cart cart = cartsDao.removeProductFromCart(userId, sku, quantity);
            if (cart == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<Cart>(cart, HttpStatus.OK);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Removes all of the {@linkplain Product product} with the given sku from the
     * {@linkplain Cart cart} for the user with the given userId
     * 
     * @param userId The id of the {@link User user} who owns the cart
     * @param sku The sku of the {@link Product product} to remove from the cart
     * 
     * @return ResponseEntity with updated {@link Cart cart} object and HTTP status of OK<br>
     * ResponseEntity with HTTP status of NOT_FOUND if the cart or product does not exist<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * 
     * Example: Remove all items with sku 3 from the cart for user with id 1
     * DELETE http://localhost:8080/carts/1/items/3
     */

    /**
     * Deletes the {@linkplain Cart cart} for the user with the given userId
     * 
     * @param userId The id of the {@link User user} who owns the cart
     * 
     * @return ResponseEntity with HTTP status of OK<br>
     * ResponseEntity with HTTP status of NOT_FOUND if the cart does not exist<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * 
     * Example: Delete the cart for user with id 1
     * DELETE http://localhost:8080/carts/1
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<Cart> deleteCart(@PathVariable int userId) {
        LOG.info("DELETE /carts/" + userId);

        try {
            boolean deleted = cartsDao.deleteCart(userId);
            if (!deleted) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for the total of the {@linkplain Cart cart} for the user with
     * the given userId
     * 
     * @param userId The id of the {@link User user} who owns the cart
     * 
     * @return ResponseEntity with the price of the {@link Cart cart} and HTTP status of OK<br>
     * ResponseEntity with HTTP status of NOT_FOUND if the cart does not exist<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * 
     * Example: Get the total of the cart for user with id 1
     * GET http://localhost:8080/carts/{userId}/total
     */
    @GetMapping("/{userId}/total")
    public ResponseEntity<Double> getCartTotal(@PathVariable int userId) {
        LOG.info("GET /carts/" + userId + "/total");

        try {
            Double total = cartsDao.getCartTotal(userId);
            if (total == 0) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<Double>(total, HttpStatus.OK);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
