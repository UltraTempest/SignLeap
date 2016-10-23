package test;

import static org.junit.Assert.*;

import org.junit.Test;

import recording.SignClassifier;
import weka.core.Instance;

public class SignClassifierTest{
	private final SignClassifier signCls=new SignClassifier();
	
	@Test
	public void classificationTest() throws Exception {
		 /* Counters for correct and wrong predictions. */
		 int correct = 0, wrong = 0;
		 /* Classify all instances and check with the correct class values */
		 for (Instance inst : signCls.getTrainingSet()) {
		     double predictedClassValue = signCls.classifyInstance(inst);
		     double realClassValue = inst.classValue();
		     if (predictedClassValue==realClassValue)
		         correct++;
		     else
		         wrong++;
		 }
		 signCls.evaluate();
		 System.out.println("Classifier got " + correct + " right and " + wrong + " wrong" );
		 if(wrong>100)
			 fail("Failed");
	}
}