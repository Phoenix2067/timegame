package com.rewinder;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * A bullet fired by the player.
 */
public class Bullet {
    public Rectangle rect;
    private double vx;
    private double vy;

    /**
     * Creates a bullet that travels from the spawn position toward the target.
     *
     * @param x       the starting x coordinate
     * @param y       the starting y coordinate
     * @param targetX the target x coordinate
     * @param targetY the target y coordinate
     * @param world   the Pane to add the bullet node to
     */
    public Bullet(double x, double y, double targetX, double targetY, Pane world) {
        rect = new Rectangle(x, y, 10, 10);
        rect.setFill(Color.YELLOW);

        double angle = Math.atan2(targetY - y, targetX - x);
        vx = Math.cos(angle) * 8;
        vy = Math.sin(angle) * 8;

        world.getChildren().add(rect);
    }

    /**
     * Updates the bullet position based on the current time scale.
     *
     * @param timeScale the current time scaling factor
     */
    public void update(double timeScale) {
        rect.setX(rect.getX() + vx * timeScale);
        rect.setY(rect.getY() + vy * timeScale);
    }
}
