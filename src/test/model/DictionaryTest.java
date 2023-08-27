package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Test class for the Dictionary class which tests its methods with various types of input sentences.
public class DictionaryTest {
    private static Dictionary dictionary;

    @BeforeEach
    public void setup() throws IOException {
            dictionary = new Dictionary("data/AFINN-111.txt");
        }


    @Test
    public void testPositiveSentence() {
        String sentence = "This is a great day!";
        String result = dictionary.analyzeSentence(sentence);
        Assertions.assertEquals("Positive", result);
    }

    @Test
    public void testNegativeSentence() {
        String sentence = "This is a terrible day!";
        String result = dictionary.analyzeSentence(sentence);
        Assertions.assertEquals("Negative", result);
    }

    @Test
    public void testNeutralSentence() {
        String sentence = "This is a day.";
        String result = dictionary.analyzeSentence(sentence);
        Assertions.assertEquals("Neutral", result);
    }

    @Test
    public void testEmptySentence() {
        String sentence = "";
        String result = dictionary.analyzeSentence(sentence);
        Assertions.assertEquals("Neutral", result);
    }

    @Test
    public void testSentenceWithUnknownWords() {
        String sentence = "asdf ghjk lkj";
        String result = dictionary.analyzeSentence(sentence);
        Assertions.assertEquals("Neutral", result);
    }

    @Test
    public void testGetPath() {
        String filepath = "data/AFINN-111.txt";
        assertEquals(filepath, dictionary.getPath());
    }
}
