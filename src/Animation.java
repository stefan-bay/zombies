import java.awt.image.BufferedImage;
import java.util.ArrayList;
//import java.awt.*;
//import java.awt.geom.AffineTransform;

/**
 * Class that represents an animation as a series of BufferedImages.
 */
public class Animation {
    // The frames for this image.
    private ArrayList<BufferedImage> frames;
    // The number of images to render.
    private int count;
    // The location of the animation in the list of frames.
    private int location;
    // The cooldown between frames.
    private Cooldown animationCooldown;

    /**
     * Creates an animation.
     * @param frames the frames of this animation
     * @param refreshRate the cooldown timer of this animation
     */
    Animation (ArrayList<BufferedImage> frames, int refreshRate) {
        this.frames = frames;
        location = 0; // first frame
        count = frames.size();

         animationCooldown = new Cooldown(refreshRate);
    }

    /**
     * Tries to start the cooldown.
     * @return true if the cooldown is started and the next frame can be shown.
     */
    private boolean shouldUpdateFrame() {
        return animationCooldown.startCooldown();
    }

    /**
     * Get the next image of this animation.
     * @return the next image of this animation.
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
     * get the previous image of this animaiton.
     * @return the previous image of this animation
     */
    BufferedImage previousImage() {
        if (location == 0)
            location = count - 1;
        else
            location--;
        return frames.get(location);
    }

    /**
     * reset this animation to the beginning of the frames.
     */
    public void reset() {
        this.location = 0;
    }

    /**
     * get the location of current image in the image array.
     * @return the location of the current image.
     */
    public int getLocation() {
        return location;
    }
}
