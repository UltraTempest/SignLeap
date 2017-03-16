package test;
//http://stackoverflow.com/questions/3793400/is-there-a-function-in-java-to-get-moving-average
import classifier.MovingAverageFilter;
import junit.framework.TestCase;

public class MovingAverageFilterTest extends TestCase {

    private final static int SIZE = 5;
    private static final double FULL_SUM = 12.5d;

    private MovingAverageFilter r;
    
    @Override
    public void setUp() {
        r = new MovingAverageFilter(SIZE);
    }

    public void testInitial() {
        assertEquals(0d, r.getAverage());
    }

    public void testOne() {
        r.add(3.5d);
        assertEquals(3.5d / SIZE, r.getAverage());
    }

    public void testFillBuffer() {
        fillBufferAndTest();
    }

    public void testForceOverWrite() {
        fillBufferAndTest();

        final double newVal = SIZE + .5d;
        r.add(newVal);
        // get the 'full sum' from fillBufferAndTest(), add the value we just added,
        // and subtract off the value we anticipate overwriting.
        assertEquals((FULL_SUM + newVal - .5d) / SIZE, r.getAverage());
    }

    public void testManyValues() {
        for (int i = 0; i < 1003; i++) r.add((double) i);
        fillBufferAndTest();
    }


    private void fillBufferAndTest() {
        // Don't write a zero value so we don't confuse an initialized
        // buffer element with a data element.
        for (int i = 0; i < SIZE; i++) r.add(i + .5d);
        assertEquals(FULL_SUM / SIZE, r.getAverage());
    }
}