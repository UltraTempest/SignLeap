package classifier;
//http://stackoverflow.com/questions/3793400/is-there-a-function-in-java-to-get-moving-average
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.Queue;

public final class MovingAverageFilter {

	private final Queue<BigDecimal> window = new LinkedList<BigDecimal>();
	private final int period;
	private BigDecimal sum = BigDecimal.ZERO;

	public MovingAverageFilter(final int period) {
		assert period > 0 : "Period must be a positive integer";
		this.period = period;
		fillWindowWithZeros();
	}

	private void fillWindowWithZeros(){
		for(int i=0;i<period;++i)
			window.add(BigDecimal.ZERO);
	}

	public void add(final double... num) {
		for(final double i:num){
			final BigDecimal bd=new BigDecimal(i);
			sum = sum.add(bd);
			window.add(bd);
			if (window.size() > period) {
				sum = sum.subtract(window.remove());
			}
		}
	}

	public double getAverage() {
		if (window.isEmpty()) return BigDecimal.ZERO.doubleValue();
		final BigDecimal divisor = BigDecimal.valueOf(window.size());
		return sum.divide(divisor, 2, RoundingMode.HALF_UP).doubleValue();
	}
}
