package rewinder;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Fire obstacle that fades out with time.
 */
public class Fire {
    public Rectangle rect;
    public double life = 400;
    public boolean active = true;

    public Fire(double x, double y, double width, double height, Pane world) {
        rect = new Rectangle(x, y, width, height);
        rect.setFill(Color.ORANGERED);
        world.getChildren().add(rect);
    }

    public void update(double timeScale) {
        if (!active) return;

        life -= timeScale;

        if (life <= 0) {
            active = false;
            life = 0;
            rect.setOpacity(0.1);
        } else {
            rect.setOpacity(life / 400.0);
        }
    }

    public void restore(double life, boolean active) {
        this.life = life;
        this.active = active;
        rect.setOpacity(active ? life / 400.0 : 0.1);
    }
}
