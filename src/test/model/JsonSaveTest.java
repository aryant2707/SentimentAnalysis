package model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonSave;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonSaveTest extends JsonTest {
    private static final String FILE_NAME = "data/saved-data-test.json";
    private static final List<String> TEST_INPUTS = new ArrayList<>(Arrays.asList("input1", "input2", "input3"));
    private static final List<String> TEST_OUTCOMES = new ArrayList<>(Arrays.asList("outcome1", "outcome2", "outcome3"));

    @BeforeEach
    public void setup() {
        Path path = Paths.get(FILE_NAME);
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testSaveToJson() throws IOException {
        JsonSave.saveToJson(TEST_INPUTS, TEST_OUTCOMES, "data/saved-data-test.json");

        File file = new File("data/saved-data-test.json");
        assertTrue(file.exists());
    }

    @Test
    public void testSaveAndLoadToJson() throws IOException {
        JsonSave.saveToJson(TEST_INPUTS, TEST_OUTCOMES, "data/saved-data-test.json");

        List<String> inputs = new ArrayList<>();
        List<String> outcomes = new ArrayList<>();
        String jsonString = new String(Files.readAllBytes(Paths.get("data/saved-data-test.json")), StandardCharsets.UTF_8);
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray dataArray = jsonObject.getJSONArray("data");
        for (int i = 0; i < dataArray.length(); i++) {
            JSONObject record = dataArray.getJSONObject(i);
            inputs.add(record.getString("input"));
            outcomes.add(record.getString("outcome"));
        }

        assertEquals(TEST_INPUTS, inputs);
        assertEquals(TEST_OUTCOMES, outcomes);
    }

}