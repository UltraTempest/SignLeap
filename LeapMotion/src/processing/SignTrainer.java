package processing;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collection;
import java.util.Map;
import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Image;
import com.leapmotion.leap.ImageList;

import database.SignedDB;
import processing.core.PApplet;
import processing.core.PImage;
import recording.HandData;
import recording.HandData.Handedness;

public class SignTrainer extends PApplet{
	
	public static final char charToTrain='5';
	public static final Handedness hand= Handedness.RIGHT;
	
	public static final int ONE_HAND_NUM_FEATURES = 60;
	public static final int TW0_HAND_NUM_FEATURES = 120;
	
	private final Controller controller = new Controller();
	private final OneHandTrainer trainer = new OneHandTrainer(controller, charToTrain, hand);
	
	 public static void main(String[] args) {
	        PApplet.main("processing.SignTrainer");
	    }

  public void settings(){
	    size(300, 300);
	}
	    
	 public void setup(){ 
		 controller.setPolicy(Controller.PolicyFlag.POLICY_IMAGES);
	     background(230);
	}

	 public void draw(){
		displayLeapImages();
		try {
			trainer.train();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	 
	 private void displayLeapImages(){
		 Frame frame = controller.frame();
		 if(frame.isValid()){
		   ImageList images = frame.images();
		   for(Image image : images)
		   {
			   PImage[] cameras = new PImage[2];
		     //Processing PImage class
		     PImage camera = cameras[image.id()];
		     camera = createImage(image.width(), image.height(), RGB);
		     camera.loadPixels();
		     
		     //Get byte array containing the image data from Image object
		     byte[] imageData = image.data();

		     //Copy image data into display object, in this case PImage defined in Processing
		     for(int i = 0; i < image.width() * image.height(); i++){
		       int r = (imageData[i] & 0xFF) << 16; //convert to unsigned and shift into place
		       int g = (imageData[i] & 0xFF) << 8;
		       int b = imageData[i] & 0xFF;
		       camera.pixels[i] =  r | g | b; 
		     }
		     
		     //Show the image
		     camera.updatePixels();
		     image(camera, 300 * image.id(), 0, 300, 300);  
		   }
		}
	 }
}	

class OneHandTrainer{
	
	private final int NUM_SAMPLES = 250;
	protected Controller controller;
	protected HandData handData = new HandData();
	protected char trainingChar; 
	protected Handedness hand;
	protected int count =0;
	private final Path filePath=Paths.get( "SignData/TrainingData/num.arff");
	
	public OneHandTrainer(Controller controller, char trainingChar, Handedness hand){
		this.controller=controller;
		 this.trainingChar = trainingChar;
	     this.hand=hand;
	}
	
	protected String formatString(Collection<Float> sample){
		StringBuilder sb = new StringBuilder();
		for(Float i: sample){
			sb.append(i+",");
		}
		return sb.toString();	
	}

	public void train() throws InterruptedException{
	   if(count==NUM_SAMPLES){
		   System.out.println("Done training");
		   System.exit(0);
	   }
	        Map<String, Float> sample = handData.getOneHandPosition(controller);
	        if(sample==null || sample.size()!=SignTrainer.ONE_HAND_NUM_FEATURES){
	           //System.out.println("Please place only " + hand + " hand in view");
	            return;
	        }
	        String toInsert = "\n" + formatString(sample.values())+String.valueOf(trainingChar);
	        try {
				Files.write(filePath, toInsert.getBytes(), StandardOpenOption.APPEND);
			} catch (IOException e) {
				e.printStackTrace();
			}
	        System.out.println("Inserted " + trainingChar +" no." + count + ": " + sample);
	        count++;
	}
}

class TwoHandTrainer extends OneHandTrainer{
	public TwoHandTrainer(Controller controller,char trainingChar){
		super(controller, trainingChar, null);
	}

	private final int NUM_SAMPLES = 1010;
	private final int prepDelay=20;
	private final long SAMPLE_DELAY=(long) 0.1;
	
	@Override
	public void train() throws InterruptedException{
		   SignedDB db= new SignedDB();
		   String table;
		   if(hand.equals(Handedness.RIGHT))
			   table="right_data";
		   else
			   table="left_data";
		   for(int i=0; i< NUM_SAMPLES+prepDelay;i++){
		        Thread.sleep(SAMPLE_DELAY);
		        Map<String, Float> sample = handData.getTwoHandsPosition(controller);
		        while(sample==null || sample.size()!=SignTrainer.TW0_HAND_NUM_FEATURES){
		           System.out.println("Please place 2 hands in view");
		            sample = handData.getTwoHandsPosition(controller);
		        }
		        if(i<prepDelay)
		        	System.out.println("Preparing:" + (prepDelay-i));
		        else{
		        	db.insertSignValues("isl_two_hand_data.db", trainingChar, sample, table);
		        	System.out.println("Inserted " + trainingChar +" no." + (i-prepDelay) + ": " + sample);
		        }
		   }
		   System.out.println("Done training");
		}
}