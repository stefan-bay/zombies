import javax.swing.*;
import java.awt.*;

/**
 * Main class for game.
 */
public class GameContainer {
    JFrame frame;
    public static void main(String[] args) {
        GameContainer container = new GameContainer();
    }

    GameContainer() {
        frame = new JFrame();
        frame.setTitle("Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        frame.pack();
        frame.setVisible(true);
        // Show Main Menu
        // call this.launchGameMode(gameMode) with a gamemode when it is seletced.
        //Temp
        launchGameMode(gameMode.RUNRIGHT);
    }

    enum gameMode {
        RUNRIGHT,
    }

    void launchGameMode(gameMode mode) {
        GameMode gameModeInstance;
        switch (mode) {
            case RUNRIGHT:
                gameModeInstance = new RunRight(frame);
                break;
            default:
                gameModeInstance = new RunRight(frame);
                break;
        }
        Game game = new Game(gameModeInstance);
        game.startGame();
    }
}
