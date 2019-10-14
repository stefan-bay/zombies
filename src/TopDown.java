import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
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
    VictoryBox victoryBox;
    EndScreen endScreen;

    boolean[] keysPressed = new boolean[26];
    int projectileSpeed = 15;
    Cooldown fireCooldown = new Cooldown(500);
    Cooldown enemySpawnCooldown = new Cooldown(5000);
    int enemySize = 40;
    Random enemyPos = new Random();
    boolean firePressed = false;
    Point2D.Double fireCoords;
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
            if(firePressed) {
                shoot(fireCoords);
            }
        }
        gameScene.repaint();
    }

    @Override
    public void start() {
        container.add(gameScene, BorderLayout.EAST);
    }


    void initializeGame() {
        player = new Player(0,0,30,30){
            @Override
            Image getImage() {
                return playerImage;
            }
        };
        player.setColliding(true);
        gameObjects.add(player);

        HealthBar playerHealthBar = new HealthBar(player, 200);
        gameObjects.add(playerHealthBar);

        gameScene = new Scene(gameObjects, size, size);
        container.add(gameScene);
        container.pack();
        container.addKeyListener(keyListener);
        container.addMouseListener(mouseListener);
    }

    MouseListener mouseListener = new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent mouseEvent) {

        }

        @Override
        public void mousePressed(MouseEvent mouseEvent) {
            double mouseX = -(player.getX() - (mouseEvent.getX() - size/2));
            double mouseY = -(player.getY() - (mouseEvent.getY() - size/2));
            fireCoords = new Point2D.Double(mouseX,mouseY);
            System.out.println("MouseCoords: " + fireCoords);
            firePressed = true;
        }

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {
            firePressed = false;
        }

        @Override
        public void mouseEntered(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseExited(MouseEvent mouseEvent) {

        }
    };

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
        System.out.println("Fire:" + direction);
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
            HealthBar bar = new HealthBar(enemy, enemyHealth);
            gameObjects.add(bar);
        }
    }
}
