package com.rewinder;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import java.io.InputStream;

/**
 * Loads image assets for the game.
 */
public class AssetLoader {
    /**
     * Loads an image from the given classpath path, or returns a placeholder if
     * the resource is missing.
     *
     * @param path the classpath resource path for the image
     * @return the loaded Image or a placeholder Image if the file is not found
     */
    public Image loadImageOrPlaceholder(String path) {
        InputStream stream = getClass().getResourceAsStream(path);
        if (stream == null)
            return new WritableImage(40, 55);
        return new Image(stream);
    }

    /**
     * Loads the player sprite set used for idle, jump, and running animation.
     *
     * @return the loaded PlayerSprites object
     */
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
