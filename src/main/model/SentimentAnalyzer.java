package model;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.util.*;

import java.util.*;

// SentimentAnalyzer is a class that provides sentiment analysis functionality using the Stanford CoreNLP library.
public class SentimentAnalyzer {

    enum Sentiment {
        Unknown,
        Positive,
        Neutral,
        Negative
    }

    private String statement;
    private Sentiment sentiment;
    private List<String> result = new ArrayList<>();

    public SentimentAnalyzer() {
    }

    // Analyzes the sentiment of the given text using Stanford CoreNLP library.
    // REQUIRES: A non-null string input 'text'.
    // MODIFIES: The 'statement', 'sentiment' and 'result' member variables.
    // EFFECTS: Analyzes the sentiment of the input text and returns a list of statement and sentiment.
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public List<String> analyzeSentiment(String text) {
        statement = text;
        // create a new Properties object
        Properties props = new Properties();

        // set the properties for the pipeline
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, parse, sentiment");

        // create a new StanfordCoreNLP pipeline with the specified properties
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        // create a new Annotation object with the input text
        Annotation document = new Annotation(text);

        // run all the annotators on the input text
        pipeline.annotate(document);

        // get the sentences from the annotated document
        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);

        // iterate over each sentence and get its sentiment
        String overallSentiment = "";
        for (CoreMap sentence : sentences) {
            String sentiment = sentence.get(SentimentCoreAnnotations.SentimentClass.class);
            overallSentiment += sentiment + " ";
        }
        String trimmed = overallSentiment.trim();

        switch (trimmed) {
            case "Positive":
                sentiment = Sentiment.Positive;
                break;
            case "Negative":
                sentiment = Sentiment.Negative;
                break;
            case "Neutral":
                sentiment = Sentiment.Neutral;
                break;
            default:
                sentiment = Sentiment.Unknown;
        }
        result.add(statement);
        result.add(String.valueOf(sentiment));
        EventLog.getInstance().logEvent(new Event("Sentiment Analysis ran on statement"));

        return result;
    }


    // Returns the analyzed statement.
    public String getStatement() {
        EventLog.getInstance().logEvent(new Event("getStatement called"));

        return statement;
    }

    // Returns the analyzed sentiment.
    public Sentiment getSentiment() {
        EventLog.getInstance().logEvent(new Event("getSentiment called"));
        return sentiment;

    }
}