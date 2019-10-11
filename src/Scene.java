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
        for (GameObject gameObject : objectsToDraw) {
            g.drawImage(gameObject.getImage(), (int)-width/2, 0, null );
        }
    }
}
