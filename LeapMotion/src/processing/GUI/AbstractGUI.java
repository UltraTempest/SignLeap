package processing.GUI;

import processing.Page;
import processing.core.PApplet;

public abstract class AbstractGUI implements IGUI{
  private static PApplet page;
  protected boolean rendered=false;
  public AbstractGUI(PApplet page){
	  AbstractGUI.page=page;
  }
  
  public Page getPage(){
	  return (Page) AbstractGUI.page;
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
