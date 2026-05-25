package com.rewinder;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

/**
 * Pause and how-to-play menu.
 * Supports both Pane-based Launcher and Canvas-based Main game systems.
 */
public class PauseMenu extends Pane {
    private final Label menuText = new Label();

    public PauseMenu() {
        setPrefSize(1000, 600);
        setStyle("-fx-background-color: rgba(0,0,0,0.82);");

        menuText.setText(
                "REWINDER\n\n" +
                "HOW TO PLAY\n\n" +
                "A / D  - Move\n" +
                "W or SPACE  - Jump\n" +
                "Q  - Normal time\n" +
                "SHIFT  - Slow time\n" +
                "E  - Fast forward time\n" +
                "Hold R  - Rewind\n" +
                "1  - Easy level\n" +
                "2  - Medium level\n" +
                "3  - Hard level\n" +
                "4  - Ultra hard level\n" +
                "P or ESC  - Pause / menu\n" +
                "ENTER  - Start / resume\n\n" +
                "Use slow time to dodge bullets, fast time to burn out fires,\n" +
                "and rewind if you make a mistake."
        );

        menuText.setStyle("-fx-font-size: 23px; -fx-text-fill: white; -fx-font-family: Arial; -fx-padding: 30; -fx-background-color: rgba(20,20,20,0.9); -fx-border-color: white; -fx-border-width: 2;");
        menuText.setLayoutX(170);
        menuText.setLayoutY(40);

        getChildren().add(menuText);
    }

    public void draw(GraphicsContext graphics, double screenWidth, double screenHeight, boolean startScreen) {
        // Transparent black backing overlay
        graphics.setFill(Color.color(0, 0, 0, 0.75));
        graphics.fillRect(0, 0, screenWidth, screenHeight);

        // Center card window sizes
        double boxWidth = Math.min(680, screenWidth - 40);
        double boxHeight = Math.min(460, screenHeight - 40);
        double boxX = (screenWidth - boxWidth) / 2;
        double boxY = (screenHeight - boxHeight) / 2;

        // Dark slate futuristic translucent main box
        graphics.setFill(Color.color(0.06, 0.08, 0.12, 0.92));
        graphics.fillRoundRect(boxX, boxY, boxWidth, boxHeight, 16, 16);

        // Border highlighting
        graphics.setStroke(Color.rgb(0, 191, 255)); // Deep Sky Blue border
        graphics.setLineWidth(2.5);
        graphics.strokeRoundRect(boxX, boxY, boxWidth, boxHeight, 16, 16);

        // Render Title
        graphics.setTextAlign(TextAlignment.CENTER);
        graphics.setFill(Color.rgb(255, 215, 0)); // Golden title
        graphics.setFont(Font.font("Outfit", 32)); // Sized down slightly for safety

        String title = startScreen ? "REWINDER" : "GAME PAUSED";
        graphics.fillText(title, screenWidth / 2, boxY + 40);

        // Subtitle
        graphics.setFill(Color.rgb(220, 220, 220));
        graphics.setFont(Font.font("Inter", 15));
        graphics.fillText("CONTROLS & HOW TO PLAY", screenWidth / 2, boxY + 65);

        // Controls layout - left-aligned side-by-side columns to prevent overlaps!
        double keyColX = boxX + 40;
        double descColX = boxX + 250;
        double currentY = boxY + 105;
        double lineSpacing = 24;

        String[][] controls = {
            {"A / D or Left/Right Arrow", "Move left / right"},
            {"W / SPACE / Up Arrow", "Jump"},
            {"Q", "Normal time speed"},
            {"SHIFT (Hold)", "Slow down time (Uses Energy)"},
            {"E (Hold)", "Fast forward time (Uses Energy)"},
            {"R (Hold)", "Rewind time backward"},
            {"P / ESC", "Pause / Unpause game"},
            {"1, 2, 3, 4", "Switch levels (1 to 4)"},
            {"ENTER", "Start / Resume Game"}
        };

        graphics.setFont(Font.font("Consolas", 14));
        graphics.setTextAlign(TextAlignment.LEFT);

        for (String[] control : controls) {
            // Draw Key in cyan
            graphics.setFill(Color.rgb(0, 255, 200));
            graphics.fillText(control[0], keyColX, currentY);

            // Draw Description in white
            graphics.setFill(Color.WHITE);
            graphics.fillText(control[1], descColX, currentY);

            currentY += lineSpacing;
        }

        // Draw Tip at the bottom
        graphics.setTextAlign(TextAlignment.CENTER);
        graphics.setFont(Font.font("Inter", 13));
        graphics.setFill(Color.rgb(255, 120, 120)); // Soft red/pink
        graphics.fillText("TIP: Use Slow to dodge, Fast to decay fires, and Rewind if you fail!",
                screenWidth / 2, boxY + boxHeight - 50);

        graphics.setFill(Color.rgb(0, 255, 200));
        graphics.setFont(Font.font("Inter", 15));
        graphics.fillText("Press ENTER to Begin / Resume playing", screenWidth / 2, boxY + boxHeight - 22);
    }
}
