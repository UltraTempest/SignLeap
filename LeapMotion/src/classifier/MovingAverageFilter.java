package classifier;
//http://stackoverflow.com/questions/3793400/is-there-a-function-in-java-to-get-moving-average
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.Queue;

public class MovingAverageFilter {

    private final Queue<BigDecimal> window = new LinkedList<BigDecimal>();
    private final int period;
    private BigDecimal sum = BigDecimal.ZERO;

    public MovingAverageFilter(int period) {
        assert period > 0 : "Period must be a positive integer";
        this.period = period;
    }

    public void add(double num) {
    	BigDecimal bd=new BigDecimal(num);
        sum = sum.add(bd);
        window.add(bd);
        if (window.size() > period) {
            sum = sum.subtract(window.remove());
        }
    }

    public Double getAverage() {
        if (window.isEmpty()) return BigDecimal.ZERO.doubleValue();
        BigDecimal divisor = BigDecimal.valueOf(window.size());
        return sum.divide(divisor, 2, RoundingMode.HALF_UP).doubleValue();
    }
}
