package processing;

import java.util.Timer;
import java.util.TimerTask;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Image;
import com.leapmotion.leap.ImageList;

import classifier.SignClassifier;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import recording.AbstractHandData.Handedness;
import recording.AlphabetTrainer;
import recording.ITrainer;
import recording.NumberTrainer;
import recording.OneHandTrainer;
import recording.TwoHandTrainer;

@SuppressWarnings("unused")
public class SignTrainer extends PApplet{

	public char charToTrain='b';
	public static final Handedness hand= Handedness.RIGHT;
	private final Controller controller = new Controller();

	private final ITrainer trainer = new AlphabetTrainer(controller,hand);
	//private final ITrainer trainer = new NumberTrainer(controller,hand);
	//private final ITrainer trainer = new TwoHandTrainer(controller);

	private PImage charImage; 

	boolean timerSet=false;
	private Timer timer;
	int currentTime;
	PFont font;


	public static void main(String[] args) {
		PApplet.main("processing.SignTrainer");
	}

	public void settings(){
		size(600, 600);
	}

	public void setup(){ 
		controller.setPolicy(Controller.PolicyFlag.POLICY_IMAGES);
		background(255);
		font = createFont("Arial", 40);
		fill(0);
		textSize(30);
	}

	public void draw(){
		if(timerSet){
			timer();
			return;}
		displayLeapImages();
		char c=trainer.train();
		if(c!=charToTrain){
			timer();
			charToTrain=c;
		}
		renderImage();
	}

	private void renderImage(){
		String filename=SignClassifier.language +  "/" + hand+"/" + charToTrain + ".jpg";
		charImage= loadImage(filename);
		image(charImage,0, 301, 600, 300);
	}

	private void timer(){
		background(255);
		if(!timerSet){
			time();
			timerSet=true;}
		text(currentTime +" second(s), next sign is "+ charToTrain, 125, 150);
		renderImage();
	}

	protected void time(){
		timer=new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			int i = 6;//defined for a 5 second countdown
			public void run() {
				i--;
				currentTime=i;
				if (i< 0){
					timer.cancel();
					timerSet=false;
				}
			}
		}, 0, 1000);
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