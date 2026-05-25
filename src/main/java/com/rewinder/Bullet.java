package com.rewinder;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Bullet projectile.
 */
public class Bullet {
    public Rectangle rect;
    private double vx;
    private double vy;

    public Bullet(double x, double y, double targetX, double targetY, Pane world) {
        rect = new Rectangle(x, y, 10, 10);
        rect.setFill(Color.YELLOW);

        double angle = Math.atan2(targetY - y, targetX - x);
        vx = Math.cos(angle) * 8;
        vy = Math.sin(angle) * 8;

        world.getChildren().add(rect);
    }

    public void update(double timeScale) {
        rect.setX(rect.getX() + vx * timeScale);
        rect.setY(rect.getY() + vy * timeScale);
    }
}
