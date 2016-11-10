package test;

import static org.junit.Assert.*;

import org.junit.Test;

import recording.AbstractSignClassifier;
import recording.HandData.Handedness;
import recording.OneHandSignClassifier;
import recording.TwoHandSignClassifier;
import weka.core.Instance;

public class SignClassifierTest{
	
	@Test
	public void twoHandClassificationTest(){
		classicationTest(new TwoHandSignClassifier(Handedness.RIGHT.toString()));
		classicationTest(new TwoHandSignClassifier(Handedness.LEFT.toString()));
	}
	
	@Test
	public void rightClassificationTest(){
		oneHandClassificationTest(Handedness.RIGHT, "num");
		oneHandClassificationTest(Handedness.RIGHT, "alpha");
	}
	
	@Test
	public void leftClassificationTest(){
		oneHandClassificationTest(Handedness.LEFT, "num");
		oneHandClassificationTest(Handedness.LEFT, "alpha");
	}
	
	public void oneHandClassificationTest(Handedness hand, String type){
		 classicationTest(new OneHandSignClassifier(hand.toString(), type));
	}
	
	private void classicationTest(AbstractSignClassifier signCls){
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
		 try {
			signCls.evaluate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		 System.out.println("Classifier got " + correct + " right and " + wrong + " wrong" );
		 if(wrong>100)
			 fail("Failed");
	}
}