package test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.AfterClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import leaderboard.AlphabetHighScoreManager;
import leaderboard.HighScoreManager;
import leaderboard.NumbersHighScoreManager;

public final class HighScoreManagerTest{

	private HighScoreManager scoreManager;
	private final  String expected="1.	Marge		300" + "\n" +
			"2.	Lisa		270" + "\n" + 
			"3.	Bart		240" + "\n" + 
			"4.	Maggie		220" + "\n" + 
			"5.	Homer		100" + "\n";

	@AfterClass
	public static void cleanUp() throws IOException{
		Files.delete(Paths.get(HighScoreManager.ALPHA_HIGHSCORE_FILE));
		Files.delete(Paths.get(HighScoreManager.NUM_HIGHSCORE_FILE));
	}

	@Test
	public void alphabetLeaderboardTest(){
		scoreManager = new AlphabetHighScoreManager();
		scoreManager.addScore("Bart",240);
		scoreManager.addScore("Marge",300);
		scoreManager.addScore("Maggie",220);
		scoreManager.addScore("Homer",100);
		scoreManager.addScore("Lisa",270);

		assertEquals(expected,scoreManager.getHighscoreString());
	}
	
	@Test
	public void numbersLeaderboardTest(){
		scoreManager = new NumbersHighScoreManager();
		scoreManager.addScore("Bart",240);
		scoreManager.addScore("Marge",300);
		scoreManager.addScore("Maggie",220);
		scoreManager.addScore("Homer",100);
		scoreManager.addScore("Lisa",270);

		assertEquals(expected,scoreManager.getHighscoreString());
	}
}