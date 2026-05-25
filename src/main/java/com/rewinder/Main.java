package com.rewinder;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.HashSet;
import java.util.Set;

/**
 * Main JavaFX application class for Rewinder.
 *
 * <p>This version supports resizable windows, maximized windows, minimized windows,
 * and fullscreen mode.</p>
 */
public class Main extends Application {
    private static final int START_WIDTH = 900;
    private static final int START_HEIGHT = 520;

    private final Canvas canvas = new Canvas(START_WIDTH, START_HEIGHT);
    private GraphicsContext graphics;

    private final Set<KeyCode> keys = new HashSet<>();

    private final TimeManager timeManager = new TimeManager();
    private GameWorld world;
    private final GameCamera camera = new GameCamera(START_WIDTH);
    private final HUD hud = new HUD();
    private final PauseMenu pauseMenu = new PauseMenu();

    private boolean paused = true;
    private boolean startScreen = true;
    private boolean rewinding = false;

    @Override
    public void start(Stage stage) {
        graphics = canvas.getGraphicsContext2D();

        AssetLoader loader = new AssetLoader();
        PlayerSprites sprites = loader.loadPlayerSprites();
        world = new GameWorld(sprites);

        Pane root = new Pane(canvas);

        // Make the canvas follow the window size.
        canvas.widthProperty().bind(root.widthProperty());
        canvas.heightProperty().bind(root.heightProperty());

        Scene scene = new Scene(root, START_WIDTH, START_HEIGHT);

        scene.setOnKeyPressed(event -> {
            keys.add(event.getCode());

            if (event.getCode() == KeyCode.F11) {
                stage.setFullScreen(!stage.isFullScreen());
            }

            if (event.getCode() == KeyCode.R) {
                rewinding = true;
            }

            if (event.getCode() == KeyCode.ENTER) {
                paused = false;
                startScreen = false;
            }

            if (event.getCode() == KeyCode.P || event.getCode() == KeyCode.ESCAPE) {
                paused = !paused;
                startScreen = false;
            }

            if (!paused) {
                if (event.getCode() == KeyCode.DIGIT1) loadLevel(1);
                if (event.getCode() == KeyCode.DIGIT2) loadLevel(2);
                if (event.getCode() == KeyCode.DIGIT3) loadLevel(3);
                if (event.getCode() == KeyCode.DIGIT4) loadLevel(4);
            }
        });

        scene.setOnKeyReleased(event -> {
            keys.remove(event.getCode());

            if (event.getCode() == KeyCode.R) {
                rewinding = false;
            }
        });

        loadLevel(1);

        stage.setTitle("Rewinder - Time Control Side Scroller");
        stage.setMinWidth(640);
        stage.setMinHeight(400);
        stage.setResizable(true);
        stage.setScene(scene);
        stage.show();

        new AnimationTimer() {
            private long last = 0;

            @Override
            public void handle(long now) {
                if (last == 0) last = now;
                double delta = (now - last) / 1_000_000_000.0;
                last = now;

                if (delta > 0.05) delta = 0.05;

                if (!paused) {
                    update(delta);
                }

                draw();
            }
        }.start();
    }

    private void loadLevel(int levelNumber) {
        world.loadLevel(levelNumber);
        timeManager.reset();
        camera.reset();
        rewinding = false;
    }

    private void update(double delta) {
        if (!rewinding) {
            timeManager.update(delta, keys);
        }

        world.update(delta, timeManager, keys, rewinding);
        camera.follow(world.getPlayer(), world.getWorldWidth(), canvas.getWidth());
    }

    private void draw() {
        double screenWidth = canvas.getWidth();
        double screenHeight = canvas.getHeight();

        if (screenWidth <= 0 || screenHeight <= 0) {
            return;
        }

        drawBackground(screenWidth, screenHeight);

        graphics.save();
        graphics.translate(-camera.getX(), 0);
        world.draw(graphics);
        graphics.restore();

        hud.draw(graphics, world.getCurrentLevel(), world.getLevelName(), timeManager);

        if (rewinding && !paused) {
            graphics.setFill(Color.WHITE);
            graphics.fillText("!!! REWINDING !!!", screenWidth / 2 - 60, 145);
        }

        if (paused) {
            pauseMenu.draw(graphics, screenWidth, screenHeight, startScreen);
        }
    }

    private void drawBackground(double screenWidth, double screenHeight) {
        if (timeManager.getMode() == TimeManager.TimeMode.SLOW) {
            graphics.setFill(Color.rgb(20, 40, 80));
        } else if (timeManager.getMode() == TimeManager.TimeMode.FAST) {
            graphics.setFill(Color.rgb(80, 40, 20));
        } else {
            graphics.setFill(Color.rgb(30, 30, 40));
        }

        graphics.fillRect(0, 0, screenWidth, screenHeight);

        graphics.setFill(Color.rgb(50, 55, 75));
        int cloudCount = (int) (screenWidth / 150) + 8;
        for (int i = 0; i < cloudCount; i++) {
            double x = i * 180 - camera.getX() * 0.25;
            graphics.fillOval(x, 80, 140, 50);
        }

        graphics.setFill(Color.rgb(40, 42, 55));
        int buildingCount = (int) (screenWidth / 110) + 12;
        for (int i = 0; i < buildingCount; i++) {
            double x = i * 130 - camera.getX() * 0.45;
            double height = 120 + (i % 4) * 35;
            graphics.fillRect(x, Math.max(0, screenHeight - 70 - height), 80, height);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
