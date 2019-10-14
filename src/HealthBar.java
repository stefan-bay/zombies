import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class HealthBar extends GameObject {

    private static double Width = 50;
    private static double Height = 10;

    private static double yOffset = 15;

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

    @Override
    double getX() {
        return object.getX();
    }

    @Override
    double getY() {
        return object.getY() - yOffset;
    }

    @Override
    Image getImage() {

        double bar_width = (object.getHealth() / (double)maxHealth) * Width;

        if (bar_width <= 0)
            bar_width = 1;

        BufferedImage bar = new BufferedImage((int)bar_width, (int)Height, BufferedImage.TYPE_INT_ARGB);

        Graphics bar_g = bar.getGraphics();
        bar_g.setColor(Color.red);
        bar_g.fillRect(0, 0, (int)bar_width, (int)Height);
        bar_g.dispose();

        return bar;
    }
}
