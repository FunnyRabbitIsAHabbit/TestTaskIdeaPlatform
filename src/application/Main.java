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
                "arrival_date", "arrival_time", "origin", "destination"));

        Set<Map<Object, Object>> searchList = Set.of(toFindFromVVO, toFindFromTLV);
        List<Object> myResultJSON = new ArrayList<>();

        for (Map<Object, Object> toFind : searchList) {
            ProcessJSON processJSON = new ProcessJSON(myJSON, toFindArrayBy, toFind, toAppendToResult);
            myResultJSON.add(processJSON.getOutJSON());
        }

        myResultJSON.forEach(
                obj -> {
                    List<Map<String, String>> datesTimes = (ArrayList) obj;
                    if (!datesTimes.isEmpty()) {

                        List<Object> patterns = toAppendToResult.get(0);
                        ProcessCleanData result = new ProcessCleanData(datesTimes,
                                patterns,
                                List.of("arrival", "departure"),
                                List.of("date", "time"));

                        List<Long> cleanResult = result.getResultData();
                        ProcessMetrics answerX = new ProcessMetrics(cleanResult);
                        double resultMean = answerX.getAverage();
                        double result90 = answerX.getPercentile(90);

                        int hourDiv = 1000 * 60 * 60;
                        int minuteDiv = 1000 * 60;
                        int secondDiv = 1000;

                        List<Integer> dateResult = List.of(
                                (int) (resultMean / hourDiv) % 24,
                                (int) (resultMean / minuteDiv) % 60,
                                (int) (resultMean / secondDiv) % 60,

                                (int) (result90 / hourDiv) % 24,
                                (int) (result90 / minuteDiv) % 60,
                                (int) (result90 / secondDiv) % 60
                        );

                        System.out.println(
                                "Mean time Tel-Aviv – Vladivostok: " +
                                        dateResult.get(0) + " hours, " +
                                        dateResult.get(1) + " minutes, " +
                                        dateResult.get(2) + " seconds;\n" +
                                        "90th prc. time Tel-Aviv – Vladivostok: " +
                                        dateResult.get(3) + " hours, " +
                                        dateResult.get(4) + " minutes, " +
                                        dateResult.get(5) + " seconds."
                        );
                    }
                }
        );
    }

}