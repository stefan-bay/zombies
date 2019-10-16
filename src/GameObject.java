import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * Class that represents an object within the game that can be drawn.
 */
abstract class GameObject {
    // Represents game object coordinates and hitbox size within the scene it is in.
    private double x;
    private double y;
    private double width;
    private double height;

    // True if this object can collide with other game objects
    private boolean colliding = true;
    // The health of this object.
    private int health;
    // True if this object can be destroyed by damage.
    private boolean destroyable;
    // The speed of projectiles produced by this GameObject.
    private int projectileSpeed = 15;

    // True if this object has been destroyed and should be removed.
    private boolean shouldRemove;

    // True if this object collides with other colliding GameObjects.
    public boolean isColliding() {
        return colliding;
    }

    /**
     * Sets whether or not this GameObject should collide with other GameObjects.
     * @param colliding
     */
    public void setColliding(boolean colliding) {
        this.colliding = colliding;
    }

    /**
     * Get the health of this object.
     */
    public int getHealth() {
        return this.health;
    }

    /**
     * Sets the health of this GameObject
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * @return the maximum x coordinate of this object.
     */
    public double getMaxX() {
        return this.x + this.width;
    }

    /**
     * @return the maximum y coordinate of this object.
     */
    public double getMaxY() {
        return this.y + this.height;
    }

    /**
     * @return the minimum x coordinate of this object.
     */
    public double getMinX() {
        return this.x;
    }

    /**
     * @return the minimum y coordinate of this object.
     */
    public double getMinY() {
        return this.y;
    }

    /**
     * Create a new GameObject with the specified height and width.
     */
    GameObject(double x, double y, double width, double height) {
        this(x,y,width,height,-1);
    }

    /**
     * Create a new GameObject that has health. If health is -1 it should not have health and be non-destroyable.
     */
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

        this.shouldRemove = false;
    }

    /**
     * @param damage amount of damage to deal to this object
     * @return if this object is out of health;
     */
    void damage(int damage) {
        if(destroyable) {
            this.health -= damage;
            if(this.health <= 0) {
                this.shouldRemove = true;
            }
        }
    }

    /**
     * Sets whether or not this object should be removed from the scene.
     */
    void setShouldRemove(boolean shouldRemove) {
        this.shouldRemove = true;
    }

    /**
     * Get whether or not this object should be removed from the scene.
     * @return
     */
    boolean shouldRemove() {
        return this.shouldRemove;
    }

    /**
     * @return the x value of this object
     */
    double getX() {
        return x;
    }

    /**
     * @return the y value of this object.
     */
    double getY() {
        return y;
    }

    /**
     * @return the width of this object
     */
    double getWidth() {
        return width;
    }

    /**
     * @return the height of this object
     */
    double getHeight() {
        return height;
    }

    /**
     * @param x the new x value of this object.
     */
    void setX(double x) { this.x = x; }

    /**
     * @param y the y value of this object
     */
    void setY(double y) {
        this.y = y;
    }

    /**
     * Sets the width of this object.
     */
    void setWidth(double width) {
        this.width = width;
    }

    /**
     * Sets th height of this object.
     */
    void setHeight(double height) {
        this.height = height;
    }

    /**
     * Update this object. Currently does nothing, but allows other subclasses to override this method.
     */
    void update(ArrayList<GameObject> gameObjects){}

    /**
     * @return true if this object's hitbox intersects the other object's hitbox
     */
    boolean intersects(GameObject other) {
        return intersects(this.getX() - this.getWidth()/2, this.getY()- this.getHeight()/2, other);
    }

    /**
     * @return true if this objects hitbox would intersect another objects hitbox if this object were moved by x in the
     * x direction and y in the y direction.
     */
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

    /**
     * Returns true if this object would not collide with any other if it were moved by the specified x and y coordinate
     * in their respective directions.
     */
    boolean canMove(double x, double y, ArrayList<GameObject> others) {
        for(GameObject other : others) {
            // translate intersect to the top left.
            if (other != this && other.isColliding() && isColliding() && this.intersects(getX() + x - getWidth()/2, getY() + y - getHeight()/2, other)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Try to reposition this object by the specified deltas if it would not collide.
     */
    void move(double xDelta, double yDelta, ArrayList<GameObject> objects) {
        if (canMove(xDelta, yDelta, objects)) {
            setX(getX() + xDelta);
            setY(getY() + yDelta);
        }
    }

    /**
     * Spawns a projectile in the specified direction with a radius away from this object.
     * @return the projectile
     */
    Projectile getProjectile(Point2D.Double direction) {
        double directionLength = Math.sqrt(Math.pow(direction.getX(),2) + Math.pow(direction.getY(),2));
        // Unit vector of direction
        Point2D.Double unitDirection = new Point2D.Double(direction.getX()/directionLength, direction.getY()/directionLength);
        // Length of cube diameter
        double cubeRadius = Math.sqrt(Math.pow(getWidth(),2) + Math.pow(getHeight(),2));
        // scale diameter to get radius
        cubeRadius/=2;
        // Create a new direction for the projectile based on the unit direction we want and the speed it should move in.
        double projectileX = getX() + unitDirection.getX() * cubeRadius;
        double projectileY = getY() +  unitDirection.getY() * cubeRadius;
        Point2D.Double newProjectileCoords = new Point2D.Double(unitDirection.getX() * projectileSpeed, unitDirection.getY() * projectileSpeed);
        return new Projectile( projectileX, projectileY, newProjectileCoords);
    }

    /**
     * Compute the distance between this GameObject and another GameObject.
     */
    double distanceTo(GameObject other) {
        double x = this.getX() - other.getX();
        double y = this.getY() - other.getY();
        double distance = Math.sqrt(Math.pow(x,2) + Math.pow(y,2));
        return distance;
    }

    abstract Image getImage();
}
