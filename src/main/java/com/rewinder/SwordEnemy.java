package com.rewinder;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Sword enemy that patrols between two x positions.
 * Supports both Pane-based Launcher and Canvas-based Main game systems.
 */
public class SwordEnemy extends Enemy {
    public Rectangle body; // For Pane-based launcher
    private Rectangle sword; // For Pane-based launcher

    private final double patrolLeft;
    private final double patrolRight;
    private double direction = 1;
    private double speed = 1.8;

    // Canvas-based constructor
    public SwordEnemy(double x, double y, double patrolLeft, double patrolRight) {
        super(x, y, 35, 50);
        this.patrolLeft = patrolLeft;
        this.patrolRight = patrolRight;
    }

    // Pane-based constructor
    public SwordEnemy(double x, double y, double patrolLeft, double patrolRight, Pane world) {
        super(x, y, 35, 50);
        this.patrolLeft = patrolLeft;
        this.patrolRight = patrolRight;

        body = new Rectangle(x, y, 35, 50);
        body.setFill(Color.PURPLE);

        sword = new Rectangle(x + 35, y + 20, 30, 5);
        sword.setFill(Color.LIGHTGRAY);

        world.getChildren().addAll(body, sword);
    }

    @Override
    public void update(double delta, GameWorld game) {
        // This is called from the canvas GameWorld
        updatePatrol(delta * 60);
    }

    public void update(double timeScale) {
        // This is called from the Pane-based Launcher
        updatePatrol(timeScale);
        if (body != null) {
            body.setX(x);
            body.setY(y);
        }
        if (sword != null) {
            sword.setX(direction > 0 ? x + 35 : x - 30);
            sword.setY(y + 20);
        }
    }

    private void updatePatrol(double timeScale) {
        x += speed * direction * timeScale;

        if (x < patrolLeft) {
            x = patrolLeft;
            direction = 1;
        }

        if (x > patrolRight) {
            x = patrolRight;
            direction = -1;
        }
    }

    public void restorePosition(double x, double y) {
        this.x = x;
        this.y = y;
        if (body != null) {
            body.setX(x);
            body.setY(y);
        }
        if (sword != null) {
            sword.setY(y + 20);
            sword.setX(direction > 0 ? x + 35 : x - 30);
        }
    }

    @Override
    public void draw(GraphicsContext graphics) {
        // Draw Purple body
        graphics.setFill(Color.PURPLE);
        graphics.fillRect(x, y, width, height);

        // Draw glowing face/eyes for rich details
        graphics.setFill(Color.RED);
        if (direction > 0) {
            graphics.fillRect(x + 20, y + 10, 5, 5);
            graphics.fillRect(x + 28, y + 10, 5, 5);
        } else {
            graphics.fillRect(x + 5, y + 10, 5, 5);
            graphics.fillRect(x + 13, y + 10, 5, 5);
        }

        // Draw light gray sword pointing in direction
        graphics.setFill(Color.LIGHTGRAY);
        if (direction > 0) {
            graphics.fillRect(x + width, y + 20, 30, 5);
        } else {
            graphics.fillRect(x - 30, y + 20, 30, 5);
        }
    }
}
