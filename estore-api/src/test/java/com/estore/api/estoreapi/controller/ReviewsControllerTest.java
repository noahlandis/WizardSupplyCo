package com.estore.api.estoreapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.estore.api.estoreapi.model.Review;
import com.estore.api.estoreapi.persistence.ReviewsDAO;

/**
 * Tests the ReviewsController
 * 
 * @author Priyank Patel
 */
public class ReviewsControllerTest {
    private ReviewController reviewController;
    private ReviewsDAO mockReviewDAO;

    /**
     * Before each test, create a new InventoryController and inject a mock
     * InventoryDAO
     */
    @BeforeEach
    public void setupReviewController() {
        mockReviewDAO = mock(ReviewsDAO.class);
        reviewController = new ReviewController(mockReviewDAO);
    }

    @Test
    public void testGetReview() throws IOException {
        // Setup
        Review newReview = new Review(1,1, 1, 5,"This is a review");
        when(mockReviewDAO.getReview(1, 1)).thenReturn(newReview);

        // Invoke
        ResponseEntity<Review> response = reviewController.getReview(1, 1);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetReviewNotFound() throws IOException {
        // Setup
        when(mockReviewDAO.getReview(1, 1)).thenReturn(null);

        // Invoke
        ResponseEntity<Review> response = reviewController.getReview(1, 1);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetReviewException() throws IOException {
        // Setup
        when(mockReviewDAO.getReview(1, 1)).thenThrow(new IOException());

        // Invoke
        ResponseEntity<Review> response = reviewController.getReview(1, 1);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testReviewsForProduct() throws IOException {
        // Setup
        Review[] newReview = new Review[1];
        newReview[0] = new Review(1,1, 1, 5,"This is a review");
        when(mockReviewDAO.getReviews(1)).thenReturn(newReview);

        // Invoke
        ResponseEntity<Review[]> response = reviewController.getReviewsForProduct(1);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testReviewsForProductNotFound() throws IOException {
        // Setup
        when(mockReviewDAO.getReviews(1)).thenReturn(null);

        // Invoke
        ResponseEntity<Review[]> response = reviewController.getReviewsForProduct(1);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testReviewsForProductException() throws IOException {
        // Setup
        when(mockReviewDAO.getReviews(1)).thenThrow(new IOException());

        // Invoke
        ResponseEntity<Review[]> response = reviewController.getReviewsForProduct(1);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testReviewsByUser() throws IOException {
        // Setup
        Review[] newReview = new Review[1];
        newReview[0] = new Review(1,1, 1, 5,"This is a review");
        when(mockReviewDAO.getReviewsByUser(1)).thenReturn(newReview);

        // Invoke
        ResponseEntity<Review[]> response = reviewController.getReviewsByUser(1);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testReviewsByUserNotFound() throws IOException {
        // Setup
        when(mockReviewDAO.getReviewsByUser(1)).thenReturn(null);

        // Invoke
        ResponseEntity<Review[]> response = reviewController.getReviewsByUser(1);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testReviewsByUserException() throws IOException {
        // Setup
        when(mockReviewDAO.getReviewsByUser(1)).thenThrow(new IOException());

        // Invoke
        ResponseEntity<Review[]> response = reviewController.getReviewsByUser(1);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testDeleteReview() throws IOException {
        // Setup
        when(mockReviewDAO.deleteReview(1, 1)).thenReturn(true);

        // Invoke
        ResponseEntity<Review> response = reviewController.deleteReview(1, 1);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteReviewNotFound() throws IOException {
        // Setup
        when(mockReviewDAO.deleteReview(1, 1)).thenReturn(false);

        // Invoke
        ResponseEntity<Review> response = reviewController.deleteReview(1, 1);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteReviewException() throws IOException {
        // Setup
        when(mockReviewDAO.deleteReview(1, 1)).thenThrow(new IOException());

        // Invoke
        ResponseEntity<Review> response = reviewController.deleteReview(1, 1);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testCreateReview() throws IOException {
        // Setup
        Review newReview = new Review(1,1, 1, 5,"This is a review");
        when(mockReviewDAO.createReview(newReview)).thenReturn(newReview);

        // Invoke
        ResponseEntity<Review> response = reviewController.createReview(newReview);

        // Analyze
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testCreateReviewException() throws IOException {
        // Setup
        Review newReview = new Review(1,1, 1, 5,"This is a review");
        when(mockReviewDAO.createReview(newReview)).thenThrow(new IOException());

        // Invoke
        ResponseEntity<Review> response = reviewController.createReview(newReview);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testCreateReviewExisting() throws IOException {
        // Setup
        Review newReview = new Review(1,1, 1, 5,"This is a review");
        when(mockReviewDAO.createReview(newReview)).thenReturn(null);

        // Invoke
        ResponseEntity<Review> response = reviewController.createReview(newReview);

        // Analyze
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void testUpdateReview() throws IOException {
        // Setup
        Review newReview = new Review(1,1, 1, 5,"This is a review");
        when(mockReviewDAO.updateReview(newReview)).thenReturn(newReview);

        // Invoke
        ResponseEntity<Review> response = reviewController.updateReview(newReview);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testUpdateReviewException() throws IOException {
        // Setup
        Review newReview = new Review(1,1, 1, 5,"This is a review");
        when(mockReviewDAO.updateReview(newReview)).thenThrow(new IOException());

        // Invoke
        ResponseEntity<Review> response = reviewController.updateReview(newReview);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testUpdateReviewNotFound() throws IOException {
        // Setup
        Review newReview = new Review(1,1, 1, 5,"This is a review");
        when(mockReviewDAO.updateReview(newReview)).thenReturn(null);

        // Invoke
        ResponseEntity<Review> response = reviewController.updateReview(newReview);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


}
