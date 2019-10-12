import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Animation {
    ArrayList<BufferedImage> frames;
    int count;
    int location;

    Animation (ArrayList<BufferedImage> frames) {
        this.frames = frames;
        location = 0; // first frame
        count = frames.size();
    }

    public BufferedImage nextImage() {
        if (location == count - 1) // last frame
            location = 0;          // back to first frame
        else
            location++;

        return frames.get(location);
    }

    public BufferedImage previousImage() {
        if (location == 0)
            location = count - 1;
        else
            location--;

        return frames.get(location);
    }
}
