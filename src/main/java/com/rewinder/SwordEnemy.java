package com.rewinder;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Sword enemy that patrols between two x positions.
 */
public class SwordEnemy extends Enemy {
    public Rectangle body; // For Pane-based launcher
    private Rectangle sword; // For Pane-based launcher

    private final double patrolLeft;
    private final double patrolRight;
    private double direction = 1;
    private double speed = 1.8;

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

}
