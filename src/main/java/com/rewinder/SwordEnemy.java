package com.rewinder;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Patrol sword enemy.
 */
public class SwordEnemy extends Enemy {
    private final double patrolLeft;
    private final double patrolRight;
    private double direction = 1;
    private final double speed = 115;

    public SwordEnemy(double x, double y, double patrolLeft, double patrolRight) {
        super(x, y, 45, 55);
        this.patrolLeft = patrolLeft;
        this.patrolRight = patrolRight;
    }

    @Override
    public void update(double delta, GameWorld game) {
        x += speed * direction * delta;

        if (x < patrolLeft) {
            x = patrolLeft;
            direction = 1;
        }

        if (x > patrolRight) {
            x = patrolRight;
            direction = -1;
        }
    }

    @Override
    public void draw(GraphicsContext graphics) {
        graphics.setFill(Color.PURPLE);
        graphics.fillRoundRect(x, y, width, height, 8, 8);
        graphics.setFill(Color.LIGHTGRAY);

        if (direction > 0) {
            graphics.fillRect(x + 36, y + 20, 30, 5);
        } else {
            graphics.fillRect(x - 21, y + 20, 30, 5);
        }

        graphics.setFill(Color.WHITE);
        graphics.fillText("Sword", x - 2, y - 8);
    }
}
