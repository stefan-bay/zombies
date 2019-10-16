import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Projectile extends GameObject{
    Image thisImage;
    static int size = 15;
    Point2D.Double direction;
    int damage = 10;

    Projectile(double x,double y, Point2D.Double dir) {
        super(x,y,size,size);
        thisImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics g = thisImage.getGraphics();
        g.setColor(new Color(0,0,0,0));
        g.fillRect(0,0,size,size);
        g.setColor(Color.white);
        g.drawOval(size/4,size/4,size/2,size/2);
        g.setColor(Color.RED);
        g.fillOval(size/4,size/4,size/2,size/2);
        g.dispose();
        direction = dir;
        setColliding(false);
    }

    @Override
    void update(ArrayList<GameObject> objects) {
        super.update(objects);
        // Update Pos
        move(direction.getX(), direction.getY(), objects);
        //CheckHit
        for (int i = 0; i < objects.size(); i++) {
            GameObject object = objects.get(i);
            if (!(this == object) && object.isColliding() && this.intersects(object)) {
                object.damage(damage);
                objects.add(new HitExplosion(this.getX(), this.getY()));

                this.setShouldRemove(true);
                break;
            }
        }
    }

    @Override
    Image getImage() {
        return thisImage;
    }
}
