package com.rewinder;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores the game state snapshot used for rewinding.
 */
public class WorldState {
    public double playerX;
    public double playerY;
    public double playerVelocityY;

    public List<Vector2> bulletPositions = new ArrayList<>();
    public List<Double> fireLives = new ArrayList<>();
    public List<Boolean> fireActive = new ArrayList<>();
    public List<Vector2> swordPositions = new ArrayList<>();

    /**
     * Creates a new rewind state record.
     *
     * @param playerX         the player's x position
     * @param playerY         the player's y position
     * @param playerVelocityY the player's vertical velocity
     */
    public WorldState(double playerX, double playerY, double playerVelocityY) {
        this.playerX = playerX;
        this.playerY = playerY;
        this.playerVelocityY = playerVelocityY;
    }
}
