package com.maze.generation;

import com.maze.model.Maze;
import com.maze.model.Position;


public abstract class AbstractMazeGenerator implements MazeGenerator {

    @Override
    public Maze generate(int rows, int cols) {
        Position start = new Position(1, 1);
        Position goal = new Position(rows - 2, cols - 2);
        Maze maze = new Maze(rows, cols, start, goal);
        carve(maze);
        return maze;
    }

    public abstract void carve(Maze maze);
}


