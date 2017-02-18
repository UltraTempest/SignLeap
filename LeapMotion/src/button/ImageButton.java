package button;

import command.ChangeImageCommand;
import command.ICommand;
import g4p_controls.GImageButton;
import g4p_controls.ImageManager;
import processing.core.PApplet;
import processing.core.PImage;

public final class ImageButton extends GImageButton implements IButton{
	private final ICommand command;

	private boolean timerRunning=false;
	private final ButtonTimer bTimer;

	private final String tickFileName="tick.jpg";
	private final String image;
	private boolean ticked=false;

	public ImageButton(final PApplet page,final  float arg1,final float arg2,
			final float arg3,final float arg4,final String image) {
		super(page, arg1, arg2, arg3, arg4, new String[]{image,image,image});
		this.command=new ChangeImageCommand(page, this);
		this.image=image;
		bTimer=new ButtonTimer(100, command, 0.05, 1.0);
	}

	@Override
	public ICommand getCommand(){
		return command;
	}

	public void ticked(){
		if(!ticked){
			setImage(new String[]{tickFileName,tickFileName,tickFileName});
			ticked=true;
		}
		else{
			setImage(new String[]{image,image,image});
			ticked=false;
		}
	}
	
	public boolean isTicked() {
		return ticked;
	}

	@Override
	public boolean isTimerRunning(){
		return timerRunning;
	}

	@Override
	public void cancelTimerTask(){
		bTimer.cancel();
		timerRunning=false;
	}

	@Override
	public void startCountdown(){
		bTimer.schuedule();
		timerRunning=true;
	}

	@Override
	public double getCountdown(){
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