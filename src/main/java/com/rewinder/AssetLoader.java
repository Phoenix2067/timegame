package com.rewinder;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

import java.io.InputStream;

/**
 * Loads images like the player.
 */
public class AssetLoader {
    public Image loadImageOrPlaceholder(String path) {
        InputStream stream = getClass().getResourceAsStream(path);
        if (stream == null) return new WritableImage(40, 55);
        return new Image(stream);
    }

    public PlayerSprites loadPlayerSprites() {
        Image idle = loadImageOrPlaceholder("/assets/idle.png");
        Image jump = loadImageOrPlaceholder("/assets/jump.png");

        Image[] runFrames = new Image[] {
                loadImageOrPlaceholder("/assets/run1.png"),
                loadImageOrPlaceholder("/assets/run2.png"),
                loadImageOrPlaceholder("/assets/run3.png"),
                loadImageOrPlaceholder("/assets/run4.png"),
                loadImageOrPlaceholder("/assets/run5.png"),
                loadImageOrPlaceholder("/assets/run6.png")
        };

        return new PlayerSprites(idle, jump, runFrames);
    }
}
