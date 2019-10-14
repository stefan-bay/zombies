import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Player extends GameObject {

    enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    Image playerImage;
    static double widthInitial = 30;
    static double heightInitial = 30;
    static double xInitial = 0;
    static double yInitial = -heightInitial;

    Direction direction;

    Animation currentAnimation;

    Animation runRightAnimation;
    Animation runLeftAnimation;

    Animation idleRightAnimation;
    Animation idleLeftAnimation;

    Player() {
        super(xInitial, yInitial, widthInitial, heightInitial);
        playerImage = new BufferedImage((int) widthInitial,(int) heightInitial, BufferedImage.TYPE_INT_RGB);
        Graphics g = playerImage.getGraphics();
        g.setColor(Color.CYAN);
        g.fillRect(0,0,(int)widthInitial,(int)heightInitial);

        ArrayList<BufferedImage> runRight = new ArrayList<>();
        for (int i = 1; i < 9; i++) {
            try {
                runRight.add(ImageIO.read(new File("res/robot/Run (" + i + ").png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        runRightAnimation = new Animation(runRight);

        ArrayList<BufferedImage> runLeft = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            runLeft.add(Animation.flipImage(runRight.get(i)));
        }
        runLeftAnimation = new Animation(runLeft);

        ArrayList<BufferedImage> idleRight = new ArrayList<>();
        for (int i = 1; i < 11; i++) {
            try {
                idleRight.add(ImageIO.read(new File("res/robot/Idle (" + i + ").png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        idleRightAnimation = new Animation(idleRight);

        ArrayList<BufferedImage> idleLeft = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            idleLeft.add(Animation.flipImage(idleRight.get(i)));
        }
        idleLeftAnimation = new Animation(idleLeft);


        this.direction = Direction.RIGHT;

        //this.setWidth(idleRight.get(0).getWidth());
        //this.setHeight(idleRight.get(0).getHeight());

        setCurrentAnimation(idleRightAnimation);
    }

    void move(double x, double y, ArrayList<GameObject> objects) {
        super.move(x,y,objects);
        if (x > 0) {
            this.direction = Direction.RIGHT;
            setCurrentAnimation(runRightAnimation);
        } else if (x < 0) {
            this.direction = Direction.LEFT;
            setCurrentAnimation(runLeftAnimation);
        } else if (x == 0 && y == 0)
            if (this.direction == Direction.RIGHT)
                setCurrentAnimation(idleRightAnimation);
            else
                setCurrentAnimation(idleLeftAnimation);
    }

    public void setCurrentAnimation(Animation animation) {
        this.currentAnimation = animation;
    }

    public void setPlayerImage(Image image) {
        this.playerImage = image;
    }

    @Override
    Image getImage() {
        return currentAnimation.nextImage();
    }
}
