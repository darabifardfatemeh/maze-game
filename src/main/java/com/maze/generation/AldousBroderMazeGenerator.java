package com.maze.generation;

import com.maze.model.Maze;

public class AldousBroderMazeGenerator extends AbstractMazeGenerator {

    private int[][] DIRS = {{2, 0}, {-2, 0}, {0, 2}, {0, -2}};

    @Override
    public void carve(Maze maze) {
        int totalCells = ((maze.getRows() - 1) / 2) * ((maze.getCols() - 1) / 2);
        int visitedCells = 1;
        int row = maze.getStart().getRow();
        int col = maze.getStart().getCol();
        maze.setPath(row, col);

        while (visitedCells < totalCells) {
            int[] d = DIRS[(int) (Math.random() * DIRS.length)];
            int nextRow = row + d[0];
            int nextCol = col + d[1];
            if (!maze.isInside(nextRow, nextCol)) continue;

            if (!maze.isWalkable(nextRow, nextCol)) {
                maze.setPath(row + d[0] / 2, col + d[1] / 2);
                maze.setPath(nextRow, nextCol);
                visitedCells++;
            }
            row = nextRow;
            col = nextCol;
        }
    }
}
