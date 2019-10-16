import java.awt.*;

public class SecondsCounter extends GameObject {

    private Cooldown secondsCoolDown = new Cooldown(1000);

    private int seconds = 0;

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

    @Override
    double getX() {
        return super.getX() + this.getWidth()/2 + buffer;
    }

    @Override
    double getY() {
        return super.getY() + this.getHeight()/2 + buffer;
    }

    @Override
    double getWidth() {
        return NumberHelper.imageWidthForNumber(seconds);
    }

    @Override
    Image getImage() {
        if (secondsCoolDown.startCooldown())
            seconds++;

        return NumberHelper.imageForNumber(seconds);
    }
}
