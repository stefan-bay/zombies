import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class EndScreen extends GameObject{
    ArrayList<BufferedImage> endScreenList, youLoseList, killCountList;
    Animation bloodAnimation, youLoseAnimation, killsAnimation;

    public EndScreen() {
        super(-400, -400, 0, 0);
        endScreenList = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            try {
                endScreenList.add(ImageIO.read(new File("res/blood_gif/frame_" + i + ".png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.setColliding(false);
        bloodAnimation = new Animation(endScreenList, 100);

        youLoseList = new ArrayList<>();
        for (int i = 45; i < 50; i+=2) {
            try {
                youLoseList.add(ImageIO.read(new File("res/YouLose_animation/YouLose" + i + ".png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        youLoseAnimation = new Animation(youLoseList, 100);

        killCountList = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            try {
                killCountList.add(ImageIO.read(new File("res/UI/kills.png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        killsAnimation = new Animation(killCountList, 100);
    }


    @Override
    Image getImage() {
        BufferedImage image = bloodAnimation.nextImage();
        BufferedImage youLoseImage = youLoseAnimation.nextImage();
        BufferedImage killsImage = killsAnimation.nextImage();
        Color transparent = new Color(50, 50,50,0);

        Graphics2D g = (Graphics2D)image.getGraphics();
        //g.setColor(Color.red);

        g.setColor(transparent);
        g.drawRect(300, 500, killsImage.getWidth(), killsImage.getHeight());
        g.drawImage(killsImage, null, 300, 500);

        g.setColor(transparent);
        g.drawRect(300,400, youLoseImage.getWidth(), youLoseImage.getHeight());
        g.drawImage(youLoseImage, null, 250, 400);

        g.dispose();
        return image;
    }
}