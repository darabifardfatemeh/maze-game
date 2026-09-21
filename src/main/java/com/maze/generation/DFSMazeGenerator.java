package com.maze.generation;

import com.maze.model.Maze;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DFSMazeGenerator extends AbstractMazeGenerator {

    private int[][] DIRS = { {2,0}, {-2,0}, {0,2}, {0,-2} };

    @Override
    public void carve(Maze maze) {
        dfsCarve(maze, maze.getStart().getRow(), maze.getStart().getCol());
    }

    public void dfsCarve(Maze maze, int row, int col) {
        maze.setPath(row, col);
        List<int[]> dirs = new ArrayList<>();
        for (int[] d : DIRS) dirs.add(d);
        Collections.shuffle(dirs);
        for (int[] d : dirs) {
            int nextRow = row + d[0];
            int nextCol = col + d[1];
            if (maze.isInside(nextRow, nextCol) && !maze.isWalkable(nextRow, nextCol)) {
                maze.setPath(row + d[0] / 2, col + d[1] / 2);
                dfsCarve(maze, nextRow, nextCol);
            }
        }
    }

}
