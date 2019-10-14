import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Point2D;
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
    int projectileSpeed = 5;

    // for scrolling
    int right_buffer = 80;

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
        for(int i = 0; i < gameObjects.size(); i++) {
            GameObject object = gameObjects.get(i);
            object.update(gameObjects);
        }

        if(!won) {
            if (keysPressed[3]) {
                runRight();
            }
            if (keysPressed[0]) {
                runLeft();
            }
            if(keysPressed[22]) {
                runUp();
            }
            if(keysPressed[18]) {
                runDown();
            }
            if(keysPressed[9]) {
                shoot(new Point2D.Double(-projectileSpeed,0));
            }
            if(keysPressed[10]) {
                shoot(new Point2D.Double(0,projectileSpeed));
            }
            if(keysPressed[11]) {
                shoot(new Point2D.Double(projectileSpeed,0));
            }
            if(keysPressed[8]) {
                shoot(new Point2D.Double(0, -projectileSpeed));
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
        // Target
        gameObjects.add(new GameObject(100,100,100,100, 100) {
                            @Override
                            Image getImage() {
                                Image thisImage = new BufferedImage(100,100, BufferedImage.TYPE_INT_RGB);
                                Graphics g = thisImage.getGraphics();
                                g.setColor(Color.magenta);
                                //g.fillRect(0,0,size,size);
                                g.setColor(Color.CYAN);
                                return thisImage;                            }
                        });
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
        } else
            player.move(moveSpeed,0);
    }

    void runLeft() {
        if (player.getX() < -right_buffer) {
            for (GameObject go : foregroundObjects)
                go.setX(go.getX() + moveSpeed);

            for (GameObject go : backgroundObjects)
                go.setX(go.getX() + 1);
        } else
            player.move(-moveSpeed,0);
    }

    void runUp() {
        if (player.getY() < -right_buffer) {
            for (GameObject go : foregroundObjects)
                go.setY(go.getY() + moveSpeed);

            for (GameObject go : backgroundObjects)
                go.setY(go.getY() + 1);
        } else
            player.move(0,-moveSpeed);
    }

    void runDown() {
        if (player.getY() > right_buffer) {
            for (GameObject go : foregroundObjects)
                go.setY(go.getY() - moveSpeed);

            for (GameObject go : backgroundObjects)
                go.setY(go.getY() - 1);
        } else
            player.move(0,moveSpeed);
    }

    void shoot(Point2D.Double direction) {
        double projectileX = player.getX() + (player.getWidth()/2 * (direction.getX() != 0 ? (direction.getX()/Math.abs(direction.getX())) : 1));
        double projectileY = player.getY() + (player.getHeight()/2 * (direction.getY() != 0 ? (direction.getY()/Math.abs(direction.getY())) : 1));
        Projectile projectile = new Projectile(projectileX, projectileY, direction);
        gameObjects.add(projectile);
    }

    void playerStop() {
        player.move(0, 0);
    }

}
