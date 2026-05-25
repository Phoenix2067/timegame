package com.rewinder;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * Draws game HUD.
 */
public class HUD {
    public void draw(GraphicsContext graphics, int levelNumber, String levelName, TimeManager timeManager) {
        graphics.save();

        // Use standard modern fonts
        graphics.setFont(Font.font("Inter", 14));
        graphics.setFill(Color.WHITE);

        // Clean hints instead of overflowing string
        graphics.fillText("ESC / P: Pause & Controls", 20, 25);

        graphics.setFont(Font.font("Outfit", 16));
        graphics.fillText("Level " + levelNumber + ": " + levelName, 20, 50);

        graphics.setFont(Font.font("Inter", 14));
        graphics.fillText("Time Mode: " + timeManager.getMode(), 20, 75);

        // Render Energy Bar with rounded corners
        graphics.setFill(Color.rgb(40, 40, 40, 0.8));
        graphics.fillRoundRect(20, 90, 200, 20, 6, 6);

        if (timeManager.getMode() == TimeManager.TimeMode.SLOW) {
            graphics.setFill(Color.rgb(30, 144, 255)); // Dodge blue
        } else if (timeManager.getMode() == TimeManager.TimeMode.FAST) {
            graphics.setFill(Color.rgb(255, 140, 0)); // Dark orange
        } else {
            graphics.setFill(Color.rgb(50, 205, 50)); // Lime green
        }

        double width = 200 * (timeManager.getEnergy() / timeManager.getMaxEnergy());
        if (width > 0) {
            graphics.fillRoundRect(20, 90, width, 20, 8, 6);
        }

        graphics.setStroke(Color.WHITE);
        graphics.setLineWidth(1.5);
        graphics.strokeRoundRect(20, 90, 200, 20, 8, 6);

        graphics.setFill(Color.WHITE);
        graphics.setFont(Font.font("Inter", 12));
        graphics.fillText("Time Energy", 230, 105);

        graphics.restore();
    }
}
