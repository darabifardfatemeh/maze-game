package com.maze.model;



public class Position {

    private int row;
    private int col;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }
    public int getRow() {
        return row;
    }
    public int getCol() {
        return col;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public boolean isSame(Position other) {
        return row == other.row && col == other.col;
    }

    public int distanceTo(Position other) {
        return Math.abs(row - other.row) + Math.abs(col - other.col);
    }

}
