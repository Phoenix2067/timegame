package com.rewinder;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.Set;

/**
 * Player character with movement, jumping, collision, and animation state.
 */
public class Player extends GameObject {
    public double vx = 0;
    public double vy = 0;

    private boolean onGround = false;
    private final PlayerSprites sprites;
    private int runFrameIndex = 0;
    private int animationCounter = 0;
    private double facing = 1;

    public Player(double x, double y, PlayerSprites sprites) {
        super(x, y, 40, 55);
        this.sprites = sprites;
    }

    /**
     * Updates player movement using normal, unscaled time.
     *
     * @param delta seconds since last frame
     * @param keys current keys
     * @param platforms platforms
     */
    public void update(double delta, Set<KeyCode> keys, List<Platform> platforms) {
        double speed = 280;
        double gravity = 1100;

        vx = 0;

        if (keys.contains(KeyCode.A) || keys.contains(KeyCode.LEFT)) {
            vx = -speed;
            facing = -1;
        }

        if (keys.contains(KeyCode.D) || keys.contains(KeyCode.RIGHT)) {
            vx = speed;
            facing = 1;
        }

        if ((keys.contains(KeyCode.SPACE) || keys.contains(KeyCode.W) || keys.contains(KeyCode.UP)) && onGround) {
            vy = -520;
            onGround = false;
        }

        vy += gravity * delta;

        x += vx * delta;
        for (Platform platform : platforms) {
            if (intersects(platform)) {
                if (vx > 0) x = platform.x - width;
                if (vx < 0) x = platform.x + platform.width;
                vx = 0;
            }
        }

        y += vy * delta;
        onGround = false;

        for (Platform platform : platforms) {
            if (intersects(platform)) {
                if (vy > 0) {
                    y = platform.y - height;
                    vy = 0;
                    onGround = true;
                } else if (vy < 0) {
                    y = platform.y + platform.height;
                    vy = 0;
                }
            }
        }

        updateAnimation(keys);
    }

    private void updateAnimation(Set<KeyCode> keys) {
        boolean moving = keys.contains(KeyCode.A) || keys.contains(KeyCode.D) ||
                keys.contains(KeyCode.LEFT) || keys.contains(KeyCode.RIGHT);

        if (!onGround) {
            // jump frame
        } else if (moving) {
            animationCounter++;
            if (animationCounter >= 8) {
                animationCounter = 0;
                runFrameIndex++;
                if (runFrameIndex >= sprites.runFrames.length) {
                    runFrameIndex = 0;
                }
            }
        } else {
            runFrameIndex = 0;
        }
    }

    public void respawn(double spawnX, double spawnY) {
        x = spawnX;
        y = spawnY;
        vx = 0;
        vy = 0;
    }

    public double getFacing() {
        return facing;
    }

    @Override
    public void draw(GraphicsContext graphics) {
        boolean hasRealImage = sprites.idle.getWidth() > 1 && sprites.idle.getHeight() > 1;

        if (!hasRealImage) {
            graphics.setFill(Color.DEEPSKYBLUE);
            graphics.fillRoundRect(x, y, width, height, 10, 10);
            graphics.setStroke(Color.WHITE);
            graphics.strokeRoundRect(x, y, width, height, 10, 10);
            graphics.setFill(Color.BLACK);
            graphics.fillOval(x + 22, y + 14, 6, 6);
            return;
        }

        if (!onGround) {
            graphics.drawImage(sprites.jump, x, y, width, height);
        } else if (Math.abs(vx) > 0) {
            graphics.save();
            if (facing < 0) {
                graphics.translate(x + width, y);
                graphics.scale(-1, 1);
                graphics.drawImage(sprites.runFrames[runFrameIndex], 0, 0, width, height);
            } else {
                graphics.drawImage(sprites.runFrames[runFrameIndex], x, y, width, height);
            }
            graphics.restore();
        } else {
            graphics.drawImage(sprites.idle, x, y, width, height);
        }
    }
}
