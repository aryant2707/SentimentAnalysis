package model;

import java.util.ArrayList;
import java.util.List;

//
public class Analysis {
    private final List<List<String>> sentiments;

    // EFFECTS: Constructs a new Analysis object with an empty list of sentiments
    public Analysis() {
        sentiments = new ArrayList<>();
    }

    // REQUIRES: s is not null
    // MODIFIES: this.sentiments
    // EFFECTS: Adds the given sentiment to the list of sentiments for this analysis
    public void addSentiment(List<String> s) {
        sentiments.add(s);
        EventLog.getInstance().logEvent(new Event("Sentiment added to Analysis"));
    }

    public static List<String> getLogs() {
        List<String> events = new ArrayList<>();
        for (Event e : EventLog.getInstance()) {
            events.add(e.toString());
        }
        return events;
    }

    // REQUIRES: The method requires that the sentiments list is not empty.
    // MODIFIES: The method modifies the sentiments list by removing the last element from it.
    // EFFECTS: After the method is executed, the sentiments list will have one less element than before.
    // If the sentiments list is empty, then an IndexOutOfBoundsException will be thrown.
    public void removeSentiment() {
        sentiments.remove(sentiments.size() - 1);
        EventLog.getInstance().logEvent(new Event("Sentiment removed from Analysis"));

    }

    // EFFECTS: Returns the list of sentiments for this analysis
    public List<List<String>> getSentiments() {
        EventLog.getInstance().logEvent(new Event("returned list of Sentiments"));
        return sentiments;

    }
}
