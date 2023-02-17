package com.estore.api.estoreapi.model;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import java.util.HashSet;

/**
 * The unit test suite for the Description class
 */
@Tag("Model-tier")
public class DescriptionTest {
    @Test
    public void testConstructor() {
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

    @Test
    public void removeTag() {
        // Setupv
        Description description = new Description("A REALLY Fast Broom!");
        String tag = "New Tag";
        description.addTag(tag);
        int expectedTagCount = 0;
        HashSet<String> expectedReturnedTags = new HashSet<>();

        // Invoke
        description.removeTag(tag);
        HashSet<String> returnedTags = description.getTags();

        // Analyze
        assertEquals(expectedReturnedTags, returnedTags);
        assertEquals(expectedTagCount, returnedTags.size());
        assertEquals(false, returnedTags.contains(tag));

    }


    @Test
    public void testToString() {
        // Setup
        String summary = "A REALLY Fast Broom!";
        Description description = new Description(summary);
        description.addTag("Wood");
        description.addTag("Broom");
        description.addTag("Flying");
        String expectedString = String.format(Description.STRING_FORMAT,summary, description.getTags());

        // Invoke
        String actualString = description.toString();

        // Analyze
        assertEquals(expectedString, actualString);
    }
}