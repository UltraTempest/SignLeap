package processing.GUI;

import processing.core.PApplet;

public class GUIFactory {
private static PApplet page;

  public GUIFactory(PApplet page){
	  GUIFactory.page=page;
  }
  
  public IGUI createGameOverGUI(int score){
	  return new GameOverGUI(page, score);
  }
  
  public IGUI createIntroductionGUI(){
	  return new IntroductionGUI(page);
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
