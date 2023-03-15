package application;

import org.apache.commons.math3.stat.descriptive.rank.Percentile;
import java.util.List;

public class ProcessMetrics {

    private final List<Long> data;

    // constructor, no default values
    public ProcessMetrics(List<Long> data) {
        this.data = data;
    }

    public Long getAverage() {
        Integer count = this.data.size();
        Long total = this.data.stream().reduce(0l, Long::sum);

        // Rounded result
        return total / count;
    }

    public Double getPercentile(int quantile) {
        Percentile x = new Percentile(quantile);

        return x.evaluate(this.data.stream().mapToDouble(i -> i).toArray());
    }
}
