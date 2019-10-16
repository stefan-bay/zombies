import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

/**
 * Class to make the main menu when you run the game.
 */
public class MainMenu extends JPanel implements ActionListener {
    JLabel playLabel;
    MainMenu mainMenu;
    ImageIcon playImage;
    MouseAdapter mouse;

    /**
     * Constructor for MainMenu
     * @param gc
     */
    public MainMenu(GameContainer gc) {
        mainMenu = this;
        setPreferredSize(new Dimension(800,800));
        Color mainMenuColor = new Color(130, 155, 146);
        setBackground(mainMenuColor);
        setLayout(new FlowLayout());

        playImage = new ImageIcon("res/buttons/Play.png");
        playLabel = new JLabel(playImage); //Puts the Play.png image in the JLabel
        add(playLabel);

       mouse = new MouseAdapter() { //Clicking anywhere on the main menu starts the game.
            public void mousePressed(MouseEvent me) {
                Point mouseLoc = MouseInfo.getPointerInfo().getLocation();
                //resets mouse diff to be according to the current location of the JFrame.
                Point2D.Double mouseDiff = new Point2D.Double(me.getX() - mouseLoc.getX(), me.getY() - mouseLoc.getY());
                playGame(gc, mouseDiff);
                playLabel.setVisible(false);
                removeTheMouseListener(); //removes the 'play button' functionality once started playing.
            }
        };
       addMouseListener(mouse);
    }

    void removeTheMouseListener() {
        removeMouseListener(mouse);
    }

    private void playGame(GameContainer gc, Point2D.Double mouseDiff) {
        gc.launchGame(mouseDiff);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}