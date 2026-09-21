package com.maze.entity;

import com.maze.controller.GameController;
import com.maze.model.Position;


public abstract class Enemy extends Entity {

    public Enemy(Position position) {
        super(position);
    }

    public abstract void onPlayerContact(GameController controller);
}
