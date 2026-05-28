package com.rewinder;

import javafx.scene.image.Image;

/**
 * Stores player animation frames.
 */
public class PlayerSprites {
    public final Image idleFrame;
    public final Image jumpFrame;
    public final Image[] runFrames;

    /**
     * Constructs the player sprite set.
     *
     * @param idleFrame the idle frame image
     * @param jumpFrame the jump frame image
     * @param runFrames the run animation frames
     */
    public PlayerSprites(Image idleFrame, Image jumpFrame, Image[] runFrames) {
        this.idleFrame = idleFrame;
        this.jumpFrame = jumpFrame;
        this.runFrames = runFrames;
    }
}
