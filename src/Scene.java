import javax.swing.*;
import java.awt.*;
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
        g.translate(getWidth()/2,getHeight()/2);
        for (int i = 0; i < objectsToDraw.size(); i++) {
            GameObject gameObject = objectsToDraw.get(i);
            if(!isShown(gameObject)) {
                continue;
            }
            g.drawImage(gameObject.getImage(), (int) (gameObject.getX() - gameObject.getWidth()/2), (int) (gameObject.getY() - gameObject.getHeight()/2), null );

        }
    }

    private boolean isShown(GameObject object) {
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
