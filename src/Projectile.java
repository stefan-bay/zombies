import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Projectile extends GameObject{
    Image thisImage;
    static double size = 10;
    Point2D.Double direction;
    int damage = 10;

    Projectile(double x,double y, Point2D.Double dir) {
        super(x,y,size,size);
        thisImage = new BufferedImage((int) size,(int) size,BufferedImage.TYPE_INT_RGB);
        Graphics g = thisImage.getGraphics();
        g.setColor(Color.BLACK);
        g.fillRect((int)x,(int)y,(int)size,(int)size);
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
