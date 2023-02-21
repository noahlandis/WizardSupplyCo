package com.estore.api.estoreapi.model;

import java.util.HashSet;
import java.util.logging.Logger;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a Product's Description
 * @author Noah Landis
 */
public class Description {
    private static final Logger LOG = Logger.getLogger(Product.class.getName());

    // Package private for tests
    static final String STRING_FORMAT = "Description [summary=%s, tags=%s]";
    @JsonProperty("summary") private String summary;
    @JsonProperty ("tags") private HashSet<String> tags;

    /**
     * Create a description with a given summary and tags
     * @param summary A summary of the product
     * @param tags The tags associated with the product
     */
    public Description(@JsonProperty("summary") String summary, @JsonProperty("tags") HashSet<String> tags) {
        this.summary = summary;
        this.tags = tags;
    }

    // /**
    //  * Creates a description
    //  * @param summary A summary of the product
    //  */
    // public Description(@JsonProperty("summary") String summary) {
    //     this(summary, new HashSet<>());        
    // }

    /**
     * Retrieves the summary of the product
     * @return The product's summary
     */
    public String getSummary() { return summary; }

    /**
     * Sets the summary of the product
     * @param summary The summary of the product
     */
    public void setSummary(String summary) { this.summary = summary; }


     /**
     * Retrieves the tags of the product
     * @return The product's tags
     */
    public HashSet<String> getTags() { return tags; }


    /**
     * Adds a tag to the product's tags
     * @param tag The tag to be added
     */
    public void addTag(String tag) {
        tags.add(tag);
    }

    /**
     * Removes a tag from the product's tags
     * @param tag The tag to be removed
     */
    public void removeTag(String tag) {
        tags.remove(tag);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT, summary, tags);
    }
}