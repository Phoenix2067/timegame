package com.rewinder;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Holds and updates all active game objects.
 */
public class GameWorld {
    private final Player player;
    private final List<Platform> platforms = new ArrayList<>();
    private final List<Laser> lasers = new ArrayList<>();
    private final List<Fire> fires = new ArrayList<>();
    private final List<Enemy> enemies = new ArrayList<>();
    private final List<Checkpoint> checkpoints = new ArrayList<>();

    private final CollisionManager collisionManager = new CollisionManager();
    private final RewindManager rewindManager = new RewindManager();

    private Door exitDoor;
    private LevelManager.LevelData levelData;
    private int currentLevel = 1;

    public GameWorld(PlayerSprites sprites) {
        player = new Player(100, 350, sprites);
    }

    public void loadLevel(int levelNumber) {
        currentLevel = levelNumber;
        levelData = LevelManager.loadLevel(levelNumber);

        platforms.clear();
        lasers.clear();
        fires.clear();
        enemies.clear();
        checkpoints.clear();
        rewindManager.clear();

        player.respawn(levelData.playerSpawnX, levelData.playerSpawnY);
        exitDoor = new Door(levelData.exitX, levelData.exitY);

        for (LevelManager.PlatformData data : levelData.platforms) {
            platforms.add(new Platform(data.x, data.y, data.width, data.height));
        }

        for (LevelManager.FireData data : levelData.fires) {
            fires.add(new Fire(data.x, data.y, data.width, data.height, data.burnTime));
        }

        for (LevelManager.EnemyData data : levelData.laserEnemies) {
            enemies.add(new LaserEnemy(data.x, data.y));
        }

        for (LevelManager.EnemyData data : levelData.swordEnemies) {
            enemies.add(new SwordEnemy(data.x, data.y, data.patrolLeft, data.patrolRight));
        }

        for (LevelManager.CheckpointData data : levelData.checkpoints) {
            checkpoints.add(new Checkpoint(data.x, data.y));
        }
    }

    public void update(double delta, TimeManager timeManager, Set<KeyCode> keys, boolean rewinding) {
        if (rewinding) {
            rewindManager.rewind(player, lasers, fires);
            return;
        }

        double worldDelta = delta * timeManager.getWorldTimeScale();

        player.update(delta, keys, platforms);

        for (Enemy enemy : enemies) {
            enemy.update(worldDelta, this);
        }

        for (Fire fire : fires) {
            fire.update(worldDelta);
        }

        for (int i = lasers.size() - 1; i >= 0; i--) {
            Laser laser = lasers.get(i);
            laser.update(worldDelta);

            if (laser.x < -300 || laser.x > levelData.worldWidth + 300) {
                lasers.remove(i);
            }
        }

        if (collisionManager.playerTouchesFire(player, fires) ||
                collisionManager.playerTouchesLaser(player, lasers) ||
                collisionManager.playerTouchesEnemy(player, enemies) ||
                player.y > 700) {
            respawnPlayer();
            lasers.clear();
        }

        if (exitDoor != null && player.intersects(exitDoor)) {
            int nextLevel = currentLevel + 1;
            if (nextLevel > 4) nextLevel = 1;
            loadLevel(nextLevel);
        }

        rewindManager.record(player, lasers, fires);
    }

    public void draw(GraphicsContext graphics) {
        for (Platform platform : platforms) platform.draw(graphics);
        for (Fire fire : fires) fire.draw(graphics);
        for (Enemy enemy : enemies) enemy.draw(graphics);
        for (Checkpoint checkpoint : checkpoints) checkpoint.draw(graphics);
        for (Laser laser : lasers) laser.draw(graphics);
        if (exitDoor != null) exitDoor.draw(graphics);
        player.draw(graphics);
    }

    public void addLaser(Laser laser) {
        lasers.add(laser);
    }

    public void respawnPlayer() {
        player.respawn(levelData.playerSpawnX, levelData.playerSpawnY);
    }

    public Player getPlayer() {
        return player;
    }

    public double getWorldWidth() {
        return levelData.worldWidth;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public String getLevelName() {
        return levelData.name;
    }
}
