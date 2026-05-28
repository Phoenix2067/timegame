package com.rewinder;

import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;

import java.util.List;
import java.util.Set;

/**
 * Player movement, jump, collision, and animation.
 */
public class Player extends GameObject {
    private final ImageView view;
    private final PlayerSprites sprites;

    public double vx = 0;
    public double vy = 0;
    public double velocityY = 0;
    private boolean canJump = false;
    private int runFrameIndex = 0;
    private int animationCounter = 0;
    private boolean facingRight = true;

    /**
     * Creates a player with default spawn position.
     *
     * @param sprites the player sprite set used for animation
     */
    public Player(PlayerSprites sprites) {
        super(50, 450, 40, 55);
        this.sprites = sprites;
        view = new ImageView(sprites.idleFrame);
        view.setFitWidth(40);
        view.setFitHeight(55);
        respawn();
    }

    /**
     * Updates player movement, gravity, and collision with platforms.
     *
     * @param keys      the set of currently pressed keys
     * @param platforms the list of platforms to check collisions against
     * @param gravity   the gravity value applied each frame
     */
    public void update(Set<KeyCode> keys, List<Rectangle> platforms, double gravity) {
        boolean aPressed = keys.contains(KeyCode.A) || keys.contains(KeyCode.LEFT);
        boolean dPressed = keys.contains(KeyCode.D) || keys.contains(KeyCode.RIGHT);

        vx = 0;
        if (aPressed) {
            view.setX(view.getX() - 5);
            facingRight = false;
            vx = -5;
        }
        if (dPressed) {
            view.setX(view.getX() + 5);
            facingRight = true;
            vx = 5;
        }

        x = view.getX();

        if ((keys.contains(KeyCode.W) || keys.contains(KeyCode.SPACE) || keys.contains(KeyCode.UP)) && canJump) {
            velocityY = -12;
            canJump = false;
        }

        velocityY += gravity;
        vy = velocityY;
        view.setY(view.getY() + velocityY);
        y = view.getY();

        canJump = false;
        for (Rectangle platform : platforms) {
            if (velocityY > 0 && view.getBoundsInParent().intersects(platform.getBoundsInParent())) {
                if (view.getY() + view.getFitHeight() < platform.getY() + 15) {
                    view.setY(platform.getY() - view.getFitHeight());
                    y = view.getY();
                    velocityY = 0;
                    vy = 0;
                    canJump = true;
                }
            }
        }

        updateAnimation(aPressed, dPressed);
    }

    private void updateAnimation(boolean aPressed, boolean dPressed) {
        if (!canJump) {
            view.setImage(sprites.jumpFrame);
        } else if (aPressed || dPressed) {
            animationCounter++;
            if (animationCounter >= 8) {
                animationCounter = 0;
                runFrameIndex++;
                if (runFrameIndex >= sprites.runFrames.length)
                    runFrameIndex = 0;
                view.setImage(sprites.runFrames[runFrameIndex]);
            }
        } else {
            view.setImage(sprites.idleFrame);
            runFrameIndex = 0;
        }

        if (facingRight)
            view.setScaleX(1);
        else
            view.setScaleX(-1);
    }

    /**
     * Respawns the player at the default starting position.
     */
    public void respawn() {
        view.setX(50);
        view.setY(450);
        x = 50;
        y = 450;
        velocityY = 0;
        vy = 0;
        vx = 0;
        canJump = false;
    }

    /**
     * Respawns the player at the specified position.
     *
     * @param spawnX the x coordinate to respawn at
     * @param spawnY the y coordinate to respawn at
     */
    public void respawn(double spawnX, double spawnY) {
        view.setX(spawnX);
        view.setY(spawnY);
        x = spawnX;
        y = spawnY;
        velocityY = 0;
        vy = 0;
        vx = 0;
        canJump = false;
    }

    /**
     * @return the player's current x coordinate
     */
    public double getX() {
        return view.getX();
    }

    /**
     * @return the player's current y coordinate
     */
    public double getY() {
        return view.getY();
    }

    /**
     * Sets the player's x coordinate.
     *
     * @param x the new x coordinate
     */
    public void setX(double x) {
        view.setX(x);
        this.x = x;
    }

    /**
     * Sets the player's y coordinate.
     *
     * @param y the new y coordinate
     */
    public void setY(double y) {
        view.setY(y);
        this.y = y;
    }

    /**
     * @return the player's ImageView node
     */
    public ImageView getView() {
        return view;
    }

    /**
     * @return true if the player has fallen below the world bounds
     */
    public boolean fellOutOfWorld() {
        return view.getY() > 800;
    }

    /**
     * Checks whether the player intersects a fire obstacle.
     *
     * @param fire the fire to test collision against
     * @return true if the player intersects the fire
     */
    public boolean intersects(Fire fire) {
        if (fire.rect == null)
            return false;
        return view.getBoundsInParent().intersects(fire.rect.getBoundsInParent());
    }

    /**
     * Checks whether the player intersects an enemy.
     *
     * @param enemy the enemy to test collision against
     * @return true if the player intersects the enemy
     */
    public boolean intersects(Enemy enemy) {
        return view.getBoundsInParent().intersects(enemy.x, enemy.y, enemy.width, enemy.height);
    }

}
