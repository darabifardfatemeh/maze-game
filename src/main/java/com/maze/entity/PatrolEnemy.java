package com.maze.entity;

import com.maze.controller.GameController;
import com.maze.exceptions.LethalCollisionException;
import com.maze.model.Position;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class PatrolEnemy extends Enemy {
    private Position endA;
    private Position endB;

    public PatrolEnemy(Position endA, Position endB) {
        super(endB);
        this.endA = endA;
        this.endB = endB;
    }

    public void tick() {
        setPosition(getPosition().isSame(endA) ? endB : endA);
    }

    @Override
    public void onPlayerContact(GameController controller) {
        throw new LethalCollisionException("You died :( ");
    }

    public Position getEndA() {
        return endA;
    }


    @Override
    public void draw(GraphicsContext g, double cellSize) {
        if (!getActive()) return;
        double x = getPosition().getCol() * cellSize;
        double y = getPosition().getRow() * cellSize;
        double padding = cellSize * 0.10;
        g.setFill(Color.CRIMSON);
        g.fillRect(x + padding, y + padding,
                cellSize - 2 * padding, cellSize - 2 * padding);
        g.setFill(Color.WHITE);
        double eyeSize = cellSize * 0.18;
        double eyeY = y + cellSize * 0.30;
        g.fillOval(x + cellSize * 0.25, eyeY, eyeSize, eyeSize);
        g.fillOval(x + cellSize * 0.55, eyeY, eyeSize, eyeSize);
        g.setFill(Color.BLACK);
        double pupilSize = cellSize * 0.08;
        g.fillOval(x + cellSize * 0.30, eyeY + cellSize * 0.05, pupilSize, pupilSize);
        g.fillOval(x + cellSize * 0.60, eyeY + cellSize * 0.05, pupilSize, pupilSize);
    }
}
