import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainMenu extends JPanel implements ActionListener {
    JLabel playLabel, selectLabel, runRightLabel, topDownLabel;
    MainMenu mainMenu;
    ImageIcon playImage, select, runRight, topDown;
    boolean visible = true;
    int numClicks;

    public MainMenu(GameContainer gc) {
        mainMenu = this;

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        setPreferredSize(screenSize);
        Color mainMenuColor = new Color(130, 155, 146);

        setBackground(mainMenuColor);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));


        select = new ImageIcon("buttons/LevelSelect.png"); //If adding more things on main menu:
                                                                    //change adding order -- selectLabel then levelLabels
        selectLabel = new JLabel(select);

        runRight = new ImageIcon("buttons/RunRight.png");
        runRightLabel = new JLabel(runRight);

        topDown = new ImageIcon("buttons/TopDown.png");
        topDownLabel = new JLabel(topDown);

        add(selectLabel);
        add(runRightLabel);
        runRightLabel.setVisible(false);

        add(topDownLabel);
        topDownLabel.setVisible(false);

        selectLabel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                numClicks++;
                if (numClicks % 2 == 0) {
                    runRightLabel.setVisible(false);
                    topDownLabel.setVisible(false);
                }
                else if (numClicks % 2 == 1) {
                    runRightLabel.setVisible(true);
                    topDownLabel.setVisible(true);
                }
                System.out.println(numClicks);

            }
        });

        runRightLabel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                playRunRight(gc);
                setVisible(false);
            }
        });

        topDownLabel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                playTopDown(gc);
                setVisible(false);
            }
        });


    }

    private void playRunRight(GameContainer gc) {
        gc.launchGameMode(GameContainer.gameMode.RUNRIGHT);
    }

    private void playTopDown(GameContainer gc) {
        gc.launchGameMode(GameContainer.gameMode.TOPDOWN);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}