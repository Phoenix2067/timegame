package com.rewinder;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Fire obstacle that fades out with time.
 * Supports both Pane-based Launcher and Canvas-based Main game systems.
 */
public class Fire extends GameObject {
    public Rectangle rect; // For Pane-based launcher
    public double life;
    public double burnTime;
    public boolean active = true;

    // Canvas-based constructor
    public Fire(double x, double y, double width, double height, double burnTime) {
        super(x, y, width, height);
        this.burnTime = burnTime;
        this.life = burnTime;
    }

    // Pane-based constructor
    public Fire(double x, double y, double width, double height, Pane world) {
        super(x, y, width, height);
        this.burnTime = 400; // Original default burnTime/life
        this.life = 400;
        rect = new Rectangle(x, y, width, height);
        rect.setFill(Color.ORANGERED);
        world.getChildren().add(rect);
    }

    public void update(double timeScale) {
        if (!active) return;

        life -= timeScale;

        if (life <= 0) {
            active = false;
            life = 0;
            if (rect != null) rect.setOpacity(0.1);
        } else {
            if (rect != null) rect.setOpacity(life / burnTime);
        }
    }

    public void restore(double life, boolean active) {
        this.life = life;
        this.active = active;
        if (rect != null) rect.setOpacity(active ? life / burnTime : 0.1);
    }

    @Override
    public void draw(GraphicsContext graphics) {
        if (!active) {
            graphics.setGlobalAlpha(0.1);
        } else {
            graphics.setGlobalAlpha(Math.max(0.1, life / burnTime));
        }

        graphics.setFill(Color.rgb(255, 69, 0)); // OrangeRed
        graphics.fillRect(x, y, width, height);

        if (active) {
            graphics.setFill(Color.rgb(255, 165, 0)); // Orange
            graphics.fillRect(x + 5, y + 10, width - 10, height - 10);
            graphics.setFill(Color.YELLOW);
            graphics.fillRect(x + 12, y + 25, width - 24, height - 25);
        }

        graphics.setGlobalAlpha(1.0);
    }
}
