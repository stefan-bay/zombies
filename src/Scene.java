import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * Handles painting of GameObjects
 */
public class Scene extends JPanel{

    ArrayList<GameObject> objectsToDraw;
    double width;
    double height;

    Player player = null;

    /**
     * Construct scene
     * @param objects ArrayList of objects to draw, passing once allows us to us one pointer throughout game to
     *                modify gameobjects to be displayed
     * @param width  width of screen
     * @param height height of screen
     */
    Scene(ArrayList<GameObject> objects, double width, double height) {
        setPreferredSize(new Dimension((int)width,(int)height));
        this.width = width;
        this.height = height;
        setBackground(Color.white);
        this.objectsToDraw = objects;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.translate(getWidth()/2,getHeight()/2);

        for (int i = 0; i < objectsToDraw.size(); i++) {
            GameObject gameObject = objectsToDraw.get(i);
            if(!isShown(gameObject)) {
                continue;
            }
            if (gameObject instanceof EndScreen)
                setBackground(Color.black);
            if(gameObject instanceof Player)
                player = (Player)gameObject;

            g.drawImage(gameObject.getImage(), (int) (gameObject.getX() - gameObject.getWidth()/2), (int) (gameObject.getY() - gameObject.getHeight()/2), null );
        }
    }

    private int distanceToPlayer(GameObject object) {
        if (player == null)
            return 0;
        Point2D objectPoint = new Point2D.Double(object.getX(), object.getY());
        Point2D playerPoint = new Point2D.Double(player.getX(), player.getY());

        return (int)objectPoint.distance(playerPoint);
    }

    private boolean isShown(GameObject object) {
        // if object is too far away, despawn
        if (distanceToPlayer(object) > 2500) {
            object.setShouldRemove(true);
            return false;
        }

        if (object.getMinX() > width)
            return false;
        if (object.getMaxX() < -(width))
            return false;
        if (object.getMinY() > height)
            return false;
        if (object.getMaxY() < -(height))
            return false;

        return true;
    }
}
