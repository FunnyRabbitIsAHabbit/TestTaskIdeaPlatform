package application;

import org.json.JSONObject;

import java.util.List;

public class ProcessJSON {

    private JSONObject processedJSON;

    // constructor, no default values
    public ProcessJSON(JSONObject jo, List<String> toRetrieve) {

        this.processJSON(jo, toRetrieve);
    }

    public final void processJSON(JSONObject jo, List<String> toRetrieve) {
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
