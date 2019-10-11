import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Scene extends JPanel{

    ArrayList<GameObject> objectsToDraw;

    Scene(ArrayList<GameObject> objects, double width, double height) {
        this.objectsToDraw = objects;
    }


    @Override
    public void paintComponent(Graphics g) {
        for (GameObject gameObject : objectsToDraw) {
            g.drawImage(gameObject.getImage(), (int)gameObject.getX(), (int)gameObject.getY(), null );
        }
    }
}
