package com.rewinder;

import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;

import java.util.List;
import java.util.Set;

/**
 * Player movement, jump, collision, and animation.
 */
public class Player {
    private final ImageView view;
    private final PlayerSprites sprites;

    public double velocityY = 0;
    private boolean canJump = false;
    private int runFrameIndex = 0;
    private int animationCounter = 0;

    public Player(PlayerSprites sprites) {
        this.sprites = sprites;
        view = new ImageView(sprites.idleFrame);
        view.setFitWidth(40);
        view.setFitHeight(55);
        respawn();
    }

    public void update(Set<KeyCode> keys, List<Rectangle> platforms, double gravity) {
        boolean aPressed = keys.contains(KeyCode.A);
        boolean dPressed = keys.contains(KeyCode.D);

        if (aPressed) view.setX(view.getX() - 5);
        if (dPressed) view.setX(view.getX() + 5);

        if ((keys.contains(KeyCode.W) || keys.contains(KeyCode.SPACE)) && canJump) {
            velocityY = -12;
            canJump = false;
        }

        velocityY += gravity;
        view.setY(view.getY() + velocityY);

        canJump = false;
        for (Rectangle platform : platforms) {
            if (velocityY > 0 && view.getBoundsInParent().intersects(platform.getBoundsInParent())) {
                if (view.getY() + view.getFitHeight() < platform.getY() + 15) {
                    view.setY(platform.getY() - view.getFitHeight());
                    velocityY = 0;
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
        }

        if (aPressed) view.setScaleX(-1);
        if (dPressed) view.setScaleX(1);
    }

    public void respawn() {
        view.setX(50);
        view.setY(450);
        velocityY = 0;
    }

    public double getX() {
        return view.getX();
    }

    public double getY() {
        return view.getY();
    }

    public void setX(double x) {
        view.setX(x);
    }

    public void setY(double y) {
        view.setY(y);
    }

    public ImageView getView() {
        return view;
    }

    public boolean fellOutOfWorld() {
        return view.getY() > 800;
    }
}
