import java.awt.*;
import java.awt.image.BufferedImage;

public class VictoryBox extends GameObject {
    Image groundImage;

    VictoryBox(double x, double y, double width, double height) {
        super(x, y, width, height);
        groundImage = new BufferedImage((int) width,(int) height, BufferedImage.TYPE_INT_RGB);
        Graphics groundGraphics = groundImage.getGraphics();
        groundGraphics.setColor(Color.MAGENTA);
        groundGraphics.fillRect((int)0,(int)0,(int)width,(int)height);
    }

    @Override
    Image getImage() {
       return groundImage;
    }
}
