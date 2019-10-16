import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.util.Random;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 *  Basic game to make the player run to the right.
 */
public class Game {
    int moveSpeed = 5;
    int size = 800;
    ArrayList<GameObject> gameObjects = new ArrayList<>();

    boolean hasLost = false;

    // debug
    boolean straight_to_endscreen = false;
    boolean test_hit_explosion = false;
    boolean explosion_on = false;

    Scene gameScene;
    JFrame container;
    Player player;
    HealthBar playerHealthBar;
    VictoryBox victoryBox;
    Flashlight flashlight;
    Cooldown daylightCooldown = new Cooldown(5);
    boolean isDayTime = true;
    int ambientLight = 0;
    int lightMod = 1;

    boolean[] keysPressed = new boolean[26];
    int projectileSpeed = 15;
    Cooldown fireCooldown = new Cooldown(100);
    Cooldown enemySpawnCooldown = new Cooldown(5000);
    int enemySize = 40;
    Random enemyPos = new Random();
    boolean firePressed = false;
    Point2D.Double fireCoords;

    // for scrolling
    int buffer = 50;
    int enemyHealth = 100;
    int killCount = 0;
    int tickTime = 50;

    java.util.Timer gameTimer = new Timer();

    Game(JFrame frame) {
        container = frame;
        initializeGame();
        start();
        mainLoop();
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

        playerHealthBar = new HealthBar(player, 200);
        gameObjects.add(playerHealthBar);

        if (!test_hit_explosion) {
            flashlight = new Flashlight(player.getX(), player.getY(), size, size, getMouseLoc());
            gameObjects.add(flashlight);
        }
            gameScene = new Scene(gameObjects, size, size);
            container.add(gameScene);
            container.pack();
            container.addKeyListener(keyListener);
            container.addMouseListener(mouseListener);


        if (straight_to_endscreen) {
            player.setHealth(0);
            killCount = 1100;
        }
    }

    public void start() {
        container.add(gameScene, BorderLayout.EAST);
    }

    private void mainLoop() {
        update();
        gameTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                mainLoop();
            }
        }, tickTime );
    }

    public void update() {
        updateAllGameObjects();
        if (!test_hit_explosion) {
            if (!hasLost) {
                handleKeyPress();
                spawnEnemy();
                redrawFlashlight();
                checkLose();
            }
        }
        if (test_hit_explosion) {
            if (!explosion_on) {
                gameObjects.add(new HitExplosion(0, 0));
                explosion_on = true;
            }
        }
        gameScene.repaint();
    }

    void updateAllGameObjects() {
        for(int i = 0; i < gameObjects.size(); i++) {
            GameObject object = gameObjects.get(i);

            if (object.shouldRemove()) {
                gameObjects.remove(object);
                if (object instanceof Enemy) {
                    killCount++;
                    System.out.println("kills: " + killCount);
                }
                continue;
            }

            object.update(gameObjects);
            if (object instanceof Enemy) {
                updateEnemy((Enemy)object);
            }
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
                shoot(getMouseLoc());
            }
        }
    }

    void redrawFlashlight() {
        setAmbientLight();
        flashlight.createFlashLight(player.getX(), player.getY(), size, size, getMouseLoc());
        gameObjects.remove(player);
        gameObjects.remove(flashlight);
        gameObjects.remove(playerHealthBar);
        gameObjects.add(flashlight);
        gameObjects.add(player);
        gameObjects.add(playerHealthBar);
    }



    void updateEnemy(Enemy enemy) {
        // Updating an enemy returns the projectile.
        GameObject projectile = enemy.update(gameObjects, player.getX(), player.getY());
        if (projectile != null) {
            gameObjects.add(projectile);
        }

    }

    void checkLose() {
        if (player.getHealth() <= 0) {
            hasLost = true;
            gameObjects.clear();
            gameObjects.add(new EndScreen(killCount));
        }
    }

    Point2D.Double getMouseLoc() {
        Point mouseLoc = MouseInfo.getPointerInfo().getLocation();
        double mouseX = (mouseLoc.getX() - size/2 - 60 - player.getX());
        double mouseY = (mouseLoc.getY() - size/2 - 60 - player.getY());

        return new Point2D.Double(mouseX, mouseY);
    }

    void setAmbientLight() {
        if(daylightCooldown.startCooldown()) {
            ambientLight += lightMod;
            if(ambientLight>=Flashlight.maxAmbientLight || ambientLight <=0) {
                lightMod *= -1;
            }
            isDayTime = ambientLight < 180;
            flashlight.setAmbientLight(ambientLight, isDayTime);
        }

    }

    void runRight() {
        if (player.getX() > size/2 - buffer) {
            for (GameObject go : gameObjects) {
                if (!(go instanceof Player)) {
                    go.setX(go.getX() - player.getMoveSpeed());
                }
            }
        } else {
            player.move(player.getMoveSpeed(), 0, gameObjects);
        }
    }

    void runLeft() {
        if (player.getX() < -size/2 + buffer) {
            for (GameObject go : gameObjects) {
                if (!(go instanceof Player)) {
                    go.setX(go.getX() + player.getMoveSpeed());
                }
            }
        } else {
            player.move(-player.getMoveSpeed(), 0, gameObjects);
        }
    }

    void runUp() {
        if (player.getY() < -size/2 + buffer) {
            for (GameObject go : gameObjects) {
                if (!(go instanceof Player)) {
                    go.setY(go.getY() + player.getMoveSpeed());
                }
            }
        } else {
            player.move(0, -player.getMoveSpeed(), gameObjects);
        }
    }

    void runDown() {
        if (player.getY() > size/2 - buffer) {
            for (GameObject go : gameObjects) {
                if (!(go instanceof Player)) {
                    go.setY(go.getY() - player.getMoveSpeed());
                }
            }
        } else {
            player.move(0, player.getMoveSpeed(), gameObjects);
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
            HealthBar bar = new HealthBar(enemy, enemyHealth);
            gameObjects.add(bar);
        }
    }

    MouseListener mouseListener = new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent mouseEvent) { }

        @Override
        public void mousePressed(MouseEvent mouseEvent) {
            firePressed = true;
        }

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {
            firePressed = false;
        }

        @Override
        public void mouseEntered(MouseEvent mouseEvent) { }

        @Override
        public void mouseExited(MouseEvent mouseEvent) { }
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

            if (keyCode == KeyEvent.VK_SHIFT)
                player.setMoveSpeed(player.getMoveSpeed() + player.sprintModifier);
        }

        @Override
        public void keyReleased(KeyEvent keyEvent) {
            int keyCode = keyEvent.getKeyCode();
            int letterValue = keyCode - KeyEvent.VK_A;
            if(letterValue < 26 && letterValue >= 0) {
                keysPressed[letterValue] = false;
            }

            if (keyCode == KeyEvent.VK_SHIFT)
                player.setMoveSpeed(player.getMoveSpeed() - player.sprintModifier);
        }
    };
}
