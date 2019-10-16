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
 *  Survival game class.
 */
public class Game {
    // The size of the game window
    int size = 800;
    // All of the GameObjects to draw on the screen.
    ArrayList<GameObject> gameObjects = new ArrayList<>();

    // True if the player has lost the game.
    boolean hasLost = false;

    // debug
    boolean straight_to_endscreen = false;
    boolean test_hit_explosion = false;
    boolean explosion_on = false;
    boolean test_enemy_healthbar = false;

    // The scene used to draw all the game objects
    Scene gameScene;
    // The container to draw the scene in.
    JFrame container;
    // The player that is controlled by a human
    Player player;
    // The healthbar of the player so it can be drawn over the flashlight shading
    HealthBar playerHealthBar;
    // The flashlight object to control the shading of the scene.
    Flashlight flashlight;
    // The cooldown between making the scene darker and lighter.
    Cooldown daylightCooldown = new Cooldown(5);
    // True if it is daytime and the flashlight should not be shown.
    boolean isDayTime = true;
    // The ambient light (darkness) in the scene between 0 and 255.
    int ambientLight = 0;
    // The amount to increase or decrease the light by in the scene.
    int lightMod = 1;

    // Array of keys that are currently pressed.
    boolean[] keysPressed = new boolean[26];
    // The cooldown between fires by the player.
    Cooldown fireCooldown = new Cooldown(100);
    // The cooldown between enemies spawning.
    Cooldown enemySpawnCooldown = new Cooldown(2000);
    // The cooldown for nighttime and daytime before the direction of ambientlight increase/decrease switches.
    Cooldown nightTimeCooldown = new Cooldown(10000);

    // The size of an enemy.
    int enemySize = 40;
    // The position generator for new enemies.
    Random enemyPos = new Random();
    // True if the firekey is currently pressed. referenced on update.
    boolean firePressed = false;

    // the buffer between the edge of the screen and the player.
    SecondsCounter secondsCounter;

    // for scrolling
    int buffer = 50;
    // The amount of kills the player has gotten, also tracks enemy friendly fire deaths.
    int killCount = 0;
    // The time in MS between updates of this game.
    int tickTime = 50;
    // Difference between window mouse coordinates and monitor mouse coordinates.
    Point2D.Double mouseDiff;
    // The timer to use between updates to this game,
    java.util.Timer gameTimer = new Timer();

    /**
     *  Creates a game instance with the provided JFrame
    */
     Game(JFrame frame, Point2D.Double mouseOffset) {
        container = frame;
        mouseDiff = mouseOffset;
        initializeGame();
        start();
        mainLoop();
    }

    /**
     * Initializes the game with a player, flashight and scene
     */
    void initializeGame() {
        Sprite playerSprite = new Sprite(Sprite.SpriteType.PLAYER);
        player = new Player(0,0,34,33);

        player.setColliding(true);
        gameObjects.add(player);
        playerHealthBar = new HealthBar(player, 200);
        gameObjects.add(playerHealthBar);

        if (!test_hit_explosion) {
            flashlight = new Flashlight(player.getX(), player.getY(), size, size, getMouseLoc());
            gameObjects.add(flashlight);
        }
        // Construct the scene.
        gameScene = new Scene(gameObjects, size, size);
        container.add(gameScene);
        container.pack();
        // Add listeners to the window.
        container.addKeyListener(keyListener);
        container.addMouseListener(mouseListener);

        // Debug
            if (test_enemy_healthbar)
                spawnEnemy();

        // seconds counter with default width and height
        secondsCounter = new SecondsCounter(-size/2, -size/2, 15, 22);
        gameObjects.add(secondsCounter);

        if (straight_to_endscreen) {
            player.setHealth(0);
            secondsCounter.setSeconds(12);
            killCount = 1100;
        }
    }

    /**
     * Adds the scene to the window.
     */
    public void start() {
        container.add(gameScene, BorderLayout.EAST);
    }

    /**
     * The main game loop that reruns the update method. Should use cooldown.
     */
    private void mainLoop() {
        update();
        gameTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                mainLoop();
            }
        }, tickTime );
    }

    /**
     * Updates the game state on an interval.
     */
    public void update() {
        updatePlayerDirection();
        updateAllGameObjects();
        if (!test_hit_explosion) {
            if (!hasLost) {
                // Perform player actions.
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
        // gameScene has a pointer to the list of objects, it draws all of them.
        gameScene.repaint();
    }

    void updatePlayerDirection() {
        player.setDirection(getMouseLoc());
    }

    /**
     * Update all of the game objects and remove the if necessary.
     */
    void updateAllGameObjects() {
        for(int i = 0; i < gameObjects.size(); i++) {
            GameObject object = gameObjects.get(i);

            if (object.shouldRemove()) {
                gameObjects.remove(object);
                if (object instanceof Enemy) {
                    killCount++;
                }
                continue;
            }

            object.update(gameObjects);
            // Update the enemies to make them fire and move.
            if (object instanceof Enemy) {
                updateEnemy((Enemy)object);
            }
        }
    }

    // Perform an action based on which keys are pressed.
    void handleKeyPress() {
        // Control movement of the player.
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
        // Fire if the fire key is pressed and we are not on cooldown.
        if(!fireCooldown.isOnCooldown) {
            if(firePressed) {
                // Shoot wherever the mouse is pointing.
                shoot(getMouseLoc());
            }
        }
    }

    /**
     * Redraw the flashlight image. Make sure it, the player, and the player's healthbar are ontop of the shading.
     */
    void redrawFlashlight() {
        setAmbientLight();
        flashlight.createFlashLight(player.getX(), player.getY(), size, size, getMouseLoc());
        gameObjects.remove(secondsCounter);
        gameObjects.remove(player);
        gameObjects.remove(flashlight);
        gameObjects.remove(playerHealthBar);
        gameObjects.add(flashlight);
        gameObjects.add(player);
        gameObjects.add(playerHealthBar);
        gameObjects.add(secondsCounter);
    }

    /**
     * Update an enemy to make it shoot if it can and move it.
     * @param enemy the enemy to update.
     */
    void updateEnemy(Enemy enemy) {
        // Updating an enemy returns the projectile.
        GameObject projectile = enemy.update(gameObjects, player);
        if (projectile != null) {
            gameObjects.add(projectile);
        }

    }

    /**
     * Check to see if the player has lost and if it has show the end screen.
     */
    void checkLose() {
        if (player.getHealth() <= 0) {
            hasLost = true;
            gameObjects.clear();
            gameObjects.add(new EndScreen(killCount, secondsCounter.getSeconds()));
        }
    }

    /**
     * Gets the current location of the mouse on the screen relative to the player.
     * @return a point that is the mouse's location on the screen.
     */
    Point2D.Double getMouseLoc() {
        Point mouseLoc = MouseInfo.getPointerInfo().getLocation();
        // Translate the mouse coordinates by the offset between the window and the display.
        // Make the mouse coordinates relative to the player.
        double mouseX = (mouseLoc.getX() - size/2 + mouseDiff.getX() - player.getX());
        double mouseY = (mouseLoc.getY() - size/2 + mouseDiff.getY() - player.getY());

        return new Point2D.Double(mouseX, mouseY);
    }

    /**
     * Updates the ambient light in the scene. Darkens or lightens the display regularly, make it oscilate.
     */
    void setAmbientLight() {
        if(daylightCooldown.startCooldown() && !nightTimeCooldown.isOnCooldown) {
            ambientLight += lightMod;
            // Check if we need to reverse the direction we are increasing or decreasing the light by.
            if((ambientLight>=Flashlight.maxAmbientLight || ambientLight <=0) && nightTimeCooldown.startCooldown()) {
                lightMod *= -1;
            }
            // Set the light to daytime if there is less than 180 ambient light (darkness)
            isDayTime = ambientLight < 180;
            flashlight.setAmbientLight(ambientLight, isDayTime);
        }

    }

    /**
     * Move the player. If the player would hit a boundry, move everything else in the scene instead.
     */
    void runRight() {
        if (player.getX() > size/2 - buffer) {
            for (GameObject go : gameObjects) {
                if (go instanceof SecondsCounter) continue; // seconds counter does not move
                if (!(go instanceof Player)) {
                    go.setX(go.getX() - player.getMoveSpeed());
                }
            }
        } else {
            player.move(player.getMoveSpeed(), 0, gameObjects);
        }
    }

    /**
     * Move the player. If the player would hit a boundry, move everything else in the scene instead.
     */
    void runLeft() {
        if (player.getX() < -size/2 + buffer) {
            for (GameObject go : gameObjects) {
                if (go instanceof SecondsCounter) continue; // seconds counter does not move
                if (!(go instanceof Player)) {
                    go.setX(go.getX() + player.getMoveSpeed());
                }
            }
        } else {
            player.move(-player.getMoveSpeed(), 0, gameObjects);
        }
    }

    /**
     * Move the player. If the player would hit a boundry, move everything else in the scene instead.
     */
    void runUp() {
        if (player.getY() < -size/2 + buffer) {
            for (GameObject go : gameObjects) {
                if (go instanceof SecondsCounter) continue; // seconds counter does not move
                if (!(go instanceof Player)) {
                    go.setY(go.getY() + player.getMoveSpeed());
                }
            }
        } else {
            player.move(0, -player.getMoveSpeed(), gameObjects);
        }
    }

    /**
     * Move the player. If the player would hit a boundry, move everything else in the scene instead.
     */
    void runDown() {
        if (player.getY() > size/2 - buffer) {
            for (GameObject go : gameObjects) {
                if (go instanceof SecondsCounter) continue; // seconds counter does not move
                if (!(go instanceof Player)) {
                    go.setY(go.getY() - player.getMoveSpeed());
                }
            }
        } else {
            player.move(0, player.getMoveSpeed(), gameObjects);
        }
    }

    /**
     * Make the player shoot in a direction
     * @param direction the direction to shoot in
     */
    void shoot(Point2D.Double direction) {
        if(fireCooldown.startCooldown()) {
            Projectile projectile = player.getProjectile(direction);
            gameObjects.add(projectile);
        }

    }

    /**
     * Spawn a new enemy.
     */
    void spawnEnemy() {
        if(enemySpawnCooldown.startCooldown()) {
            // Random generator for the class of the enemy.
            Random random = new Random();
            // The position of the new enemy relative to the player, within two screen sizes.
            int enemyX = (int)player.getX() + enemyPos.nextInt(size*2)-size;
            int enemyY = (int)player.getY() + enemyPos.nextInt(size*2)-size;
            if (test_enemy_healthbar) {
                enemyX = -900;
                enemyY = -900;
            }



            Enemy enemy = new Enemy(enemyX, enemyY);
            gameObjects.add(enemy);
            HealthBar bar = new HealthBar(enemy, enemy.getHealth());
            gameObjects.add(bar);
        }
    }

    /**
     * Mouse listener for clicks in the screen to fire.
     */
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

    /**
     * Keylistener for keyboard pressed by the user.
     */
    KeyListener keyListener = new KeyListener() {
        @Override
        public void keyTyped(KeyEvent keyEvent) {
        }

        @Override
        public void keyPressed(KeyEvent keyEvent) {
            int keyCode = keyEvent.getKeyCode();
            int letterValue = keyCode - KeyEvent.VK_A;
            // Set the appropriate key in the array to be pressed if it is available.
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
