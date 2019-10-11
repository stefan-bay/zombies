import java.util.ArrayList;

/**
 *  Basic game to make the player run to the right.
 */
public class RunRight implements GameMode {

    int size = 500;
    ArrayList<GameObject> gameObjects = new ArrayList<>();
    Scene gameScene;
    @Override
    public void update() {

    }

    @Override
    public void start() {

    }



    void initializeGame() {
        Ground ground = new Ground(0, size/2-size/4, size, size/2);
        gameObjects.add(ground);
        gameScene = new Scene(gameObjects, size, size);
    }
}
