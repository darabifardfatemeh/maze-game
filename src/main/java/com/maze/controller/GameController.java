package com.maze.controller;
import com.maze.entity.Collectible;
import com.maze.entity.Enemy;
import com.maze.entity.Entity;
import com.maze.entity.PatrolEnemy;
import com.maze.entity.Food;
import com.maze.entity.Star;
import com.maze.model.Maze;
import com.maze.model.Player;
import com.maze.model.Position;
import com.maze.placer.BranchAwarePlacer;
import com.maze.generation.MazeGenerator;
import com.maze.score.ScoreCalculator;
import com.maze.util.PathFinder;

import java.util.ArrayList;
import java.util.List;
public class GameController {
    private final int PATROL_ENEMIES_LEVEL_2 = 3;
    private final int STATIC_ENEMIES_LEVEL_3 = 2;
    private final int STAR_COUNT = 7;
    private final int FOOD_COUNT = 5;
    private final int STAR_TIME_BONUS_SECONDS = 20;
    private final int FOOD_TIME_BONUS_SECONDS = 10;
    private final int MAZE_ROWS = 31;
    private final int MAZE_COLS = 41;
    private final int[] LEVEL_TIMES = {75, 95, 120};
    private int currentLevelIndex;
    private int remainingSeconds;
    private int totalScore;
    private boolean won;

    private List<MazeGenerator> mazeGenerators;
    private ScoreCalculator scoreCalculator;
    private BranchAwarePlacer placer;
    private Maze maze;
    private Player player;
    private List<Entity> entities = new ArrayList<>();



    public GameController(List<MazeGenerator> mazeGenerators,
                          ScoreCalculator scoreCalculator,
                          BranchAwarePlacer placer) {
        this.mazeGenerators = mazeGenerators;
        this.scoreCalculator = scoreCalculator;
        this.placer = placer;
    }



    public void startGame() {
        currentLevelIndex = 0;
        totalScore = 0;
        startLevel();
    }

    public void startLevel() {
        maze = mazeGenerators.get(currentLevelIndex).generate(MAZE_ROWS, MAZE_COLS);
        player = new Player(maze.getStart());
        won = false;
        remainingSeconds = LEVEL_TIMES[currentLevelIndex];
        scoreCalculator.reset();
        spawnEntitiesForLevel();
    }

    public void spawnEntitiesForLevel() {
        List<Position> shortestPath = PathFinder.shortestPath(maze, maze.getStart(), maze.getGoal());
        entities.clear();
        placer.beginLevel(maze);

        entities.addAll(placer.place(maze, STAR_COUNT, Star::new));

        entities.addAll(placer.place(maze, FOOD_COUNT, Food::new));

        if (currentLevelIndex == 1) {
            entities.addAll(placer.place(maze, shortestPath, PATROL_ENEMIES_LEVEL_2));
        }
        if (currentLevelIndex == 2) {
            entities.addAll(placer.place(shortestPath, STATIC_ENEMIES_LEVEL_3));
        }

    }


    public void tickOneSecond() {
        if (remainingSeconds > 0) remainingSeconds--;
        for (Entity e : entities) {
            if (e instanceof PatrolEnemy p) p.tick();
        }
        Position pos = player.getPosition();
        contactEnemyAt(pos.getRow(), pos.getCol());
    }

    public boolean isTimeUp() {
        return remainingSeconds <= 0;
    }



    public void consumeStarForBonusTime() {
        if (player.useStar()) remainingSeconds += STAR_TIME_BONUS_SECONDS;
    }


    public void applyFoodTimeBonus() {
        remainingSeconds += FOOD_TIME_BONUS_SECONDS;
    }

    public int getRemainingSeconds() {
        return remainingSeconds;
    }

    public boolean isLastLevel() {
        return currentLevelIndex >= LEVEL_TIMES.length - 1;
    }

    public void nextLevel() {
        currentLevelIndex++;
        startLevel();
    }

    public Maze getMaze() { return maze; }
    public Player getPlayer() { return player; }
    public boolean isWon() { return won; }
    public int getScore() { return scoreCalculator.getScore(); }
    public int getTotalScore() { return totalScore; }
    public int getCurrentLevelIndex() { return currentLevelIndex; }
    public List<Entity> getEntities() { return entities; }
    public void moveUp() { move(-1, 0); }
    public void moveDown() { move(1, 0); }
    public void moveLeft() { move(0, -1); }
    public void moveRight() { move(0, 1); }

    private void move(int dRow, int dCol) {
        if (won || isTimeUp()) return;
        Position pos = player.getPosition();
        int nextRow = pos.getRow() + dRow;
        int nextCol = pos.getCol() + dCol;
        if (!maze.isWalkable(nextRow, nextCol)) return;

        contactEnemyAt(nextRow, nextCol);
        player.changePosition(nextRow, nextCol);
        collectAt(nextRow, nextCol);

        if (pos.isSame(maze.getGoal())) {
            won = true;
            totalScore += scoreCalculator.onWin(remainingSeconds, player.getStars());
        }
    }

    private void contactEnemyAt(int row, int col) {
        for (Entity e : entities) {
            if (e instanceof Enemy enemy && e.getActive()
                    && e.getPosition().getRow() == row && e.getPosition().getCol() == col) {
                enemy.onPlayerContact(this);
            }
        }
    }

    private void collectAt(int row, int col) {
        for (Entity e : entities) {
            if (e instanceof Collectible c && e.getActive()
                    && e.getPosition().getRow() == row && e.getPosition().getCol() == col) {
                c.onCollect(this);
            }
        }
    }

}
