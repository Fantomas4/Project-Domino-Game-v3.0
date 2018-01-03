/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominogamev3;

import static dominogamev3.DominoGameMain.intChoiceValidityCheck;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 * A Class used to create and manage a thread in which a Hungarian game instance runs.
 * @author Sierra Kilo
 */
public class HungarianGameThread extends Thread {

    private HungarianGameLogic gameInstance;
    private HungarianGameJFrame gameFrame;
    ArrayList<Player> playerList; 
    
    private static boolean stopThread; // Used to check whether the thread must terminate or not.
    
    private static Object sharedLock;

    /**
     *
     * @param gamemode the amount of players participating in the game. The accepted range is 2-4.
     * @param username a String representing the name of the human player participating in the game.
     * @param gameFrame a HungarianGameJFrame object representing the Frame of the GUI used for the game.
     * @param sharedLock an object representing the sharedLock used between the GUI thread and the Hungarian game thread for their synchronization.
     */
    public HungarianGameThread(int gamemode, String username, HungarianGameJFrame gameFrame, Object sharedLock) {
        gameInstance = new HungarianGameLogic(gamemode, username);
        playerList = gameInstance.getPlayerOrderedList();
        this.gameFrame = gameFrame;
        
        stopThread = false;
        
        this.sharedLock = sharedLock;
    }
    
    /**
     * Called to change the value of the stopThread flag.
     * @param value the new boolean value for the stopThread flag.
     */
    public void setStopFlag(boolean value) {
        stopThread = value;
    }

    /**
     * Used to get the game instance of the Hungarian game in its current state.
     * @return a HungarianGameLogic object representing the current state of the game instance of the Hungarian game.
     */
    public HungarianGameLogic getGameInstance() {
        return gameInstance;
    }

    private void updatePlayingNowLabel() {
        // Set the GUI label that shows who plays now:
        System.out.println("DIAG: updateguielements CHECKPOINT 1");
        gameFrame.getPlayingNowLabel().setText(gameInstance.getPlayingNowObj().getPlayerName());
    }
    
    private void updateRoundCounterLabel() {
        gameFrame.getRoundCounterLabel().setText(Integer.toString(gameInstance.getRoundCount()));
    }
    
    private void resetPlayerStatusPanel() {
        JLabel[] nameLabels = gameFrame.getPlayerNameLabels();
        JLabel[] tLeftLabels = gameFrame.getPlayerTilesLeftStatusLabels();
        JLabel[] scoreLabels = gameFrame.getPlayerScoreStatusLabels();
        
        for (JLabel label : nameLabels) {
            label.setText("");
        }
        
        for (JLabel label : tLeftLabels) {
            label.setText("");
        }
        
        for (JLabel label : scoreLabels) {
            label.setText("");
        }
    }
    
    private void setPlayerNameLabels() { 
        JLabel[] nameLabels = gameFrame.getPlayerNameLabels();
        
        for (int i = 0 ; i < playerList.size() ; i++) {
            nameLabels[i].setText(playerList.get(i).getPlayerName());
        }
        
    }
    
    private void updatePlayerTilesLeftLabels() {
        JLabel[] tLeftLabels = gameFrame.getPlayerTilesLeftStatusLabels();
        
        for (int i = 0 ; i < playerList.size() ; i++) {
            tLeftLabels[i].setText(Integer.toString(playerList.get(i).getPlayerTilesAmount()));
        }
    }
    
    private void updatePlayerScoreLabels() {
        JLabel[] scoreLabels = gameFrame.getPlayerScoreStatusLabels();
        
        for (int i = 0 ; i < playerList.size() ; i++) {
            scoreLabels[i].setText(Integer.toString(playerList.get(i).getScore()));
        }
    }

    private void updateTableLabel() {
        // Get the table of the game and show in on the GUI:
        Table tableObj = gameInstance.getTable();
        ArrayList<Tile> tableTiles = tableObj.getTable();
        String tableText = "";

        for (Tile piece : tableTiles) {
            tableText += "|" + piece.getNum1() + " " + piece.getNum2() + "| ";
        }

        gameFrame.getTableLabel().setText(tableText);
    }

    private void resetButtonChoices(JRadioButton[] buttons) {
        // reset all the radio buttons by emptying their text and disabling them.

        for (JRadioButton button : buttons) {
            button.setText("");
            button.setEnabled(false);
        }

    }

    private void updateButtonChoices() {
        // Get the hand of the player and show it on the GUI using the JRadioButtons as choices:

        JRadioButton[] choiceRadioButtons = gameFrame.getChoiceRadioButtons();

        // reset all the radio buttons by emptying their text and disabling them.
        resetButtonChoices(gameFrame.getChoiceRadioButtons());

        int pos = 0; // position indicator used to traverse the choiceRadioButtons array
        ArrayList<Tile> playerHand = gameInstance.getPlayingNowObj().getPlayerTiles();

        for (int i = 0; i < playerHand.size(); i++) {
            choiceRadioButtons[pos].setText("|" + playerHand.get(i).getNum1() + " " + playerHand.get(i).getNum2() + "|");
            choiceRadioButtons[pos].setEnabled(true);
            pos++;
        }

        // for diagnostic purposes, print the hand to console
    }
    
    private void enableSubmitButton() {
        gameFrame.getSubmitButton().setEnabled(true);
    }
    
    private void disableSubmitButton() {
        gameFrame.getSubmitButton().setEnabled(false);
    }

    /**
     * The main method that when called, initializes and runs the back-end game engine of the Hungarian game.
     * @throws InterruptedException
     */
    public void executeGame() throws InterruptedException {
        
        resetPlayerStatusPanel();
        setPlayerNameLabels();
        
        do {
            System.out.println("DIAG: THREAD CHECKPOINT 1");
            gameInstance.setPlayingNowPlayer(gameInstance.firstPlayerIndex());

            do {

                updatePlayingNowLabel();
                updateRoundCounterLabel();
                
                System.out.println("DIAG: PlayerOrderedList index 0: " + gameInstance.getPlayerOrderedList().get(0));
                updatePlayerTilesLeftLabels();
                updatePlayerScoreLabels();
                updateTableLabel();

                System.out.printf("%n%n%n%n%n%n%n%n%n%n%n");
                System.out.println("==================================================================================================================================");

                System.out.println("*** Player  > " + gameInstance.getPlayingNowObj().getPlayerName() + " <  is playing now. ***");
                System.out.printf("%n");

                if (gameInstance.getPlayingNowObj() instanceof Human) {
                    System.out.println("DIAG: THREAD CHECKPOINT 2");
                    // if the player playing now is a human, show his tiles (hand) on the GUI.
                    gameFrame.resetRadioButtonSelector(); // sets the selector for the radio button group to the jRadioButton1
                    enableSubmitButton();
                    updateButtonChoices();

                    // suspend the thread and wait for the human to make a move through the GUI.
                    // suspend the thread until a notify() call from the GUI part of the program is executed
                    // to indicate that the user has finished his move.
                    synchronized (sharedLock) {
                        try {
                            System.out.println("DIAG: WAITING....");
                            sharedLock.wait();
                        } catch (InterruptedException e) {

                        }
                    }

                    System.out.println("DIAG: PROCEEDING...");
                    updateTableLabel();

                } else {
                    // if the player playing now is a bot, update the playingNowLabel,
                    // reset and disable the radio button choices
                    // and update the TableLabel.

                    disableSubmitButton();
                    
                    updatePlayingNowLabel();

                    resetButtonChoices(gameFrame.getChoiceRadioButtons());
                    
                    Thread.sleep(500);
                    
                    gameInstance.botPlays();

                    updateTableLabel();

                }

                if (gameInstance.whoPlaysNext() >= 0) {
                    gameInstance.setPlayingNowPlayer(gameInstance.whoPlaysNext());
                } else {
                    // round ends
                    break;
                }
                
                // we check if the stopThread flag has been set to true,
                // meaning the thread should exit immediately
                if (stopThread == true) {
                    return;
                }

            } while (true);            
            
            int roundPoints = gameInstance.giveRoundPoints(); // give the round points to the winner
            gameFrame.roundEndMessage(roundPoints); 
            gameInstance.resetRound();
            gameInstance.incRoundCount();

        } while (gameInstance.scoreLimitReached() == false);

        System.out.println("%n%n%n");
        System.out.println("                              *** Player " + gameInstance.getWinnerPlayerName() + " has won the game by reaching the score limit! ***");
        gameFrame.gameWinnerMessage(gameInstance.getWinnerPlayerName());
    }

    /**
     * Used to call the executeGame() method that starts the back-end game engine of the HungarianGame.
     */
    @Override
    public void run() {
        try {
            executeGame();
        } catch (InterruptedException ex) {
            Logger.getLogger(HungarianGameThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
