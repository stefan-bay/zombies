import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 *  Basic game to make the player run to the right.
 */
public class TopDown implements GameMode {
    int moveSpeed = 5;
    int size = 500;
    ArrayList<GameObject> gameObjects = new ArrayList<>();

    ArrayList<GameObject> backgroundObjects = new ArrayList<>();
    ArrayList<GameObject> foregroundObjects = new ArrayList<>();


    Scene gameScene;
    JFrame container;
    Player player;
    VictoryBox victoryBox;
    boolean[] keysPressed = new boolean[26];
    boolean won = false;
    boolean jumping = false;

    // for scrolling
    int right_buffer = 30;

    TopDown(JFrame frame) {
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
                    //g.fillRect(0,0,size,size);
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
            } else if(keysPressed[22]) {
                runUp();
            } else if(keysPressed[18]){
                runDown();
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
        Image playerImage = new BufferedImage((int) Player.widthInitial,(int) Player.heightInitial, BufferedImage.TYPE_INT_RGB);
        Graphics g = playerImage.getGraphics();
        g.setColor(Color.CYAN);
        g.fillRect(0,0,(int)Player.widthInitial,(int)Player.heightInitial);
        player = new Player(){
            @Override
            Image getImage() {
                return playerImage;
            }
        };
        gameObjects.add(player);

        int victoryBoxSize = size/8;
        victoryBox = new VictoryBox( -size/7, -victoryBoxSize, victoryBoxSize, victoryBoxSize);
        gameObjects.add(victoryBox);
        gameScene = new Scene(gameObjects, size, size);
        container.add(gameScene);
        container.pack();
        container.addKeyListener(keyListener);

        foregroundObjects.add(victoryBox);

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
        if (player.getX() > right_buffer) {
            for (GameObject go : foregroundObjects)
                go.setX(go.getX() - moveSpeed);

            for (GameObject go : backgroundObjects)
                go.setX(go.getX() - 1);
        }
        player.move(moveSpeed,0);
    }

    void runLeft() {
        player.move(-moveSpeed,0);
    }

    void runUp() {
        player.move(0,-moveSpeed);
    }

    void runDown() {
        player.move(0,moveSpeed);
    }


    void playerStop() {
        player.move(0, 0);
    }

}
