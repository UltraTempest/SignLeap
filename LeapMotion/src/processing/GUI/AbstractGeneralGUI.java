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
				page.background(230);			  
				page.setTextSizeToDefault();
				page.fill(255);
				page.textAlign(PConstants.LEFT);
				//page.text ("LOADING " + ((page.frameCount%301) / 3) + "%", 50, 130);
				page.text ("LOADING " + percent + "%", 50, 130);
				page.rect(48, 138, 204, 24);
				page.fill(0);
				//int fillX = ((page.frameCount%301) / 3 * 2);
				int fillX = percent*2;
				page.rect(250, 140, fillX-200, 20);
				//((Page) page).renderLeapWarning();
				return true;
			}
			if(button.isTimerRunning()){
				button.cancelTimerTask();
				page.background(230);
			}
			return false;
		}
}
