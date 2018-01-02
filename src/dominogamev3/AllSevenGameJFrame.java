/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominogamev3;

import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 *
 * @author Sierra Kilo
 */
public class AllSevenGameJFrame extends javax.swing.JFrame {
    
    JFrame previousFrame; // holds the JFrame object of the previous Frame (in this case, the main Menu Frame)
    
    private int gamemode; // the amount of players participating in the game
    private String username; // the name of the human player
    
    private AllSevenGameThread gameThread;
    private JRadioButton[] moveTypeRadioButtons;
    private JRadioButton[] choiceRadioButtons;
    private JLabel[] playerTilesLeftLabels;
    private JLabel[] playerScoreLabels;
    private JLabel[] playerNameLabels;
    private int choice; // holds an integer representing the index of the tile from the hand the human player has chosen through the GUI radio buttons.

    private static Object sharedLock;

    /**
     * Creates new form MainGameJFrame
     * @param gamemode an integer that determines the amount of players to be added to the game. The range of the values is 2-4.
     * @param username a String that is used to get the username of the human player participating in the game.
     */
    public AllSevenGameJFrame(int gamemode, String username, JFrame previousFrame) {
        initComponents();

        // initialize necessary class fields
        this.previousFrame = previousFrame;
        this.gamemode = gamemode; 
        this.username = username; 
        
        moveTypeRadioButtons = new JRadioButton[]{jRadioButtonMoveType1, jRadioButtonMoveType2};

        choiceRadioButtons = new JRadioButton[]{jRadioButton1, jRadioButton2, jRadioButton3, jRadioButton4, jRadioButton5, jRadioButton6,
            jRadioButton7, jRadioButton8, jRadioButton9, jRadioButton10, jRadioButton11, jRadioButton12, jRadioButton13, jRadioButton14,
            jRadioButton15, jRadioButton16, jRadioButton17, jRadioButton18, jRadioButton19};

        playerTilesLeftLabels = new JLabel[]{jTLeftLabel1, jTLeftLabel2, jTLeftLabel3, jTLeftLabel4};

        playerScoreLabels = new JLabel[]{jScoreLabel1, jScoreLabel2, jScoreLabel3, jScoreLabel4};

        playerNameLabels = new JLabel[]{jNameLabel1, jNameLabel2, jNameLabel3, jNameLabel4};

        sharedLock = new Object();

        // initialize and start the AllSeven Game Thread
        startGameEngineThread();

        // since the first radio button is always selected by default, 
        // we initialize the choice variable to 1 to reflect this.
        choice = 1;

    }
    
    private void startGameEngineThread() {
        gameThread = new AllSevenGameThread(gamemode, username, this, sharedLock);
        gameThread.start();
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^STARTED GAME ENGINE!");
    }

    
    private void stopGameEngineThread() {
        // notify the gameThread in case it currently waits,
        // so as to proceed to the stopThread flag checking point of its do-while loop
        System.out.println("DIAG: PREPARING FOR ----- EXIT------ NOTIFYALL...");
        synchronized (sharedLock) {
            sharedLock.notifyAll();
        }

        // set the stopThread flag inside the gameThread class to true.
        gameThread.setStopFlag(true);
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^STOPPED GAME ENGINE!");
    }

    /**
     * Used to get the object of the playingNow JLabel.
     * @return the JLabel object of the playingNow label.
     */
    public JLabel getPlayingNowLabel() {
        return jPlayingNowLabel;
    }

    /**
     * Used to get the object of the roundCounter JLabel.
     * @return the JLabel object of the roundCounter label.
     */
    public JLabel getRoundCounterLabel() {
        return jRoundCounterLabel;
    }

    /**
     * Used to get the object of the Table JLabel.
     * @return the JLabel object of the Table label.
     */
    public JLabel getTableLabel() {
        return jTableLabel;
    }

    /**
     * Used to get the JRadioButton[] array of the JRadioButton objects representing the tile choice JRadioButtons.
     * @return a JRadioButton[] array containing the JRadioButton objects of the tile choice Radio Buttons.
     */
    public JRadioButton[] getChoiceRadioButtons() {
        return choiceRadioButtons;
    }

    /**
     * Used to get the JLabel[] array of the JLabel objects representing the playerName labels.
     * @return a JLabel[] array containing the JLabel objects of the playerName labels.
     */
    public JLabel[] getPlayerNameLabels() {
        return playerNameLabels;
    }

    /**
     * Used to get the JLabel[] array of the JLabel objects representing the playerTilesLeft labels.
     * @return a JLabel[] array containing the JLabel objects of the playerTilesLeft labels.
     */
    public JLabel[] getPlayerTilesLeftStatusLabels() {
        return playerTilesLeftLabels;
    }

    /**
     * Used to get the JLabel[] array of the JLabel objects representing the playerScore labels.
     * @return a JLabel[] array containing the JLabel objects of the playerScore labels.
     */
    public JLabel[] getPlayerScoreStatusLabels() {
        return playerScoreLabels;
    }

    /**
     * Used to get the JButton object of the Submit Button.
     * @return the JButton object of the Submit button.
     */
    public JButton getSubmitButton() {
        return jSubmitButton;
    }

    /**
     * Used to get the JRadioButton[] array of the JRadioButton objects representing the MoveType Radio Buttons.
     * @return a JRadioButton[] array containing the JRadioButton objects of the MoveType Radio Buttons.
     */
    public JRadioButton[] getMoveTypeRadioButtons() {
        return moveTypeRadioButtons;
    }

    /**
     * Called to reset the selection of the tile choice Radio Buttons 
     * by setting the JRadioButton1 as selected.
     */
    public void resetRadioButtonSelector() {
        // resets the selection of the tile choice radio buttons
        // by setting the jRadioButton1 as selected.
        jRadioButton1.setSelected(true);
        choice = 1;
    }

    /**
     * Called to display the error message for the scenario in which two tiles are left in the heap
     * and there was an attempt to give one to the human player.
     */
    public void giveRandomHeapTileErrorMessage() {
        JOptionPane.showMessageDialog(null, "Two tiles are left in the heap. You can not be given any more tiles.",
                "Unavailable move", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Called to display the error message for the scenario in which the player has no possible move 
     * with any of his hand tiles.
     */
    public void noPossibleMoveAvailableMessage() {
        JOptionPane.showMessageDialog(null, "You have no possible moves with your tiles, so you will be given a random tile from the heap.",
                "No available move", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Called to display the information message that informs the human user about
     * the round winner player's name and the amount of points the round winner was given.
     * @param roundPoints
     */
    public void roundEndMessage(int roundPoints) {
        JOptionPane.showMessageDialog(null, "*** END OF ROUND! ***\nRound Winner: " + gameThread.getGameInstance().getWinnerPlayerName()
                + "\nPoints given: " + roundPoints,
                "Round end", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Called to display the information message that informs the human user about
     * the game winner player's name.
     * @param name a String representing the name of the user who won the game.
     */
    public void gameWinnerMessage(String name) {
        JOptionPane.showMessageDialog(null, "*** Player " + name + " has won the game by reaching the score limit! ***", "We have a winner!", JOptionPane.INFORMATION_MESSAGE);
    }

    private void submitAction() {

        if (jRadioButtonMoveType1.isSelected() == true) {

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

                if (selected != null) {
                    // the user has selected a choice and pressed the "OK" button
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
                } // else, if the user has pressed the "CANCEL" button in the input
            // dialog, nothing happends.
            }
        } else if (jRadioButtonMoveType2.isSelected() == true) {
            // user chose to take a random tile from the heap;
            System.out.println("DIAG: getAllTiles size: " + gameThread.getGameInstance().getHeap().getAllTiles().size());
            if (gameThread.getGameInstance().getHeap().getAllTiles().size() > 2) {
                // if the heap contains more than 2 pieces, one can be given to the user.
                gameThread.getGameInstance().getPlayingNowObj().addTileToPlayer(gameThread.getGameInstance().getHeap().pickRandomTile());
                // notify the gameThread that the human player has finished his move
                // and recover it from its suspended state
                System.out.println("DIAG: PREPARING FOR NOTIFYALL...");
                synchronized (sharedLock) {
                    sharedLock.notifyAll();
                }
            } else {
                System.out.printf("> Two or less tiles are left in the heap. You can not be given a tile.");
                giveRandomHeapTileErrorMessage();
            }
        }
    }

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
        jLabel14 = new javax.swing.JLabel();
        jPlayingNowLabel = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
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
        jLabel1 = new javax.swing.JLabel();
        jNameLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTLeftLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScoreLabel1 = new javax.swing.JLabel();
        jPlayer2Panel = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jNameLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTLeftLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jScoreLabel2 = new javax.swing.JLabel();
        jPlayer3Panel = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jNameLabel3 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTLeftLabel3 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jScoreLabel3 = new javax.swing.JLabel();
        jPlayer4Panel = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jNameLabel4 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jTLeftLabel4 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jScoreLabel4 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jRadioButtonMoveType1 = new javax.swing.JRadioButton();
        jRadioButtonMoveType2 = new javax.swing.JRadioButton();
        jPanel3 = new javax.swing.JPanel();
        jRoundCounterLabel = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(187, 187, 187), 1, true));

        jLabel14.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("dominogamev3/Bundle"); // NOI18N
        jLabel14.setText(bundle.getString("AllSevenGameJFrame.jLabel14.text")); // NOI18N

        jPlayingNowLabel.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jPlayingNowLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel13.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText(bundle.getString("AllSevenGameJFrame.jLabel13.text")); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPlayingNowLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jPlayingNowLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTablePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, bundle.getString("AllSevenGameJFrame.jTablePanel.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 14))); // NOI18N

        jTableLabel.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jTableLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jTablePanelLayout = new javax.swing.GroupLayout(jTablePanel);
        jTablePanel.setLayout(jTablePanelLayout);
        jTablePanelLayout.setHorizontalGroup(
            jTablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTableLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 1475, Short.MAX_VALUE)
        );
        jTablePanelLayout.setVerticalGroup(
            jTablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTableLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
        );

        jMoveChoicePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, bundle.getString("AllSevenGameJFrame.jMoveChoicePanel.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 14))); // NOI18N
        jMoveChoicePanel.setLayout(new java.awt.GridLayout(3, 4));

        tileChoicesButtonGroup.add(jRadioButton1);
        jRadioButton1.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jRadioButton1.setSelected(true);
        jRadioButton1.setText(bundle.getString("AllSevenGameJFrame.jRadioButton12.text")); // NOI18N
        jRadioButton1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });
        jMoveChoicePanel.add(jRadioButton1);

        tileChoicesButtonGroup.add(jRadioButton2);
        jRadioButton2.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jRadioButton2.setText(bundle.getString("AllSevenGameJFrame.jRadioButton12.text")); // NOI18N
        jRadioButton2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });
        jMoveChoicePanel.add(jRadioButton2);

        tileChoicesButtonGroup.add(jRadioButton3);
        jRadioButton3.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jRadioButton3.setText(bundle.getString("AllSevenGameJFrame.jRadioButton12.text")); // NOI18N
        jRadioButton3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jRadioButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton3ActionPerformed(evt);
            }
        });
        jMoveChoicePanel.add(jRadioButton3);

        tileChoicesButtonGroup.add(jRadioButton4);
        jRadioButton4.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jRadioButton4.setText(bundle.getString("AllSevenGameJFrame.jRadioButton12.text")); // NOI18N
        jRadioButton4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jRadioButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton4ActionPerformed(evt);
            }
        });
        jMoveChoicePanel.add(jRadioButton4);

        tileChoicesButtonGroup.add(jRadioButton5);
        jRadioButton5.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jRadioButton5.setText(bundle.getString("AllSevenGameJFrame.jRadioButton12.text")); // NOI18N
        jRadioButton5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jRadioButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton5ActionPerformed(evt);
            }
        });
        jMoveChoicePanel.add(jRadioButton5);

        tileChoicesButtonGroup.add(jRadioButton6);
        jRadioButton6.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jRadioButton6.setText(bundle.getString("AllSevenGameJFrame.jRadioButton12.text")); // NOI18N
        jRadioButton6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jRadioButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton6ActionPerformed(evt);
            }
        });
        jMoveChoicePanel.add(jRadioButton6);

        tileChoicesButtonGroup.add(jRadioButton7);
        jRadioButton7.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jRadioButton7.setText(bundle.getString("AllSevenGameJFrame.jRadioButton12.text")); // NOI18N
        jRadioButton7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jRadioButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton7ActionPerformed(evt);
            }
        });
        jMoveChoicePanel.add(jRadioButton7);

        tileChoicesButtonGroup.add(jRadioButton8);
        jRadioButton8.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jRadioButton8.setText(bundle.getString("AllSevenGameJFrame.jRadioButton12.text")); // NOI18N
        jRadioButton8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jRadioButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton8ActionPerformed(evt);
            }
        });
        jMoveChoicePanel.add(jRadioButton8);

        tileChoicesButtonGroup.add(jRadioButton9);
        jRadioButton9.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jRadioButton9.setText(bundle.getString("AllSevenGameJFrame.jRadioButton12.text")); // NOI18N
        jRadioButton9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jRadioButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton9ActionPerformed(evt);
            }
        });
        jMoveChoicePanel.add(jRadioButton9);

        tileChoicesButtonGroup.add(jRadioButton10);
        jRadioButton10.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jRadioButton10.setText(bundle.getString("AllSevenGameJFrame.jRadioButton12.text")); // NOI18N
        jRadioButton10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jRadioButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton10ActionPerformed(evt);
            }
        });
        jMoveChoicePanel.add(jRadioButton10);

        tileChoicesButtonGroup.add(jRadioButton11);
        jRadioButton11.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jRadioButton11.setText(bundle.getString("AllSevenGameJFrame.jRadioButton12.text")); // NOI18N
        jRadioButton11.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jRadioButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton11ActionPerformed(evt);
            }
        });
        jMoveChoicePanel.add(jRadioButton11);

        tileChoicesButtonGroup.add(jRadioButton12);
        jRadioButton12.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jRadioButton12.setText(bundle.getString("AllSevenGameJFrame.jRadioButton12.text")); // NOI18N
        jRadioButton12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jRadioButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton12ActionPerformed(evt);
            }
        });
        jMoveChoicePanel.add(jRadioButton12);

        tileChoicesButtonGroup.add(jRadioButton13);
        jRadioButton13.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jRadioButton13.setText(bundle.getString("AllSevenGameJFrame.jRadioButton12.text")); // NOI18N
        jMoveChoicePanel.add(jRadioButton13);

        tileChoicesButtonGroup.add(jRadioButton14);
        jRadioButton14.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jRadioButton14.setText(bundle.getString("AllSevenGameJFrame.jRadioButton12.text")); // NOI18N
        jMoveChoicePanel.add(jRadioButton14);

        tileChoicesButtonGroup.add(jRadioButton15);
        jRadioButton15.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jRadioButton15.setText(bundle.getString("AllSevenGameJFrame.jRadioButton12.text")); // NOI18N
        jMoveChoicePanel.add(jRadioButton15);

        tileChoicesButtonGroup.add(jRadioButton16);
        jRadioButton16.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jRadioButton16.setText(bundle.getString("AllSevenGameJFrame.jRadioButton12.text")); // NOI18N
        jMoveChoicePanel.add(jRadioButton16);

        tileChoicesButtonGroup.add(jRadioButton17);
        jRadioButton17.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jRadioButton17.setText(bundle.getString("AllSevenGameJFrame.jRadioButton12.text")); // NOI18N
        jMoveChoicePanel.add(jRadioButton17);

        tileChoicesButtonGroup.add(jRadioButton18);
        jRadioButton18.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jRadioButton18.setText(bundle.getString("AllSevenGameJFrame.jRadioButton12.text")); // NOI18N
        jMoveChoicePanel.add(jRadioButton18);

        tileChoicesButtonGroup.add(jRadioButton19);
        jRadioButton19.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jRadioButton19.setText(bundle.getString("AllSevenGameJFrame.jRadioButton12.text")); // NOI18N
        jMoveChoicePanel.add(jRadioButton19);

        jSubmitButton.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jSubmitButton.setText(bundle.getString("AllSevenGameJFrame.jSubmitButton.text")); // NOI18N
        jSubmitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jSubmitButtonActionPerformed(evt);
            }
        });
        jScrollPane1.setViewportView(jSubmitButton);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, bundle.getString("AllSevenGameJFrame.jPanel2.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 14))); // NOI18N
        jPanel2.setLayout(new java.awt.GridLayout(1, 4, 20, 20));

        jPlayer1Panel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(187, 187, 187), 1, true));
        jPlayer1Panel.setLayout(new java.awt.GridLayout(3, 2));

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText(bundle.getString("AllSevenGameJFrame.jLabel1.text")); // NOI18N
        jPlayer1Panel.add(jLabel1);

        jNameLabel1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jNameLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPlayer1Panel.add(jNameLabel1);

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText(bundle.getString("AllSevenGameJFrame.jLabel2.text")); // NOI18N
        jPlayer1Panel.add(jLabel2);

        jTLeftLabel1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jTLeftLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPlayer1Panel.add(jTLeftLabel1);

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText(bundle.getString("AllSevenGameJFrame.jLabel3.text")); // NOI18N
        jPlayer1Panel.add(jLabel3);

        jScoreLabel1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jScoreLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPlayer1Panel.add(jScoreLabel1);

        jPanel2.add(jPlayer1Panel);

        jPlayer2Panel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(187, 187, 187), 1, true));
        jPlayer2Panel.setLayout(new java.awt.GridLayout(3, 2));

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText(bundle.getString("AllSevenGameJFrame.jLabel1.text")); // NOI18N
        jPlayer2Panel.add(jLabel4);

        jNameLabel2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jNameLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPlayer2Panel.add(jNameLabel2);

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText(bundle.getString("AllSevenGameJFrame.jLabel2.text")); // NOI18N
        jPlayer2Panel.add(jLabel5);

        jTLeftLabel2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jTLeftLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPlayer2Panel.add(jTLeftLabel2);

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText(bundle.getString("AllSevenGameJFrame.jLabel3.text")); // NOI18N
        jPlayer2Panel.add(jLabel6);

        jScoreLabel2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jScoreLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPlayer2Panel.add(jScoreLabel2);

        jPanel2.add(jPlayer2Panel);

        jPlayer3Panel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(187, 187, 187), 1, true));
        jPlayer3Panel.setLayout(new java.awt.GridLayout(3, 2));

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText(bundle.getString("AllSevenGameJFrame.jLabel1.text")); // NOI18N
        jPlayer3Panel.add(jLabel7);

        jNameLabel3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jNameLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPlayer3Panel.add(jNameLabel3);

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText(bundle.getString("AllSevenGameJFrame.jLabel2.text")); // NOI18N
        jPlayer3Panel.add(jLabel8);

        jTLeftLabel3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jTLeftLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPlayer3Panel.add(jTLeftLabel3);

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText(bundle.getString("AllSevenGameJFrame.jLabel3.text")); // NOI18N
        jPlayer3Panel.add(jLabel9);

        jScoreLabel3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jScoreLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPlayer3Panel.add(jScoreLabel3);

        jPanel2.add(jPlayer3Panel);

        jPlayer4Panel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(187, 187, 187), 1, true));
        jPlayer4Panel.setLayout(new java.awt.GridLayout(3, 2));

        jLabel10.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText(bundle.getString("AllSevenGameJFrame.jLabel1.text")); // NOI18N
        jPlayer4Panel.add(jLabel10);

        jNameLabel4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jNameLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPlayer4Panel.add(jNameLabel4);

        jLabel11.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText(bundle.getString("AllSevenGameJFrame.jLabel2.text")); // NOI18N
        jPlayer4Panel.add(jLabel11);

        jTLeftLabel4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jTLeftLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPlayer4Panel.add(jTLeftLabel4);

        jLabel12.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText(bundle.getString("AllSevenGameJFrame.jLabel3.text")); // NOI18N
        jPlayer4Panel.add(jLabel12);

        jScoreLabel4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jScoreLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPlayer4Panel.add(jScoreLabel4);

        jPanel2.add(jPlayer4Panel);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, bundle.getString("AllSevenGameJFrame.jPanel4.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 14))); // NOI18N
        jPanel4.setLayout(new java.awt.GridLayout(1, 2));

        moveTypeButtonGroup.add(jRadioButtonMoveType1);
        jRadioButtonMoveType1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jRadioButtonMoveType1.setSelected(true);
        jRadioButtonMoveType1.setText(bundle.getString("AllSevenGameJFrame.jRadioButtonMoveType1.text")); // NOI18N
        jRadioButtonMoveType1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel4.add(jRadioButtonMoveType1);

        moveTypeButtonGroup.add(jRadioButtonMoveType2);
        jRadioButtonMoveType2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jRadioButtonMoveType2.setText(bundle.getString("AllSevenGameJFrame.jRadioButtonMoveType2.text")); // NOI18N
        jRadioButtonMoveType2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel4.add(jRadioButtonMoveType2);

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(187, 187, 187)));

        jRoundCounterLabel.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jRoundCounterLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel15.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText(bundle.getString("AllSevenGameJFrame.jLabel15.text")); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRoundCounterLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jRoundCounterLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jMenu1.setText(bundle.getString("AllSevenGameJFrame.jMenu1.text")); // NOI18N

        jMenuItem1.setText(bundle.getString("AllSevenGameJFrame.jMenuItem1.text")); // NOI18N
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText(bundle.getString("AllSevenGameJFrame.jMenuItem2.text")); // NOI18N
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

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
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 1485, Short.MAX_VALUE)
                    .addComponent(jMoveChoicePanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTablePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jMoveChoicePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        this.dispose();
        stopGameEngineThread();
        
        while (gameThread.getState() != Thread.State.TERMINATED) {
            System.out.println("diag: TO THREAD DEN TERMATISE AKOMA!");
        }
        
        previousFrame.setVisible(true);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        stopGameEngineThread();
        
        while (gameThread.getState() != Thread.State.TERMINATED) {
            System.out.println("diag: TO THREAD DEN TERMATISE AKOMA!");
        }
        
        startGameEngineThread();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

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
            java.util.logging.Logger.getLogger(AllSevenGameJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AllSevenGameJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AllSevenGameJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AllSevenGameJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new AllSevenGameJFrame(2, "testuser", new MainMenuJFrame()).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jMoveChoicePanel;
    private javax.swing.JLabel jNameLabel1;
    private javax.swing.JLabel jNameLabel2;
    private javax.swing.JLabel jNameLabel3;
    private javax.swing.JLabel jNameLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
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
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JRadioButton jRadioButton5;
    private javax.swing.JRadioButton jRadioButton6;
    private javax.swing.JRadioButton jRadioButton7;
    private javax.swing.JRadioButton jRadioButton8;
    private javax.swing.JRadioButton jRadioButton9;
    private javax.swing.JRadioButton jRadioButtonMoveType1;
    private javax.swing.JRadioButton jRadioButtonMoveType2;
    private javax.swing.JLabel jRoundCounterLabel;
    private javax.swing.JLabel jScoreLabel1;
    private javax.swing.JLabel jScoreLabel2;
    private javax.swing.JLabel jScoreLabel3;
    private javax.swing.JLabel jScoreLabel4;
    private javax.swing.JScrollPane jScrollPane1;
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
