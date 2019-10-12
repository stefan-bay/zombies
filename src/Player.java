import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends GameObject{

    Image playerImage;

    Player(double x, double y, double width, double height) {
        super(x, y, width, height);
        playerImage = new BufferedImage((int) width,(int) height, BufferedImage.TYPE_INT_RGB);
        Graphics groundGraphics = playerImage.getGraphics();
        groundGraphics.setColor(Color.BLUE);
        groundGraphics.fillRect(,0,(int)width,(int)height);
    }


    @Override
    Image getImage() {
        return playerImage;
    }
}
