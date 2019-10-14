import java.awt.*;
import java.awt.geom.Point2D;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.TimerTask;

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
    private int projectileSpeed = 15;

    public boolean isColliding() {
        return colliding;
    }

    public void setColliding(boolean colliding) {
        this.colliding = colliding;
    }

    public int getHealth() {
        return this.health;
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
        return intersects(this.getX() - this.getWidth()/2, this.getY()- this.getHeight()/2, other);
    }

    boolean intersects(double x, double y, GameObject other) {
        double otherX = other.getX() - other.getWidth()/2;
        double otherY = other.getY() - other.getHeight()/2;
        if (x < otherX + other.getWidth()  &&
                x + this.getWidth() > otherX &&
               y < otherY + other.getHeight() &&
                y + this.getHeight() > otherY) {
            return true;
        }
        return false;
    }
    
    void fall(ArrayList<GameObject> others) {
        move(0.0,1.0, others);

    }

    boolean canMove(double x, double y, ArrayList<GameObject> others) {
        for(GameObject other : others) {
            // translate intersect to the top left.
            if (other != this && other.isColliding() && isColliding() && this.intersects(getX() + x - getWidth()/2, getY() + y - getHeight()/2, other)) {
                return false;
            }
        }
        return true;
    }
    
    void move(double xDelta, double yDelta, ArrayList<GameObject> objects) {
        if (canMove(xDelta, yDelta, objects)) {
            setX(getX() + xDelta);
            setY(getY() + yDelta);
        }
    }

    Projectile getProjectile(Point2D.Double direction) {
        double directionLength = Math.sqrt(Math.pow(direction.getX(),2) + Math.pow(direction.getY(),2));
        // Unit vector of direction
        Point2D.Double unitDirection = new Point2D.Double(direction.getX()/directionLength, direction.getY()/directionLength);
        // Length of cube radius
        double cubeRadius = Math.sqrt(Math.pow(getWidth(),2) + Math.pow(getHeight(),2));
        cubeRadius/=2;
        double projectileX = getX() + unitDirection.getX() * cubeRadius;
        double projectileY = getY() +  unitDirection.getY() * cubeRadius;
        Point2D.Double newProjectileCoords = new Point2D.Double(unitDirection.getX() * projectileSpeed, unitDirection.getY() * projectileSpeed);
        return new Projectile( projectileX, projectileY, newProjectileCoords);
    }

    abstract Image getImage();
}
