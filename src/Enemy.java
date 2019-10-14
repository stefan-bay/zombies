import java.awt.*;
import java.awt.image.BufferedImage;

public class Enemy extends GameObject {
    Image enemyImage;

    public Enemy(double x, double y, double width, double height) {
        super(x, y, width, height);
        enemyImage = new BufferedImage((int) width,(int) height, BufferedImage.TYPE_INT_RGB);
        Graphics enemyGraphics = enemyImage.getGraphics();
        enemyGraphics.setColor(Color.DARK_GRAY);
        enemyGraphics.fillRect((int)0,(int)0,(int)width,(int)height);
    }

    @Override
    Image getImage() {
        return enemyImage;
    }
}
