package com.estore.api.estoreapi.model;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * This class is made to represent a review
 * 
 * @author Priyank Patel
 */
public class Review {
    private static final Logger LOG = Logger.getLogger(Review.class.getName());

    private final String STRING_FORMAT = "Review [reiewId=%d, userId=%d, productId=%d, rating=%d, description=%s]";
    
    @JsonProperty("reviewId") private int reviewId;
    @JsonProperty("userId") private int userId;
    @JsonProperty("productId") private int productId;
    @JsonProperty("rating") private int rating;
    @JsonProperty("description") private String description;
    
    /**
     * Create a Review with the given userID, productID, rating and review
     * @param userId The User ID of the user
     * @param productId The Product ID of the product
     * @param rating The rating of the product
     * @param description The review of the product
     */
    @JsonCreator
    public Review(
        @JsonProperty("userId") int userId,
        @JsonProperty("productId") int productId,
        @JsonProperty("rating") int rating,
        @JsonProperty("description") String description) {
        this.userId = userId;
        this.productId = productId;
        this.rating = rating;
        this.description = description;
    }

    /**
     * Get the review ID of the review
     * @return The review ID of the review
     */
    public int getReviewId() {
        return reviewId;
    }

    /**
     * Set the review ID of the review
     * @param reviewId The review ID of the review
     */
    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    /**
     * Get the User ID of the review
     * @return The User ID of the review
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Set the User ID of the review
     * @param userId The User ID of the review
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Get the Product ID of the review
     * @return The Product ID of the review
     */
    public int getProductId() {
        return productId;
    }

    /**
     * Get the product ID of the review
     * @param productId
     */
    public void setProductId(int productId) {
        this.productId = productId;
    }

    /**
     * Get the rating of the review
     * @return The rating of the review
     */
    public int getRating() {
        return rating;
    }

    /**
     * Set the rating of the review
     * @param rating The rating of the review
     */
    public void setRating(int rating) {
        this.rating = rating;
    }

    /**
     * Get the review description of the review
     * @return The description of the review
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the description description of the review
     * @param description The description of the review
     */
    public void setDescription(String description) {
        this.description = description;
    }
        
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT, reviewId, userId, productId, rating, description);
    }
}
