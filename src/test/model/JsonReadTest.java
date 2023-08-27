package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import persistence.JsonRead;

import java.util.ArrayList;
import java.util.List;


public class JsonReadTest extends JsonTest{

    @Test
    public void testLoadFromJson() {
        // Test with empty input file
        List<String> inputs = new ArrayList<>();
        List<String> outcomes = new ArrayList<>();
        String emptyPath = "data/empty-file.json";
        String validPath = "data/testReadJSON.json";
        String invalidFile = "data/invalidJSON.json";
        JsonRead.loadFromJson(inputs, outcomes, emptyPath);
        Assertions.assertEquals(0, inputs.size());
        Assertions.assertEquals(0, outcomes.size());

        // Test with valid input file
        inputs.clear();
        outcomes.clear();
        JsonRead.loadFromJson(inputs, outcomes, validPath);
        Assertions.assertEquals(3, inputs.size());
        Assertions.assertEquals(3, outcomes.size());
        Assertions.assertEquals("hello", inputs.get(0));
        Assertions.assertEquals("world", outcomes.get(0));

        // Test with invalid input file
        inputs.clear();
        outcomes.clear();
        JsonRead.loadFromJson(inputs, outcomes, invalidFile);
    }
}

