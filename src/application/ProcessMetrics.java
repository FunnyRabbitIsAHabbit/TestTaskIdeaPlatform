package application;

import org.apache.commons.math3.stat.StatUtils;
import java.util.List;

public class ProcessMetrics {

    private final List<Long> data;

    // constructor, no default values
    public ProcessMetrics(List<Long> data) {
        this.data = data;
    }

    public double getAverage() {
        double[] dat = this.data.stream().mapToDouble(i -> i).toArray();

        return StatUtils.mean(dat);
    }

    public double getPercentile(int quantile) {
        double[] dat = this.data.stream().mapToDouble(i -> i).toArray();

        return StatUtils.percentile(dat, quantile);
    }
}
