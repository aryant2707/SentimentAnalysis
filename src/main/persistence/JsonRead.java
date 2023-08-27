package persistence;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.opentest4j.AssertionFailedError;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class JsonRead {

    private JsonRead() {}

    public static void loadFromJson(List inputs, List outcomes, String path) throws AssertionFailedError {
        try (FileReader reader = new FileReader(path)) {
            JSONTokener tokener = new JSONTokener(reader);
            JSONObject json = new JSONObject(tokener);
            JSONArray data = json.getJSONArray("data");

            for (int i = 0; i < data.length(); i++) {
                JSONObject record = data.getJSONObject(i);
                inputs.add(record.getString("input"));
                outcomes.add(record.getString("outcome"));
            }

            System.out.println("Data loaded from file: " + path);
        } catch (IOException e) {
            System.out.println("An error occurred while loading the data from file.");
        } catch (JSONException e) {
            System.out.println("An error occurred while parsing the data from file.");
        }

    }
}
