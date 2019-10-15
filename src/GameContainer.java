import javax.swing.*;
import java.awt.*;

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

    GameContainer() {
        frame = new JFrame();
        frame.setTitle("Game");
        mainMenu = new MainMenu(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false); //CHANGE THIS

        if (bypassMainMenu)
            launchGameMode(); // bypass main menu
        else
            frame.add(mainMenu);
        frame.pack();


        // Show Main Menu
        // call this.launchGameMode(gameMode) with a gamemode when it is seletced.
        //Temp
        //launchGameMode(gameMode.RUNRIGHT);

    }

    void launchGameMode() {
        Game game = new Game(frame);
    }
}
