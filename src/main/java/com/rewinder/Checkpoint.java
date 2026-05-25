package com.rewinder;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Checkpoint flag.
 */
public class Checkpoint extends GameObject {
    public Checkpoint(double x, double y) {
        super(x, y - 60, 55, 60);
    }

    @Override
    public void draw(GraphicsContext graphics) {
        graphics.setFill(Color.GOLD);
        graphics.fillRect(x, y, 12, 60);
        graphics.fillPolygon(
                new double[]{x + 12, x + 55, x + 12},
                new double[]{y, y + 15, y + 30},
                3
        );
    }
}
