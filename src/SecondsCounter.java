import java.awt.*;

/**
 * GameObject representing a counter displaying how many seconds have elapsed
 */
public class SecondsCounter extends GameObject {

    // cooldown so that counter updates only once a second
    private Cooldown secondsCoolDown = new Cooldown(1000);

    // seconds passed
    private int seconds = 0;

    // padding from point set to display at
    private int buffer = 10;

    SecondsCounter(double x, double y, double width, double height) {
        super(x, y, width, height);

        this.setColliding(false);
    }

    void setBuffer(int buf) {
        this.buffer = buf;
    }

    // debug
    void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    int getSeconds() {
        return this.seconds;
    }

    /**
     * Display in proper x position
     */
    @Override
    double getX() {
        return super.getX() + this.getWidth()/2 + buffer;
    }

    /**
     * Display in proper y position
     */
    @Override
    double getY() {
        return super.getY() + this.getHeight()/2 + buffer;
    }

    /**
     * Width of image to be created
     */
    @Override
    double getWidth() {
        return NumberHelper.imageWidthForNumber(seconds);
    }

    /**
     * Image reflecting number of seconds that have passed
     */
    @Override
    Image getImage() {
        if (secondsCoolDown.startCooldown())
            seconds++;

        return NumberHelper.imageForNumber(seconds);
    }
}
