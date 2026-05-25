package rewinder;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.List;

/**
 * Builds all four levels.
 */
public class LevelBuilder {
    private final Pane world;
    private final List<Rectangle> platforms;
    private final List<Fire> fires;
    private final List<Sniper> snipers;
    private final List<SwordEnemy> swordEnemies;

    private Rectangle exitDoor;
    private String levelName = "Training Facility";

    public LevelBuilder(Pane world, List<Rectangle> platforms, List<Fire> fires, List<Sniper> snipers, List<SwordEnemy> swordEnemies) {
        this.world = world;
        this.platforms = platforms;
        this.fires = fires;
        this.snipers = snipers;
        this.swordEnemies = swordEnemies;
    }

    public Rectangle buildLevel(int levelNumber) {
        if (levelNumber == 1) createEasyLevel();
        else if (levelNumber == 2) createMediumLevel();
        else if (levelNumber == 3) createHardLevel();
        else createUltraHardLevel();

        return exitDoor;
    }

    public String getLevelName() {
        return levelName;
    }

    private void createEasyLevel() {
        levelName = "Training Facility";
        addPlatform(0, 550, 3000, 50);
        addPlatform(350, 480, 180, 20);
        addPlatform(700, 430, 180, 20);
        addPlatform(1100, 490, 220, 20);
        addPlatform(1550, 450, 220, 20);
        addPlatform(2100, 470, 250, 20);
        snipers.add(new Sniper(850, 500, world));
        fires.add(new Fire(1350, 450, 70, 100, world));
        fires.add(new Fire(1900, 450, 70, 100, world));
        addCheckpoint(1500, 490);
        addExitDoor(2750, 460);
    }

    private void createMediumLevel() {
        levelName = "Industrial Zone";
        addPlatform(0, 550, 5000, 50);
        addPlatform(350, 470, 180, 20);
        addPlatform(650, 420, 180, 20);
        addPlatform(950, 480, 180, 20);
        addPlatform(1300, 440, 220, 20);
        addPlatform(1700, 400, 200, 20);
        addPlatform(2100, 470, 250, 20);
        addPlatform(2600, 430, 220, 20);
        addPlatform(3100, 490, 250, 20);
        addPlatform(3650, 440, 250, 20);
        addPlatform(4200, 470, 250, 20);
        snipers.add(new Sniper(850, 500, world));
        snipers.add(new Sniper(1800, 350, world));
        snipers.add(new Sniper(3300, 440, world));
        swordEnemies.add(new SwordEnemy(1150, 500, 1000, 1400, world));
        swordEnemies.add(new SwordEnemy(2900, 500, 2700, 3250, world));
        fires.add(new Fire(1450, 450, 70, 100, world));
        fires.add(new Fire(1550, 450, 70, 100, world));
        fires.add(new Fire(2400, 450, 70, 100, world));
        fires.add(new Fire(2500, 450, 70, 100, world));
        fires.add(new Fire(3950, 450, 70, 100, world));
        fires.add(new Fire(4050, 450, 70, 100, world));
        addCheckpoint(2300, 490);
        addExitDoor(4700, 460);
    }

    private void createHardLevel() {
        levelName = "Temporal Ruins";
        addPlatform(0, 550, 7000, 50);

        for (int i = 0; i < 15; i++) {
            double x = 300 + i * 420;
            double y = (i % 4 == 0) ? 470 : (i % 4 == 1) ? 410 : (i % 4 == 2) ? 490 : 430;
            addPlatform(x, y, 180, 20);
        }

        snipers.add(new Sniper(850, 430, world));
        snipers.add(new Sniper(1750, 360, world));
        snipers.add(new Sniper(2800, 460, world));
        snipers.add(new Sniper(4300, 440, world));
        snipers.add(new Sniper(5600, 460, world));

        swordEnemies.add(new SwordEnemy(1150, 500, 950, 1450, world));
        swordEnemies.add(new SwordEnemy(2300, 500, 2100, 2650, world));
        swordEnemies.add(new SwordEnemy(3600, 500, 3350, 3900, world));
        swordEnemies.add(new SwordEnemy(5000, 500, 4750, 5250, world));
        swordEnemies.add(new SwordEnemy(6100, 500, 5900, 6400, world));

        for (int i = 0; i < 10; i++) fires.add(new Fire(1500 + i * 420, 450, 70, 100, world));

        addCheckpoint(2200, 490);
        addCheckpoint(4200, 490);
        addCheckpoint(5900, 490);
        addExitDoor(6650, 460);
    }

    private void createUltraHardLevel() {
        levelName = "Time Collapse";
        addPlatform(0, 550, 12000, 50);

        for (int i = 0; i < 30; i++) {
            double x = 350 + i * 370;
            double y = (i % 4 == 0) ? 480 : (i % 4 == 1) ? 420 : (i % 4 == 2) ? 370 : 450;
            addPlatform(x, y, 170, 20);
        }

        for (int i = 0; i < 10; i++) snipers.add(new Sniper(800 + i * 1050, 500, world));

        for (int i = 0; i < 10; i++) {
            double start = 1000 + i * 950;
            swordEnemies.add(new SwordEnemy(start, 500, start - 200, start + 350, world));
        }

        for (int i = 0; i < 15; i++) fires.add(new Fire(1400 + i * 650, 450, 75, 100, world));

        addCheckpoint(2000, 490);
        addCheckpoint(4000, 490);
        addCheckpoint(6200, 490);
        addCheckpoint(8500, 490);
        addCheckpoint(10500, 490);
        addExitDoor(11600, 460);
    }

    private void addPlatform(double x, double y, double width, double height) {
        Rectangle platform = new Rectangle(x, y, width, height);
        platform.setFill(Color.web("#333333"));
        platform.setStroke(Color.WHITE);
        platforms.add(platform);
        world.getChildren().add(platform);
    }

    private void addCheckpoint(double x, double y) {
        Rectangle pole = new Rectangle(x, y - 70, 10, 70);
        pole.setFill(Color.GOLD);
        Rectangle flag = new Rectangle(x + 10, y - 70, 45, 25);
        flag.setFill(Color.YELLOW);
        world.getChildren().addAll(pole, flag);
    }

    private void addExitDoor(double x, double y) {
        exitDoor = new Rectangle(x, y, 60, 90);
        exitDoor.setFill(Color.LIMEGREEN);
        exitDoor.setStroke(Color.WHITE);
        world.getChildren().add(exitDoor);
    }
}
