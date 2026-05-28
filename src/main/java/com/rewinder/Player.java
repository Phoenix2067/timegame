package com.rewinder;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;

import java.util.List;
import java.util.Set;

/**
 * Player movement, jump, collision, and animation.
 * Supports both Pane-based Launcher and Canvas-based Main game systems.
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

    // Canvas-based constructor
    public Player(double x, double y, PlayerSprites sprites) {
        super(x, y, 40, 55);
        this.sprites = sprites;
        view = new ImageView(sprites.idleFrame);
        view.setFitWidth(40);
        view.setFitHeight(55);
        respawn(x, y);
    }

    // Canvas-based update
    public void update(double delta, Set<KeyCode> keys, List<Platform> platforms) {
        boolean aPressed = keys.contains(KeyCode.A) || keys.contains(KeyCode.LEFT);
        boolean dPressed = keys.contains(KeyCode.D) || keys.contains(KeyCode.RIGHT);

        vx = 0;
        if (aPressed) {
            vx = -5 * delta * 60;
            facingRight = false;
        }
        if (dPressed) {
            vx = 5 * delta * 60;
            facingRight = true;
        }

        x += vx;
        view.setX(x);

        if ((keys.contains(KeyCode.W) || keys.contains(KeyCode.SPACE) || keys.contains(KeyCode.UP)) && canJump) {
            vy = -12;
            canJump = false;
        }

        vy += 0.5 * delta * 60;
        y += vy * delta * 60;
        velocityY = vy;
        view.setY(y);

        canJump = false;
        for (Platform platform : platforms) {
            if (vy > 0 && this.intersects(platform)) {
                if (y + height - (vy * delta * 60) <= platform.y + 15) {
                    y = platform.y - height;
                    view.setY(y);
                    vy = 0;
                    velocityY = 0;
                    canJump = true;
                }
            }
        }

        updateAnimation(aPressed, dPressed);
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

    @Override
    public void draw(GraphicsContext graphics) {
        Image img = view.getImage();
        if (facingRight) {
            graphics.drawImage(img, x, y, width, height);
        } else {
            graphics.save();
            graphics.translate(x + width / 2, y + height / 2);
            graphics.scale(-1, 1);
            graphics.drawImage(img, -width / 2, -height / 2, width, height);
            graphics.restore();
        }
    }
}
