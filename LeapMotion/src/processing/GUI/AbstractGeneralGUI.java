package processing.GUI;

import button.Button;
import processing.Page;
import processing.core.PApplet;
import processing.core.PConstants;

public abstract class AbstractGeneralGUI extends AbstractGUI{
	protected boolean rendered=false;
	public AbstractGeneralGUI(PApplet page) {
		super(page);
	}
	
	 protected void createGUI(){
			//To be implemented by subclass
		  }
	 
	  @Override
	  public void render(){
		  if(rendered==false){
				createGUI();
			    rendered=true;
			  }
	  }	
	  
	  protected boolean checkIfMouseOverButton(Button button){
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
				//((Page) page).renderLeapWarning();
				return true;
			}
			if(button.isTimerRunning()){
				button.cancelTimerTask();
				page.setDefaultBackground();
			}
			return false;
		}
}
