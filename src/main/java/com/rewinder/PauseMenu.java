package com.rewinder;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

/**
 * Pause and how-to-play menu.
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

}
