package gui;

import java.lang.reflect.Constructor;
import command.ICommand;
import leaderboard.HighScoreManager;
import processing.Page;

public final class GUIManager {
	private static Page page;
	private Class<?> currentGUIclass;

	public GUIManager(final Page page){
		GUIManager.page=page;
	}

	public void setGameOverGUI(final int score, final boolean leaderboardFlag){
		changeState(GameOverGUI.class, score, leaderboardFlag);
	}

	public void setSubMenuGUI(final ICommand command) {
		changeState(SubMenuGUI.class, command);
	}
	
	public void setGameSubMenuGUI(final ICommand command) {
		changeState(GameSubMenuGUI.class, command);
	}

	public void setIntroductionSignCharacterGUI(){
		changeState(IntroductionSignCharacterGUI.class);
	}

	public void setIntroductionGUI(final int index){
		changeState(IntroductionGUI.class,index);
	}

	public void setWelcomeGUI(){
		changeState(WelcomeGUI.class);
	}

	public void setTrainingNumbersGUI(final String[] signs){
		changeState(TrainingNumbersGUI.class,(Object []) signs);
	}

	public void setTrainingAlphabetGUI(final String[] signs){
		changeState(TrainingAlphabetGUI.class,(Object []) signs);
	}

	public void setSelectAlphabetGUI(){
		changeState(SelectAlphabetGUI.class);
	}

	public void setSelectNumberGUI(){
		changeState(SelectNumbersGUI.class);
	}

	public void setSignAlphabetGUI(){
		changeState(SignAlphabetGUI.class);
	}

	public void setSignNumbersGUI(){
		changeState(SignNumbersGUI.class);
	}

	public void setMainMenuGUI(){
		changeState(MainMenuGUI.class);
	}

	public void setLeaderboardGUI(final HighScoreManager scoreManager){
		changeState(LeaderboardGUI.class,scoreManager);
	}

	private void deleteCurrentGUI(){
		if(currentGUIclass!=null)
			page.currentGUIDisposal();
	}

	private void changeState(final Class<?> clazz,final Object... objs){
		if(classCheck(clazz)){
			deleteCurrentGUI();
			final Constructor<?> constructor = clazz.getConstructors()[0];
			if(objs instanceof String[])
				changeCurrentGUI(constructor,page,objs);
			else
				changeCurrentGUI(constructor,concat(objs,page));
			currentGUIclass=clazz;
		}
	}

	private void changeCurrentGUI(final Constructor<?> constructor,final Object... objs){
		try {
			page.changeGUI((IGUI) constructor.newInstance(objs));
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	private boolean classCheck(final Class<?> obj){
		return !obj.equals(currentGUIclass);
	}

	private Object[] concat(final Object[] a,final  Object... b) {
		if(a.length==0) return b;
		int aLen = a.length;
		int bLen = b.length;
		final Object[] c= new Object[aLen+bLen];
		System.arraycopy(b, 0, c, 0, bLen);
		System.arraycopy(a, 0, c, bLen, aLen);
		return c;
	}
}