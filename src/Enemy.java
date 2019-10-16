import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Enemy extends GameObject {
    Image enemyImage;
    Cooldown fireCooldown = new Cooldown(1000);
    int enemyMoveSpeed = 3;
    boolean canFire = true;
    int enemyProjectileSpeed = 15;
    int meleeDamage = 5;

    public int getEnemyMoveSpeed() {
        return enemyMoveSpeed;
    }

    public void setEnemyMoveSpeed(int enemyMoveSpeed) {
        this.enemyMoveSpeed = enemyMoveSpeed;
    }

    public int getEnemyProjectileSpeed() {
        return enemyProjectileSpeed;
    }

    public void setEnemyProjectileSpeed(int enemyProjectileSpeed) {
        this.enemyProjectileSpeed = enemyProjectileSpeed;
    }


    public boolean isCanFire() {
        return canFire;
    }

    public void setCanFire(boolean canFire) {
        this.canFire = canFire;
    }




    public Enemy(double x, double y, double width, double height, int health) {
        super(x, y, width, height, health);
        enemyImage = new BufferedImage((int) width,(int) height, BufferedImage.TYPE_INT_RGB);
        Graphics enemyGraphics = enemyImage.getGraphics();
        enemyGraphics.setColor(Color.DARK_GRAY);
        enemyGraphics.fillRect((int)0,(int)0,(int)width,(int)height);
    }

    boolean canFire() {
        return canFire && fireCooldown.startCooldown();
    }

    Projectile update(ArrayList<GameObject> objects, GameObject player){
        double xDelta = player.getX() - getX();
        double yDelta = player.getY() - getY();
        // Make direction unit vector.
        double length = Math.sqrt(Math.pow(xDelta, 2) + Math.pow(yDelta, 2));
        xDelta /= length;
        yDelta /= length;
        move(xDelta * enemyMoveSpeed, yDelta * enemyMoveSpeed, objects);
        if(!canFire && this.intersects(player)) {
            player.damage(meleeDamage);
        }
        // enemy only fires within 400 pixels of player
        if (length < 400)
            if(canFire()) {
                return getProjectile(new Point2D.Double(xDelta * enemyProjectileSpeed, yDelta * enemyProjectileSpeed));
            }
        return null;
    }

    @Override
    boolean canMove(double x, double y, ArrayList<GameObject> others) {
        double radius = Math.sqrt(Math.pow(getWidth()/2,2) + Math.pow(getHeight()/2,2));

        for(GameObject other : others) {
            // translate intersect to the top left.
            if(other instanceof Player && this.distanceTo(other) <= 50) {
                other.damage(meleeDamage);
            }
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
