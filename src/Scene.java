import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Scene extends JPanel{

    ArrayList<GameObject> gameObjects = new ArrayList<>();

    @Override
    public void paintComponent(Graphics g) {
        for (GameObject gameObject : gameObjects) {
            g.drawImage(gameObject.getImage(), (int)gameObject.getX(), (int)gameObject.getY(), null );
        }
    }
}
