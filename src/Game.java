import java.util.Timer;
import java.util.TimerTask;

/**
 * Game objects, state, and mechanics.
 */
public class Game {

    int tickTime = 10;

    Game() {

    }

    /**
     * Starts the game by initializing and starting the game loop.
     */
    void startGame() {

    }

    private void mainLoop() {
        Timer gameTimer = new Timer();
        gameTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                mainLoop();
            }
        }, tickTime );
    }
}
