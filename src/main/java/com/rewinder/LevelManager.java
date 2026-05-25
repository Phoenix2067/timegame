package com.rewinder;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates the four levels for the game.
 */
public class LevelManager {
    public static LevelData loadLevel(int levelNumber) {
        switch (levelNumber) {
            case 1: return easyLevel();
            case 2: return mediumLevel();
            case 3: return hardLevel();
            case 4: return ultraHardLevel();
            default: return easyLevel();
        }
    }

    private static LevelData easyLevel() {
        LevelData level = new LevelData("Training Facility", 3000);
        level.playerSpawnX = 100;
        level.playerSpawnY = 350;
        level.exitX = 2750;
        level.exitY = 360;

        level.platforms.add(new PlatformData(0, 450, 3000, 70));
        level.platforms.add(new PlatformData(350, 380, 180, 25));
        level.platforms.add(new PlatformData(700, 330, 180, 25));
        level.platforms.add(new PlatformData(1100, 390, 220, 25));
        level.platforms.add(new PlatformData(1550, 350, 220, 25));
        level.platforms.add(new PlatformData(2100, 370, 250, 25));

        level.laserEnemies.add(new EnemyData(850, 275, "LASER"));
        level.fires.add(new FireData(1350, 390, 70, 60, 8));
        level.fires.add(new FireData(1900, 390, 70, 60, 7));
        level.checkpoints.add(new CheckpointData(1500, 400));

        return level;
    }

    private static LevelData mediumLevel() {
        LevelData level = new LevelData("Industrial Zone", 5000);
        level.playerSpawnX = 100;
        level.playerSpawnY = 350;
        level.exitX = 4700;
        level.exitY = 360;

        level.platforms.add(new PlatformData(0, 450, 5000, 70));
        level.platforms.add(new PlatformData(350, 370, 180, 25));
        level.platforms.add(new PlatformData(650, 320, 180, 25));
        level.platforms.add(new PlatformData(950, 380, 180, 25));
        level.platforms.add(new PlatformData(1300, 340, 220, 25));
        level.platforms.add(new PlatformData(1700, 300, 200, 25));
        level.platforms.add(new PlatformData(2100, 370, 250, 25));
        level.platforms.add(new PlatformData(2600, 330, 220, 25));
        level.platforms.add(new PlatformData(3100, 390, 250, 25));
        level.platforms.add(new PlatformData(3650, 340, 250, 25));
        level.platforms.add(new PlatformData(4200, 370, 250, 25));

        level.laserEnemies.add(new EnemyData(850, 265, "LASER"));
        level.laserEnemies.add(new EnemyData(1800, 245, "LASER"));
        level.laserEnemies.add(new EnemyData(3300, 335, "LASER"));

        level.swordEnemies.add(new EnemyData(1150, 395, "SWORD", 1000, 1400));
        level.swordEnemies.add(new EnemyData(2900, 395, "SWORD", 2700, 3250));

        level.fires.add(new FireData(1450, 390, 70, 60, 8));
        level.fires.add(new FireData(1550, 390, 70, 60, 8));
        level.fires.add(new FireData(2400, 390, 70, 60, 7));
        level.fires.add(new FireData(2500, 390, 70, 60, 7));
        level.fires.add(new FireData(3950, 390, 70, 60, 6));
        level.fires.add(new FireData(4050, 390, 70, 60, 6));
        level.checkpoints.add(new CheckpointData(2300, 400));

        return level;
    }

    private static LevelData hardLevel() {
        LevelData level = new LevelData("Temporal Ruins", 7000);
        level.playerSpawnX = 100;
        level.playerSpawnY = 350;
        level.exitX = 6650;
        level.exitY = 360;

        level.platforms.add(new PlatformData(0, 450, 7000, 70));

        for (int i = 0; i < 15; i++) {
            double x = 300 + i * 420;
            double y = (i % 4 == 0) ? 370 : (i % 4 == 1) ? 310 : (i % 4 == 2) ? 390 : 330;
            level.platforms.add(new PlatformData(x, y, 180, 25));
        }

        level.laserEnemies.add(new EnemyData(850, 255, "LASER"));
        level.laserEnemies.add(new EnemyData(1750, 235, "LASER"));
        level.laserEnemies.add(new EnemyData(2800, 335, "LASER"));
        level.laserEnemies.add(new EnemyData(4300, 315, "LASER"));
        level.laserEnemies.add(new EnemyData(5600, 335, "LASER"));

        level.swordEnemies.add(new EnemyData(1150, 395, "SWORD", 950, 1450));
        level.swordEnemies.add(new EnemyData(2300, 395, "SWORD", 2100, 2650));
        level.swordEnemies.add(new EnemyData(3600, 395, "SWORD", 3350, 3900));
        level.swordEnemies.add(new EnemyData(5000, 395, "SWORD", 4750, 5250));
        level.swordEnemies.add(new EnemyData(6100, 395, "SWORD", 5900, 6400));

        for (int i = 0; i < 10; i++) {
            level.fires.add(new FireData(1500 + i * 420, 390, 70, 60, 6));
        }

        level.checkpoints.add(new CheckpointData(2200, 400));
        level.checkpoints.add(new CheckpointData(4200, 400));
        level.checkpoints.add(new CheckpointData(5900, 400));

        return level;
    }

    private static LevelData ultraHardLevel() {
        LevelData level = new LevelData("Time Collapse", 12000);
        level.playerSpawnX = 100;
        level.playerSpawnY = 350;
        level.exitX = 11600;
        level.exitY = 360;

        level.platforms.add(new PlatformData(0, 450, 12000, 70));

        for (int i = 0; i < 30; i++) {
            double x = 350 + i * 370;
            double y = (i % 4 == 0) ? 380 : (i % 4 == 1) ? 320 : (i % 4 == 2) ? 270 : 350;
            level.platforms.add(new PlatformData(x, y, 170, 25));
        }

        for (int i = 0; i < 10; i++) {
            level.laserEnemies.add(new EnemyData(800 + i * 1050, 395, "LASER"));
        }

        for (int i = 0; i < 10; i++) {
            double start = 1000 + i * 950;
            level.swordEnemies.add(new EnemyData(start, 395, "SWORD", start - 200, start + 350));
        }

        for (int i = 0; i < 15; i++) {
            level.fires.add(new FireData(1400 + i * 650, 390, 75, 60, 5));
        }

        level.checkpoints.add(new CheckpointData(2000, 400));
        level.checkpoints.add(new CheckpointData(4000, 400));
        level.checkpoints.add(new CheckpointData(6200, 400));
        level.checkpoints.add(new CheckpointData(8500, 400));
        level.checkpoints.add(new CheckpointData(10500, 400));

        return level;
    }

    public static class LevelData {
        public String name;
        public double worldWidth;
        public double playerSpawnX;
        public double playerSpawnY;
        public double exitX;
        public double exitY;
        public List<PlatformData> platforms = new ArrayList<>();
        public List<EnemyData> laserEnemies = new ArrayList<>();
        public List<EnemyData> swordEnemies = new ArrayList<>();
        public List<FireData> fires = new ArrayList<>();
        public List<CheckpointData> checkpoints = new ArrayList<>();

        public LevelData(String name, double worldWidth) {
            this.name = name;
            this.worldWidth = worldWidth;
        }
    }

    public static class PlatformData {
        public double x, y, width, height;
        public PlatformData(double x, double y, double width, double height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }
    }

    public static class EnemyData {
        public double x, y;
        public String type;
        public double patrolLeft;
        public double patrolRight;

        public EnemyData(double x, double y, String type) {
            this.x = x;
            this.y = y;
            this.type = type;
        }

        public EnemyData(double x, double y, String type, double patrolLeft, double patrolRight) {
            this.x = x;
            this.y = y;
            this.type = type;
            this.patrolLeft = patrolLeft;
            this.patrolRight = patrolRight;
        }
    }

    public static class FireData {
        public double x, y, width, height, burnTime;
        public FireData(double x, double y, double width, double height, double burnTime) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.burnTime = burnTime;
        }
    }

    public static class CheckpointData {
        public double x, y;
        public CheckpointData(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }
}
