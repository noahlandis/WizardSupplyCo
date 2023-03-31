package com.estore.api.estoreapi.persistence;
import java.io.IOException;

import com.estore.api.estoreapi.model.Review;

/**
 * Defines the Reviews interface for Review object persistence
 * 
 * @author Priyank Patel
 */
public interface ReviewsDAO {
    /**
     * Retrieves all {@linkplain Review[] reviews} with the given sku
     * 
     * @param sku The sku of the {@link Review review} to get
     * 
     * @return a {@link Review review} object with the matching sku
     * <br>
     * null if no {@link Review review} with a matching sku is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    Review[] getReviews(int sku) throws IOException;

    /**
     * Retrieves all {@linkplain Review reviews} with the given sku and userId
     * 
     * @param sku The sku of the {@link Review review} to get
     * 
     * @param userId The userId of the {@link Review review} to get
     * 
     * @return a {@link Review review} object with the matching sku and userId
     * <br>
     * null if no {@link Review review} with a matching sku and userId is found
     * 
     * @throws IOException if an issue with underlying storage
     */
     Review getReview(int sku, int userId) throws IOException;

    /**
     * Retrieves all {@linkplain Review reviews} with the given userId
    *
    * @param userId The userId of the {@link Review review} to get
    *
    * @return a {@link Review review} object with the matching userId
    * <br>
    * null if no {@link Review review} with a matching userId is found
    *
    * @throws IOException if an issue with underlying storage
    */
    Review[] getReviewsByUser(int userId) throws IOException;

    /**
     * Creates and saves a {@linkplain Review review}
     * 
     * @param review {@linkplain Review review} object to be created and saved
     * <br>
     * The sku of the review object is ignored and a new uniqe sku is assigned
     * Checks if the review already exists by name and if so, returns null
     *
     * @return new {@link Review review} if successful, null otherwise 
     * 
     * @throws IOException if an issue with underlying storage
     */
    Review createReview(Review review) throws IOException;

    /**
     * Updates and saves a {@linkplain Review review}
     * 
     * @param review {@linkplain Review review} object to be updated and saved
     * <br>
     * The sku of the review object is ignored and a new uniqe sku is assigned
     * Checks if the review already exists by name and if so, returns null
     *
     * @return updated {@link Review review} if successful, null otherwise 
     * 
     * @throws IOException if an issue with underlying storage
     */
    Review updateReview(Review review) throws IOException;

    /**
     * Deletes a {@linkplain Review review}
     * 
     * @param productId The productId of the {@link Review review} to delete
     * 
     * @param userId The userId for which tp delete the {@link Review review}
     * <br>
     * The sku of the review object is ignored and a new uniqe sku is assigned
     * Checks if the review already exists by name and if so, returns null
     *
     * @return true if successful, false otherwise 
     * 
     * @throws IOException if an issue with underlying storage
     */
    boolean deleteReview(int productId, int userId) throws IOException;

}
