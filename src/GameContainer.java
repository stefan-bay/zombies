import javax.swing.*;
import java.awt.geom.Point2D;
import java.io.IOException;

/**
 * Main class for game.
 */
public class GameContainer {
    JFrame frame;
    MainMenu mainMenu;

    boolean bypassMainMenu = false;

    public static void main(String[] args) {
        new GameContainer();
    }

    GameContainer(){
        frame = new JFrame();
        frame.setTitle("Game");
        mainMenu = new MainMenu(this);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);

        if (bypassMainMenu) //for faster debugging
            launchGame(new Point2D.Double(0,0)); // bypass main menu
        else
            frame.add(mainMenu);

        frame.pack();
    }

    void launchGame(Point2D.Double mouseDiff) {
        new Game(frame, mouseDiff);
    }
}
