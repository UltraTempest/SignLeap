package test;

import static org.junit.Assert.assertEquals;
import java.util.Arrays;
import java.util.Collection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import classifier.MovingAverageFilter;

@RunWith(Parameterized.class)
public class MovingAverageFilterTest2 {
	
	private final int period;
	private final double expected;
	private final double[] input;

	public MovingAverageFilterTest2(final int period,final double expected,final double... input) {
		this.period=period;
		this.input= input;
		this.expected= expected;
	}
	
	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {     
			{ 2, 3, new double[]{2,4}},
			{ 3, 3, new double[]{3,3,3}},
		});
	}

	@Test
	public void test() {
		final MovingAverageFilter mov = new MovingAverageFilter(period);
		mov.add(input);
		assertEquals(Double.doubleToLongBits(expected),Double.doubleToLongBits(mov.getAverage()));
	}
}