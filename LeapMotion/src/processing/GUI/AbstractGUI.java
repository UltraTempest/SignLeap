package processing.GUI;

import g4p_controls.GAbstractControl;
import processing.Page;
import processing.core.PApplet;

public abstract class AbstractGUI implements IGUI{
  private static PApplet page;
  public AbstractGUI(PApplet page){
	  AbstractGUI.page=page;
  }
  
  public Page getPage(){
	  return (Page) AbstractGUI.page;
  }
  
  protected void objectDisposal(GAbstractControl object){
	  object.setVisible(false);
	  object.dispose();
  }
  
  @Override
  public void dispose(){
	  getPage().background(230);
  }
}
