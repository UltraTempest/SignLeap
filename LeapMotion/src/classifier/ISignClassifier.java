package classifier;

import java.util.Map;

public interface ISignClassifier {

	public double score(final Map<String, Float> data,final String currentChar);

	public void resetRollingAverage();

}
