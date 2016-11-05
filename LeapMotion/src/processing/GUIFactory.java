package processing;

import processing.GUI.IGUI;
import processing.GUI.LeaderboardGUI;
import processing.GUI.GameOverGUI;
import processing.GUI.MainMenuGUI;
import processing.GUI.SignAlphabetGUI;
import processing.GUI.SignNumbersGUI;
import processing.GUI.WelcomeGUI;
import processing.core.PApplet;

public class GUIFactory {
private static PApplet page;

  public GUIFactory(PApplet page){
	  GUIFactory.page=page;
  }
  
  public IGUI createGameOverGUI(int score){
	  return new GameOverGUI(page, score);
  }
  
  public IGUI createWelcomeGUI(){
	  return new WelcomeGUI(page);
  }
  
  public IGUI createSignAlphabetGUI(){
	  return new SignAlphabetGUI(page);
  }
  
  public IGUI createSignNumbersGUI(){
	  return new SignNumbersGUI(page);
  }
  
  public IGUI createMainMenuGUI(){
	  return new MainMenuGUI(page);
  }
  
  public IGUI createLeaderboardGUI(){
	  return new LeaderboardGUI(page);
  }
}
