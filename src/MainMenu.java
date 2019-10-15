import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainMenu extends JPanel implements ActionListener {
    JLabel playLabel, selectLabel, runRightLabel, topDownLabel, selectLevelLabel,
           level1Label, level2Label, level3Label, level4Label, level5Label;
    MainMenu mainMenu;
    ImageIcon playImage,select, runRight, topDown, selectLevel,
              level1, level2, level3, level4, level5;

    int numClicksSelect, numClicksLevel;

    public MainMenu(GameContainer gc) {
        mainMenu = this;
        setPreferredSize(new Dimension(800,800));
        Color mainMenuColor = new Color(130, 155, 146);
        setBackground(mainMenuColor);
        setLayout(new FlowLayout());

        playImage = new ImageIcon("buttons/Play.png");
        playLabel = new JLabel(playImage);
        add(playLabel);
        //        select = new ImageIcon("buttons/GameMode.png"); //If adding more things on main menu:
//                                                                    //change adding order -- selectLabel then levelLabels
//        selectLabel = new JLabel(select);
//
//        runRight = new ImageIcon("buttons/RunRight.png");
//        runRightLabel = new JLabel(runRight);
//
//        topDown = new ImageIcon("buttons/TopDown.png");
//        topDownLabel = new JLabel(topDown);
//
//        JPanel levelPanel = new JPanel();
//        levelPanel.setPreferredSize(new Dimension(100, 115));
//
//        selectLevel = new ImageIcon("buttons/SelectLevel.png");
//        selectLevelLabel = new JLabel(selectLevel);
//
//        level1 = new ImageIcon("buttons/level1.png");
//        level1Label = new JLabel(level1);
//
//        level2 = new ImageIcon("buttons/level2.png");
//        level2Label = new JLabel(level2);
//
//        level3 = new ImageIcon("buttons/level3.png");
//        level3Label = new JLabel(level3);
//
//        level4 = new ImageIcon("buttons/level4.png");
//        level4Label = new JLabel(level4);
//
//        level5 = new ImageIcon("buttons/level5.png");
//        level5Label = new JLabel(level5);
//
//        add(selectLabel);
//
//        add(runRightLabel);
//        runRightLabel.setVisible(false);
//        add(topDownLabel);
//        topDownLabel.setVisible(false);
//
//        add(selectLevelLabel);
//        levelPanel.add(level1Label);
//        levelPanel.add(level2Label);
//        levelPanel.add(level3Label);
//        levelPanel.add(level4Label);
//        levelPanel.add(level5Label);
//        add(levelPanel);
//        levelPanel.setVisible(false);

//        selectLabel.addMouseListener(new MouseAdapter() {
//            public void mousePressed(MouseEvent me) {
//                numClicksSelect++;
//                if (numClicksSelect % 2 == 0) {
//                    runRightLabel.setVisible(false);
//                    topDownLabel.setVisible(false);
//                }
//                else if (numClicksSelect % 2 == 1) {
//                    runRightLabel.setVisible(true);
//                    topDownLabel.setVisible(true);
//                }
//            }
//        });



//        selectLevelLabel.addMouseListener(new MouseAdapter() {
//            public void mousePressed(MouseEvent me) {
//                numClicksLevel++;
//                if (numClicksLevel % 2 == 0) {
//                    levelPanel.setVisible(false);
//                }
//                else if (numClicksLevel % 2 == 1) {
//                    levelPanel.setVisible(true);
//                }
//            }
//        });

//        runRightLabel.addMouseListener(new MouseAdapter() {
//            public void mousePressed(MouseEvent me) {
//                playRunRight(gc);
//                setVisible(false);
//            }
//        });
//
//        topDownLabel.addMouseListener(new MouseAdapter() {
//            public void mousePressed(MouseEvent me) {
//                playTopDown(gc);
//                setVisible(false);
//            }
//        });
        playLabel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                playButton(gc);
                playLabel.setVisible(false);
            }
        });
    }

    private void playTopDown(GameContainer gc) {
        gc.launchGameMode();
    }

//    private void playRunRight(GameContainer gc) {
//        gc.launchGameMode(GameContainer.gameMode.RUNRIGHT);
//    }
//
//    private void playTopDown(GameContainer gc) {
//        gc.launchGameMode(GameContainer.gameMode.TOPDOWN);
//    }

    private void playButton(GameContainer gc) {
        gc.launchGameMode();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}