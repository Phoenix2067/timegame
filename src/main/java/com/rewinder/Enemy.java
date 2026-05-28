package com.rewinder;

/**
 * Base enemy class.
 */
public abstract class Enemy extends GameObject {
    /**
     * Creates a new enemy with the given position and size.
     *
     * @param x      the x coordinate
     * @param y      the y coordinate
     * @param width  the width of the enemy
     * @param height the height of the enemy
     */
    public Enemy(double x, double y, double width, double height) {
        super(x, y, width, height);
    }

}
