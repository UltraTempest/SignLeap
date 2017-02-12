package leaderboard;

import java.io.Serializable;

public final class Score  implements Serializable, Comparable<Score>{    
	private static final long serialVersionUID = 1L;
    private final int score;
    private final String name;

    public final int getScore() {
        return score;
    }

    public final String getName() {
        return name;
    }

    public Score(final String name,final int score) {
        this.score = score;
        this.name = name;
    }    
    
    @Override
    public final int compareTo(final Score score1) {              
        return ((Integer)(score1.getScore())).compareTo(getScore());
    }
}