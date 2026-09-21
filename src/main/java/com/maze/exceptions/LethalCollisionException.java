package com.maze.exceptions;


public class LethalCollisionException extends RuntimeException {
    public LethalCollisionException(String message) {
        super(message);
    }
}
