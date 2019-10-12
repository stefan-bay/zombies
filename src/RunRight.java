import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 *  Basic game to make the player run to the right.
 */
public class RunRight implements GameMode {

    int size = 500;
    ArrayList<GameObject> gameObjects = new ArrayList<>();
    Scene gameScene;
    JFrame container;

    RunRight(JFrame frame) {
        container = frame;
        initializeGame();
    }

    @Override
    public void update() {

    }

    @Override
    public void start() {
        container.add(gameScene, BorderLayout.EAST);
    }


    void initializeGame() {
        Ground ground = new Ground(-size/2, 0, size, size/2);
        Player player = new Player();
        gameObjects.add(ground);
        gameObjects.add(player);
        gameScene = new Scene(gameObjects, size, size);
        container.add(gameScene);
        container.pack();
    }
}
