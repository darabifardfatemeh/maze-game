package com.maze.entity;

import com.maze.controller.GameController;
import com.maze.exceptions.LethalCollisionException;
import com.maze.model.Position;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class StaticEnemy extends Enemy {

    public StaticEnemy(Position position) {
        super(position);
    }

    @Override
    public void onPlayerContact(GameController controller) {

        if (controller.getPlayer().useBomb()) {
            setActive();
        } else {
            throw new LethalCollisionException(" You died :( ");
        }
    }

    @Override
    public void draw(GraphicsContext g, double cellSize) {
        if (!getActive()) return;
        double x = getPosition().getCol() * cellSize;
        double y = getPosition().getRow() * cellSize;
        double padding = cellSize * 0.12;


        g.setFill(Color.DARKMAGENTA);
        g.fillOval(x + padding, y + padding,
                cellSize - 2 * padding, cellSize - 2 * padding);


        g.setFill(Color.BLACK);
        double eyeSize = cellSize * 0.16;
        g.fillOval(x + cellSize * 0.28, y + cellSize * 0.30, eyeSize, eyeSize);
        g.fillOval(x + cellSize * 0.56, y + cellSize * 0.30, eyeSize, eyeSize);


        g.setStroke(Color.BLACK);
        g.setLineWidth(2.0);
        double mx = x + cellSize * 0.50;
        double my = y + cellSize * 0.65;
        g.strokeLine(mx - 4, my - 4, mx + 4, my + 4);
        g.strokeLine(mx - 4, my + 4, mx + 4, my - 4);
    }
}
