package rewinder;

import javafx.scene.image.Image;

/**
 * Stores player animation frames.
 */
public class PlayerSprites {
    public final Image idleFrame;
    public final Image jumpFrame;
    public final Image[] runFrames;

    public PlayerSprites(Image idleFrame, Image jumpFrame, Image[] runFrames) {
        this.idleFrame = idleFrame;
        this.jumpFrame = jumpFrame;
        this.runFrames = runFrames;
    }
}
