package com.rewinder;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Enemy that shoots lasers.
 */
public class LaserEnemy extends Enemy {
    private double shootTimer = 1.4;

    public LaserEnemy(double x, double y) {
        super(x, y, 45, 55);
    }

    @Override
    public void update(double delta, GameWorld game) {
        shootTimer -= delta;

        if (shootTimer <= 0) {
            double direction = game.getPlayer().x < x ? -1 : 1;
            game.addLaser(new Laser(x + width / 2, y + 25, direction));
            shootTimer = 1.4;
        }
    }

    @Override
    public void draw(GraphicsContext graphics) {
        graphics.setFill(Color.CRIMSON);
        graphics.fillRoundRect(x, y, width, height, 8, 8);
        graphics.setFill(Color.WHITE);
        graphics.fillText("Laser", x - 2, y - 8);
    }
}
