
/**
 * Main class for game.
 */
public class GameContainer {
    public static void main(String[] args) {
        // Show Main Menu
        // call this.launchGameMode(gameMode) with a gamemode when it is seletced.
    }

    void launchGameMode(GameMode gameMode) {
        Game game = new Game(gameMode);
        game.startGame();
    }
}
