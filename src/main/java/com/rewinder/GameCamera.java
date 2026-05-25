package com.rewinder;

/**
 * Side-scrolling camera.
 *
 * <p>The camera now accepts the current screen width so resizing the window does
 * not break scrolling.</p>
 */
public class GameCamera {
    private double x;

    /**
     * Creates a camera.
     *
     * @param initialScreenWidth initial visible screen width
     */
    public GameCamera(double initialScreenWidth) {
        this.x = 0;
    }

    /**
     * Follows the player while staying inside the level.
     *
     * @param player player object
     * @param worldWidth level width
     * @param screenWidth current canvas/window width
     */
    public void follow(Player player, double worldWidth, double screenWidth) {
        x = player.x - screenWidth * 0.32;

        if (x < 0) x = 0;
        if (x > worldWidth - screenWidth) x = worldWidth - screenWidth;
        if (x < 0) x = 0;
    }

    /**
     * Gets camera x offset.
     *
     * @return camera x position
     */
    public double getX() {
        return x;
    }

    /**
     * Resets camera.
     */
    public void reset() {
        x = 0;
    }
}
