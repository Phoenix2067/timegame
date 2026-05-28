package com.rewinder;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Fire obstacle that fades out with time.
 */
public class Fire extends GameObject {
    public Rectangle rect;
    public double life;
    public double burnTime;
    public boolean active = true;

    /**
     * Creates a fire obstacle and adds it to the given world.
     *
     * @param x      the x coordinate of the fire
     * @param y      the y coordinate of the fire
     * @param width  the width of the fire hitbox
     * @param height the height of the fire hitbox
     * @param world  the Pane to add the fire rectangle to
     */
    public Fire(double x, double y, double width, double height, Pane world) {
        super(x, y, width, height);
        this.burnTime = 800;
        this.life = 800;
        rect = new Rectangle(x, y, width, height);
        rect.setFill(Color.ORANGERED);
        world.getChildren().add(rect);
    }

    /**
     * Updates the fire lifetime and opacity.
     *
     * @param timeScale the current time scaling factor
     */
    public void update(double timeScale) {
        if (!active)
            return;

        life -= timeScale;

        if (life <= 0) {
            active = false;
            life = 0;
            rect.setOpacity(0.1);
        } else {
            rect.setOpacity(life / 400.0);
        }
    }

    /**
     * Restores the fire state from a rewind snapshot.
     *
     * @param life   the saved life value
     * @param active whether the fire is active
     */
    public void restore(double life, boolean active) {
        this.life = life;
        this.active = active;
        rect.setOpacity(active ? life / 400.0 : 0.1);
    }
}
