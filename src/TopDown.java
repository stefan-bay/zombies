import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.ArrayList;

/**
 *  Basic game to make the player run to the right.
 */
public class TopDown implements GameMode {
    int moveSpeed = 5;
    int size = 800;
    ArrayList<GameObject> gameObjects = new ArrayList<>();

    Scene gameScene;
    JFrame container;
    Player player;
    boolean[] keysPressed = new boolean[26];
    int projectileSpeed = 15;
    Cooldown fireCooldown = new Cooldown(500);
    Cooldown enemySpawnCooldown = new Cooldown(5000);
    int enemySize = 40;
    Random enemyPos = new Random();
    // for scrolling
    int right_buffer = 80;
    int enemyHealth = 100;

    TopDown(JFrame frame) {
        container = frame;
        initializeGame();
    }

    @Override
    public void update() {
        spawnEnemy();
        updateAllGameObjects();
        handleKeyPress();

    }

    void updateAllGameObjects() {
        for(int i = 0; i < gameObjects.size(); i++) {
            GameObject object = gameObjects.get(i);
            object.update(gameObjects);
            if (object instanceof Enemy) {
                updateEnemy((Enemy)object);
            }
        }
    }

    void updateEnemy(Enemy enemy) {
        // Updating an enemy returns the projectile.
        GameObject projectile = enemy.update(gameObjects, player.getX(), player.getY());
        if (projectile != null) {
            gameObjects.add(projectile);
        }
    }

    void handleKeyPress() {
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
        if(!fireCooldown.isOnCooldown) {
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
        }
        gameScene.repaint();
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
        player.setColliding(true);
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
            for (GameObject go : gameObjects) {
                if (!(go instanceof Player)) {
                    go.setX(go.getX() - moveSpeed);
                }
            }
        } else {
            player.move(moveSpeed, 0, gameObjects);
        }
    }

    void runLeft() {
        if (player.getX() < -right_buffer) {
            for (GameObject go : gameObjects) {
                if (!(go instanceof Player)) {
                    go.setX(go.getX() + moveSpeed);
                }
            }
        } else {
            player.move(-moveSpeed, 0, gameObjects);
        }
    }

    void runUp() {
        if (player.getY() < -right_buffer) {
            for (GameObject go : gameObjects) {
                if (!(go instanceof Player)) {
                    go.setY(go.getY() + moveSpeed);
                }
            }
        } else {
            player.move(0, -moveSpeed, gameObjects);
        }
    }

    void runDown() {
        if (player.getY() > right_buffer) {
            for (GameObject go : gameObjects) {
                if (!(go instanceof Player)) {
                    go.setY(go.getY() - moveSpeed);
                }
            }
        } else {
            player.move(0, moveSpeed, gameObjects);
        }
    }

    void shoot(Point2D.Double direction) {
        if(fireCooldown.startCooldown()) {
            Projectile projectile = player.getProjectile(direction);
            gameObjects.add(projectile);
        }

    }

    void spawnEnemy() {
        if(enemySpawnCooldown.startCooldown()) {
            int enemyX = (int)player.getX() + enemyPos.nextInt(size*2)-size;
            int enemyY = (int)player.getY() + enemyPos.nextInt(size*2)-size;
            Enemy enemy = new Enemy(enemyX, enemyY, enemySize, enemySize, enemyHealth);
            gameObjects.add(enemy);
        }
    }
}
