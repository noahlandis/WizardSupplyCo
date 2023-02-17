package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * The unit test suite for the Description class
 * 
 * @author SWEN Faculty
 */
@Tag("Model-tier")
public class DescriptionTest {
    @Test
    public void testDescription() {
        // Setup
        String expectedSummary = "A REALLY Fast Broom!";
        HashSet<String> expectedTags = new HashSet<>();
        expectedTags.add("Broom");
        expectedTags.add("Fast");
        expectedTags.add("Wood");

        // Invoke
        Description description = new Description(expectedSummary, expectedTags);

        // Analyze
        assertEquals(expectedSummary, description.getSummary());
        assertEquals(expectedTags, description.getTags());
    }

    @Test
    public void testGetTags() {
        // Setup
        String expectedSummary = "A REALLY Fast Broom!";
        HashSet<String> expectedTags = new HashSet<>();
        Description description = new Description(expectedSummary, expectedTags);
        expectedTags.add("Broom");
        expectedTags.add("Fast");
        expectedTags.add("Wood");

        // Invoke
        HashSet<String> returnedTags = description.getTags();

        // Analyze
        assertEquals(expectedTags, returnedTags);
    }

    @Test
    public void testAddTag() {
        // Setup
        String summary = "A REALLY Fast Broom!";
        Description description = new Description(summary);
        int expectedTagCount = 1;
        // Invoke
        description.addTag("New Tag");
        HashSet<String> returnedTags = description.getTags();
        // Analyze
        assertEquals(expectedTagCount, returnedTags.size());
        assertEquals(true, returnedTags.contains("New Tag"));
    }
}