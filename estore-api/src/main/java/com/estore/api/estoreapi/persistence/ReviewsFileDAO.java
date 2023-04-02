package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.estore.api.estoreapi.model.Review;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Implements the functionality for JSON file-based peristance for Reviews
 * 
 * {@literal @}Component Spring annotation instantiates a single instance of this
 * class and injects the instance into other classes as needed
 * 
 * @author Priyank Patel
 */
@Repository
public class ReviewsFileDAO implements ReviewsDAO {
    private static final Logger LOG = Logger.getLogger(ReviewsFileDAO.class.getName());

    private Map<Integer, Review> reviews; // Map of product ID to reviews cached here 
                                    // so no repeated reads from file
    private ObjectMapper objectMapper; // Provides JSON Object to/from Java Object serialization and deserialization
    private String filename; // Filename to read from and write to
    private static int nextReviewId; // The next review ID to assign to a new review

    /**
     * Creates a Reviews File Data Access Object
     * 
     * @param filename Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public ReviewsFileDAO(@Value("${reviews.file}") String filename, ObjectMapper objectMapper) throws IOException {
        LOG.info("ReviewsFileDAO created");
        this.filename = filename;
        this.objectMapper = objectMapper;
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        load(); // load the reviews from the file
    }
    
    /**
     * Loads the reviews from the file
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        LOG.info("Loading reviews from the file: " + filename);
        reviews = new HashMap<>();
        nextReviewId = 0;

        // Deserialize the JSON text from the file into an array of Review objects
        // and add each Review object to the reviews map
        Review[] reviewArray = objectMapper.readValue(new File(filename), Review[].class);

        for (Review review : reviewArray) {
            reviews.put(review.getReviewId(), review);
            if(review.getReviewId() > nextReviewId) {
                nextReviewId = review.getReviewId();
            }
        }
        //Make the next review ID the next integer after the highest review ID
        ++nextReviewId; // Increment the next review ID to assign to a new review
        return true;
    }

    /**
     * Saves the {@linkplain Review reviews} from the map into the file as an array of JSON objects
     * 
     * @return true if the {@link Review reviews} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        LOG.info("Saving reviews to the file: " + filename);
        // Serialize the reviews map into an array of Review objects
        Review[] reviewArray = getReviews();
        // Write the array of Review objects to the file as JSON objects
        objectMapper.writeValue(new File(filename), reviewArray);
        return true;
    }
    
    /**
     * Generates an array of {@linkplain Review reviews} from the map
     * 
     * @return an array of {@link Review reviews}
     * 
     * @throws IOException
    */
    private Review[] getReviews() throws IOException {
        ArrayList<Review> reviewArrayList = new ArrayList<>();
        for(Review review : reviews.values()) {
            reviewArrayList.add(review);
        }
        Review[] reviewsArray = new Review[reviewArrayList.size()];
        reviewsArray = reviewArrayList.toArray(reviewsArray);
        return reviewsArray;
    }

    @Override
    public Review[] getReviews(int sku) throws IOException {
        ArrayList<Review> reviewArrayList = new ArrayList<>();
        for(Review review : reviews.values()) {
            if(review.getSku() == sku) {
                reviewArrayList.add(review);
            }
        }
        Review[] reviewsArray = new Review[reviewArrayList.size()];
        reviewsArray = reviewArrayList.toArray(reviewsArray);
        return reviewsArray;
    }

    @Override
    public Review getReview(int sku, int userId) throws IOException {
        Review[] reviewsArray = getReviews(sku);
        for(Review review : reviewsArray) {
            if(review.getUserId() == userId) {
                return review;
            }
        }
        return null;
    }

    @Override
    public Review[] getReviewsByUser(int userId) throws IOException {
        ArrayList<Review> reviewArrayList = new ArrayList<>();
        for(Review review : reviews.values()) {
            if(review.getUserId() == userId) {
                reviewArrayList.add(review);
            }
        }

        Review[] reviewsArray = new Review[reviewArrayList.size()];
        reviewsArray = reviewArrayList.toArray(reviewsArray);
        return reviewsArray;
    }

    @Override
    public Review createReview(Review review) throws IOException {
        synchronized(reviews) {
            //Check if the user already has a review for the same product
            for(Review existingReview : reviews.values()) {
                if(existingReview.getSku() == review.getSku() && existingReview.getUserId() == review.getUserId()) {
                    return null;
                }
            }
        }
        Review newReview = new Review(
            nextReviewId,
            review.getSku(),
            review.getUserId(),
            review.getRating(),
            review.getComment()
        );
        reviews.put(newReview.getReviewId(), newReview);
        save();
        return newReview;
    }

    @Override
    public Review updateReview(Review review) throws IOException {
        synchronized(reviews) {
            if(reviews.containsKey(review.getReviewId()) == false) {
                return null;
            }
                reviews.put(review.getReviewId(), review);
                save();
                return review;
        }
    }

    @Override
    public boolean deleteReview(int productId, int userId) throws IOException {
        synchronized(reviews) {
            Review reviewToDelete = getReview(productId, userId);
            if(reviewToDelete != null) {
                reviews.remove(reviewToDelete.getReviewId());
                save();
                return true;
            }
            else{
                return false;
            }
        }
    }

    /**
     * Get average rating for a particular product
     */
    public double getAverageRating(int sku) throws IOException {
        Review[] reviewsArray = getReviews(sku);
        double totalRating = 0;
        for(Review review : reviewsArray) {
            totalRating += review.getRating();
        }
        return totalRating / reviewsArray.length;
    }
}
