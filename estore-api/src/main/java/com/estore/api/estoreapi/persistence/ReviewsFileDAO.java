package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import org.springframework.stereotype.Repository;

import com.estore.api.estoreapi.model.Review;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Repository
public class ReviewsFileDAO implements ReviewsDAO {
    private static final Logger LOG = Logger.getLogger(ReviewsFileDAO.class.getName());

    Map<Integer, Review[]> reviews; // Map of product ID to reviews cached here 
                                    // so no repeated reads from file
    private ObjectMapper objectMapper; // Provides JSON Object to/from Java Object serialization and deserialization
    private String filename; // Filename to read from and write to

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
    private void load() throws IOException {
        LOG.info("Loading reviews from the file: " + filename);
        reviews = new TreeMap<>();

        // Desrialize the JSON text from the file into an array of Review objects
        // and add each Review object to the reviews map
        Review[][] reviewsFromFile = objectMapper.readValue(new File(filename), Review[][].class);
        for (Review[] reviewArray : reviewsFromFile) {
            for (Review review : reviewArray) {
                reviews.put(review.getReviewId(), reviewArray);
            }
        }
    }


    @Override
    public Review[][] getReviews() throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Review[] getReviews(int sku) throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Review getReview(int sku, int userId) throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Review[] getReviewsByUser(int userId) throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Review createReview(Review review) throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createReview'");
    }

    @Override
    public Review updateReview(Review review) throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateReview'");
    }

    @Override
    public boolean deleteReview(int productId, int userId) throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteReview'");
    }

    
}
