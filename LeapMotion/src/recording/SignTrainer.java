package recording;

import java.util.Map;
import java.util.Scanner;

import com.leapmotion.leap.Controller;

import database.SignedDB;
import recording.HandData.Handedness;

public class SignTrainer {
	
	public static void main(String args[]){
		new OneHandTrainer().train();
		//new TwoHandTrainer().train();
	}
}	

class OneHandTrainer{
	
	private final int NUM_SAMPLES = 1000;
	protected final long SAMPLE_DELAY = (long) 0.1;
	public static final int ONE_HAND_NUM_FEATURES = 60;
	
	public void train(){
		Scanner scan = new Scanner(System.in);
	        char training_char = getCharToTrain(scan);
	        Handedness hand=getHandUsed(scan);
	        try {
				trainChar(training_char,hand);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}

	protected char getCharToTrain(Scanner scan){
		System.out.println("Enter char to train: ");
		char training_char = scan.nextLine().charAt(0);
	    return Character.toLowerCase(training_char);
	}
	
	protected Handedness getHandUsed(Scanner scan){
		System.out.println("Hand to be used? r/l?");
		char handChar = scan.nextLine().charAt(0);
		scan.close();
	    return handChar=='r'? Handedness.RIGHT : Handedness.LEFT;
	}

	protected void trainChar(char training_char, Handedness hand) throws InterruptedException{
	   Controller controller = new Controller();
	   HandData handData=new HandData();
	   String table;
	   if(Character.isDigit(training_char))
		   table="num_data";
	   else
		   table="alpha_data";
	   for(int i=0; i< NUM_SAMPLES;i++){
	        Thread.sleep(SAMPLE_DELAY);
	        Map<String, Float> sample = handData.getOneHandPosition(controller);
	        while(sample==null || sample.size()!=ONE_HAND_NUM_FEATURES){
	           System.out.println("Please place only " + hand + " hand in view");
	            sample = handData.getOneHandPosition(controller);
	        }
	        System.out.println("Inserted " + training_char +" no." + i + ": " + sample);
	        new SignedDB().insertSignValues("isl_" + hand + "_data.db", training_char, sample, table);
	   }
	   System.out.println("Done training");
	}
}

class TwoHandTrainer extends OneHandTrainer{
	private final int NUM_SAMPLES = 1010;
	public static final int TW0_HAND_NUM_FEATURES = 120;
	private final int prepDelay=20;
	
	@Override
	protected void trainChar(char training_char, Handedness hand) throws InterruptedException{
		   Controller controller = new Controller();
		   HandData handData=new HandData();
		   SignedDB db= new SignedDB();
		   String table;
		   if(hand.equals(Handedness.RIGHT))
			   table="right_data";
		   else
			   table="left_data";
		   for(int i=0; i< NUM_SAMPLES+prepDelay;i++){
		        Thread.sleep(SAMPLE_DELAY);
		        Map<String, Float> sample = handData.getTwoHandsPosition(controller);
		        while(sample==null || sample.size()!=TW0_HAND_NUM_FEATURES){
		           System.out.println("Please place 2 hands in view");
		            sample = handData.getTwoHandsPosition(controller);
		        }
		        if(i<prepDelay)
		        	System.out.println("Preparing:" + (prepDelay-i));
		        else{
		        	db.insertSignValues("isl_two_hand_data.db", training_char, sample, table);
		        	System.out.println("Inserted " + training_char +" no." + (i-prepDelay) + ": " + sample);
		        }
		   }
		   System.out.println("Done training");
		}
}