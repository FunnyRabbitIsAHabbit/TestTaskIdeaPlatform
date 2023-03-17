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
                        Map<String, String> timeZones = Map.of(
                                "VVO", "Asia/Vladivostok",
                                "TLV", "Israel"
                        );
                        ProcessCleanData result = new ProcessCleanData(datesTimes,
                                patterns,
                                List.of("arrival", "departure"),
                                List.of("date", "time"),
                                List.of("destination", "origin"),
                                timeZones);

                        List<Long> cleanResult = result.getResultData();
                        ProcessMetrics answerX = new ProcessMetrics(cleanResult);

                        Map<String, Integer> resultMean = convertMilliseconds(answerX.getAverage());
                        Map<String, Integer> result90 = convertMilliseconds(answerX.getPercentile(90));

                        System.out.println(
                                "Mean time Tel-Aviv – Vladivostok: " +
                                        "\nHours: " + resultMean.get("Hours") +
                                        "\nMinutes: " + resultMean.get("Minutes") +
                                        "\nSeconds: " + resultMean.get("Seconds") +
                                        "\n-------------\n90th prc. time Tel-Aviv – Vladivostok: " +
                                        "\nHours: " + result90.get("Hours") +
                                        "\nMinutes: " + result90.get("Minutes") +
                                        "\nSeconds: " + result90.get("Seconds")
                        );
                    }
                }
        );
    }

    public static Map<String, Integer> convertMilliseconds(double milliseconds) {
        int hourDiv = 1000 * 60 * 60;
        int dayDiv = hourDiv * 24;
        int minuteDiv = 1000 * 60;
        int secondDiv = 1000;

        int days = (int) (milliseconds / dayDiv);
        int hours = (int) (milliseconds / hourDiv) % 24 + 24 * days;
        int minutes = (int) (milliseconds / minuteDiv) % 60;
        int seconds = (int) (milliseconds / secondDiv) % 60;

        return Map.of(
                "Hours", hours,
                "Minutes", minutes,
                "Seconds", seconds
        );
    }

}