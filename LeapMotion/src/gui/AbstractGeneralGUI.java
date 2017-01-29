package gui;

import button.Button;
import processing.Page;
import processing.core.PApplet;
import processing.core.PConstants;

public abstract class AbstractGeneralGUI extends AbstractGUI{
	public AbstractGeneralGUI(PApplet page) {
		super(page);
	}

	protected void checkIfMouseOverButton(Button button){
		Page page=getPage();
		if(button.isMouseOver(page)){
			if(!button.isTimerRunning()){
				button.startCountdown();
			}
			int percent=(int) (button.getCountdown()*100);
			int xCoordinate=674;
			int yCoordinate=593;
			page.setDefaultBackground();			  
			page.fill(255);
			page.rect(xCoordinate, yCoordinate, 204, 24);
			int fillX = percent*2; 
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
