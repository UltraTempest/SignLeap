package button;

import classifier.SignClassifier;
import command.ChangeImageCommand;
import command.ICommand;
import g4p_controls.GImageButton;
import g4p_controls.ImageManager;
import gui.AbstractSignCharacterGUI;
import gui.IImageSelectorGUI;
import processing.Page;
import processing.core.PApplet;
import processing.core.PImage;

public final class ImageButton extends GImageButton implements IButton{
	private final ICommand command;
	private final ButtonTimer bTimer;
	private final IImageSelectorGUI gui;

	private final static String tickFileName="tick.jpg";
	private final String image;
	private final String signChar; 
	private boolean ticked=true;

	public ImageButton(final IImageSelectorGUI gui,final  float arg1,final float arg2,final float arg3,final float arg4,
			final String signChar) {
		super(gui.getPApplet(), arg1, arg2, arg3, arg4, new String[]{tickFileName,tickFileName,tickFileName});
		this.gui=gui;
		final Page page=gui.getPApplet();
		final String imageName= SignClassifier.language+"/" + page.getHand() +"/%s"+ AbstractSignCharacterGUI.imageType;
		this.signChar=signChar;
		this.image=String.format(imageName, signChar);
		this.command=new ChangeImageCommand(this);
		ticked();
		bTimer=new ButtonTimer(command);
		addEventHandler(page, "handleButtonEvents");
	}

	@Override
	public ICommand getCommand(){
		return command;
	}

	@Override
	public boolean isTimerRunning(){
		return bTimer.isTimerRunning();
	}

	@Override
	public void cancelTimerTask(){
		bTimer.cancelTask();
	}

	@Override
	public void startCountdown(){
		bTimer.schuedule();
	}

	@Override
	public int getCountdown(){
		return bTimer.getCountdown();
	}

	@Override
	public boolean isMouseOver(){
		final PApplet page= getPApplet();
		final float buttonX=getX();
		final float buttonY=getY();
		final float buttonHeight=getHeight();
		final float buttonWidth = getWidth();

		return (buttonX <= page.mouseX && page.mouseX <= buttonX+buttonWidth && 
				buttonY <= page.mouseY && page.mouseY <= buttonY+buttonHeight);
	}

	public void ticked(){
		if(!ticked){
			setImage(new String[]{tickFileName,tickFileName,tickFileName});
			gui.addSign(signChar);
			ticked=true;
		}
		else{
			setImage(new String[]{image,image,image});
			gui.removeSign(signChar);
			ticked=false;
		}
	}

	public boolean isTicked() {
		return ticked;
	}

	public void setImage(final String[] fnames) {
		//======================================
		bimage = ImageManager.loadImage(winApp, fnames);
		// There should be 3 images if not use as many as possible, 
		// duplicating the last one if neccessary
		if(bimage.length != 3){
			final PImage[] temp = new PImage[3];
			for(int i = 0; i < 3; i++)
				temp[i] = bimage[Math.min(i, bimage.length - 1)];
			bimage = temp;
		}
		//======================================
		// resize images if needed
		for(int i = 0; i < bimage.length; i++){
			if(bimage[i].width != width || bimage[i].height != height)
				bimage[i].resize((int)width, (int)height);                    
		}  

	}
}