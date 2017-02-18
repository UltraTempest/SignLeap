package gui;

import button.IButton;
import processing.Page;
import processing.core.PApplet;
import processing.core.PConstants;

public abstract class AbstractGeneralGUI extends AbstractGUI{
	public AbstractGeneralGUI(final PApplet page) {
		super(page);
		getPage().turnOnLeapMouse();
	}

	protected final void handleMouseOverButton(final IButton... buttons){
		final Page page=getPage();
		for(final IButton button:buttons){
			outerif:
				if(button.isMouseOver()){
					if(!button.isTimerRunning())
						button.startCountdown();
					final int percent=(int) (button.getCountdown()*100);
					if(percent==100)
						break outerif;
					final int xCoordinate=674;
					final int yCoordinate=593;
					page.setDefaultBackground();			  
					page.fill(255);
					page.rect(xCoordinate, yCoordinate, 204, 24);
					final int fillX = percent*2; 
					page.fill(100);
					page.setTextSizeToDefault();
					page.textAlign(PConstants.LEFT);
					page.text ("LOADING " + percent + "%", xCoordinate, yCoordinate);
					page.rect(xCoordinate+202, yCoordinate+2, fillX-200, 20);
					return;
				}
			if(button.isTimerRunning()){
				button.cancelTimerTask();
				page.setDefaultBackground();
			}
		}
	}
}
