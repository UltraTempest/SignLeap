package test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import leaderboard.AlphabetHighScoreManager;
import leaderboard.HighScoreManager;
import leaderboard.NumbersHighScoreManager;

public final class HighScoreManagerTest{

	@After
	public void cleanUp() throws IOException{
		Files.delete(Paths.get(HighScoreManager.ALPHA_HIGHSCORE_FILE));
		Files.delete(Paths.get(HighScoreManager.NUM_HIGHSCORE_FILE));
	}

	@Test
	public void test(){

		final String expected="1.	Marge		300" + "\n" +
				"2.	Lisa		270" + "\n" + 
				"3.	Bart		240" + "\n" + 
				"4.	Maggie		220" + "\n" + 
				"5.	Homer		100" + "\n";

		HighScoreManager hm = new AlphabetHighScoreManager();
		hm.addScore("Bart",240);
		hm.addScore("Marge",300);
		hm.addScore("Maggie",220);
		hm.addScore("Homer",100);
		hm.addScore("Lisa",270);

		assertEquals(expected,hm.getHighscoreString());

		hm = new NumbersHighScoreManager();
		hm.addScore("Bart",240);
		hm.addScore("Marge",300);
		hm.addScore("Maggie",220);
		hm.addScore("Homer",100);
		hm.addScore("Lisa",270);

		assertEquals(expected,hm.getHighscoreString());
	}
}