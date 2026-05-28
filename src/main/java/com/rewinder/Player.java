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

    // Pane-based constructor
    public Player(PlayerSprites sprites) {
        super(50, 450, 40, 55);
        this.sprites = sprites;
        view = new ImageView(sprites.idleFrame);
        view.setFitWidth(40);
        view.setFitHeight(55);
        respawn();
    }

    // Pane-based update
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
                if (runFrameIndex >= sprites.runFrames.length) runFrameIndex = 0;
                view.setImage(sprites.runFrames[runFrameIndex]);
            }
        } else {
            view.setImage(sprites.idleFrame);
            runFrameIndex = 0;
        }

        if (facingRight) view.setScaleX(1);
        else view.setScaleX(-1);
    }

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

    public double getX() {
        return view.getX();
    }

    public double getY() {
        return view.getY();
    }

    public void setX(double x) {
        view.setX(x);
        this.x = x;
    }

    public void setY(double y) {
        view.setY(y);
        this.y = y;
    }

    public ImageView getView() {
        return view;
    }

    public boolean fellOutOfWorld() {
        return view.getY() > 800;
    }

    public boolean intersects(Fire fire) {
        if (fire.rect == null) return false;
        return view.getBoundsInParent().intersects(fire.rect.getBoundsInParent());
    }

    public boolean intersects(Enemy enemy) {
        return view.getBoundsInParent().intersects(enemy.x, enemy.y, enemy.width, enemy.height);
    }

    public boolean intersects(Door door) {
        return view.getBoundsInParent().intersects(door.x, door.y, door.width, door.height);
    }

}
