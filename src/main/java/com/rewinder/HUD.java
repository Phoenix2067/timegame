package com.rewinder;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Draws game HUD.
 */
public class HUD {
    public void draw(GraphicsContext graphics, int levelNumber, String levelName, TimeManager timeManager) {
        graphics.setFill(Color.WHITE);
        graphics.fillText("A/D or Arrows: Move   SPACE/W/UP: Jump   SHIFT: Slow   E: Fast   R: Rewind   P/ESC: Pause   1-4: Levels", 20, 25);
        graphics.fillText("Level " + levelNumber + ": " + levelName, 20, 50);
        graphics.fillText("Mode: " + timeManager.getMode(), 20, 75);

        graphics.setFill(Color.DARKGRAY);
        graphics.fillRect(20, 90, 200, 20);

        if (timeManager.getMode() == TimeManager.TimeMode.SLOW) {
            graphics.setFill(Color.DODGERBLUE);
        } else if (timeManager.getMode() == TimeManager.TimeMode.FAST) {
            graphics.setFill(Color.ORANGE);
        } else {
            graphics.setFill(Color.LIMEGREEN);
        }

        double width = 200 * (timeManager.getEnergy() / timeManager.getMaxEnergy());
        graphics.fillRect(20, 90, width, 20);

        graphics.setStroke(Color.WHITE);
        graphics.strokeRect(20, 90, 200, 20);
        graphics.fillText("Time Energy", 230, 105);
    }
}
