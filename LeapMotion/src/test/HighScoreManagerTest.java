package test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import leaderboard.HighScoreManager;

public final class HighScoreManagerTest {

	@After
	public final void cleanUp() throws IOException{
		Files.delete(Paths.get(HighScoreManager.HIGHSCORE_FILE));
	}

	@Test
	public final void test(){
		final HighScoreManager hm = new HighScoreManager();
		hm.addScore("Bart",240);
		hm.addScore("Marge",300);
		hm.addScore("Maggie",220);
		hm.addScore("Homer",100);
		hm.addScore("Lisa",270);

		Assert.assertEquals(hm.getHighscoreString(), 
				"1.	Marge		300" + "\n" +
						"2.	Lisa		270" + "\n" + 
						"3.	Bart		240" + "\n" + 
						"4.	Maggie		220" + "\n" + 
						"5.	Homer		100" + "\n");
	}
}