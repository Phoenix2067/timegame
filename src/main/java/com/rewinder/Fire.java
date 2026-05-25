package com.rewinder;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Fire obstacle that burns out faster in fast time.
 */
public class Fire extends GameObject {
    public double life;
    public final double maxLife;
    public boolean active = true;

    public Fire(double x, double y, double width, double height, double life) {
        super(x, y, width, height);
        this.life = life;
        this.maxLife = life;
    }

    public void update(double delta) {
        if (!active) return;

        life -= delta;

        if (life <= 0) {
            life = 0;
            active = false;
        }
    }

    @Override
    public void draw(GraphicsContext graphics) {
        if (!active) {
            graphics.setFill(Color.GRAY);
            graphics.fillText("Burned out", x - 8, y + 30);
            return;
        }

        double percent = life / maxLife;
        double currentHeight = Math.max(8, height * percent);

        graphics.setFill(Color.ORANGERED);
        graphics.fillOval(x, y + height - currentHeight, width, currentHeight);

        graphics.setFill(Color.YELLOW);
        graphics.fillOval(x + 12, y + height - currentHeight + 12, width - 24, Math.max(8, currentHeight - 15));

        graphics.setFill(Color.WHITE);
        graphics.fillText("Fire", x + 15, y - 8);
    }
}
