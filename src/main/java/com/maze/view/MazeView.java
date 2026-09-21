package com.maze.view;

import com.maze.entity.Entity;
import com.maze.model.Maze;
import com.maze.model.Player;
import com.maze.model.Position;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Collections;
import java.util.List;


public class MazeView extends Canvas {
    private static double DEFAULT_CELL_SIZE = 24.0;

    private Maze maze;
    private Player player;
    private List<Entity> entities = Collections.emptyList();
    private double cellSize = DEFAULT_CELL_SIZE;

    public MazeView(double width, double height) {
        super(width, height);
    }

    public void bind(Maze maze, Player player) {
        this.maze = maze;
        this.player = player;
        resizeToFit();
        draw();
    }

    public void setEntities(List<Entity> entities) {
        this.entities = entities != null ? entities : Collections.emptyList();
    }

    public void draw() {
        GraphicsContext g = getGraphicsContext2D();
        g.setFill(Color.WHITESMOKE);
        g.fillRect(0, 0, getWidth(), getHeight());

        if (maze == null) return;

        // Draw cells
        for (int r = 0; r < maze.getRows(); r++) {
            for (int c = 0; c < maze.getCols(); c++) {
                if (!maze.isWalkable(r, c)) {
                    g.setFill(Color.DARKSLATEGRAY);
                } else {
                    g.setFill(Color.BEIGE);
                }
                g.fillRect(c * cellSize, r * cellSize, cellSize, cellSize);
            }
        }

        // Goal
        Position goal = maze.getGoal();
        g.setFill(Color.LIGHTGREEN);
        g.fillRect(goal.getCol() * cellSize, goal.getRow() * cellSize, cellSize, cellSize);

        for (Entity e : entities) {
            if (e.getActive()) {
                e.draw(g, cellSize);
            }
        }


        if (player != null) {
            Position p = player.getPosition();
            g.setFill(Color.ROYALBLUE);
            double padding = cellSize * 0.15;
            g.fillOval(p.getCol() * cellSize + padding, p.getRow() * cellSize + padding,
                    cellSize - 2 * padding, cellSize - 2 * padding);
        }

        
        g.setStroke(Color.gray(0, 0.2));
        for (int r = 0; r <= maze.getRows(); r++) {
            g.strokeLine(0, r * cellSize, maze.getCols() * cellSize, r * cellSize);
        }
        for (int c = 0; c <= maze.getCols(); c++) {
            g.strokeLine(c * cellSize, 0, c * cellSize, maze.getRows() * cellSize);
        }
    }

    private void resizeToFit() {
        if (maze == null) return;
        double widthNeeded = maze.getCols() * cellSize;
        double heightNeeded = maze.getRows() * cellSize;
        setWidth(widthNeeded);
        setHeight(heightNeeded);
    }
}
