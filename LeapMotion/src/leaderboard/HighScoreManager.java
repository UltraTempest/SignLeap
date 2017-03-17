package leaderboard;

import java.util.*;
import java.io.*;

public abstract class HighScoreManager {
	// An arraylist of the type "score" we will use to work with the scores inside the class
	private List<Score> scores;

	// The name of the file where the alphabet highscores will be saved
	private final String highScoreFile;
	
	// The name of the file where the numbers highscores will be saved
	public static final String NUM_HIGHSCORE_FILE = "numleaderboard.dat";
	
	// The name of the file where the alphabet highscores will be saved
	public static final String ALPHA_HIGHSCORE_FILE = "alphaleaderboard.dat";
	
	//Initialising an in and outputStream for working with the file
	private ObjectOutputStream outputStream;
	private ObjectInputStream inputStream;

	public HighScoreManager(final String filename) {
		//initialising the scores-arraylist
		scores = new ArrayList<Score>();
		highScoreFile=filename;
	}

	public final List<Score> getScores() {
		loadScoreFile();
		Collections.sort(scores);
		return scores;
	}

	public final void addScore(final String name,final int score) {
		loadScoreFile();
		scores.add(new Score(name, score));
		updateScoreFile();
	}

	@SuppressWarnings("unchecked")
	private final void loadScoreFile() {
		try {
			File file = new File(highScoreFile);
			if(!file.exists() || file.toString().equals("")){
				PrintWriter writer = new PrintWriter(highScoreFile, "UTF-8");
				writer.close();
				return;
			}
			inputStream = new ObjectInputStream(new FileInputStream(highScoreFile));
			scores = (ArrayList<Score>) inputStream.readObject();
		} catch (FileNotFoundException e) {
			System.out.println("[Laad] FNF Error: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("[Laad] IO Error: " + e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println("[Laad] CNF Error: " + e.getMessage());
		} finally {
			try {
				if (outputStream != null) {
					outputStream.flush();
					outputStream.close();
				}
				if (inputStream != null) 
					inputStream.close();
			} catch (final IOException e) {
				System.out.println("[Laad] IO Error: " + e.getMessage());
			}
		}
	}

	private final void updateScoreFile() {
		try {
			outputStream = new ObjectOutputStream(
					new FileOutputStream(highScoreFile));
			outputStream.writeObject(scores);
		} catch (FileNotFoundException e) {
			System.out.println("[Update] FNF Error: " + e.getMessage() +
					",the program will try and make a new file");
		} catch (IOException e) {
			System.out.println("[Update] IO Error: " + e.getMessage());
		} finally {
			try {
				if (outputStream != null) {
					outputStream.flush();
					outputStream.close();
				}
				if (inputStream != null) 
					inputStream.close();
			} catch (IOException e) {
				System.out.println("[Update] Error: " + e.getMessage());
			}
		}
	}

	public final String getHighscoreString() {
		final StringBuilder highscoreSb = new StringBuilder();
		final int max = 5;
		getScores();

		int i = 0;
		int x = scores.size();
		if (x > max) {
			x = max;
		}
		while (i < x) {
			highscoreSb.append( (i + 1) + ".\t" + scores.get(i).getName() 
					+ "\t\t" + scores.get(i).getScore() + "\n");
			++i;
		}
		return highscoreSb.toString();
	}
}