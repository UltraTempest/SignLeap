package gui;

import button.IButton;
import processing.Page;
import processing.core.PApplet;
import processing.core.PConstants;

public abstract class AbstractGeneralGUI extends AbstractGUI{

	private final String buttonLoadText="Hover Over Button %s%%";

	public AbstractGeneralGUI(final PApplet page) {
		super(page);
		getPage().turnOnLeapMouse();
	}
	
	public void render(){
		super.render();
	}

	protected final void handleMouseOverButton(final IButton... buttons){
		final Page page=getPage();
		for(final IButton button:buttons){
			outerif:
				if(button.isMouseOver()){
					if(!button.isTimerRunning())
						button.startCountdown();
					int percent=button.getCountdown();
					if(percent>95)
						break outerif;
					percent+=5;
					final int xCoordinate=674;
					final int yCoordinate=593;
					page.setDefaultBackground();			  
					page.fill(255);
					page.rect(xCoordinate, yCoordinate, 204, 24);
					final int fillX = percent*2; 
					page.fill(100);
					page.textAlign(PConstants.LEFT);
					page.text (String.format(buttonLoadText, percent), xCoordinate, yCoordinate-2);
					page.rect(xCoordinate+202, yCoordinate+2, fillX-200, 20);
					continue;
				}
		if(button.isTimerRunning()){
			button.cancelTimerTask();
			page.setDefaultBackground();
		}
		}
	}
}
