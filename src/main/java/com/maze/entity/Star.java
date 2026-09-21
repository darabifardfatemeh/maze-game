package com.maze.entity;

import com.maze.controller.GameController;
import com.maze.model.Position;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class Star extends Collectible {

    public Star(Position position) {
        super(position);
    }

    @Override
    public void onCollect(GameController controller) {
        controller.getPlayer().addStar();
        setActive();
    }

    @Override
    public void draw(GraphicsContext g, double cellSize) {
        if (!getActive()) return;
        double cx = getPosition().getCol() * cellSize + cellSize / 2.0;
        double cy = getPosition().getRow() * cellSize + cellSize / 2.0;
        double outerR = cellSize * 0.40;
        double innerR = cellSize * 0.18;


        double[] xs = new double[10];
        double[] ys = new double[10];
        for (int i = 0; i < 10; i++) {
            double r = (i % 2 == 0) ? outerR : innerR;
            double angle = Math.PI / 2 + i * Math.PI / 5;
            xs[i] = cx + r * Math.cos(angle);
            ys[i] = cy - r * Math.sin(angle);
        }
        g.setFill(Color.GOLD);
        g.fillPolygon(xs, ys, 10);
        g.setStroke(Color.DARKGOLDENROD);
        g.setLineWidth(1.0);
        g.strokePolygon(xs, ys, 10);
    }
}
