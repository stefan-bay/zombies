import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class EndScreen extends GameObject{
    ArrayList<BufferedImage> endScreen;
    Animation bloodAnimation;

    public EndScreen() {
        super(-400, -400, 0, 0);
        endScreen = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            try {
                endScreen.add(ImageIO.read(new File("res/blood_gif/frame_" + i + ".png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.setColliding(false);
        bloodAnimation = new Animation(endScreen, 100);
    }

    @Override
    Image getImage() {
        return bloodAnimation.nextImage();
    }
}
