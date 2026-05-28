package com.rewinder;

/**
 * Exit door.
 */
public class Door extends GameObject {
    /**
     * Creates a new exit door at the specified position.
     *
     * @param x the x coordinate of the door
     * @param y the y coordinate of the door
     */
    public Door(double x, double y) {
        super(x, y, 60, 90);
    }

}
