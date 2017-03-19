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
	
	final MovingAverageFilter mov;
	private final double expected;
	private final double[] input;

	public MovingAverageFilterTest2(final int period,final double expected,final double... input) {
		mov = new MovingAverageFilter(period);
		this.input= input;
		this.expected= expected;
	}
	
	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {     
			{ 10, 13.5, new double[]{2,4,7,5,3,6,7,98,3}},
			{ 3, 3, new double[]{3,3,3}},
		});
	}

	@Test
	public void test() {	
		mov.add(input);
		assertEquals(Double.doubleToLongBits(expected),Double.doubleToLongBits(mov.getAverage()));
	}
}