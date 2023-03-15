package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ProcessCleanData {

    private final List<Map<String, String>> data;
    private final List<Long> resultData;
    private final List<?> patterns;

    private final SimpleDateFormat formatter =
            new SimpleDateFormat("dd.MM.yyyy hh:mm", Locale.ENGLISH);

    // constructor, no default values, call to business function
    public ProcessCleanData(List<Map<String, String>> data,
                            List<?> patterns) {
        this.data = data;
        this.resultData = new ArrayList<>();
        this.patterns = patterns;

        // Set time to Greenwich
        this.formatter.setTimeZone(TimeZone.getTimeZone("UTC"));

        this.main(List.of("arrival", "departure"));
    }

    public List<Map<String, String>> getData() {
        return data;
    }

    public List<Long> getResultData() {
        return resultData;
    }

    private Map<String, Date> convertKeysToDatetime(Map<String, String> x) {

        Map<String, String> toConvert = new HashMap<>();
        Map<String, Date> toReturn = new HashMap<>();

        for (Object pattern : this.patterns) {
            String pat = (String) pattern;

            x.forEach(
                    (key, value) -> {
                        if (Objects.equals(pat, key)) {
                            if (pat.contains("date")) {
                                toConvert.put(pat.split("_")[0], value);
                            }
                        }
                    }
            );

            x.forEach(
                    (key, value) -> {
                        if (Objects.equals(pat, key)) {
                            if (pat.contains("time")) {
                                toConvert.replace(
                                        pat.split("_")[0],
                                        toConvert.get(pat.split("_")[0]) + " " + value);
                            }
                        }
                    }
            );
        }

        toConvert.forEach(
                (key, value) -> {
                    try {
                        Date date = this.formatter.parse(value);
                        toReturn.put(key, date);
                    } catch (ParseException e) {
                        toReturn.put(key, null);
                    }
                }
        );

        return toReturn;
    }

    private void main(List<String> label) {

        this.data.forEach(
                item -> {
                    // 1
                    Map<String, Date> out = this.convertKeysToDatetime(item);
                    Long difference = out.get(label.get(0)).getTime() -
                            out.get(label.get(1)).getTime();
                    this.resultData.add(difference);

                    // 3
                    // ...
                    // 4
                    // Profit
                }
        );
    }
}
