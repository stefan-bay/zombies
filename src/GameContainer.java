import javax.swing.*;

/**
 * Main class for game.
 */
public class GameContainer {
    JFrame frame;
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setTitle("Cube");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(rotatingCube, BorderLayout.CENTER);
        frame.add(cp,BorderLayout.EAST);
        frame.pack();
        frame.setVisible(true);
        JFrame frame = new JFrame();

        // Show Main Menu
        // call this.launchGameMode(gameMode) with a gamemode when it is seletced.
    }

    void launchGameMode(GameMode gameMode) {
        Game game = new Game(gameMode);
        game.startGame();
    }
}
