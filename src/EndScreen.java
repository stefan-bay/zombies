import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * GameObject to handle ending screen
 */
public class EndScreen extends GameObject{
    private Animation bloodAnimation;

    private BufferedImage youLoseImage;
    private BufferedImage killsImage;

    private BufferedImage killCountImage;

    private BufferedImage youLastedImage;
    private BufferedImage secondsImage;
    private BufferedImage secondsSurvivedImage;

    EndScreen(int killCount, int secondsSurvived) {
        super(-400, -400, 0, 0);
        this.setColliding(false);

        ArrayList<BufferedImage> endScreenList = Animation.getListForPath("res/blood_gif/frame_", 0, 15);
        bloodAnimation = new Animation(endScreenList, 100);

        // load images

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

        // create images that rely on numbers
        this.killCountImage = NumberHelper.imageForNumber(killCount);
        this.secondsSurvivedImage = NumberHelper.imageForNumber(secondsSurvived);
    }

    /**
     * Draw end screen with animation
     */
    @Override
    Image getImage() {
        BufferedImage image = bloodAnimation.nextImage(); // dripping blood
        BufferedImage youLoseImage = this.youLoseImage;
        BufferedImage killsImage = this.killsImage;

        Color transparent = new Color(50, 50,50,0);

        // draw over blood animation
        Graphics2D g = (Graphics2D)image.getGraphics();
        g.setColor(transparent);

        int iw = image.getWidth();
        int ih = image.getHeight();

        int cx = iw / 2;
        int cy = ih / 2;

        g.drawImage(youLoseImage, null, cx - youLoseImage.getWidth()/2, cy - ih/3 );

        g.drawImage(killsImage, null, cx - killCountImage.getWidth()/2 - killsImage.getWidth() - 15, cy - killsImage.getHeight()/2);
        g.drawImage(killCountImage, null, cx - killCountImage.getWidth()/2, cy - killCountImage.getHeight()/2);

        g.drawImage(youLastedImage, null, cx - youLastedImage.getWidth() - secondsSurvivedImage.getWidth()/2 - 15, cy + ih/3);
        g.drawImage(secondsSurvivedImage, null, cx - secondsSurvivedImage.getWidth()/2, cy + ih/3);
        g.drawImage(secondsImage, null, cx + secondsSurvivedImage.getWidth()/2 + 15, cy + ih/3);

        g.dispose();
        return image;
    }
}