import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Represents an enemy to the player.
 */
public class Enemy extends GameObject {
    // The image of this enemy.
    Image enemyImage;
    // The cooldown on when this enemy can fire.
    Cooldown fireCooldown = new Cooldown(1000);
    // The movement speed of this enemy
    int enemyMoveSpeed = 3;
    // True if this enemy fires projectiles
    boolean canFire = true;
    // The speed of the projectiles this enemy fires.
    int enemyProjectileSpeed = 15;
    // The melee damage of this enemy.
    int meleeDamage = 5;

    // Sets the movement speed of this enemy.
    public void setEnemyMoveSpeed(int enemyMoveSpeed) {
        this.enemyMoveSpeed = enemyMoveSpeed;
    }

    /**
     * Sets the projectile speed across the screen of this enemies projectiles.
     * @param enemyProjectileSpeed the new speed of this
     */
    public void setEnemyProjectileSpeed(int enemyProjectileSpeed) {
        this.enemyProjectileSpeed = enemyProjectileSpeed;
    }

    /**
     * Sets whether or not this enemy fires projectiles.
     * @param canFire true if this enemy can fire projectiles.
     */
    public void setCanFire(boolean canFire) {
        this.canFire = canFire;
    }

    /**
     * Construct an new enemy.
     * @param x the x coordinate of this enemy.
     * @param y the y coordinate of this enemy.
     * @param width the width of this enemy
     * @param height the height of this enemy.
     * @param health the health of this enemy.
     */
    public Enemy(double x, double y, double width, double height, int health) {
        super(x, y, width, height, health);
        enemyImage = new BufferedImage((int) width,(int) height, BufferedImage.TYPE_INT_RGB);
        Graphics enemyGraphics = enemyImage.getGraphics();
        enemyGraphics.setColor(Color.DARK_GRAY);
        enemyGraphics.fillRect((int)0,(int)0,(int)width,(int)height);
    }

    /**
     * @return true if this enemy can fire a projectile.
     */
    boolean canFire() {
        return canFire && fireCooldown.startCooldown();
    }

    /**
     * Get a new projectile if this enemy can fire and move the enemy
     * @param objects the gameobjects of thsi game so the enemy can move
     * @param player the player to calculate the melee range.
     * @return a new projectile if this enemy is firing.
     */
    Projectile update(ArrayList<GameObject> objects, GameObject player){
        // Compute the difference between this enemys position and the players.
        double xDelta = player.getX() - getX();
        double yDelta = player.getY() - getY();
        // Make direction unit vector.
        double length = Math.sqrt(Math.pow(xDelta, 2) + Math.pow(yDelta, 2));
        xDelta /= length;
        yDelta /= length;
        // Move the enemy in the direction of the player by movespeed.
        move(xDelta * enemyMoveSpeed, yDelta * enemyMoveSpeed, objects);
        // enemy only fires within 400 pixels of player
        if (length < 400)
            if(canFire()) {
                return getProjectile(new Point2D.Double(xDelta * enemyProjectileSpeed, yDelta * enemyProjectileSpeed));
            }
        return null;
    }

    @Override
    boolean canMove(double x, double y, ArrayList<GameObject> others) {
        for(GameObject other : others) {
            // Melee the player if this enemy is within 50px
            if(other instanceof Player && this.distanceTo(other) <= 50) {
                other.damage(meleeDamage);
            }
            // Don't let this enemy move if it would hit another colliding object.
            if (other != this && other.isColliding() && isColliding() && this.intersects(getX() + x - getWidth()/2, getY() + y - getHeight()/2, other)) {
                return false;
            }
        }
        return true;
    }


    @Override
    Image getImage() {
        return enemyImage;
    }
}
