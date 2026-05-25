package com.rewinder;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores game state for rewind.
 */
public class WorldState {
    public double playerX;
    public double playerY;
    public double playerVelocityY;

    public List<Vector2> bulletPositions = new ArrayList<>();
    public List<Double> fireLives = new ArrayList<>();
    public List<Boolean> fireActive = new ArrayList<>();
    public List<Vector2> swordPositions = new ArrayList<>();

    public WorldState(double playerX, double playerY, double playerVelocityY) {
        this.playerX = playerX;
        this.playerY = playerY;
        this.playerVelocityY = playerVelocityY;
    }
}
