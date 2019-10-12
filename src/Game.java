import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Game objects, state, and mechanics.
 */
public class Game {

    int tickTime = 10;
    GameMode game;
    Timer gameTimer = new Timer();

    Game(GameMode gameMode) {
        this.game = gameMode;
    }

    /**
     * Starts the game by initializing and starting the game loop.
     */
    void startGame() {
        game.start();
        mainLoop();
    }

    private void mainLoop() {
        game.update();
        gameTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                mainLoop();
            }
        }, tickTime );
    }
}
