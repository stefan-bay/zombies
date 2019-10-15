import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainMenu extends JPanel implements ActionListener {
    JLabel playLabel;
    MainMenu mainMenu;
    ImageIcon playImage;

    public MainMenu(GameContainer gc) {
        mainMenu = this;
        setPreferredSize(new Dimension(800,800));
        Color mainMenuColor = new Color(130, 155, 146);
        setBackground(mainMenuColor);
        setLayout(new FlowLayout());

        playImage = new ImageIcon("buttons/Play.png");
        playLabel = new JLabel(playImage);
        add(playLabel);

        playLabel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                playGame(gc);
                playLabel.setVisible(false);
            }
        });
    }

    private void playGame(GameContainer gc) {
        gc.launchGame();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}