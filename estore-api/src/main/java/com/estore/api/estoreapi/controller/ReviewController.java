package com.estore.api.estoreapi.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estore.api.estoreapi.model.Review;
import com.estore.api.estoreapi.persistence.ReviewsDAO;

@RestController
@RequestMapping("reviews")
public class ReviewController {
    private static final Logger LOG = Logger.getLogger(ReviewController.class.getName());
    private ReviewsDAO reviewsDao;

    /**
     * Creates a REST API controller to respond to requests
     * 
     * @param reviewDao The {@link ReviewDAO Review Data Access Object} to perform CRUD operations
     * <br>
     * This dependency is injected by the Spring Framework
     */
    public ReviewController(ReviewsDAO reviewsDao) {
        this.reviewsDao = reviewsDao;    
    }

    /**
     * Responds to the GET request for a {@linkplain Review review} for the given sku and userId
     * 
     * @param sku The sku of product for which to get the review
     * 
     * @param userid The userid of the user to get the review for
     * 
     * @return ResponseEntity with {@link Review review} object and HTTP status of OK if found<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * 
     * Example: Get review with sku 1 and userid 1
     * GET http://localhost:8080/reviews/1/1
     */
    @GetMapping("/{sku}/{userid}")
    public ResponseEntity<Review> getReview(@PathVariable("sku") int sku, @PathVariable("userid") int userid) {
        LOG.info("GET /reviews/" + sku + "/" + userid + "");

        try {
            Review review = reviewsDao.getReview(sku, userid);
            if (review != null) {
                return new ResponseEntity<>(review, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error getting review", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for a {@linkplain Review review} for the given sku
     * 
     * @param sku The sku of product for which to get the review
     * 
     * @return ResponseEntity with {@link Review review} object and HTTP status of OK if found<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * 
     * Example: Get review with sku 1
     * GET http://localhost:8080/reviews/1
     */
    @GetMapping("/{sku}")
    public ResponseEntity<Review[]> getReviewsForProduct(@PathVariable("sku") int sku) {
        LOG.info("GET /reviews/" + sku + "");
        
        try {
            Review[] review = reviewsDao.getReviews(sku);
            if (review != null) {
                return new ResponseEntity<>(review, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error getting review", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for a {@linkplain Review reviews} for the given userId
     * 
     * @param userid
     * 
     * @return ResponseEntity with {@link Review review} object and HTTP status of OK if found<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * 
     * Example: Get review with userid 1
     * GET http://localhost:8080/reviews/user/1
     */
    @GetMapping("/user/{userid}")
    public ResponseEntity<Review[]> getReviewsByUser(@PathVariable("userid") int userid) {
        LOG.info("GET /reviews/user/" + userid + "");
        
        try {
            Review[] review = reviewsDao.getReviewsByUser(userid);
            if (review != null) {
                return new ResponseEntity<>(review, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error getting review", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the DELETE request for a {@linkplain Review review} for the given sku and userId
     * 
     * @param sku The sku of product for which to delete the review
     * 
     * @param userid The userid of the user to delete the review for
     * 
     * @return ResponseEntity with HTTP status of OK if deleted<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * 
     * Example: Delete review with sku 1 and userid 1
     * DELETE http://localhost:8080/reviews/1/1
     */
    @DeleteMapping("/{sku}/{userid}")
    public ResponseEntity<Review> deleteReview(@PathVariable("sku") int sku, @PathVariable("userid") int userid) {
        LOG.info("DELETE /reviews/" + sku + "/" + userid);

        try {
            if(reviewsDao.deleteReview(sku, userid)){
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error deleting review", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Response to the POST request for a {@linkplain Review review} for the given sku and userId
     * 
     * @param sku The sku of product for which to add the review
     * 
     * @param userid The userid of the user to add the review for
     * 
     * @param review The review to add
     * 
     * @return ResponseEntity with HTTP status of OK if added<br>
     * ResponseEntity with HTTP status of CONFLICT if not added<br>
     * 
     * Example: Add review with sku 1 and userid 1
     * POST http://localhost:8080/reviews/1/1
     */
    @PostMapping("/{sku}/{userid}")
    public ResponseEntity<Review> createReview(@RequestBody Review review) {
        LOG.info("POST /reviews/" + review.getSku() + "/" + review.getUserId());
        
        try {
            Review createdReview = reviewsDao.createReview(review);
            if(createdReview != null){
                return new ResponseEntity<>(createdReview, HttpStatus.CREATED);
            }
            return new ResponseEntity<>(HttpStatus.CONFLICT)
            ;
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error creating review", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Response to the PUT request for a {@linkplain Review review} for the given sku and userId
     * 
     * @param sku The sku of product for which to update the review
     *  
     * @param userid The userid of the user to update the review for
     * 
     * @param review The review to update
     * 
     * @return ResponseEntity with HTTP status of OK if updated<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not updated<br>
     * 
     * Example: Update review with sku 1 and userid 1
     * PUT http://localhost:8080/reviews/1/1
     */
    @PutMapping("/{sku}/{userid}")
    public ResponseEntity<Review> updateReview(@RequestBody Review review) {
        LOG.info("PUT /reviews/" + review.getSku() + "/" + review.getUserId());
        
        try {
            Review updatedReview = reviewsDao.updateReview(review);
            if(updatedReview != null){
                return new ResponseEntity<>(updatedReview, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error updating review", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

