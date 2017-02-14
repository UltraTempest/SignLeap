package processing;

import java.util.Timer;
import java.util.TimerTask;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Image;
import com.leapmotion.leap.ImageList;

import classifier.SignClassifier;
import processing.core.PApplet;
import processing.core.PImage;
import recording.AbstractHandData.Handedness;
import trainer.AlphabetTrainer;
import trainer.ITrainer;
import trainer.NumberTrainer;


public final class SignTrainer extends PApplet{

	private String charToTrain;
	private final Handedness hand= Handedness.LEFT;
	private final static Controller controller = new Controller();

	//private final ITrainer trainer = new AlphabetTrainer(controller,hand);
	private final ITrainer trainer = new NumberTrainer(controller,hand);
	//private final ITrainer trainer = new TwoHandTrainer(controller);

	private PImage charImage; 

	private boolean timerSet=false;
	private Timer timer;
	private int currentTime;

	public final static void main(final String[] args) {
		PApplet.main("processing.SignTrainer");
	}

	public final void settings(){
		size(600, 600);
	}

	public final void setup(){ 
		controller.setPolicy(Controller.PolicyFlag.POLICY_IMAGES);
		background(255);
		createFont("Arial", 40);
		fill(0);
		textSize(30);
		charToTrain=trainer.getCurrentCharacter();
		timer();
	}

	public void draw(){
		if(timerSet){
			timer();
			return;}
		displayLeapImages();
		final String c=trainer.train();
		if(!c.equals(charToTrain)){
			timer();
			charToTrain=c;
		}
		renderImage();
	}

	private void renderImage(){
		final String filename=SignClassifier.language +  "/" + hand+"/" 
				+ charToTrain + ".jpg";
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

	private final void displayLeapImages(){
		final Frame frame = controller.frame();
		if(frame.isValid()){
			final ImageList images = frame.images();
			for(Image image : images)
			{
				final PImage[] cameras = new PImage[2];
				//Processing PImage class
				PImage camera = cameras[image.id()];
				camera = createImage(image.width(), image.height(), RGB);
				camera.loadPixels();

				//Get byte array containing the image data from Image object
				final byte[] imageData = image.data();

				//Copy image data into display object, in this case PImage defined in Processing
				int r,g,b;
				for(int i = 0; i < image.width() * image.height(); i++){
					r = (imageData[i] & 0xFF) << 16; //convert to unsigned and shift into place
					g = (imageData[i] & 0xFF) << 8;
					b = imageData[i] & 0xFF;
					camera.pixels[i] =  r | g | b; 
				}

				//Show the image
				camera.updatePixels();
				image(camera, 640 * image.id(), 0);  
			}
		}
	}

}	