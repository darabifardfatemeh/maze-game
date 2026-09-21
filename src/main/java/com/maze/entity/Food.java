package com.maze.entity;

import com.maze.controller.GameController;
import com.maze.model.Position;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Food extends Collectible {

    public Food(Position position) {
        super(position);
    }

    @Override
    public void onCollect(GameController controller) {
        controller.applyFoodTimeBonus();
        setActive();
    }

    @Override
    public void draw(GraphicsContext g, double cellSize) {
        if (!getActive()) return;
        double cx = getPosition().getCol() * cellSize + cellSize / 2.0;
        double cy = getPosition().getRow() * cellSize + cellSize / 2.0;
        double r = cellSize * 0.22;
        g.setFill(Color.rgb(220, 120, 60));
        g.fillOval(cx - r, cy - r, 2 * r, 2 * r);
        g.setStroke(Color.rgb(140, 70, 30));
        g.setLineWidth(1.2);
        g.strokeOval(cx - r, cy - r, 2 * r, 2 * r);
        g.setFill(Color.rgb(90, 160, 70));
        g.fillOval(cx - r * 0.2, cy - r * 1.35, r * 0.55, r * 0.45);
    }
}
