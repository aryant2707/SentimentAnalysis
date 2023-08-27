package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

// Test Class for the Analysis class.
public class AnalysisTest {
    private Analysis analysis;

    @BeforeEach
    public void setUp() {
        analysis = new Analysis();
    }

    @Test
    public void testAddSentiment() {
        Analysis analysis = new Analysis();

        // Test adding a sentiment
        List<String> sentiment1 = new ArrayList<>();
        sentiment1.add("Statement 1");
        sentiment1.add("Positive");
        analysis.addSentiment(sentiment1);

        List<List<String>> expectedSentiments1 = new ArrayList<>();
        expectedSentiments1.add(sentiment1);
        Assertions.assertEquals(expectedSentiments1, analysis.getSentiments());

        // Test adding multiple sentiments
        List<String> sentiment2 = new ArrayList<>();
        sentiment2.add("Statement 2");
        sentiment2.add("Negative");
        analysis.addSentiment(sentiment2);

        List<List<String>> expectedSentiments2 = new ArrayList<>();
        expectedSentiments2.add(sentiment1);
        expectedSentiments2.add(sentiment2);
        Assertions.assertEquals(expectedSentiments2, analysis.getSentiments());
    }

    @Test
    public void testRemoveSentiment() {
        Analysis analysis = new Analysis();

        // Test removing a sentiment
        List<String> sentiment1 = new ArrayList<>();
        sentiment1.add("Statement 1");
        sentiment1.add("Positive");
        analysis.addSentiment(sentiment1);
        List<List<String>> expectedSentiments1 = new ArrayList<>();
        expectedSentiments1.add(sentiment1);
        Assertions.assertEquals(expectedSentiments1, analysis.getSentiments());
        analysis.removeSentiment();
        expectedSentiments1.remove(0);
        Assertions.assertEquals(expectedSentiments1, analysis.getSentiments());

    }
    @Test
    public void testGetSentiments() {
        Analysis analysis = new Analysis();

        // Test getting sentiments when there are none
        List<List<String>> expectedSentiments1 = new ArrayList<>();
        Assertions.assertEquals(expectedSentiments1, analysis.getSentiments());

        // Test getting sentiments when there is one
        List<String> sentiment1 = new ArrayList<>();
        sentiment1.add("Statement 1");
        sentiment1.add("Positive");
        analysis.addSentiment(sentiment1);

        List<List<String>> expectedSentiments2 = new ArrayList<>();
        expectedSentiments2.add(sentiment1);
        Assertions.assertEquals(expectedSentiments2, analysis.getSentiments());

        // Test getting sentiments when there are multiple
        List<String> sentiment2 = new ArrayList<>();
        sentiment2.add("Statement 2");
        sentiment2.add("Negative");
        analysis.addSentiment(sentiment2);

        List<List<String>> expectedSentiments3 = new ArrayList<>();
        expectedSentiments3.add(sentiment1);
        expectedSentiments3.add(sentiment2);
        Assertions.assertEquals(expectedSentiments3, analysis.getSentiments());
    }

    @Test
    public void testGetSentimentsEmpty() {
        List<List<String>> expected = new ArrayList<>();
        Assertions.assertEquals(expected, analysis.getSentiments());
    }
}