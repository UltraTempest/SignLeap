package test;

import static org.junit.Assert.*;

import org.junit.Test;

import recording.SignClassifier;
import weka.core.Instance;

public class SignClassifierTest extends SignClassifier{

	@Test
	public void classificationTest() throws Exception {
		 /* Counters for correct and wrong predictions. */
		 int correct = 0, wrong = 0;
		 /* Classify all instances and check with the correct class values */
		 for (Instance inst : trainingSet) {
		     double predictedClassValue = cls.classifyInstance(inst);
		     double realClassValue = inst.classValue();
		     if (predictedClassValue==realClassValue)
		         correct++;
		     else
		         wrong++;
		 }
		 System.out.println("Classifier got " + correct + " right and " + wrong + " wrong" );
		 if(wrong>0)
			 fail("Failed");
	}

}
