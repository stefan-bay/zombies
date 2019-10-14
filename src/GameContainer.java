import javax.swing.*;
import java.awt.*;

/**
 * Main class for game.
 */
public class GameContainer {
    JFrame frame;
    MainMenu mainMenu;
    public static void main(String[] args) {
        GameContainer container = new GameContainer();
    }

    GameContainer() {
        frame = new JFrame();
        frame.setTitle("Game");
        mainMenu = new MainMenu(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);

        // bypass main menu
        launchGameMode(gameMode.TOPDOWN);
//        frame.add(mainMenu);

        // Show Main Menu
        // call this.launchGameMode(gameMode) with a gamemode when it is seletced.
        //Temp
        //launchGameMode(gameMode.RUNRIGHT);

    }

    enum gameMode {
        RUNRIGHT,
        TOPDOWN,
    }

    void launchGameMode(gameMode mode) {
        GameMode gameModeInstance;
        switch (mode) {
            case RUNRIGHT:
                gameModeInstance = new RunRight(frame);
                break;
            case TOPDOWN:
                gameModeInstance = new TopDown(frame);
                break;
            default:
                gameModeInstance = new RunRight(frame);
                break;
        }
        Game game = new Game(gameModeInstance);
        game.startGame();
    }
}
