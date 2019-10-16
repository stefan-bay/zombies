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

    private BufferedImage youLastedImage;
    private BufferedImage secondsImage;
    private BufferedImage secondsSurvivedImage;

    private int secondsSurvived;

    /**
     * Holds widths of each number png
     */
    private static int [] numberwidth = {
            15, 14, 19, 21, 19, 22, 19, 20, 17, 17
    };

    EndScreen(int killCount, int secondsSurvived) {
        super(-400, -400, 0, 0);
        this.setColliding(false);

        this.secondsSurvived = secondsSurvived;

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

        try {
            youLastedImage = ImageIO.read(new File("res/UI/You Lasted.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            secondsImage = ImageIO.read(new File("res/UI/seconds.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }



        this.killCountImage = NumberHelper.imageForNumber(killCount);
        this.secondsSurvivedImage = NumberHelper.imageForNumber(secondsSurvived);
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

        g.drawImage(youLoseImage, null, 250, 400);
        g.drawImage(killsImage, null, 300, 500);
        g.drawImage(killCountImage, null, 300 + killsImage.getWidth() + 10, 500);

        int you_lasted_total_seconds = youLastedImage.getWidth() + secondsSurvivedImage.getWidth() + NumberHelper.imageWidthForNumber(secondsSurvived);

        g.drawImage(youLastedImage, null, 200, 550);
        g.drawImage(secondsSurvivedImage, null, 200 + youLastedImage.getWidth() + 10 , 550);
        g.drawImage(secondsImage, null, 200 + youLastedImage.getWidth() + 10 + secondsSurvivedImage.getWidth() + 10, 550);

        g.dispose();
        return image;
    }
}