package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// Analyzes the sentiment of input sentences based on the scores of individual words.
public class Dictionary {
    private Map<String, Integer> wordScores;
    String filePath;

    //    REQUIRES: A valid file path to a text file containing scores.
    //    MODIFIES: The wordScores map.
    //    EFFECTS: Constructs a new Dictionary object and populates the wordScores map with the scores from
    //    the dictionary.
    public Dictionary(String filePath) throws IOException {
        wordScores = new HashMap<>();
        this.filePath = filePath;

        // Read the text file and populate the wordScores map
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t");
                String word = parts[0];
                int score = Integer.parseInt(parts[1]);
                wordScores.put(word, score);
            }
        }

    }

    //    REQUIRES: A non-null sentence string.
    //    EFFECTS: Analyzes the sentiment of the input sentence by adding up the scores of the individual words in the
    //    sentence, and returns a string indicating whether the sentiment of the sentence is positive, negative or
    //    neutral.
    public String analyzeSentence(String sentence) {
        int totalScore = 0;
        String[] words = sentence.split(" ");
        for (String word : words) {
            if (wordScores.containsKey(word)) {
                totalScore += wordScores.get(word);
            }
        }
        EventLog.getInstance().logEvent(new Event("Dictionary model ran on statement"));
        if (totalScore > 0) {
            return "Positive";
        } else if (totalScore < 0) {
            return "Negative";
        } else {
            return "Neutral";
        }
    }

    public String getPath() {
        return this.filePath;
    }
}