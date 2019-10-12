import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Player extends GameObject {

    Image playerImage;
    static double widthInitial = 20;
    static double heightInitial = 30;
    static double xInitial = 0;
    static double yInitial = -heightInitial;

    Animation currentAnimation;

    Animation runRightAnimation;
    Animation idleAnimation;

    Player() {
        super(xInitial, yInitial, widthInitial, heightInitial);
        playerImage = new BufferedImage((int) widthInitial,(int) heightInitial, BufferedImage.TYPE_INT_RGB);
        Graphics g = playerImage.getGraphics();
        g.setColor(Color.CYAN);
        g.fillRect(0,0,(int)widthInitial,(int)heightInitial);

        ArrayList<BufferedImage> run = new ArrayList<>();
        for (int i = 1; i < 9; i++) {
            try {
                run.add(ImageIO.read(new File("res/robot/Run (" + i + ").png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        runRightAnimation = new Animation(run);

        ArrayList<BufferedImage> idle = new ArrayList<>();
        for (int i = 1; i < 9; i++) {
            try {
                idle.add(ImageIO.read(new File("res/robot/Idle (" + i + ").png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        idleAnimation = new Animation(idle);

        currentAnimation = idleAnimation;
    }

    void move(double x, double y) {
        if (x > 0)
            currentAnimation = runRightAnimation;
        else if (x == 0 && y == 0)
            currentAnimation = idleAnimation;
        setX(getX() + x);
        setY(getY() + y);
    }

    public void setPlayerImage(Image image) {
        this.playerImage = image;
    }

    @Override
    Image getImage() {
        return currentAnimation.nextImage();
    }
}
