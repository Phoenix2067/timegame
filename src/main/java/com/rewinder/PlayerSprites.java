package com.rewinder;

import javafx.scene.image.Image;

/**
 * Stores player animation images.
 */
public class PlayerSprites {
    /** Idle frame. */
    public final Image idle;

    /** Jump frame. */
    public final Image jump;

    /** Running animation frames. */
    public final Image[] runFrames;

    /**
     * Creates a player sprite container.
     *
     * @param idle idle image
     * @param jump jump image
     * @param runFrames running frames
     */
    public PlayerSprites(Image idle, Image jump, Image[] runFrames) {
        this.idle = idle;
        this.jump = jump;
        this.runFrames = runFrames;
    }
}
