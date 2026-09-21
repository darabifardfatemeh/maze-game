package com.maze.score;

public class ScoreCalculator {

    private int levelScore;

    public void reset() {
        levelScore = 0;
    }

    public int onWin(int remainingSeconds, int remainingStars) {
        levelScore = remainingSeconds * 10 * Math.max(1, remainingStars);
        return levelScore;
    }

    public int getScore() {
        return levelScore;
    }
}

