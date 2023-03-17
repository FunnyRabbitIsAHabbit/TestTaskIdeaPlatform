package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ProcessCleanData {

    private final List<Map<String, String>> data;
    private final List<Long> resultData;
    private final List<?> patterns;

    private final SimpleDateFormat formatter =
            new SimpleDateFormat("dd.MM.yyyy hh:mm", Locale.ENGLISH);

    // constructor, no default values, call to business function
    public ProcessCleanData(List<Map<String, String>> data,
                            List<?> patterns,
                            List<String> labelsArrivalDeparture,
                            List<String> labelsDateTime,
                            List<String> labelsAirport,
                            Map<String, String> airportToTimeZone) {
        this.data = data;
        this.resultData = new ArrayList<>();
        this.patterns = patterns;

        this.main(labelsArrivalDeparture,
                labelsDateTime,
                labelsAirport,
                airportToTimeZone);
    }

    public List<Map<String, String>> getData() {
        return data;
    }

    public List<Long> getResultData() {
        return resultData;
    }

    private Map<String, Date> convertKeysToDatetime(Map<String, String> x,
                                                    List<String> labelsArrivalDep,
                                                    List<String> labels,
                                                    List<String> labelsForDate,
                                                    Map<String, String> labelsTimeZone) {

        Map<String, String> interimLabelsMap = IntStream.range(0, labelsArrivalDep.size())
                .boxed()
                .collect(Collectors.toMap(labelsArrivalDep::get, labelsForDate::get));

        Map<String, String> toConvert = new HashMap<>();
        Map<String, Date> toReturn = new HashMap<>();

        for (Object pattern : this.patterns) {
            String pat = (String) pattern;

            x.forEach(
                    (key, value) -> {
                        if (Objects.equals(pat, key)) {
                            if (pat.contains(labels.get(0))) {
                                toConvert.put(pat.split("_")[0], value);
                            }
                        }
                    }
            );

            x.forEach(
                    (key, value) -> {
                        if (Objects.equals(pat, key)) {
                            if (pat.contains(labels.get(1))) {
                                toConvert.replace(
                                        pat.split("_")[0],
                                        toConvert.get(pat.split("_")[0]) + " " + value);
                            }
                        } else if (labelsForDate.contains(key)) {
                            toConvert.put(key, labelsTimeZone.get(x.get(key)));

                        }
                    }
            );
        }

        toConvert.forEach(
                (key, value) -> {
                    try {
                        if (labelsArrivalDep.contains(key)) {
                            this.formatter.setTimeZone(
                                    TimeZone.getTimeZone(toConvert.get(
                                            interimLabelsMap.get(key))));
                            Date date = this.formatter.parse(value);
                            toReturn.put(key, date);
                        }

                    } catch (ParseException e) {
                        toReturn.put(key, null);
                    }
                }
        );

        return toReturn;
    }

    private void main(List<String> labels1,
                      List<String> labels2,
                      List<String> labels3,
                      Map<String, String> labels4) {

        this.data.forEach(
                item -> {
                    // 1
                    Map<String, Date> out = this.convertKeysToDatetime(item,
                            labels1,
                            labels2,
                            labels3,
                            labels4);
                    Long difference = out.get(labels1.get(0)).getTime() -
                            out.get(labels1.get(1)).getTime();
                    this.resultData.add(difference);

                    // 3
                    // ...
                    // 4
                    // Profit
                }
        );
    }
}
