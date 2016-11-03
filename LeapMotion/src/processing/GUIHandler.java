package processing;

import processing.GUI.GUI;
import processing.GUI.GameOverGUI;
import processing.GUI.MainMenuGUI;
import processing.GUI.SignAlphabetGUI;
import processing.GUI.SignNumbersGUI;
import processing.GUI.WelcomeGUI;
import processing.core.PApplet;

public class GUIHandler {
private static PApplet page;

  public GUIHandler(PApplet page){
	  GUIHandler.page=page;
  }
  
  public GUI getGameOverGUI(){
	  return new GameOverGUI(page);
  }
  
  public GUI getWelcomeGUI(){
	  return new WelcomeGUI(page);
  }
  
  public GUI getSignAlphabetGUI(String hand){
	  return new SignAlphabetGUI(page, hand);
  }
  
  public GUI getSignNumbersGUI(String hand){
	  return new SignNumbersGUI(page, hand);
  }
  
  public GUI getMainMenuGUI(String hand){
	  return new MainMenuGUI(page, hand);
  }
}
