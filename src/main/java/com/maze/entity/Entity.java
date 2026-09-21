package com.maze.entity;

import com.maze.model.Position;
import javafx.scene.canvas.GraphicsContext;


public abstract class Entity {

    private Position position;
    private boolean active;

    public Entity(Position position) {
        this.position = position;
        this.active = true;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive() {
        this.active = false;
    }

    public abstract void draw(GraphicsContext g, double cellSize);
}
