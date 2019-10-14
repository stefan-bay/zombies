import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Enemy extends GameObject {
    Image enemyImage;
    int enemyProjectileSpeed = 3;

    public Enemy(double x, double y, double width, double height, int health) {
        super(x, y, width, height, health);
        enemyImage = new BufferedImage((int) width,(int) height, BufferedImage.TYPE_INT_RGB);
        Graphics enemyGraphics = enemyImage.getGraphics();
        enemyGraphics.setColor(Color.DARK_GRAY);
        enemyGraphics.fillRect((int)0,(int)0,(int)width,(int)height);
    }

    Projectile update(ArrayList<GameObject> objects, double playerX, double playerY){
        double xDelta = playerX - getX();
        double yDelta = playerY - getY();
        double length = Math.sqrt(Math.pow(xDelta, 2) + Math.pow(yDelta, 2));
        xDelta /= length;
        yDelta /= length;
        move(xDelta, yDelta, objects);
        return getProjectile(new Point2D.Double(xDelta * enemyProjectileSpeed, yDelta * enemyProjectileSpeed));
    }

    @Override
    Image getImage() {
        return enemyImage;
    }
}
