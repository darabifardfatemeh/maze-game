package com.maze.entity;

import com.maze.controller.GameController;
import com.maze.model.Position;


public abstract class Collectible extends Entity {

    public Collectible(Position position) {
        super(position);
    }

    public abstract void onCollect(GameController controller);
}
