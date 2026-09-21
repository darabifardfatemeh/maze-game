package com.maze.model;


public class Player {

    private Position position;
    private int bombs;
    private int stars;

    public Player(Position start) {
        this.position = start;
        this.bombs = 0;
        this.stars = 0;
    }

    public Position getPosition() {
        return position;
    }

    public void changePosition(int row, int col) {
        position.setRow(row);
        position.setCol(col);
    }

    public int getBombs() {
        return bombs;
    }

    public void addBomb() {
        this.bombs++;
    }

    public boolean useBomb() {
        if (bombs <= 0) return false;
        bombs--;
        return true;
    }

    public int getStars() {
        return stars;
    }

    public void addStar() {
        this.stars++;
    }


    public boolean useStar() {
        if (stars <= 0) return false;
        stars--;
        return true;
    }
}
