package com.rewinder;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Solid platform.
 */
public class Platform extends GameObject {
    public Platform(double x, double y, double width, double height) {
        super(x, y, width, height);
    }

    @Override
    public void draw(GraphicsContext graphics) {
        graphics.setFill(Color.SADDLEBROWN);
        graphics.fillRect(x, y, width, height);
        graphics.setFill(Color.TAN);
        graphics.fillRect(x, y, width, 7);
    }
}
