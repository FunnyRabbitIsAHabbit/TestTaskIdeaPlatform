package application;

import java.util.*;

public class ProcessCleanData {

    private final List<Map<String, String>> data;
    private final List<Map<String, String>> resultData;
    private final List<String> patterns;

    public ProcessCleanData(List<Map<String, String>> data,
                            List<String> patterns) {
        this.data = data;
        this.resultData = new ArrayList<>();
        this.patterns = patterns;
        this.main();
    }

    public List<Map<String, String>> getData() {
        return data;
    }

    public List<Map<String, String>> getResultData() {
        return resultData;
    }

    public Map<String, String> concatenateKeyValues(Map<String, String> x,
                                                    List<String> patterns) {
        StringBuilder outString = new StringBuilder();
        final String[] whichKey = new String[1];
        x.forEach(
                (key, value) -> {
                    outString.append(" ").append(value);;
                    whichKey[0] = key.split("_")[0];
                }
        );
        String outPattern = null;
        for (String pat : patterns) {
            if (whichKey[0].contains(pat)) {
                outPattern = pat;
            }
        }

        assert outPattern != null;
        return Map.of(outPattern, outString.toString().trim());
    }

    private void main() {
        this.data.forEach(
                item -> {
                    // 1
                    Map<String, String> newMap = this.concatenateKeyValues(item,
                            this.patterns);
                    this.resultData.add(item);
                    // 3
                    // ...
                    // 4
                    // Profit
                }
        );
    }
}
