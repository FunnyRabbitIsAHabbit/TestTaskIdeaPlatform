package application;

import org.json.simple.JSONObject;

import java.util.*;


public class Main {

    public static void main(String[] args) {
        ReadJSON toReadFromJSONFile = new ReadJSON("./tickets.json");
        JSONObject myJSON = toReadFromJSONFile.getJson();

        Object toFindArrayBy = "tickets";
        Map<Object, Object> toFindFromVVO = Map.of(
                "origin", "VVO",
                "destination", "TLV");
        Map<Object, Object> toFindFromTLV = Map.of(
                "origin", "TLV",
                "destination", "VVO");
        List<List<Object>> toAppendToResult = List.of(List.of("departure_date", "departure_time",
                "arrival_date", "arrival_time"));

        Set<Map<Object, Object>> searchList = Set.of(toFindFromVVO, toFindFromTLV);
        List<Object> myResultJSON = new ArrayList<>();

        for (Map<Object, Object> toFind : searchList) {
            ProcessJSON processJSON = new ProcessJSON(myJSON,toFindArrayBy, toFind, toAppendToResult);
            myResultJSON.add(processJSON.getOutJSON());
        }

        myResultJSON.forEach(
                obj -> {
                    List<Map<String, String>> datesTimes = (ArrayList) obj;
                    if (!datesTimes.isEmpty()) {

                        List<Object> patterns = toAppendToResult.get(0);
                        ProcessCleanData result = new ProcessCleanData(datesTimes,
                                patterns);

                        List<Long> cleanResult = result.getResultData();
                        ProcessMetrics answerX = new ProcessMetrics(cleanResult);
                        System.out.println(answerX.getAverage());
                        System.out.println(answerX.getPercentile(90));
                    }
                }
        );
    }

}