import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class HitExplosion extends GameObject {

    Random random = new Random();

    private static int buffer = 30;
    private static int frames = 5;

    private static int upperDripBound = 6;
    private static int lowerDripBound = 1;

    private static int upperOffsetBound = 5;
    private static int lowerOffsetBound = -5;

    private static int size = 40;

    static int [] color_codes = {
            0xEB0905,
            0x690402,
            0xD9423F,
            0x7F2F2E,
            0x4F2C2B
    };

    Animation splatterAnimation;

    HitExplosion(double x, double y) {
        super(x, y, size, size);
        this.setColliding(false);

        ArrayList<BufferedImage> splatterFrames = new ArrayList<>();
        for (int i = 0; i < frames; i++)
            splatterFrames.add(createRandomSplatter(i + 1));

        splatterAnimation = new Animation(splatterFrames, 170);
    }

    private Color randomColor() {
        return new Color(color_codes[random.nextInt(5)]);
    }

    BufferedImage createRandomSplatter(int n) {
        int width = (int)this.getWidth();
        int height = (int)this.getHeight();

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D img_g = (Graphics2D)image.getGraphics();
        img_g.translate(width/2, height/2);

        double theta = 0;
        int drips = n * 3;

        theta += (2 * Math.PI) / (double)drips;
        for (int i = 0; i < frames; i++) {
            img_g.setColor(randomColor());
            for (int j = 0; j < drips; j++) {
                double x = n * 3 * Math.cos(theta);
                double y = n * 3 * Math.sin(theta);
                double w = random.nextInt(upperDripBound - lowerDripBound) + lowerDripBound;
                double h = random.nextInt(upperDripBound - lowerDripBound) + lowerDripBound;
                double offsetx = random.nextInt(upperOffsetBound - lowerOffsetBound) + lowerOffsetBound;
                double offsety = random.nextInt(upperOffsetBound - lowerOffsetBound) + lowerOffsetBound;

                Rectangle.Double drip = new Rectangle.Double(x + offsetx, y + offsety, w, h);

                img_g.fill(drip);

                theta += (2* Math.PI) / (double)drips;
            }
        }

        img_g.dispose();

        return image;
    }

    @Override
    Image getImage() {
        if (splatterAnimation.getLocation() == frames - 1)
            this.setShouldRemove(true);

        return splatterAnimation.nextImage();
    }
}
