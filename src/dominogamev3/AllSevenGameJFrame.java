/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominogamev3;

import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 *
 * @author Sierra Kilo
 */
public class AllSevenGameJFrame extends javax.swing.JFrame {

    private HungarianGameThread gameThread;
    private JRadioButton[] choiceRadioButtons;
    private JLabel[] playerTilesLeftLabels;
    private JLabel[] playerScoreLabels;
    private JLabel[] playerNameLabels;
    private int choice; // holds an integer representing the index of the tile from the hand the human player has chosen through the GUI radio buttons.

    private static Object sharedLock;

    /**
     * Creates new form MainGameJFrame
     */
    public AllSevenGameJFrame(int gamemode, String username) {
        initComponents();

        // initialize necessary class fields
        choiceRadioButtons = new JRadioButton[]{jRadioButton1, jRadioButton2, jRadioButton3, jRadioButton4, jRadioButton5, jRadioButton6,
            jRadioButton7, jRadioButton8, jRadioButton9, jRadioButton10, jRadioButton11, jRadioButton12, jRadioButton13, jRadioButton14, 
            jRadioButton15, jRadioButton16, jRadioButton17, jRadioButton18, jRadioButton19};
        
        playerTilesLeftLabels = new JLabel[]{jTLeftLabel1, jTLeftLabel2, jTLeftLabel3, jTLeftLabel4};
        
        playerScoreLabels = new JLabel[]{jScoreLabel1, jScoreLabel2, jScoreLabel3, jScoreLabel4};
        
        playerNameLabels = new JLabel[]{jNameLabel1, jNameLabel2, jNameLabel3, jNameLabel4};
        
        sharedLock = new Object();

        // initialize and start the Hungarian Game Thread
        gameThread = new AllSevenGameThread(gamemode, username, this, sharedLock);
        gameThread.start();
        
        // since the first radio button is always selected by default, 
        // we initialize the choice variable to 1 to reflect this.
        choice = 1;

    }

    public JLabel getPlayingNowLabel() {
        return jPlayingNowLabel;
    }
    
    public JLabel getRoundCounterLabel() {
        return jRoundCounterLabel;
    }

    public JLabel getTableLabel() {
        return jTableLabel;
    }

    public JRadioButton[] getChoiceRadioButtons() {
        return choiceRadioButtons;
    }
    
    public JLabel[] getPlayerNameLabels() {
        return playerNameLabels;
    }
    
    public JLabel[] getPlayerTilesLeftStatusLabels() {
        return playerTilesLeftLabels;
    }
    
    public JLabel[] getPlayerScoreStatusLabels() {
        return playerScoreLabels;
    }
    
    public JButton getSubmitButton() {
        return jSubmitButton;
    }
    
    public void resetRadioButtonSelector() {
        // resets the selection of the tile choice radio buttons
        // by setting the jRadioButton1 as selected.
        jRadioButton1.setSelected(true);
        choice = 1;
    }

    public void roundEndMessage(int roundPoints) {
        JOptionPane.showMessageDialog(null, "*** END OF ROUND! ***\nRound Winner: " + gameThread.getGameInstance().getWinnerPlayerName()
                + "\nPoints given: " + roundPoints,
                "Round end", JOptionPane.INFORMATION_MESSAGE);
    }

    private void submitAction() {
        ArrayList<PossibleMove> result;
        Tile chosenTile;
        
        System.out.println("DIAG: *********** choice number is: " + choice);

        chosenTile = gameThread.getGameInstance().getPlayingNowObj().chooseTile(choice);
        result = gameThread.getGameInstance().checkTileChoice(chosenTile);

        System.out.println("DIAG: INSIDE SUBMIT ACTION FUNC!");
        if (result.size() == 0) {
            System.out.println("DIAG: SUBMIT ACTION FUNC CHECKPOINT 1");
            //there is no possible move with the chosen tile.
            System.out.printf("%n");
            System.out.println("> There is no possible move with the chosen tile! Try again!");
            System.out.printf("%n");
            JOptionPane.showMessageDialog(null, "There is no possible move with the chosen tile!\nPlease select another tile.", "Unavailable move", JOptionPane.ERROR_MESSAGE);
            //continue
        } else if (result.size() == 1) {
            System.out.println("DIAG: SUBMIT ACTION FUNC CHECKPOINT 2");
            //there is one possible move so tile is placed automatically.
            gameThread.getGameInstance().humanPlays(choice, chosenTile, result.get(0).needsRotation(), result.get(0).whereToPlace());

            // notify the gameThread that the human player has finished his move
            // and recover it from its suspended state
            synchronized (sharedLock) {
                sharedLock.notifyAll();
            }
            System.out.println("DIAG: NOTIFYALL EXECUTED!");

        } else {
            System.out.println("DIAG: SUBMIT ACTION FUNC CHECKPOINT 3");
            //there are more 2 possible moves 
            //so user is asked about where to place tile
            System.out.println("> There are 2 possible moves with this tile.");
            System.out.println("> Do you want to place the tile left or right?");

            String[] values = {"Left side", "Right side"};
            Object selected = JOptionPane.showInputDialog(null, "There are 2 possible moves with this tile.\n"
                    + "On which side of the table do you want to place the tile?",
                    "Multiple moves", JOptionPane.DEFAULT_OPTION, null, values, "0");

            String selectedString = selected.toString();

            System.out.println("DIAG: object selected is: " + selected);
            System.out.println("DIAG: selectedString is: " + selectedString);

            String side;
            if (selectedString.equals("Left side")) {
                side = "left";
            } else {
                side = "right";
            }

            if (result.get(0).whereToPlace().equals(side)) {
                System.out.println("MPIKA PERIPTOSI 1");
                gameThread.getGameInstance().humanPlays(choice, chosenTile, result.get(0).needsRotation(), side);
            } else if (result.get(1).whereToPlace().equals(side)) {
                System.out.println("MPIKA PERIPTOSI 2");
                gameThread.getGameInstance().humanPlays(choice, chosenTile, result.get(1).needsRotation(), side);
            }

            // notify the gameThread that the human player has finished his move
            // and recover it from its suspended state
            System.out.println("DIAG: PREPARING FOR NOTIFYALL...");
            synchronized (sharedLock) {
                sharedLock.notifyAll();
            }
        }
    }

    // Getter functions code END
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tileChoicesButtonGroup = new javax.swing.ButtonGroup();
        moveTypeButtonGroup = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPlayingNowLabel = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jRoundCounterLabel = new javax.swing.JLabel();
        jTablePanel = new javax.swing.JPanel();
        jTableLabel = new javax.swing.JLabel();
        jMoveChoicePanel = new javax.swing.JPanel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton4 = new javax.swing.JRadioButton();
        jRadioButton5 = new javax.swing.JRadioButton();
        jRadioButton6 = new javax.swing.JRadioButton();
        jRadioButton7 = new javax.swing.JRadioButton();
        jRadioButton8 = new javax.swing.JRadioButton();
        jRadioButton9 = new javax.swing.JRadioButton();
        jRadioButton10 = new javax.swing.JRadioButton();
        jRadioButton11 = new javax.swing.JRadioButton();
        jRadioButton12 = new javax.swing.JRadioButton();
        jRadioButton13 = new javax.swing.JRadioButton();
        jRadioButton14 = new javax.swing.JRadioButton();
        jRadioButton15 = new javax.swing.JRadioButton();
        jRadioButton16 = new javax.swing.JRadioButton();
        jRadioButton17 = new javax.swing.JRadioButton();
        jRadioButton18 = new javax.swing.JRadioButton();
        jRadioButton19 = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jSubmitButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPlayer1Panel = new javax.swing.JPanel();
        jNameLabel1 = new javax.swing.JLabel();
        jTLeftLabel1 = new javax.swing.JLabel();
        jScoreLabel1 = new javax.swing.JLabel();
        jPlayer2Panel = new javax.swing.JPanel();
        jNameLabel2 = new javax.swing.JLabel();
        jTLeftLabel2 = new javax.swing.JLabel();
        jScoreLabel2 = new javax.swing.JLabel();
        jPlayer3Panel = new javax.swing.JPanel();
        jNameLabel3 = new javax.swing.JLabel();
        jTLeftLabel3 = new javax.swing.JLabel();
        jScoreLabel3 = new javax.swing.JLabel();
        jPlayer4Panel = new javax.swing.JPanel();
        jNameLabel4 = new javax.swing.JLabel();
        jTLeftLabel4 = new javax.swing.JLabel();
        jScoreLabel4 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jRadioButton20 = new javax.swing.JRadioButton();
        jRadioButton21 = new javax.swing.JRadioButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(187, 187, 187), 1, true));

        jPlayingNowLabel.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jPlayingNowLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPlayingNowLabel.setText("... plays now");

        jSeparator1.setForeground(new java.awt.Color(187, 187, 187));
        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jRoundCounterLabel.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jRoundCounterLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jRoundCounterLabel.setText("Round ...");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPlayingNowLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 679, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRoundCounterLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPlayingNowLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
            .addComponent(jSeparator1)
            .addComponent(jRoundCounterLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTablePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Table", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 14))); // NOI18N

        jTableLabel.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jTableLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jTableLabel.setText("jTableLabel");

        javax.swing.GroupLayout jTablePanelLayout = new javax.swing.GroupLayout(jTablePanel);
        jTablePanel.setLayout(jTablePanelLayout);
        jTablePanelLayout.setHorizontalGroup(
            jTablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTableLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jTablePanelLayout.setVerticalGroup(
            jTablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTableLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
        );

        jMoveChoicePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pick a tile from your hand to make your move", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 14))); // NOI18N
        jMoveChoicePanel.setLayout(new java.awt.GridLayout(3, 4));

        tileChoicesButtonGroup.add(jRadioButton1);
        jRadioButton1.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jRadioButton1.setSelected(true);
        jRadioButton1.setText("jRadioButton1");
        jRadioButton1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });
        jMoveChoicePanel.add(jRadioButton1);

        tileChoicesButtonGroup.add(jRadioButton2);
        jRadioButton2.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jRadioButton2.setText("jRadioButton2");
        jRadioButton2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });
        jMoveChoicePanel.add(jRadioButton2);

        tileChoicesButtonGroup.add(jRadioButton3);
        jRadioButton3.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jRadioButton3.setText("jRadioButton3");
        jRadioButton3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jRadioButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton3ActionPerformed(evt);
            }
        });
        jMoveChoicePanel.add(jRadioButton3);

        tileChoicesButtonGroup.add(jRadioButton4);
        jRadioButton4.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jRadioButton4.setText("jRadioButton4");
        jRadioButton4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jRadioButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton4ActionPerformed(evt);
            }
        });
        jMoveChoicePanel.add(jRadioButton4);

        tileChoicesButtonGroup.add(jRadioButton5);
        jRadioButton5.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jRadioButton5.setText("jRadioButton5");
        jRadioButton5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jRadioButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton5ActionPerformed(evt);
            }
        });
        jMoveChoicePanel.add(jRadioButton5);

        tileChoicesButtonGroup.add(jRadioButton6);
        jRadioButton6.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jRadioButton6.setText("jRadioButton6");
        jRadioButton6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jRadioButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton6ActionPerformed(evt);
            }
        });
        jMoveChoicePanel.add(jRadioButton6);

        tileChoicesButtonGroup.add(jRadioButton7);
        jRadioButton7.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jRadioButton7.setText("jRadioButton7");
        jRadioButton7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jRadioButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton7ActionPerformed(evt);
            }
        });
        jMoveChoicePanel.add(jRadioButton7);

        tileChoicesButtonGroup.add(jRadioButton8);
        jRadioButton8.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jRadioButton8.setText("jRadioButton8");
        jRadioButton8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jRadioButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton8ActionPerformed(evt);
            }
        });
        jMoveChoicePanel.add(jRadioButton8);

        tileChoicesButtonGroup.add(jRadioButton9);
        jRadioButton9.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jRadioButton9.setText("jRadioButton9");
        jRadioButton9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jRadioButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton9ActionPerformed(evt);
            }
        });
        jMoveChoicePanel.add(jRadioButton9);

        tileChoicesButtonGroup.add(jRadioButton10);
        jRadioButton10.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jRadioButton10.setText("jRadioButton10");
        jRadioButton10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jRadioButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton10ActionPerformed(evt);
            }
        });
        jMoveChoicePanel.add(jRadioButton10);

        tileChoicesButtonGroup.add(jRadioButton11);
        jRadioButton11.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jRadioButton11.setText("jRadioButton11");
        jRadioButton11.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jRadioButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton11ActionPerformed(evt);
            }
        });
        jMoveChoicePanel.add(jRadioButton11);

        tileChoicesButtonGroup.add(jRadioButton12);
        jRadioButton12.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jRadioButton12.setText("jRadioButton12");
        jRadioButton12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jRadioButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton12ActionPerformed(evt);
            }
        });
        jMoveChoicePanel.add(jRadioButton12);

        jRadioButton13.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jRadioButton13.setText("jRadioButton13");
        jMoveChoicePanel.add(jRadioButton13);

        tileChoicesButtonGroup.add(jRadioButton14);
        jRadioButton14.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jRadioButton14.setText("jRadioButton14");
        jMoveChoicePanel.add(jRadioButton14);

        tileChoicesButtonGroup.add(jRadioButton15);
        jRadioButton15.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jRadioButton15.setText("jRadioButton15");
        jMoveChoicePanel.add(jRadioButton15);

        tileChoicesButtonGroup.add(jRadioButton16);
        jRadioButton16.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jRadioButton16.setText("jRadioButton16");
        jMoveChoicePanel.add(jRadioButton16);

        tileChoicesButtonGroup.add(jRadioButton17);
        jRadioButton17.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jRadioButton17.setText("jRadioButton17");
        jMoveChoicePanel.add(jRadioButton17);

        tileChoicesButtonGroup.add(jRadioButton18);
        jRadioButton18.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jRadioButton18.setText("jRadioButton18");
        jMoveChoicePanel.add(jRadioButton18);

        tileChoicesButtonGroup.add(jRadioButton19);
        jRadioButton19.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jRadioButton19.setText("jRadioButton19");
        jMoveChoicePanel.add(jRadioButton19);

        jSubmitButton.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jSubmitButton.setText("Submit you choice!");
        jSubmitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jSubmitButtonActionPerformed(evt);
            }
        });
        jScrollPane1.setViewportView(jSubmitButton);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Player Status", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 14))); // NOI18N
        jPanel2.setLayout(new java.awt.GridLayout(1, 4, 20, 20));

        jPlayer1Panel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(187, 187, 187), 1, true));

        jNameLabel1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jNameLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jNameLabel1.setText("Name: ");

        jTLeftLabel1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jTLeftLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jTLeftLabel1.setText("Tiles left: ...");

        jScoreLabel1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jScoreLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jScoreLabel1.setText("Score: ...");

        javax.swing.GroupLayout jPlayer1PanelLayout = new javax.swing.GroupLayout(jPlayer1Panel);
        jPlayer1Panel.setLayout(jPlayer1PanelLayout);
        jPlayer1PanelLayout.setHorizontalGroup(
            jPlayer1PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTLeftLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE)
            .addComponent(jNameLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScoreLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPlayer1PanelLayout.setVerticalGroup(
            jPlayer1PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPlayer1PanelLayout.createSequentialGroup()
                .addComponent(jNameLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTLeftLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScoreLabel1)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jPanel2.add(jPlayer1Panel);

        jPlayer2Panel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(187, 187, 187), 1, true));

        jNameLabel2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jNameLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jNameLabel2.setText("Name: ");

        jTLeftLabel2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jTLeftLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jTLeftLabel2.setText("Tiles left: ...");

        jScoreLabel2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jScoreLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jScoreLabel2.setText("Score: ...");

        javax.swing.GroupLayout jPlayer2PanelLayout = new javax.swing.GroupLayout(jPlayer2Panel);
        jPlayer2Panel.setLayout(jPlayer2PanelLayout);
        jPlayer2PanelLayout.setHorizontalGroup(
            jPlayer2PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTLeftLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE)
            .addComponent(jScoreLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jNameLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPlayer2PanelLayout.setVerticalGroup(
            jPlayer2PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPlayer2PanelLayout.createSequentialGroup()
                .addComponent(jNameLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTLeftLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScoreLabel2)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jPanel2.add(jPlayer2Panel);

        jPlayer3Panel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(187, 187, 187), 1, true));

        jNameLabel3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jNameLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jNameLabel3.setText("Name: ");

        jTLeftLabel3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jTLeftLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jTLeftLabel3.setText("Tiles left: ...");

        jScoreLabel3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jScoreLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jScoreLabel3.setText("Score: ...");

        javax.swing.GroupLayout jPlayer3PanelLayout = new javax.swing.GroupLayout(jPlayer3Panel);
        jPlayer3Panel.setLayout(jPlayer3PanelLayout);
        jPlayer3PanelLayout.setHorizontalGroup(
            jPlayer3PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTLeftLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE)
            .addComponent(jScoreLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jNameLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPlayer3PanelLayout.setVerticalGroup(
            jPlayer3PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPlayer3PanelLayout.createSequentialGroup()
                .addComponent(jNameLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTLeftLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScoreLabel3)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jPanel2.add(jPlayer3Panel);

        jPlayer4Panel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(187, 187, 187), 1, true));

        jNameLabel4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jNameLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jNameLabel4.setText("Name: ");

        jTLeftLabel4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jTLeftLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jTLeftLabel4.setText("Tiles left: ...");

        jScoreLabel4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jScoreLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jScoreLabel4.setText("Score: ...");

        javax.swing.GroupLayout jPlayer4PanelLayout = new javax.swing.GroupLayout(jPlayer4Panel);
        jPlayer4Panel.setLayout(jPlayer4PanelLayout);
        jPlayer4PanelLayout.setHorizontalGroup(
            jPlayer4PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTLeftLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE)
            .addComponent(jScoreLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jNameLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPlayer4PanelLayout.setVerticalGroup(
            jPlayer4PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPlayer4PanelLayout.createSequentialGroup()
                .addComponent(jNameLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTLeftLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScoreLabel4)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jPanel2.add(jPlayer4Panel);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Choose move type", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 14))); // NOI18N
        jPanel4.setLayout(new java.awt.GridLayout(1, 2));

        jRadioButton20.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jRadioButton20.setSelected(true);
        jRadioButton20.setText("Play using a tile from your hand");
        jRadioButton20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel4.add(jRadioButton20);

        jRadioButton21.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jRadioButton21.setText("Pass and get a random tile from the heap");
        jRadioButton21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel4.add(jRadioButton21);

        jMenu1.setText("File");

        jMenuItem1.setText("New game");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Reset current game");
        jMenu1.add(jMenuItem2);

        jMenuItem3.setText("Exit");
        jMenu1.add(jMenuItem3);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTablePanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 1485, Short.MAX_VALUE)
                    .addComponent(jMoveChoicePanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTablePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(jMoveChoicePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        // TODO add your handling code here:
        choice = 1;
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        // TODO add your handling code here:
        choice = 2;
    }//GEN-LAST:event_jRadioButton2ActionPerformed

    private void jRadioButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton3ActionPerformed
        // TODO add your handling code here:
        choice = 3;
    }//GEN-LAST:event_jRadioButton3ActionPerformed

    private void jRadioButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton4ActionPerformed
        // TODO add your handling code here:
        choice = 4;
    }//GEN-LAST:event_jRadioButton4ActionPerformed

    private void jRadioButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton5ActionPerformed
        // TODO add your handling code here:
        choice = 5;
    }//GEN-LAST:event_jRadioButton5ActionPerformed

    private void jRadioButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton6ActionPerformed
        // TODO add your handling code here:
        choice = 6;
    }//GEN-LAST:event_jRadioButton6ActionPerformed

    private void jRadioButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton7ActionPerformed
        // TODO add your handling code here:
        choice = 7;
    }//GEN-LAST:event_jRadioButton7ActionPerformed

    private void jRadioButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton8ActionPerformed
        // TODO add your handling code here:
        choice = 8;
    }//GEN-LAST:event_jRadioButton8ActionPerformed

    private void jRadioButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton9ActionPerformed
        // TODO add your handling code here:
        choice = 9;
    }//GEN-LAST:event_jRadioButton9ActionPerformed

    private void jRadioButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton10ActionPerformed
        // TODO add your handling code here:
        choice = 10;
    }//GEN-LAST:event_jRadioButton10ActionPerformed

    private void jRadioButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton11ActionPerformed
        // TODO add your handling code here:
        choice = 11;
    }//GEN-LAST:event_jRadioButton11ActionPerformed

    private void jRadioButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton12ActionPerformed
        // TODO add your handling code here:
        choice = 12;
    }//GEN-LAST:event_jRadioButton12ActionPerformed

    private void jSubmitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jSubmitButtonActionPerformed
        // TODO add your handling code here:
        submitAction();
    }//GEN-LAST:event_jSubmitButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(HungarianGameJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HungarianGameJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HungarianGameJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HungarianGameJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HungarianGameJFrame(2,"testuser").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jMoveChoicePanel;
    private javax.swing.JLabel jNameLabel1;
    private javax.swing.JLabel jNameLabel2;
    private javax.swing.JLabel jNameLabel3;
    private javax.swing.JLabel jNameLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPlayer1Panel;
    private javax.swing.JPanel jPlayer2Panel;
    private javax.swing.JPanel jPlayer3Panel;
    private javax.swing.JPanel jPlayer4Panel;
    private javax.swing.JLabel jPlayingNowLabel;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton10;
    private javax.swing.JRadioButton jRadioButton11;
    private javax.swing.JRadioButton jRadioButton12;
    private javax.swing.JRadioButton jRadioButton13;
    private javax.swing.JRadioButton jRadioButton14;
    private javax.swing.JRadioButton jRadioButton15;
    private javax.swing.JRadioButton jRadioButton16;
    private javax.swing.JRadioButton jRadioButton17;
    private javax.swing.JRadioButton jRadioButton18;
    private javax.swing.JRadioButton jRadioButton19;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton20;
    private javax.swing.JRadioButton jRadioButton21;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JRadioButton jRadioButton5;
    private javax.swing.JRadioButton jRadioButton6;
    private javax.swing.JRadioButton jRadioButton7;
    private javax.swing.JRadioButton jRadioButton8;
    private javax.swing.JRadioButton jRadioButton9;
    private javax.swing.JLabel jRoundCounterLabel;
    private javax.swing.JLabel jScoreLabel1;
    private javax.swing.JLabel jScoreLabel2;
    private javax.swing.JLabel jScoreLabel3;
    private javax.swing.JLabel jScoreLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JButton jSubmitButton;
    private javax.swing.JLabel jTLeftLabel1;
    private javax.swing.JLabel jTLeftLabel2;
    private javax.swing.JLabel jTLeftLabel3;
    private javax.swing.JLabel jTLeftLabel4;
    private javax.swing.JLabel jTableLabel;
    private javax.swing.JPanel jTablePanel;
    private javax.swing.ButtonGroup moveTypeButtonGroup;
    private javax.swing.ButtonGroup tileChoicesButtonGroup;
    // End of variables declaration//GEN-END:variables
}
