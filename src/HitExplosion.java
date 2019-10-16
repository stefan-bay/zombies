import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

/**
 * GameObject representing the explosion of blood from an enemy or player
 */
public class HitExplosion extends GameObject {

    Random random = new Random();

    private static int frames = 5;

    private static final int upperDripBound = 6;
    private static final int lowerDripBound = 1;

    private static final int upperOffsetBound = 5;
    private static final int lowerOffsetBound = -5;

    // size of explosion
    private static final int size = 40;

    private static final int [] color_codes = {
            0xEB0905,
            0x690402,
            0xD9423F,
            0x7F2F2E,
            0x4F2C2B
    };

    private Animation splatterAnimation;

    HitExplosion(double x, double y) {
        super(x, y, size, size);
        this.setColliding(false);

        ArrayList<BufferedImage> splatterFrames = new ArrayList<>();
        for (int i = 0; i < frames; i++)
            splatterFrames.add(createRandomSplatter(i + 1));

        splatterAnimation = new Animation(splatterFrames, 170);
    }

    /**
     * Random color from color palette
     */
    private Color randomColor() {
        return new Color(color_codes[random.nextInt(5)]);
    }

    /**
     * Create iteration of splatter
     * @param n iteration
     */
    private BufferedImage createRandomSplatter(int n) {
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

    /**
     * @return Animated explosion
     */
    @Override
    Image getImage() {
        // remove once animation has played through
        if (splatterAnimation.getLocation() == frames - 1)
            this.setShouldRemove(true);

        return splatterAnimation.nextImage();
    }
}
