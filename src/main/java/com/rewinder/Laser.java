package com.rewinder;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Laser projectile.
 */
public class Laser extends GameObject {
    private final double direction;
    private final double speed = 450;

    public Laser(double x, double y, double direction) {
        super(x, y, 35, 8);
        this.direction = direction;
    }

    public void update(double delta) {
        x += speed * direction * delta;
    }

    @Override
    public void draw(GraphicsContext graphics) {
        graphics.setFill(Color.RED);
        graphics.fillRoundRect(x, y, width, height, 5, 5);
        graphics.setFill(Color.PINK);
        graphics.fillRect(x + 3, y + 2, width - 6, 3);
    }
}
