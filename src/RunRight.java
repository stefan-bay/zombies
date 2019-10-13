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
    boolean jumping = false;

    RunRight(JFrame frame) {
        container = frame;
        initializeGame();
    }

    void checkWin() {
        if(player.intersects(victoryBox)) {
            gameObjects.clear();
            GameObject winObject = new GameObject(-size/2,-size/2,size,size) {
                @Override
                Image getImage() {
                    Image win = new BufferedImage(size,size, BufferedImage.TYPE_INT_RGB);
                    Graphics g = win.getGraphics();
                    g.setColor(Color.magenta);
                    g.fillRect(0,0,size,size);
                    g.setColor(Color.CYAN);
                    for (int i = 0; i < size; i+=20) {
                        for(int j = 0; j< size; j+=55) {
                            g.drawString("YOU WIN", j,i);
                        }
                    }
                    return win;
                }
            };
            winObject.setColliding(false);
            gameObjects.add(winObject);
        }
    }

    @Override
    public void update() {
        player.update(gameObjects);
//        System.out.println(player.getWidth() +", "+ player.getHeight());
        if(!won) {
            if (keysPressed[3]) {
                runRight();
            } else if (keysPressed[0]) {
                runLeft();
            } else if(jumping) {
                jump();
                jumping = false;
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
        player.setHasGravity(true);
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
            if(keyEvent.getKeyCode() == KeyEvent.VK_SPACE ) {
                jumping = true;
            }
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

    void jump() {
        player.move(0,-20);
    }

    void fall() {

    }


    void playerStop() {
        player.move(0, 0);
    }

}
