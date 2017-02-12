package gui;

import processing.core.PApplet;

public final class GUIFactory {
	private static PApplet page;

	public GUIFactory(final PApplet page){
		GUIFactory.page=page;
	}

	public final IGUI createGameOverGUI(final int score){
		return new GameOverGUI(page, score);
	}

	public final IGUI createIntroductionGUI(){
		return new IntroductionGUI(page);
	}

	public final IGUI createSignAlphabetGUI(){
		return new SignAlphabetGUI(page);
	}

	public final IGUI createSignNumbersGUI(){
		return new SignNumbersGUI(page);
	}

	public final IGUI createMainMenuGUI(){
		return new MainMenuGUI(page);
	}

	public final IGUI createLeaderboardGUI(){
		return new LeaderboardGUI(page);
	}
}