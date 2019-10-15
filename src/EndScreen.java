import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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

    private int killCount;

    EndScreen(int killCount) {
        super(-400, -400, 0, 0);
        this.setColliding(false);
        this.killCount = killCount;

        ArrayList<BufferedImage> endScreenList = getListForPath("res/blood_gif/frame_", 0, 15);
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

        generateKillCountImage();
    }

    private void generateKillCountImage() {
        ArrayList<BufferedImage> killCountList = getListForPath("res/numbers/", 0, 9);
        String killString = Integer.toString(killCount);
        int number_of_digits = killString.length();
        int [] numbers_to_display = new int[number_of_digits];
        int img_width = 0; // total width of image (depends on which numbers are shown)

        // fill numbers to display with correlating digits
        for (int i = 0; i < number_of_digits; i++) {
            int digit = Character.getNumericValue(killString.charAt(i));
            System.out.println(digit);
            numbers_to_display[i] = digit;
            img_width += numberwidth[digit];
        }

        //
        killCountImage = new BufferedImage(img_width, 22, BufferedImage.TYPE_INT_ARGB);
        Graphics2D numbers_g = (Graphics2D) killCountImage.createGraphics();

        // draw
        int loc = 0; // location in image
        for (int i = 0; i < number_of_digits; i++) {
            int digit = numbers_to_display[i];
            numbers_g.drawImage(killCountList.get(digit), null, loc, 0);
            loc += numberwidth[digit]; // update location in image
        }

        numbers_g.dispose();
    }

    ArrayList<BufferedImage> getListForPath(String path, int start, int stop) {
        ArrayList<BufferedImage> list = new ArrayList<>();
        for (int i = start; i < stop + 1; i++) {
            try {
                list.add(ImageIO.read(new File(path + i + ".png")));
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return list;
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