import javax.swing.*;
import java.io.IOException;

/**
 * Main class for game.
 */
public class GameContainer {
    JFrame frame;
    MainMenu mainMenu;

    boolean bypassMainMenu = true;

    public static void main(String[] args) {
        GameContainer container = new GameContainer();
    }

    GameContainer(){
        frame = new JFrame();
        frame.setTitle("Game");
        mainMenu = new MainMenu(this);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);

        if (bypassMainMenu)
            launchGame(); // bypass main menu
        else
            frame.add(mainMenu);

        frame.pack();
    }

    void launchGame() {
        Game game = new Game(frame);
    }
}
