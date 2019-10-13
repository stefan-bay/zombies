import java.awt.*;
import java.util.ArrayList;

abstract class GameObject {

    // Represents game object coordinates and hitbox size within the scene it is in.
    private double x;
    private double y;
    private double width;
    private double height;
    private boolean hasGravity;
    private boolean colliding = true;

    public boolean isColliding() {
        return colliding;
    }

    public void setColliding(boolean colliding) {
        this.colliding = colliding;
    }


    public boolean hasGravity() {
        return hasGravity;
    }

    public void setHasGravity(boolean hasGravity) {
        this.hasGravity = hasGravity;
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

    void setX(double x) { this.x = x; }
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
        return colliding && intersects(other);
    }

    void update(ArrayList<GameObject> gameObjects) {
        if(hasGravity) {
            fall(gameObjects);
        }
    }

    boolean intersects(GameObject other) {
        if (this.getX() < other.getX() + other.getWidth() &&
                this.getX() + this.getWidth() > other.getX() &&
                this.getY() < other.getY() + other.getHeight() &&
                this.getY() + this.getHeight() > other.getY()) {
            return true;
        }

        return false;
    }
    
    void fall(ArrayList<GameObject> others) {
        if(canMove(0,1, others)) {
            move(0.0,1.0);
        }
    }

    private boolean canMove(double x, double y, ArrayList<GameObject> others) {
        for(GameObject other : others) {
            if (other != this && other.isColliding() && this.collidesWith(other)) {
                return false;
            }
        }
        return true;
    }
    
    void move(double xDelta, double yDelta) {
        setX(getX() + xDelta);
        setY(getY() + yDelta);
    }

    abstract Image getImage();
}
