import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends GameObject{

    Image playerImage;
    static double widthInitial = 20;
    static double heightInitial = 30;
    static double xInitial = 0;
    static double yInitial = -heightInitial;

    Player() {
        super(xInitial, yInitial, widthInitial, heightInitial);
        playerImage = new BufferedImage((int) widthInitial,(int) heightInitial, BufferedImage.TYPE_INT_RGB);
        Graphics g = playerImage.getGraphics();
        g.setColor(Color.CYAN);
        g.fillRect(0,0,(int)widthInitial,(int)heightInitial);
    }

    void move(double x, double y) {
        setX(getX() + x);
        setY(getY() + y);
    }


    @Override
    Image getImage() {
        return playerImage;
    }
}
