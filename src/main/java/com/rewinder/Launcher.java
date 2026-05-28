package com.rewinder;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Main launcher for the game.
 */
public class Launcher extends Application {
    private final List<Rectangle> platforms = new ArrayList<>();
    private final List<Bullet> bullets = new ArrayList<>();
    private final List<Fire> fires = new ArrayList<>();
    private final List<Sniper> snipers = new ArrayList<>();
    private final List<SwordEnemy> swordEnemies = new ArrayList<>();
    private double timeScale = 1.0;
    private boolean isRewinding = false;
    private boolean isPaused = false;
    private boolean showMenu = true;
    private final double GRAVITY = 0.6;
    private boolean isGameFinished = false;
    private int currentLevel = 1;
    private String currentLevelName = "Training Facility";
    private final List<WorldState> history = new ArrayList<>();
    private final int MAX_HISTORY = 600;
    private Player player;
    private final Set<KeyCode> keys = new HashSet<>();
    private Rectangle exitDoor;
    private final Pane gameWorld = new Pane();
    private final Rectangle tintRect = new Rectangle(1000, 600);
    private ImageView backgroundView;
    private final Image[] bgImages = new Image[4];
    private final Label uiLabel = new Label();
    private final PauseMenu pauseMenu = new PauseMenu();

    /**
     * Sets up and starts the JavaFX game window.
     *
     * @param primaryStage the main application stage
     */
    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        root.setStyle("-fx-background-color: black;");

        AssetLoader loader = new AssetLoader();
        bgImages[0] = loader.loadImageOrPlaceholder("/assets/background.png");
        bgImages[1] = loader.loadImageOrPlaceholder("/assets/background1.png");
        bgImages[2] = loader.loadImageOrPlaceholder("/assets/background2.png");
        bgImages[3] = loader.loadImageOrPlaceholder("/assets/background3.png");

        backgroundView = new ImageView();
        backgroundView.setFitWidth(1000);
        backgroundView.setFitHeight(600);

        tintRect.setFill(Color.TRANSPARENT);
        tintRect.setMouseTransparent(true);

        root.getChildren().addAll(backgroundView, gameWorld, tintRect, pauseMenu);

        Scene scene = new Scene(root, 1000, 600, Color.BLACK);

        loadLevel(1);

        scene.setOnKeyPressed(event -> {
            keys.add(event.getCode());

            if (isGameFinished) {
                if (event.getCode() == KeyCode.DIGIT1) {
                    loadLevel(1);
                    isPaused = false;
                }
                return;
            }

            if (event.getCode() == KeyCode.ESCAPE || event.getCode() == KeyCode.P) {
                togglePauseMenu();
                return;
            }

            if (showMenu) {
                if (event.getCode() == KeyCode.ENTER) {
                    showMenu = false;
                    isPaused = false;
                    pauseMenu.setVisible(false);
                }
                return;
            }

            if (isPaused)
                return;

            if (event.getCode() == KeyCode.Q)
                timeScale = 1.0;
            if (event.getCode() == KeyCode.SHIFT)
                timeScale = 0.2;
            if (event.getCode() == KeyCode.E)
                timeScale = 8.0;
            if (event.getCode() == KeyCode.R)
                isRewinding = true;

            updateTint();

            if (event.getCode() == KeyCode.DIGIT1)
                loadLevel(1);
            if (event.getCode() == KeyCode.DIGIT2)
                loadLevel(2);
            if (event.getCode() == KeyCode.DIGIT3)
                loadLevel(3);
            if (event.getCode() == KeyCode.DIGIT4)
                loadLevel(4);
        });

        scene.setOnKeyReleased(event -> {
            keys.remove(event.getCode());
            if (event.getCode() == KeyCode.R)
                isRewinding = false;
        });

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (isPaused || showMenu) {
                    updateUI();
                    return;
                }

                if (isRewinding)
                    performRewind();
                else
                    update();
            }
        }.start();

        primaryStage.setTitle("Rewinder - Time Control Side Scroller");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Toggles the pause menu and paused game state.
     */
    private void togglePauseMenu() {
        showMenu = !showMenu;
        isPaused = showMenu;
        pauseMenu.setVisible(showMenu);
    }

    /**
     * Loads the specified game level and initializes its entities.
     *
     * @param levelNumber the level number to load
     */
    private void loadLevel(int levelNumber) {
        clearLevel();
        isGameFinished = false;

        currentLevel = levelNumber;

        if (backgroundView != null && currentLevel >= 1 && currentLevel <= bgImages.length) {
            backgroundView.setImage(bgImages[currentLevel - 1]);
        }

        AssetLoader loader = new AssetLoader();
        player = new Player(loader.loadPlayerSprites());
        gameWorld.getChildren().add(player.getView());

        LevelBuilder builder = new LevelBuilder(gameWorld, platforms, fires, snipers, swordEnemies);
        exitDoor = builder.buildLevel(levelNumber);
        currentLevelName = builder.getLevelName();
    }

    /**
     * Clears the current level state and resets game variables.
     */
    private void clearLevel() {
        gameWorld.getChildren().clear();

        platforms.clear();
        bullets.clear();
        fires.clear();
        snipers.clear();
        swordEnemies.clear();
        history.clear();

        timeScale = 1.0;
        isRewinding = false;
        keys.clear();
    }

    /**
     * Updates game objects, performs collisions, and records rewind state.
     */
    private void update() {
        player.update(keys, platforms, GRAVITY);
        gameWorld.setLayoutX(300 - player.getX());

        updateHazards();
        checkExitDoor();
        recordState();

        if (player.fellOutOfWorld())
            die();

        updateUI();
    }

    /**
     * Updates all hazard entities and handles player collisions.
     */
    private void updateHazards() {
        for (Fire fire : fires) {
            fire.update(timeScale);

            if (fire.active && player.getView().getBoundsInParent().intersects(fire.rect.getBoundsInParent())) {
                die();
            }
        }

        for (Sniper sniper : snipers) {
            sniper.update(timeScale, player.getView(), gameWorld, bullets);
        }

        for (SwordEnemy sword : swordEnemies) {
            sword.update(timeScale);

            if (player.getView().getBoundsInParent().intersects(sword.body.getBoundsInParent())) {
                die();
            }
        }

        List<Bullet> toRemove = new ArrayList<>();

        for (Bullet bullet : bullets) {
            bullet.update(timeScale);

            if (bullet.rect.getBoundsInParent().intersects(player.getView().getBoundsInParent())) {
                die();
            }

            if (Math.abs(bullet.rect.getX() - player.getX()) > 2000) {
                toRemove.add(bullet);
            }
        }

        bullets.removeAll(toRemove);

        for (Bullet bullet : toRemove) {
            gameWorld.getChildren().remove(bullet.rect);
        }
    }

    /**
     * Checks whether the player has reached the exit door.
     */
    private void checkExitDoor() {
        if (exitDoor != null && player.getView().getBoundsInParent().intersects(exitDoor.getBoundsInParent())) {
            if (currentLevel == 4) {
                isGameFinished = true;
                isPaused = true;
            } else {
                int next = currentLevel + 1;
                loadLevel(next);
            }
        }
    }

    /**
     * Records a snapshot of the current world state for rewinding.
     */
    private void recordState() {
        WorldState state = new WorldState(player.getX(), player.getY(), player.velocityY);

        for (Bullet bullet : bullets) {
            state.bulletPositions.add(new Vector2(bullet.rect.getX(), bullet.rect.getY()));
        }

        for (Fire fire : fires) {
            state.fireLives.add(fire.life);
            state.fireActive.add(fire.active);
        }

        for (SwordEnemy sword : swordEnemies) {
            state.swordPositions.add(new Vector2(sword.body.getX(), sword.body.getY()));
        }

        history.add(state);

        if (history.size() > MAX_HISTORY) {
            history.remove(0);
        }
    }

    /**
     * Restores the previous state while rewinding time.
     */
    private void performRewind() {
        if (history.isEmpty()) {
            isRewinding = false;
            return;
        }

        WorldState last = history.remove(history.size() - 1);

        player.setX(last.playerX);
        player.setY(last.playerY);
        player.velocityY = last.playerVelocityY;

        gameWorld.setLayoutX(300 - player.getX());

        int bulletCount = Math.min(bullets.size(), last.bulletPositions.size());
        for (int i = 0; i < bulletCount; i++) {
            bullets.get(i).rect.setX(last.bulletPositions.get(i).x);
            bullets.get(i).rect.setY(last.bulletPositions.get(i).y);
        }

        while (bullets.size() > last.bulletPositions.size()) {
            Bullet removed = bullets.remove(bullets.size() - 1);
            gameWorld.getChildren().remove(removed.rect);
        }

        int fireCount = Math.min(fires.size(), last.fireLives.size());
        for (int i = 0; i < fireCount; i++) {
            fires.get(i).restore(last.fireLives.get(i), last.fireActive.get(i));
        }

        int swordCount = Math.min(swordEnemies.size(), last.swordPositions.size());
        for (int i = 0; i < swordCount; i++) {
            swordEnemies.get(i).restorePosition(last.swordPositions.get(i).x, last.swordPositions.get(i).y);
        }

        uiLabel.setText("!!! REWINDING !!!");
    }

    /**
     * Resets the player after death and stops rewinding.
     */
    private void die() {
        player.respawn();
        isRewinding = false;
    }

    /**
     * Updates the status label text for the current game state.
     */
    private void updateUI() {
        if (isGameFinished) {
            uiLabel.setText("Congratulations you finished the game! To Continue the game click 1");
            updateTint();
            return;
        }

        if (showMenu || isPaused) {
            uiLabel.setText("PAUSED | ENTER: Resume | P/ESC: Toggle Menu");
            updateTint();
            return;
        }

        String mode = timeScale == 0.2 ? "SLOW" : (timeScale == 8.0 ? "FAST" : "NORMAL");
        uiLabel.setText("LEVEL " + currentLevel + ": " + currentLevelName +
                " | MODE: " + mode +
                " | Q:Normal SHIFT:Slow E:Double the Speed | R:Rewind | 1-4:Levels | P:Pause");
        updateTint();
    }

    /**
     * Launches the JavaFX application.
     *
     * @param args application command-line arguments
     */
    private void updateTint() {
        if (showMenu || isPaused) {
            tintRect.setFill(Color.TRANSPARENT);
            return;
        }

        if (timeScale == 0.2) {
            tintRect.setFill(Color.rgb(173, 216, 230, 0.25)); 
        } else if (timeScale == 8.0) {
            tintRect.setFill(Color.rgb(255, 200, 150, 0.25)); 
        } else {
            tintRect.setFill(Color.TRANSPARENT);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
