import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Scene extends JPanel{

    ArrayList<GameObject> objectsToDraw;
    double width;
    double height;

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

        Graphics2D g2d = (Graphics2D)g;

        g.translate(getWidth()/2,getHeight()/2);
        for (GameObject gameObject : objectsToDraw) {
            if(!isShown(gameObject))
                continue;


            g.drawImage(gameObject.getImage(), (int) gameObject.getX(), (int) gameObject.getY(), null );

        }
    }

    private boolean isShown(GameObject object) {
        if (object.getMinX() > width/2)
            return false;
        if (object.getMaxX() < -(width/2))
            return false;
        if (object.getMinY() > height/2)
            return false;
        if (object.getMaxY() < -(height/2))
            return false;

        return true;
    }
}
