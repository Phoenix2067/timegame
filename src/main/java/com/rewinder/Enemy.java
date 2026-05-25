package com.rewinder;

import javafx.scene.canvas.GraphicsContext;

/**
 * Base enemy class.
 */
public abstract class Enemy extends GameObject {
    public Enemy(double x, double y, double width, double height) {
        super(x, y, width, height);
    }

    public abstract void update(double delta, GameWorld game);

    @Override
    public abstract void draw(GraphicsContext graphics);
}
