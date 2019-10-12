import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
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
    VictoryBox victoryBox;
    boolean[] keysPressed = new boolean[26];
    boolean won = false;

    RunRight(JFrame frame) {
        container = frame;
        initializeGame();
    }

    void checkWin() {
        if(player.collidesWith(victoryBox)) {
            //gameObjects.clear();
            gameObjects.add(new GameObject(-size/2,-size/2,size,size) {
                @Override
                Image getImage() {
                    Image win = new BufferedImage(size/4,size/4, BufferedImage.TYPE_INT_RGB);
                    Graphics g = win.getGraphics();
                    g.setColor(Color.magenta);
                    //g.fillRect(0,0,size,size);
                    g.setColor(Color.CYAN);
                    for (int i = 0; i < size; i+=20) {
                        for(int j = 0; j< size; j+=55) {
                            g.drawString("YOU WIN", j,i);
                        }
                    }
                    return win;
                }
            });
        }
    }

    @Override
    public void update() {
//        System.out.println(player.getWidth() +", "+ player.getHeight());
        if(!won) {
            if (keysPressed[3]) {
                runRight();
            } else if (keysPressed[0]) {
                runLeft();
            } else {
                playerStop();
            }
            checkWin();
            gameScene.repaint();
        }
    }

    @Override
    public void start() {
        container.add(gameScene, BorderLayout.EAST);
    }


    void initializeGame() {
        Ground ground = new Ground(-size/2, 0, size, size/2);
        gameObjects.add(ground);
        player = new Player();
        gameObjects.add(player);

        int victoryBoxSize = size/8;
        victoryBox = new VictoryBox( size/7, -victoryBoxSize, victoryBoxSize, victoryBoxSize);
        gameObjects.add(victoryBox);
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
            int keyCode = keyEvent.getKeyCode();
            int letterValue = keyCode - KeyEvent.VK_A;
            if(letterValue < 26 && letterValue >= 0) {
                keysPressed[letterValue] = true;
            }
        }

        @Override
        public void keyReleased(KeyEvent keyEvent) {
            int keyCode = keyEvent.getKeyCode();
            int letterValue = keyCode - KeyEvent.VK_A;
            if(letterValue < 26 && letterValue >= 0) {
                keysPressed[letterValue] = false;
            }

        }
    };

    void runRight() {
        player.move(2,0);
    }

    void runLeft() {
        player.move(-2,0);
    }

    void playerStop() {
        player.move(0, 0);
    }

}
