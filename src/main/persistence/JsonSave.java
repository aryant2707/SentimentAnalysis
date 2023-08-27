package persistence;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class JsonSave {

    private JsonSave() {}

    public static void saveToJson(List inputs, List outcomes, String save) throws IOException {
        JSONObject json = new JSONObject();
        JSONArray data = new JSONArray();

        for (int i = 0; i < inputs.size(); i++) {
            JSONObject record = new JSONObject();
            record.put("input", inputs.get(i));
            record.put("outcome", outcomes.get(i));
            data.put(record);
        }

        json.put("data", data);

        FileWriter file = new FileWriter(save);
        file.write(json.toString());
        file.close();
        System.out.println("Data saved to file: " + save);
    }

}