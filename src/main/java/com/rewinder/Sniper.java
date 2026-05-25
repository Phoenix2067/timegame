package rewinder;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.util.List;

/**
 * Sniper enemy that shoots toward the player.
 */
public class Sniper {
    public Rectangle body;
    private Line laser = new Line();
    private double cooldown = 0;

    public Sniper(double x, double y, Pane world) {
        body = new Rectangle(x, y, 30, 50);
        body.setFill(Color.RED);
        laser.setStroke(Color.web("red", 0.3));
        world.getChildren().addAll(body, laser);
    }

    public void update(double timeScale, ImageView target, Pane world, List<Bullet> bullets) {
        laser.setStartX(body.getX());
        laser.setStartY(body.getY() + 10);
        laser.setEndX(target.getX() + 15);
        laser.setEndY(target.getY() + 20);

        cooldown -= timeScale;
        if (cooldown <= 0) {
            bullets.add(new Bullet(body.getX(), body.getY() + 10, target.getX(), target.getY(), world));
            cooldown = 120;
        }
    }
}
