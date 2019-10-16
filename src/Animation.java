import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
//import java.awt.*;
//import java.awt.geom.AffineTransform;

public class Animation {
    private ArrayList<BufferedImage> frames;
    private int count;
    private int location;
    private Cooldown animationCooldown;

    Animation (ArrayList<BufferedImage> frames, int refreshRate) {
        this.frames = frames;
        location = 0; // first frame
        count = frames.size();

         animationCooldown = new Cooldown(refreshRate);
    }

    private boolean shouldUpdateFrame() {
        return animationCooldown.startCooldown();
    }

    BufferedImage nextImage() {
        if (shouldUpdateFrame()) {
            if (location == count - 1) // last frame
                location = 0;          // back to first frame
            else
                location++;
        }

        return frames.get(location);
    }

    BufferedImage previousImage() {
        if (location == 0)
            location = count - 1;
        else
            location--;
        return frames.get(location);
    }

    public void reset() {
        this.location = 0;
    }

    public int getLocation() {
        return location;
    }

    public static ArrayList<BufferedImage> getListForPath(String path, int start, int stop) {
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
}
