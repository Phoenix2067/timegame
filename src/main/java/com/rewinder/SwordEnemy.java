package com.rewinder;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Sword enemy that patrols between two x positions.
 */
public class SwordEnemy {
    public Rectangle body;
    private Rectangle sword;

    private double patrolLeft;
    private double patrolRight;
    private double direction = 1;
    private double speed = 1.8;

    public SwordEnemy(double x, double y, double patrolLeft, double patrolRight, Pane world) {
        this.patrolLeft = patrolLeft;
        this.patrolRight = patrolRight;

        body = new Rectangle(x, y, 35, 50);
        body.setFill(Color.PURPLE);

        sword = new Rectangle(x + 35, y + 20, 30, 5);
        sword.setFill(Color.LIGHTGRAY);

        world.getChildren().addAll(body, sword);
    }

    public void update(double timeScale) {
        body.setX(body.getX() + speed * direction * timeScale);

        if (body.getX() < patrolLeft) {
            body.setX(patrolLeft);
            direction = 1;
        }

        if (body.getX() > patrolRight) {
            body.setX(patrolRight);
            direction = -1;
        }

        sword.setX(direction > 0 ? body.getX() + 35 : body.getX() - 30);
        sword.setY(body.getY() + 20);
    }

    public void restorePosition(double x, double y) {
        body.setX(x);
        body.setY(y);
        sword.setY(y + 20);
        sword.setX(direction > 0 ? x + 35 : x - 30);
    }
}
