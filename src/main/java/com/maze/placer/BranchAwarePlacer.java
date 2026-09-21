package com.maze.placer;

import com.maze.entity.Bomb;
import com.maze.entity.Collectible;
import com.maze.entity.Entity;
import com.maze.entity.PatrolEnemy;
import com.maze.entity.StaticEnemy;
import com.maze.model.Maze;
import com.maze.model.Position;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class BranchAwarePlacer {

    private int[][] DIRS = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    private boolean[][] reserved;
    private Random random = new Random();

    public void reserve(Position p) {
        reserved[p.getRow()][p.getCol()] = true;
    }

    public void beginLevel(Maze maze) {
        reserved = new boolean[maze.getRows()][maze.getCols()];
        reserve(maze.getStart());
        reserve(maze.getGoal());
    }

    public <T extends Collectible> List<T> place(Maze maze, int count, Function<Position, T> factory) {
        List<Position> free = new ArrayList<>();
        for (int r = 0; r < maze.getRows(); r++) {
            for (int c = 0; c < maze.getCols(); c++) {
                if (maze.isWalkable(r, c) && !reserved[r][c]) free.add(new Position(r, c));
            }
        }
        Collections.shuffle(free, random);
        List<T> placed = new ArrayList<>();
        for (Position p : free.subList(0, count)) {
            reserve(p);
            placed.add(factory.apply(p));
        }
        return placed;
    }

    public List<PatrolEnemy> place(Maze maze, List<Position> shortestPath, int desiredCount) {
        List<PatrolEnemy> result = new ArrayList<>();
        boolean[][] onPath = new boolean[maze.getRows()][maze.getCols()];
        for (Position p : shortestPath) onPath[p.getRow()][p.getCol()] = true;

        List<Position> cells = new ArrayList<>(shortestPath);
        Collections.shuffle(cells, random);

        for (Position mainCell : cells) {
            if (result.size() >= desiredCount) break;
            if (mainCell.distanceTo(maze.getStart()) < 4) continue;
            if (mainCell.distanceTo(maze.getGoal()) < 4) continue;

            boolean tooClose = false;
            for (PatrolEnemy existing : result) {
                if (existing.getEndA().distanceTo(mainCell) < 6) tooClose = true;
            }
            if (tooClose) continue;

            for (int[] d : DIRS) {
                int r = mainCell.getRow() + d[0];
                int c = mainCell.getCol() + d[1];
                if (maze.isWalkable(r, c) && !onPath[r][c]) {
                    Position branchCell = new Position(r, c);
                    result.add(new PatrolEnemy(mainCell, branchCell));
                    reserve(mainCell);
                    reserve(branchCell);
                    break;
                }
            }
        }
        return result;
    }

    public List<Entity> place(List<Position> shortestPath, int desiredCount) {
        List<Entity> result = new ArrayList<>();
        int regionSize = shortestPath.size() / (desiredCount + 1);
        for (int k = 1; k <= desiredCount; k++) {
            int idx = k * regionSize;
            Position enemyCell = shortestPath.get(idx);
            Position bombCell = shortestPath.get(idx - 5);
            result.add(new StaticEnemy(enemyCell));
            result.add(new Bomb(bombCell));
            reserve(enemyCell);
            reserve(bombCell);
        }
        return result;
    }

}
