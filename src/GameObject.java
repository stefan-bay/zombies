import java.awt.*;

abstract class GameObject {

    // Represents game object coordinates and hitbox size within the scene it is in.
    private double x;
    private double y;
    private double width;
    private double height;

    double getMaxX() {
        return getX() + getWidth() / 2;
    }
    double getMinX() {
        return getX() - getWidth() / 2;
    }
    double getMaxY() {
        return getY() + getHeight() / 2;
    }
    double getMinY() {
        return getY() + getHeight() / 2;
    }

    GameObject(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    double getX() {
        return x;
    }

    double getY() {
        return y;
    }

    double getWidth() {
        return width;
    }

    double getHeight() {
        return height;
    }

    void setX(double x) {
        this.x = x;
    }
    void setY(double y) {
        this.y = y;
    }
    void setWidth(double width) {
        this.width = width;
    }
    void setHeight(double height) {
        this.height = height;
    }

    boolean collidesWith(GameObject other) {
        boolean xOverlap = this.getMaxX() > other.getMinX() && this.getMaxX() < other.getMaxX() ||
                other.getMaxX() > this.getMinX() && other.getMaxX() < this.getMaxX();
        boolean yOverlap = this.getMaxY() > other.getMinY() && this.getMaxY() < other.getMaxY() ||
                other.getMaxX() > this.getMinY() && other.getMaxY() < this.getMaxY();
        return xOverlap && yOverlap;
    }

    abstract Image getImage();
}
