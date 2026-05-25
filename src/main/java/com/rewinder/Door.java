package com.rewinder;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Exit door.
 */
public class Door extends GameObject {
    public Door(double x, double y) {
        super(x, y, 60, 90);
    }

    @Override
    public void draw(GraphicsContext graphics) {
        graphics.setFill(Color.LIMEGREEN);
        graphics.fillRoundRect(x, y, width, height, 8, 8);
        graphics.setFill(Color.WHITE);
        graphics.fillText("EXIT", x + 12, y - 8);
    }
}
