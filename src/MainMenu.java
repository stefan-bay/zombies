import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

public class MainMenu extends JPanel implements ActionListener {
    JLabel playLabel;
    MainMenu mainMenu;
    ImageIcon playImage;
    MouseAdapter mouse;

    public MainMenu(GameContainer gc) {
        mainMenu = this;
        setPreferredSize(new Dimension(800,800));
        Color mainMenuColor = new Color(130, 155, 146);
        setBackground(mainMenuColor);
        setLayout(new FlowLayout());

        playImage = new ImageIcon("buttons/Play.png");
        playLabel = new JLabel(playImage);
        add(playLabel);

       mouse = new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                Point mouseLoc = MouseInfo.getPointerInfo().getLocation();
                Point2D.Double mouseDiff = new Point2D.Double(me.getX() - mouseLoc.getX(), me.getY() - mouseLoc.getY());
                playGame(gc, mouseDiff);
                playLabel.setVisible(false);
                removeTheMouseListener();
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