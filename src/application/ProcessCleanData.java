package application;

import java.util.ArrayList;
import java.util.Collection;

public class ProcessCleanData {

    private final Collection<Object> data;
    private final Collection<Object> resultData;

    public ProcessCleanData(Collection<Object> data) {
        this.data = data;
        this.resultData = new ArrayList<>();
        this.main();
    }

    public Collection<Object> getData() {
        return data;
    }

    public Collection<Object> getResultData() {
        return resultData;
    }

    private void main() {
        this.data.forEach(
                item -> {
                    // 1
                    this.resultData.add(item);
                    // 3
                    // ...
                    // 4
                    // Profit
                }
        );
    }
}
