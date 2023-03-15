package application;

import org.json.JSONObject;


import java.util.Set;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        ReadJSON toReadFromJSONFile = new ReadJSON("./tickets.json");
        JSONObject myJSON = toReadFromJSONFile.getJson();

        Map<String, String> toFindFromVVO = Map.of("tickets", "",
                "origin", "VVO",
                "destination", "TLV");
        Map<String, String> toFindFromTLV = Map.of("tickets", "",
                "origin", "TLV",
                "destination", "VVO");

        Set<Map<String, String>> searchList = Set.of(toFindFromVVO, toFindFromTLV);

        for (Map<String, String> toFind : searchList) {
            ProcessJSON processJSON = new ProcessJSON(myJSON, toFind);
            JSONObject processedJSON = processJSON.getProcessedJSON();

            System.out.println(processedJSON);
        }
    }

}