package com.rewinder;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

import java.io.InputStream;

/**
 * Loads game images from resources.
 *
 * <p>If an image is missing, this class returns a blank placeholder image
 * so the game does not crash while assets are still being added.</p>
 */
public class AssetLoader {

    /**
     * Loads an image from the resources folder.
     *
     * @param path resource path, such as "/assets/idle.png"
     * @return loaded image, or a placeholder if missing
     */
    public Image loadImageOrPlaceholder(String path) {
        InputStream stream = getClass().getResourceAsStream(path);

        if (stream == null) {
            return new WritableImage(40, 55);
        }

        return new Image(stream);
    }

    /**
     * Loads all player animation frames.
     *
     * @return player sprite set
     */
    public PlayerSprites loadPlayerSprites() {
        Image idle = loadImageOrPlaceholder("/assets/idle.png");
        Image jump = loadImageOrPlaceholder("/assets/jump.png");

        Image[] run = new Image[] {
                loadImageOrPlaceholder("/assets/run1.png"),
                loadImageOrPlaceholder("/assets/run2.png"),
                loadImageOrPlaceholder("/assets/run3.png"),
                loadImageOrPlaceholder("/assets/run4.png"),
                loadImageOrPlaceholder("/assets/run5.png"),
                loadImageOrPlaceholder("/assets/run6.png")
        };

        return new PlayerSprites(idle, jump, run);
    }
}
