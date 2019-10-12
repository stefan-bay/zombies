import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/**
 *  Basic game to make the player run to the right.
 */
public class RunRight implements GameMode {

    int size = 500;
    ArrayList<GameObject> gameObjects = new ArrayList<>();
    Scene gameScene;
    JFrame container;
    Player player;

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
        player = new Player();
        gameObjects.add(ground);
        gameObjects.add(player);
        gameScene = new Scene(gameObjects, size, size);
        container.add(gameScene);
        container.pack();
        container.addKeyListener(keyListener);
    }

    KeyListener keyListener = new KeyListener() {
        @Override
        public void keyTyped(KeyEvent keyEvent) {
        }

        @Override
        public void keyPressed(KeyEvent keyEvent) {
            switch (keyEvent.getKeyCode()) {
                case KeyEvent.VK_D:
                    runRight();
                    break;
                case KeyEvent.VK_A:
                    runLeft();
                    break;
            }
        }

        @Override
        public void keyReleased(KeyEvent keyEvent) {

        }
    };

    void runRight() {
        player.move(10,0);
        gameScene.repaint();
    }

    void runLeft() {
        player.move(-10,0);
        gameScene.repaint();
    }

}
