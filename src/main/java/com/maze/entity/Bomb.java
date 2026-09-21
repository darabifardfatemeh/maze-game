package com.maze.entity;

import com.maze.controller.GameController;
import com.maze.model.Position;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class Bomb extends Collectible {

    public Bomb(Position position) {
        super(position);
    }

    @Override
    public void onCollect(GameController controller) {
        controller.getPlayer().addBomb();
        setActive();
    }

    @Override
    public void draw(GraphicsContext g, double cellSize) {
        if (!getActive()) return;
        double cx = getPosition().getCol() * cellSize + cellSize / 2.0;
        double cy = getPosition().getRow() * cellSize + cellSize / 2.0;
        double r = cellSize * 0.32;


        g.setFill(Color.BLACK);
        g.fillOval(cx - r, cy - r, 2 * r, 2 * r);
        g.setStroke(Color.DARKRED);
        g.setLineWidth(1.5);
        g.strokeOval(cx - r, cy - r, 2 * r, 2 * r);


        g.setStroke(Color.SADDLEBROWN);
        g.setLineWidth(2.0);
        g.strokeLine(cx, cy - r, cx + r * 0.5, cy - r * 1.5);


        g.setFill(Color.ORANGE);
        g.fillOval(cx + r * 0.4, cy - r * 1.6, cellSize * 0.12, cellSize * 0.12);
    }
}
