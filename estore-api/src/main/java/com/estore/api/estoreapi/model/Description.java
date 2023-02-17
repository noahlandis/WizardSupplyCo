package com.estore.api.estoreapi.model;

import java.util.HashSet;
import java.util.logging.Logger;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a Product's Description
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

    /**
     * Creates a description
     * @param description A description of the product
     */
    public Description(@JsonProperty("description") String description) {
        this(description, new HashSet<>());        
    }

    /**
     * Retrieves the summary of the product
     * @return The product's summary
     */
    public String getSummary() { return summary; }


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

    @Override
    public String toString() {
        return String.format(STRING_FORMAT, summary, tags);
    }
}