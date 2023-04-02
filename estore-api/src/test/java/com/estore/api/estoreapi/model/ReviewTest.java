package com.estore.api.estoreapi.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The unit test suite for the Review class
 * 
 * @author Priyank Patel
 */
@Tag("Model-tier")
public class ReviewTest {

    @Test
    public void testConstructor(){
        // Setup
        int expectedReviewId = 1;
        int expectedUserId = 1;
        int expectedSku = 1;
        int expectedRating = 5;
        String expectedComment = "This is a review";

        // Invoke
        Review review = new Review(expectedReviewId, expectedUserId, expectedSku, expectedRating, expectedComment);

        // Analyze
        assertEquals(expectedReviewId, review.getReviewId());
        assertEquals(expectedUserId, review.getUserId());
        assertEquals(expectedSku, review.getSku());
        assertEquals(expectedRating, review.getRating());
        assertEquals(expectedComment, review.getComment());
    }

    @Test
    public void testConstructor2(){
        // Setup
        int expectedReviewId = 2;
        int expectedUserId = 2;
        int expectedSku = 2;
        int expectedRating = 3;

        // Invoke
        Review review = new Review(expectedReviewId, expectedUserId, expectedSku, expectedRating);

        // Analyze
        assertEquals(expectedReviewId, review.getReviewId());
        assertEquals(expectedUserId, review.getUserId());
        assertEquals(expectedSku, review.getSku());
        assertEquals(expectedRating, review.getRating());
    }

    @Test
    public void testSetUserId(){
        // Setup
        int expectedReviewId = 2;
        int expectedUserId = 2;
        int expectedSku = 2;
        int expectedRating = 3;
        Review review = new Review(expectedReviewId,expectedUserId, expectedSku, expectedRating);

        // Invoke
        review.setUserId(3);

        // Analyze
        assertEquals(3, review.getUserId());
    }

    @Test
    public void testSetSku(){
        // Setup
        int expectedReviewId = 2;
        int expectedUserId = 2;
        int expectedSku = 2;
        int expectedRating = 3;
        String expectedComment = "This is a review";
        Review review = new Review(expectedReviewId,expectedUserId, expectedSku, expectedRating, expectedComment);

        // Invoke
        review.setSku(3);

        // Analyze
        assertEquals(3, review.getSku());
    }

    @Test
    public void testSetRating(){
        // Setup
        int expectedReviewId = 2;
        int expectedUserId = 2;
        int expectedSku = 2;
        int expectedRating = 3;
        Review review = new Review(expectedReviewId,expectedUserId, expectedSku, expectedRating);

        // Invoke
        review.setRating(4);

        // Analyze
        assertEquals(4, review.getRating());
    }

    @Test
    public void testSetRatingLessThan1(){
        // Setup
        int expectedReviewId = 2;
        int expectedUserId = 2;
        int expectedSku = 2;
        int expectedRating = 3;
        Review review = new Review(expectedReviewId,expectedUserId, expectedSku, expectedRating);

        // Invoke
        try{
            review.setRating(0);
        }catch(IllegalArgumentException e){
            // Analyze
            assertEquals("Rating must be between 1 and 5", e.getMessage());
        }
    }

    @Test
    public void testSetRatingGreaterThan5(){
        // Setup
        int expectedReviewId = 2;
        int expectedUserId = 2;
        int expectedSku = 2;
        int expectedRating = 3;
        Review review = new Review(expectedReviewId,expectedUserId, expectedSku, expectedRating);

        // Invoke
        try{
            review.setRating(6);
        }catch(IllegalArgumentException e){
            // Analyze
            assertEquals("Rating must be between 1 and 5", e.getMessage());
        }
    }

    @Test
    public void testSetComment(){
        // Setup
        int expectedReviewId = 2;
        int expectedUserId = 2;
        int expectedSku = 2;
        int expectedRating = 3;
        String expectedComment = "This is a review";
        Review review = new Review(expectedReviewId,expectedUserId, expectedSku, expectedRating, expectedComment);

        // Invoke
        review.setComment("This is a new review");

        // Analyze
        assertEquals("This is a new review", review.getComment());
    }

    @Test
    public void testSetCommentNull(){
        // Setup
        int expectedReviewId = 2;
        int expectedUserId = 2;
        int expectedSku = 2;
        int expectedRating = 3;
        String expectedComment = "This is a review";
        Review review = new Review(expectedReviewId,expectedUserId, expectedSku, expectedRating, expectedComment);

        // Invoke
        try{
            review.setComment(null);
        }catch(IllegalArgumentException e){
            // Analyze
            assertEquals("Comment cannot be empty", e.getMessage());
        }
    }

    @Test
    public void testSetCommentEmpty(){
        // Setup
        int expectedReviewId = 2;
        int expectedUserId = 2;
        int expectedSku = 2;
        int expectedRating = 3;
        String expectedComment = "This is a review";
        Review review = new Review(expectedReviewId,expectedUserId, expectedSku, expectedRating, expectedComment);

        // Invoke
        try{
            review.setComment("");
        }catch(IllegalArgumentException e){
            // Analyze
            assertEquals("Comment cannot be empty", e.getMessage());
        }
    }

    @Test
    public void testToString(){
        // Setup
        int expectedReviewId = 2;
        int expectedUserId = 2;
        int expectedSku = 2;
        int expectedRating = 3;
        String expectedComment = "This is a review";
        Review review = new Review(expectedReviewId,expectedUserId, expectedSku, expectedRating, expectedComment);

        // Invoke
        String reviewString = review.toString();

        // Analyze
        assertEquals("Review [reviewId=2, userId=2, sku=2, rating=3, comment=This is a review]", reviewString);
    }
}
