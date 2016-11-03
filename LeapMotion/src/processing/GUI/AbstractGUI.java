package processing.GUI;

import processing.core.PApplet;

public abstract class AbstractGUI implements GUI{
  private static PApplet page;
  protected boolean rendered=false;
  public AbstractGUI(PApplet page){
	  AbstractGUI.page=page;
  }
  
  public PApplet getPage(){
	  return AbstractGUI.page;
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
  
  @Override
  public void dispose(){
	  getPage().background(230);
  }
}
