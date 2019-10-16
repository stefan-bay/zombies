import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

/**
 * GameObject representing a health bar over a player or enemy
 */
public class HealthBar extends GameObject {

    private static final double Width = 50;
    private static final double Height = 10;

    private static final double yOffset = 25;

    private GameObject object;
    private int maxHealth;

    HealthBar(GameObject object, int maxHealth) {
        super(object.getX(), object.getY() - yOffset, Width, Height);
        this.object = object;
        this.maxHealth = maxHealth;

        setWidth(Width);
        setHeight(Height);
        setColliding(false);
    }

    /**
     * Link coordinates to object it hovers above
     */
    @Override
    double getX() {
        return object.getX();
    }

    /**
     * Link coordinates to object it hovers above
     */
    @Override
    double getY() {
        return object.getY() - yOffset;
    }

    /**
     * Move with object linked to
     * @param objects
     */
    @Override
    void update(ArrayList<GameObject> objects) {
        this.setX(getX());
        this.setY(getY());
    }

    /**
     * Image showing percent health
     */
    @Override
    Image getImage() {
        // remove self if linked object is removed
        if (object.shouldRemove()) {
            this.setShouldRemove(true);
            return null;
        }

        double bar_width = (object.getHealth() / (double)maxHealth) * Width;

        BufferedImage bar = new BufferedImage((int)Width, (int)Height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D bar_g = (Graphics2D)bar.getGraphics();
        bar_g.setColor(Color.black);
        bar_g.drawRect(0, 0, (int)Width - 1, (int)Height - 1);
        bar_g.setColor(Color.red);
        bar_g.fillRect(1, 1, (int)bar_width - 2, (int)Height - 2);
        bar_g.dispose();

        return bar;
    }
}
