import java.awt.*;
import java.awt.image.BufferedImage;

public class Ground extends GameObject {
    Image groundImage;

    Ground(double x, double y, double width, double height) {
        super(x, y, width, height);
        groundImage = new BufferedImage((int) width,(int) height, BufferedImage.TYPE_INT_RGB);
    }

    @Override
    Image getImage() {
       return groundImage;
    }
}
