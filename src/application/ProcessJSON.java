package application;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ProcessJSON {

    private JSONArray selectedJSON;
    private final List<Object> outJSON;

    // constructor, no default values, call to business function
    public ProcessJSON(JSONObject jo,
                       Object toFindArrayBy,
                       Map<Object, Object> toRetrieveBy,
                       List<Object> toRetrieveFrom) {

        this.outJSON = new ArrayList<>();
        this.processJSON(jo, toFindArrayBy, toRetrieveBy, toRetrieveFrom);
    }

    public final List<Object> getOutJSON() {
        return outJSON;
    }

    public final boolean isMyKeyValue(JSONObject item,
                                      Object key,
                                      Object value) {
        return Objects.equals(item.get(key), value);
    }

    public final void collectNeededFromThisJSONArray(Object key,
                                                     Object value,
                                                     List<Object> toFindKeys) {

        for (Object item : this.selectedJSON) {
            JSONObject actualItem = (JSONObject) item;

            if (this.isMyKeyValue(actualItem, key, value)) {

                toFindKeys.forEach(
                        findKey -> this.outJSON.add(Map.of(findKey, actualItem.get(findKey)))
                );
            }
        }


    }

    public void processJSON(JSONObject jo,
                            Object toFindArrayBy,
                            Map<Object, Object> toRetrieveBy,
                            List<Object> toRetrieveFrom) {

        this.selectedJSON = (JSONArray) jo.get(toFindArrayBy);
        if (!toRetrieveBy.isEmpty()) {

            toRetrieveBy.forEach(
                    (key, value) -> this.collectNeededFromThisJSONArray(key, value, toRetrieveFrom)
            );

        }
    }
}
