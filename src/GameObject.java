import java.awt.*;

abstract class GameObject {

    // Represents game object coordinates and hitbox size within the scene it is in.
    private double x;
    private double y;
    private double width;
    private double height;

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

    abstract boolean collidesWith(GameObject other);

    abstract Image getImage();
}
