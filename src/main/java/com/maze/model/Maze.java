package com.maze.model;

public class Maze {

    private int rows;
    private int cols;
    private boolean[][] path;
    private Position start;
    private Position goal;

    public Maze(int rows, int cols, Position start, Position goal) {
        this.rows = rows;
        this.cols = cols;
        this.start = start;
        this.goal = goal;
        this.path = new boolean[rows][cols];
    }
    public int getRows() {
        return rows;
    }
    public int getCols() {
        return cols;
    }
    public Position getStart() {
        return start;
    }
    public Position getGoal() {
        return goal;
    }
    public boolean isInside(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }
    public boolean isWalkable(int row, int col) {
        return isInside(row, col) && path[row][col];
    }
    public void setPath(int row, int col) {
        path[row][col] = true;
    }
}
