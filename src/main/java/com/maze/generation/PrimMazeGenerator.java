package com.maze.generation;

import com.maze.model.Maze;
import com.maze.model.Position;

import java.util.ArrayList;
import java.util.List;

public class PrimMazeGenerator extends AbstractMazeGenerator {

    private int[][] DIRS = {{2, 0}, {-2, 0}, {0, 2}, {0, -2}};

    @Override
    public void carve(Maze maze) {
        List<Position> frontier = new ArrayList<>();
        boolean[][] inFrontier = new boolean[maze.getRows()][maze.getCols()];
        Position start = maze.getStart();
        maze.setPath(start.getRow(), start.getCol());
        addFrontier(maze, start, frontier, inFrontier);

        while (!frontier.isEmpty()) {
            Position cell = frontier.remove((int) (Math.random() * frontier.size()));

            int[] d;
            do {
                d = DIRS[(int) (Math.random() * DIRS.length)];
            } while (!maze.isWalkable(cell.getRow() + d[0], cell.getCol() + d[1]));

            maze.setPath(cell.getRow() + d[0] / 2, cell.getCol() + d[1] / 2);
            maze.setPath(cell.getRow(), cell.getCol());
            addFrontier(maze, cell, frontier, inFrontier);
        }
    }

    public void addFrontier(Maze maze, Position p, List<Position> frontier, boolean[][] inFrontier) {
        for (int[] d : DIRS) {
            int r = p.getRow() + d[0];
            int c = p.getCol() + d[1];
            if (maze.isInside(r, c) && !maze.isWalkable(r, c) && !inFrontier[r][c]) {
                frontier.add(new Position(r, c));
                inFrontier[r][c] = true;
            }
        }
    }
}
