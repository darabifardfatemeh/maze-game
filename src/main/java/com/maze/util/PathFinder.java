package com.maze.util;

import com.maze.model.Maze;
import com.maze.model.Position;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

public class PathFinder {

    private static int[][] DIRS = { {1,0},{-1,0},{0,1},{0,-1} };

    public static int[][] distanceMap(Maze maze, Position start) {
        int[][] dist = new int[maze.getRows()][maze.getCols()];
        for (int[] row : dist) Arrays.fill(row, -1);
        Queue<Position> q = new ArrayDeque<>();
        dist[start.getRow()][start.getCol()] = 0;
        q.add(start);

        while (!q.isEmpty()) {
            Position p = q.poll();
            int d = dist[p.getRow()][p.getCol()];
            for (int[] dir : DIRS) {
                int r = p.getRow() + dir[0];
                int c = p.getCol() + dir[1];
                if (maze.isWalkable(r, c) && dist[r][c] == -1) {
                    dist[r][c] = d + 1;
                    q.add(new Position(r, c));
                }
            }
        }
        return dist;
    }

    public static List<Position> shortestPath(Maze maze, Position start, Position goal) {
        int[][] dist = distanceMap(maze, start);
        List<Position> path = new ArrayList<>();
        Position curr = goal;
        path.add(curr);
        for (int d = dist[goal.getRow()][goal.getCol()]; d > 0; d--) {
            for (int[] dir : DIRS) {
                int r = curr.getRow() + dir[0];
                int c = curr.getCol() + dir[1];
                if (maze.isWalkable(r, c) && dist[r][c] == d - 1) {
                    curr = new Position(r, c);
                    break;
                }
            }
            path.add(curr);
        }
        Collections.reverse(path);
        return path;
    }
}
