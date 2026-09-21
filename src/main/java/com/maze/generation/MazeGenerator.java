package com.maze.generation;

import com.maze.model.Maze;


public interface MazeGenerator {
    Maze generate(int rows, int cols);
}
