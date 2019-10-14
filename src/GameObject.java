import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

abstract class GameObject {

    // Represents game object coordinates and hitbox size within the scene it is in.
    private double x;
    private double y;
    private double width;
    private double height;
    private boolean hasGravity;
    private boolean colliding = true;
    private int health;
    private boolean destroyable;

    public boolean isColliding() {
        return colliding;
    }

    public void setColliding(boolean colliding) {
        this.colliding = colliding;
    }

    public double getMaxX() {
        return this.x + this.width;
    }

    public double getMaxY() {
        return this.y + this.height;
    }

    public double getMinX() {
        return this.x;
    }

    public double getMinY() {
        return this.y;
    }

    public boolean hasGravity() {
        return hasGravity;
    }

    public void setHasGravity(boolean hasGravity) {
        this.hasGravity = hasGravity;
    }

    GameObject(double x, double y, double width, double height) {
        this(x,y,width,height,-1);
    }
    GameObject(double x, double y, double width, double height, int health) {
        if(health!=-1) {
            this.health = health;
            this.destroyable = true;
        } else{
            this.destroyable = false;
        }
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * @param damage amount of damage to deal to this object
     * @return if this object is out of health;
     */
    boolean damage(int damage) {
        if(destroyable) {
            this.health -= damage;
            if(this.health <= 0) {
                return true;
            }
        }
        return false;
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
        return colliding && other.isColliding() && intersects(other);
    }

    void update(ArrayList<GameObject> gameObjects) {
        if(hasGravity) {
            fall(gameObjects);
        }
    }

    boolean intersects(GameObject other) {
        return intersects(this.getX(), this.getY(), other);
    }

    boolean intersects(double x, double y, GameObject other) {
        if (x < other.getX() + other.getWidth() &&
                x + this.getWidth() > other.getX() &&
               y < other.getY() + other.getHeight() &&
                y + this.getHeight() > other.getY()) {
            return true;
        }
        return false;
    }
    
    void fall(ArrayList<GameObject> others) {
        if(canMove(0,1, others)) {
            move(0.0,1.0);
        }
    }

    boolean canMove(double x, double y, ArrayList<GameObject> others) {
        for(GameObject other : others) {
            if (other != this && other.isColliding() && isColliding() && this.intersects(getX() + x, getY() + y, other)) {
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
