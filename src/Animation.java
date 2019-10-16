import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Animate buffered images
 */
public class Animation {
    private ArrayList<BufferedImage> frames;

    // number of frames
    private int count;

    // location in animation
    private int location;

    // handles when frame updates
    private Cooldown animationCooldown;

    /**
     * @param frames ArrayList of frames that compose the animation
     * @param refreshRate rate in milliseconds
     */
    Animation (ArrayList<BufferedImage> frames, int refreshRate) {
        this.frames = frames;
        location = 0; // first frame
        count = frames.size();

         animationCooldown = new Cooldown(refreshRate);
    }

    /**
     * determine go to next frame
     */
    private boolean shouldUpdateFrame() {
        return animationCooldown.startCooldown();
    }

    /**
     * next frame in animation, restarts when gets to end
     * @return next frame
     */
    BufferedImage nextImage() {
        if (shouldUpdateFrame()) {
            if (location == count - 1) // last frame
                location = 0;          // back to first frame
            else
                location++;
        }

        return frames.get(location);
    }

    /**
     * @return location of current frame
     */
    int getLocation() {
        return location;
    }

    /**
     * Helper method to extract animation pngs
     * @param path path to animation folder
     * @param start starting index
     * @param stop stopping index
     * @return images in folder
     */
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
