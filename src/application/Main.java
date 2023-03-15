package application;

import org.json.JSONObject;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        ReadJSON toReadFromJSONFile = new ReadJSON("./tickets.json");
        JSONObject myJSON = toReadFromJSONFile.getJson();

        List<String> toFind = List.of("tickets");
        ProcessJSON processJSON = new ProcessJSON(myJSON, toFind);
        JSONObject processedJSON = processJSON.getProcessedJSON();

        System.out.println(processedJSON);
    }

}