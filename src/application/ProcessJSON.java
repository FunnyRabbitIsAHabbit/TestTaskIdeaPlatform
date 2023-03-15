package application;

import org.json.JSONObject;

import java.util.Map;

public class ProcessJSON {

    private JSONObject processedJSON;

    // constructor, no default values
    public ProcessJSON(JSONObject jo, Map<String, String> toRetrieve) {

        this.processJSON(jo, toRetrieve);
    }

    public final void processJSON(JSONObject jo, Map<String, String> toRetrieve) {
        if (toRetrieve.isEmpty()) {
            this.processedJSON = jo;
        }


        // TO-DO
        // get actual data
    }

    public final JSONObject getProcessedJSON() {
        return processedJSON;
    }
}
