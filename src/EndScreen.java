import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;

public class EndScreen extends GameObject{
    private Animation bloodAnimation;

    private BufferedImage youLoseImage;
    private BufferedImage killsImage;

    private BufferedImage killCountImage;

    /**
     * Holds widths of each number png
     */
    private static int [] numberwidth = {
            15, 14, 19, 21, 19, 22, 19, 20, 17, 17
    };

    EndScreen(int killCount) {
        super(-400, -400, 0, 0);
        this.setColliding(false);

        ArrayList<BufferedImage> endScreenList = Animation.getListForPath("res/blood_gif/frame_", 0, 15);
        bloodAnimation = new Animation(endScreenList, 100);

        try {
            youLoseImage = ImageIO.read(new File("res/UI/YouLose.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            killsImage = ImageIO.read(new File("res/UI/kills.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.killCountImage = NumberHelper.imageForNumber(killCount);
    }

    @Override
    void update(ArrayList<GameObject> gameObjects) {}

    @Override
    Image getImage() {
        BufferedImage image = bloodAnimation.nextImage(); // dripping blood
        BufferedImage youLoseImage = this.youLoseImage;
        BufferedImage killsImage = this.killsImage;

        Color transparent = new Color(50, 50,50,0);

        // draw over blood animation
        Graphics2D g = (Graphics2D)image.getGraphics();
        g.setColor(transparent);

        g.drawImage(killsImage, null, 300, 500);
        g.drawImage(killCountImage, null, 300 + killsImage.getWidth() + 10, 500);
        g.drawImage(youLoseImage, null, 250, 400);

        g.dispose();
        return image;
    }
}