package com.rewinder;

import java.util.ArrayList;
import java.util.List;

/**
 * Snapshot used for rewinding.
 */
public class RewindState {
    public double playerX;
    public double playerY;
    public double playerVX;
    public double playerVY;

    public List<Vector2> laserPositions = new ArrayList<>();
    public List<Double> fireLives = new ArrayList<>();
    public List<Boolean> fireActive = new ArrayList<>();

    public RewindState(Player player) {
        playerX = player.x;
        playerY = player.y;
        playerVX = player.vx;
        playerVY = player.vy;
    }
}
