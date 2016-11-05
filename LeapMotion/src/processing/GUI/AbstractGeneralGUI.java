package processing.GUI;

import processing.core.PApplet;

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
		  if(rendered==false)
			createGUI();
		  rendered=true;
	  }
	
}
