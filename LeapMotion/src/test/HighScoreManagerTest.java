package test;

import leaderboard.HighScoreManager;

public class HighScoreManagerTest {
    public static void main(String[] args) {
        HighScoreManager hm = new HighScoreManager();
        hm.addScore("Bart",240);
        hm.addScore("Marge",300);
        hm.addScore("Maggie",220);
        hm.addScore("Homer",100);
        hm.addScore("Lisa",270);

        System.out.print(hm.getHighscoreString());
    }
}