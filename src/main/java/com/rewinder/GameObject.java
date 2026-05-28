package com.rewinder;

/**
 * Base class for rectangular game objects.
 */
public abstract class GameObject {
    public double x;
    public double y;
    public double width;
    public double height;

    /**
     * Creates a rectangular game object.
     *
     * @param x      the x coordinate
     * @param y      the y coordinate
     * @param width  the width of the object
     * @param height the height of the object
     */
    public GameObject(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Checks whether this object intersects another one.
     *
     * @param other the other game object to check against
     * @return true if the rectangles intersect, false otherwise
     */
    public boolean intersects(GameObject other) {
        return x < other.x + other.width &&
                x + width > other.x &&
                y < other.y + other.height &&
                y + height > other.y;
    }

}
