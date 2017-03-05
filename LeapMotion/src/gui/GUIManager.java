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
		updatePage(GameOverGUI.class, score, leaderboardFlag);
	}

	public void setSubMainMenuGUI(final ICommand command) {
		updatePage(SubMenuGUI.class, command);
	}
	
	public void setIntroductionSignCharacterGUI(){
		updatePage(IntroductionSignCharacterGUI.class);
	}

	public void setIntroductionGUI(final int index){
		updatePage(IntroductionGUI.class,index);
	}

	public void setWelcomeGUI(){
		updatePage(WelcomeGUI.class);
	}

	public void setTrainingNumbersGUI(final String[] signs){
		switchTrainingGUI(TrainingNumbersGUI.class, signs);
	}
	
	public void setTrainingAlphabetGUI(final String[] signs){
		switchTrainingGUI(TrainingAlphabetGUI.class, signs);
	}

	private void switchTrainingGUI(final Class<?> clazz,final String[] signs){
			deleteCurrentGUI();
		try {
			final Constructor<?> constructor = clazz.getConstructors()[0];
			changeCurrentGUI((IGUI) constructor.newInstance(page, signs),clazz);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public void setSelectAlphabetGUI(){
		updatePage(SelectAlphabetGUI.class);
	}

	public void setSelectNumberGUI(){
		updatePage(SelectNumbersGUI.class);
	}

	public void setSignAlphabetGUI(){
		updatePage(SignAlphabetGUI.class);
	}

	public void setSignNumbersGUI(){
		updatePage(SignNumbersGUI.class);
	}

	public void setMainMenuGUI(){
		updatePage(MainMenuGUI.class);
	}

	public void setLeaderboardGUI(final HighScoreManager scoreManager){
		updatePage(LeaderboardGUI.class,scoreManager);
	}

	private void updatePage(final Class<?> clazz, final Object...objs){
		if(classCheck(clazz))
			changeState(clazz,objs);
	}

	private void changeCurrentGUI(final IGUI gui, final Class<?> clazz){
		page.changeGUI(gui);
		currentGUIclass=clazz;
	}

	private void deleteCurrentGUI(){
		if(currentGUIclass!=null)
		page.currentGUIDisposal();
	}

	private void changeState(final Class<?> clazz,final Object... objs){
			deleteCurrentGUI();
		try {
			final Constructor<?> constructor = clazz.getConstructors()[0];
			changeCurrentGUI((IGUI) constructor.newInstance(concat(new Object[] { page},objs)),clazz);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	private boolean classCheck(final Class<?> obj){
		return !obj.equals(currentGUIclass);
	}

	private Object[] concat(final Object[] a,final  Object[] b) {
		if(b==null) return a;
		int aLen = a.length;
		int bLen = b.length;
		Object[] c= new Object[aLen+bLen];
		System.arraycopy(a, 0, c, 0, aLen);
		System.arraycopy(b, 0, c, aLen, bLen);
		return c;
	}
}