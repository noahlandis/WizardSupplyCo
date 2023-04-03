package com.estore.api.estoreapi.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.estore.api.estoreapi.model.Review;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The unit test suite for the ReviewsFileDAO class
 * 
 * @author Priyank Patel
 */
@Tag("Persistence-tier")
public class ReviewsFileDAOTest {
    ReviewsDAO reviewDAO;
    Review[] testReviews;
    ObjectMapper mockObjectMapper;
    
    /**
     * Before each test, we will create and inject a Mock Object Mapper to
     * isolate the tests from the underlying file
     * @throws IOException
     */
    @BeforeEach
    public void setupReviewFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testReviews = new Review[5];
        
        testReviews[0] = new Review(3, 33, 33, 3, "This is a test review 3");
        testReviews[1] = new Review(4, 33, 44, 2, "This is a test review 1");
        testReviews[2] = new Review(5, 55, 33, 1, "This is a test review 2");
        testReviews[3] = new Review(1, 11, 11, 4, "This is a test review 4");
        testReviews[4] = new Review(2, 22, 22, 5, "This is a test review 5");

        // When the object mapper is supposed to read from the file
        // the mock object mapper will return the product array above
        when(mockObjectMapper
            .readValue(new File("Reviews_File.txt"),Review[].class))
                .thenReturn(testReviews);
        reviewDAO = new ReviewsFileDAO("Reviews_File.txt",mockObjectMapper);
    }

    @Test
    public void testGetReviews() throws IOException{
        // Invoke
        Review[] reviews = reviewDAO.getReviews(22);

        // Assert
        assertNotNull(reviews[0]);
        assertEquals(testReviews[4].getReviewId(), reviews[0].getReviewId());
        assertEquals(testReviews[4].getSku(), reviews[0].getSku());
        assertEquals(testReviews[4].getUserId(), reviews[0].getUserId());
        assertEquals(testReviews[4].getRating(), reviews[0].getRating());
        assertEquals(testReviews[4].getComment(), reviews[0].getComment());
    }

    @Test
    public void testGetReview() throws IOException{
        // Invoke
        Review review = reviewDAO.getReview(11,11);

        // Assert
        assertEquals(testReviews[3].getReviewId(), review.getReviewId());
        assertEquals(testReviews[3].getSku(), review.getSku());
        assertEquals(testReviews[3].getUserId(), review.getUserId());
        assertEquals(testReviews[3].getRating(), review.getRating());
        assertEquals(testReviews[3].getComment(), review.getComment());
    }

    @Test
    public void testGetReviewNull() throws IOException{
        // Invoke
        Review review = reviewDAO.getReview(11,22);

        // Assert
        assertNull(review);
    }

    @Test
    public void testReviewsUserByUser() throws IOException{
        // Invoke
        Review[] reviews = reviewDAO.getReviewsByUser(33);

        // Assert
        for(int i=0; i<2; i++) {
            assertEquals(testReviews[i].getReviewId(), reviews[i].getReviewId());
            assertEquals(testReviews[i].getSku(), reviews[i].getSku());
            assertEquals(testReviews[i].getUserId(), reviews[i].getUserId());
            assertEquals(testReviews[i].getRating(), reviews[i].getRating());
            assertEquals(testReviews[i].getComment(), reviews[i].getComment());
        }
    }

    @Test
    public void testCreateReview() throws IOException{        
        Review newTestReview = new Review(6, 66, 66, 5, "This is a test review 6");
        // Invoke
        Review review = reviewDAO.createReview(newTestReview);

        // Assert
        assertEquals(newTestReview.getReviewId(), review.getReviewId());
        assertEquals(newTestReview.getSku(), review.getSku());
        assertEquals(newTestReview.getUserId(), review.getUserId());
        assertEquals(newTestReview.getRating(), review.getRating());
        assertEquals(newTestReview.getComment(), review.getComment());
    }

    @Test
    public void testCreateReviewExisting() throws IOException{
        // Invoke
        Review review = reviewDAO.createReview(testReviews[0]);
        assertNull(review);
    }

    @Test
    public void testUpdateReview() throws IOException{
        Review newTestReview = new Review(1, 11, 11, 5, "This is a test review 1");
        // Invoke
        Review review = reviewDAO.updateReview(newTestReview);

        // Assert
        assertEquals(newTestReview.getReviewId(), review.getReviewId());
        assertEquals(newTestReview.getSku(), review.getSku());
        assertEquals(newTestReview.getUserId(), review.getUserId());
        assertEquals(newTestReview.getRating(), review.getRating());
        assertEquals(newTestReview.getComment(), review.getComment());
    }

    @Test
    public void testUpdateReviewNull() throws IOException{
        Review newTestReview = new Review(6, 66, 66, 5, "This is a test review 6");
        // Invoke
        Review review = reviewDAO.updateReview(newTestReview);
        assertNull(review);
    }

    @Test
    public void testDeleteReview() throws IOException{
        // Invoke
        assertTrue(reviewDAO.deleteReview(11,11));
    }

    @Test
    public void testDeleteReviewNull() throws IOException{
        // Invoke
        assertFalse(reviewDAO.deleteReview(11,22));
    }

    @Test
    public void testGetAverageRating() throws IOException{
        // Setup
        double actualAverageRating = (testReviews[0].getRating()+testReviews[2].getRating())/2;

        // Invoke
        double averageRating = reviewDAO.getAverageRating(33);

        // Assert
        assertEquals(actualAverageRating, averageRating);
    }

}
