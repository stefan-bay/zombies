import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends GameObject{

    Image playerImage;
    static double width = 20;
    static double height = 30;
    static double x = 0;
    static double y = -height;

    Player() {
        super(x, y, width, height);
        playerImage = new BufferedImage((int) width,(int) height, BufferedImage.TYPE_INT_RGB);
        Graphics g = playerImage.getGraphics();
        g.setColor(Color.CYAN);
        g.fillRect(0,0,(int)width,(int)height);
    }


    @Override
    Image getImage() {
        return playerImage;
    }
}
