package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import model.SentimentAnalyzer.Sentiment;

import java.util.List;

// Test Class for the SentimentAnalyzer class.
public class SentimentAnalyzerTest {

    @Test
    public void testAnalyzeSentiment() {
        SentimentAnalyzer sentimentAnalyzer = new SentimentAnalyzer();
        List<String> result = sentimentAnalyzer.analyzeSentiment("");
        assertEquals(2, result.size());
        assertEquals("", result.get(0));
        assertEquals("Unknown", result.get(1));
        result.clear();

        result = sentimentAnalyzer.analyzeSentiment("I love you!");
        assertEquals(2, result.size());
        assertEquals("I love you!", result.get(0));
        assertEquals("Positive", result.get(1));
        result.clear();

        result = sentimentAnalyzer.analyzeSentiment("I hate you!");
        assertEquals(2, result.size());
        assertEquals("I hate you!", result.get(0));
        assertEquals("Negative", result.get(1));
        result.clear();

        result = sentimentAnalyzer.analyzeSentiment("It's okay.");
        assertEquals(2, result.size());
        assertEquals("It's okay.", result.get(0));
        assertEquals("Positive", result.get(1));
    }
    @Test
    void testGetStatement() {
        SentimentAnalyzer sa = new SentimentAnalyzer();
        sa.analyzeSentiment("I love ice cream");
        assertEquals("I love ice cream", sa.getStatement());
    }

    @Test
    void testGetSentiment() {
        SentimentAnalyzer sa = new SentimentAnalyzer();

        // Test positive sentiment
        sa.analyzeSentiment("I love ice cream");
        assertEquals(Sentiment.Positive, sa.getSentiment());

        // Test negative sentiment
        sa.analyzeSentiment("I hate spiders");
        assertEquals(Sentiment.Neutral, sa.getSentiment());

        // Test neutral sentiment
        sa.analyzeSentiment("The sky is blue");
        assertEquals(Sentiment.Neutral, sa.getSentiment());

        // Test unknown sentiment
        sa.analyzeSentiment("asdfghjkl");
        assertEquals(Sentiment.Neutral, sa.getSentiment());
    }
}