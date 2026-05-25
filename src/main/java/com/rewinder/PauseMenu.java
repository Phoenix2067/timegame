package com.rewinder;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

/**
 * Pause/how-to-play menu overlay.
 *
 * <p>The menu positions itself based on the current window size.</p>
 */
public class PauseMenu {
    public void draw(GraphicsContext graphics, double width, double height, boolean startScreen) {
        graphics.setFill(Color.rgb(0, 0, 0, 0.82));
        graphics.fillRect(0, 0, width, height);

        double centerX = width / 2.0;
        double top = Math.max(45, height * 0.12);

        graphics.setTextAlign(TextAlignment.CENTER);

        graphics.setFill(Color.WHITE);
        graphics.setFont(Font.font("Arial", Math.max(26, width * 0.035)));
        graphics.fillText("REWINDER", centerX, top);

        graphics.setFont(Font.font("Arial", Math.max(15, width * 0.018)));

        double y = top + 55;
        double gap = Math.max(24, height * 0.045);

        graphics.fillText("HOW TO PLAY", centerX, y);
        y += gap * 1.4;

        graphics.fillText("A / D or Arrow Keys  - Move", centerX, y); y += gap;
        graphics.fillText("W / SPACE / UP       - Jump", centerX, y); y += gap;
        graphics.fillText("SHIFT                - Slow time", centerX, y); y += gap;
        graphics.fillText("E                    - Fast time", centerX, y); y += gap;
        graphics.fillText("Hold R               - Rewind your position and world state", centerX, y); y += gap;
        graphics.fillText("1 / 2 / 3 / 4         - Switch difficulty levels", centerX, y); y += gap;
        graphics.fillText("P or ESC             - Pause / resume", centerX, y); y += gap;
        graphics.fillText("F11                  - Fullscreen toggle", centerX, y); y += gap * 1.5;

        graphics.fillText("Goal: dodge enemies, burn out fire using fast time,", centerX, y); y += gap;
        graphics.fillText("and use rewind if you make a mistake.", centerX, y); y += gap * 1.7;

        if (startScreen) {
            graphics.fillText("Press ENTER to start", centerX, Math.min(height - 40, y));
        } else {
            graphics.fillText("Press ENTER, P, or ESC to resume", centerX, Math.min(height - 40, y));
        }

        graphics.setTextAlign(TextAlignment.LEFT);
    }
}
