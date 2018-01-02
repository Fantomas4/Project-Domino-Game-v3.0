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
import javax.swing.JLabel;
import javax.swing.JRadioButton;

/**
 * A Class used to create and manage a thread in which an AllSevenGame instance runs.
 * @author Sierra Kilo
 */
public class AllSevenGameThread extends Thread {

    private AllSevenGameLogic gameInstance;
    private AllSevenGameJFrame gameFrame;
    ArrayList<Player> playerList;
    private boolean holdPlayerTurn; // when set to true, the game will not give a turn to the next player. Instead, it
    // will hold the turn on the current player. Used for when a human player has no possible move
    // and is being given tiles from the heap until a move is possible. In this scenario, the turn
    // must remain as is and should not progress to the next player.
    
    private static boolean stopThread; // Used to check whether the thread must terminate or not.

    private static Object sharedLock;

    /**
     * Creates and initializes an AllSevenGameThread object.
     * 
     * @param gamemode the amount of players participating in the game. The accepted range is 2-4.
     * @param username a String representing the name of the human player participating in the game.
     * @param gameFrame an AllSevenGameJFrame object representing the Frame of the GUI used for the game.
     * @param sharedLock an object representing the sharedLock used between the GUI thread and the AllSevenGameThread for their synchronization.
     */
    public AllSevenGameThread(int gamemode, String username, AllSevenGameJFrame gameFrame, Object sharedLock) {
        gameInstance = new AllSevenGameLogic(gamemode, username);
        playerList = gameInstance.getPlayerOrderedList();
        this.gameFrame = gameFrame;
        holdPlayerTurn = false;
        
        stopThread = false;
        
        this.sharedLock = sharedLock;
    }
    
    public void setStopFlag(boolean value) {
        stopThread = value;
        System.out.println("DIAG: Mpika setStopFlag kai to neo value stopThread einai: " + stopThread);
    }

    /**
     * Used to get the game instance of the AllSeven game in its current state.
     * @return an AllSevenGameLogic object representing the current state of the game instance of the AllSeven game.
     */
    public AllSevenGameLogic getGameInstance() {
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

        for (int i = 0; i < playerList.size(); i++) {
            nameLabels[i].setText(playerList.get(i).getPlayerName());
        }

    }

    private void updatePlayerTilesLeftLabels() {
        JLabel[] tLeftLabels = gameFrame.getPlayerTilesLeftStatusLabels();

        for (int i = 0; i < playerList.size(); i++) {
            tLeftLabels[i].setText(Integer.toString(playerList.get(i).getPlayerTilesAmount()));
        }
    }

    private void updatePlayerScoreLabels() {
        JLabel[] scoreLabels = gameFrame.getPlayerScoreStatusLabels();

        for (int i = 0; i < playerList.size(); i++) {
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

    private void enableMoveTypeRadioButtons() {
        for (JRadioButton button : gameFrame.getMoveTypeRadioButtons()) {
            button.setEnabled(true);
        }
    }

    private void disableMoveTypeRadioButtons() {
        for (JRadioButton button : gameFrame.getMoveTypeRadioButtons()) {
            button.setEnabled(false);
        }
    }

    private void enableSubmitButton() {
        gameFrame.getSubmitButton().setEnabled(true);
    }

    private void disableSubmitButton() {
        gameFrame.getSubmitButton().setEnabled(false);
    }

    /**
     * The main method that when called, initializes the back-end game engine of the AllSevenGame
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
                    enableMoveTypeRadioButtons();
                    enableSubmitButton();
                    updateButtonChoices();

                    if (gameInstance.possibleMoveExists(gameInstance.getPlayingNowObj()) == true) {
                        // if the human has a possible move

                        holdPlayerTurn = false; // allow the game to progress to the next player normally

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
                        System.out.println("DIAG: stopThread status after PROCEEDING is: " + stopThread);

                    } else {
                        // if the human does not have a possible move, give him a random tile from the heap
                        Tile randomTile;
                        if (gameInstance.getHeap().getAllTiles().size() > 2) {
                            gameFrame.noPossibleMoveAvailableMessage();
                            // if the heap contains more than 2 pieces, one can be given to the user.
                            randomTile = gameInstance.getHeap().pickRandomTile();
                            gameInstance.getPlayingNowObj().addTileToPlayer(randomTile);
                            // the new tile is always added to the LAST position of the player's hand

                            holdPlayerTurn = true; // we block the progress to the next player because the human was automatically 
                            // given a random tile (he had no move), and on the next loop it will be determined
                            // whether he now has a valid move or he must be given a random tile again
                        } else {
                            holdPlayerTurn = false;
                            System.out.printf("> Two tiles are left in the heap. You can not be given another tile.");
                            gameFrame.giveRandomHeapTileErrorMessage();
                        }

                    }

                    updateTableLabel();

                } else {
                    // if the player playing now is a bot, update the playingNowLabel,
                    // reset and disable the radio button choices
                    // and update the TableLabel.
                    System.out.println("DIAG: BOT CHECKPOINT 1");
                    disableMoveTypeRadioButtons();

                    disableSubmitButton();

                    updatePlayingNowLabel();

                    resetButtonChoices(gameFrame.getChoiceRadioButtons());

                    Thread.sleep(500);

                    if (gameInstance.possibleMoveExists(gameInstance.getPlayingNowObj()) == true) {
                        // if the bot has a possible move
                        holdPlayerTurn = false; // allow the game to progress to the next player normally

                        gameInstance.botPlays();

                    } else {
                        // if the bot does not have a possible move, give it a random tile from the heap
                        Tile randomTile;

                        if (gameInstance.getHeap().getAllTiles().size() > 2) {
                            // if the heap contains more than 2 pieces, one can be given to the bot.
                            randomTile = gameInstance.getHeap().pickRandomTile();
                            gameInstance.getPlayingNowObj().addTileToPlayer(randomTile);
                            // the new tile is always added to the LAST position of the bot's hand

                            holdPlayerTurn = true; // we block the progress to the next player because the bot was automatically 
                            // given a random tile (it had no move), and on the next loop it will be determined
                            // whether it now has a valid move or it must be given a random tile again
                        } else {
                            holdPlayerTurn = false;
                            System.out.printf("> BOT: Two or less tiles are left in the heap. You can not be given another tile.");
                        }

                        updateTableLabel();
                    }
                }

                System.out.println("DIAG: Playing before: " + gameInstance.getPlayingNowIndex());
                System.out.println("DIAG: holdPlayerTurn at CHECKPOINT 1 is: " + holdPlayerTurn);
                if (holdPlayerTurn == false) {
                    // if the game should normally progress to the next player
                    if (gameInstance.whoPlaysNext() >= 0) {
                        gameInstance.setPlayingNowPlayer(gameInstance.whoPlaysNext());
                        System.out.println("DIAG: Playing before: " + gameInstance.getPlayingNowIndex());
                    } else if (gameInstance.whoPlaysNext() == -1) {
                        // round ends because a player has played all his tiles
                        System.out.println("^^^^^^round ends because a player has played all his tiles");
                        break;
                    } else if (gameInstance.whoPlaysNext() == -2) {
                        // round ends because no player has a possible move
                        System.out.println("^^^^^^round ends because no player has a possible move");
                        break;
                    }
                }
                // else (holdPlayerTurn == true), if there is an action in progress for the current player, for example
                // if he is being given tiles from the heap because he has no move,
                // continue the loop and check again after the notify signal from the GUI.
                
                // we check if the stopThread flag has been set to true,
                // meaning the thread should exit immediately
                System.out.println("BEFOREEEEEEEEEEEEEEEE EXIT THREAD EXIT THREAD EXIT THREAD!!!!!");
                System.out.println("DIAG: EXIT FLAG STATUS IS: " + stopThread);
                if (stopThread == true) {
                    System.out.println("EXIT THREAD EXIT THREAD EXIT THREAD!!!!!");
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
     * Used to call the executeGame() method and initialize the back-end game engine of the AllSevenGame.
     */
    @Override

    public void run() {
        try {
            executeGame();
        } catch (InterruptedException ex) {
            Logger.getLogger(AllSevenGameThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
