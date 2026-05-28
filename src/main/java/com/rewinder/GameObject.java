package com.rewinder;

/**
 * Base class for rectangular game objects.
 */
public abstract class GameObject {
    public double x;
    public double y;
    public double width;
    public double height;

    public GameObject(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean intersects(GameObject other) {
        return x < other.x + other.width &&
                x + width > other.x &&
                y < other.y + other.height &&
                y + height > other.y;
    }

}
