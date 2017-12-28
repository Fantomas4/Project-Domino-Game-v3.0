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
 *
 * @author Sierra Kilo
 */
public class AllSevenGameThread extends Thread{

    private AllSevenGameLogic gameInstance;
    private AllSevenGameJFrame gameFrame;
    ArrayList<Player> playerList;

    private static Object sharedLock;

    public AllSevenGameThread(int gamemode, String username, AllSevenGameJFrame gameFrame, Object sharedLock) {
        gameInstance = new AllSevenGameLogic(gamemode, username);
        playerList = gameInstance.getPlayerOrderedList();
        this.gameFrame = gameFrame;
        this.sharedLock = sharedLock;
    }

    public AllSevenGameLogic getGameInstance() {
        return gameInstance;
    }

    private void updatePlayingNowLabel() {
        // Set the GUI label that shows who plays now:
        System.out.println("DIAG: updateguielements CHECKPOINT 1");
        gameFrame.getPlayingNowLabel().setText(gameInstance.getPlayingNowObj().getPlayerName() + " plays now");
    }

    private void updateRoundCounterLabel() {
        gameFrame.getRoundCounterLabel().setText("Round: " + gameInstance.getRoundCount());
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
            nameLabels[i].setText("Player: " + playerList.get(i).getPlayerName());
        }

    }

    private void updatePlayerTilesLeftLabels() {
        JLabel[] tLeftLabels = gameFrame.getPlayerTilesLeftStatusLabels();

        for (int i = 0; i < playerList.size(); i++) {
            tLeftLabels[i].setText("Tiles left: " + playerList.get(i).getPlayerTilesAmount());
        }
    }

    private void updatePlayerScoreLabels() {
        JLabel[] scoreLabels = gameFrame.getPlayerScoreStatusLabels();

        for (int i = 0; i < playerList.size(); i++) {
            scoreLabels[i].setText("Score: " + playerList.get(i).getScore());
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
    
    public void executeGame() throws InterruptedException {
        
        resetPlayerStatusPanel();
        setPlayerNameLabels();

        do {

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

                // if the player playing now is a human, print his tiles (hand).
                if (gameInstance.getPlayingNowObj() instanceof Human) {
                    System.out.printf("*** Player tiles: ");

                    ArrayList<Tile> playerTiles = gameInstance.getPlayingNowObj().getPlayerTiles();

                    for (Tile piece : playerTiles) {
                        System.out.printf("|%d %d|", piece.getNum1(), piece.getNum2());
                    }
                    System.out.printf(" ***");
                    System.out.printf("%n");
                }

                System.out.println("==================================================================================================================================");

                System.out.printf("%n");
                System.out.printf("*** Table: ");

                ArrayList<Tile> tableTiles = gameInstance.getTable().getTable();

                for (Tile piece : tableTiles) {
                    System.out.printf("|%d %d|", piece.getNum1(), piece.getNum2());
                }

                System.out.printf(" ***");
                System.out.printf("%n%n");

                if (gameInstance.getPlayingNowObj() instanceof Bot) {
                    //the player that plays now is a bot

                    gameInstance.botPlays();

                } else {
                    //the player that plays now is Human

                    System.out.println("0. Take a random tile from the heap.");
                    System.out.println("1. Play using one of your hand's tiles.");
                    System.out.printf("%n");
                    choice = intChoiceValidityCheck(0, 1);

                    if (choice == 0) {
                        // user chose to take a random tile from the heap;
                        System.out.println("DIAG: getAllTiles size: " + gameInstance.getHeap().getAllTiles().size());
                        if (gameInstance.getHeap().getAllTiles().size() > 2) {
                            // if the heap contains more than 2 pieces, one can be given to the user.
                            gameInstance.getPlayingNowObj().addTileToPlayer(gameInstance.getHeap().pickRandomTile());

                        } else {
                            System.out.printf("> Two or less tiles are left in the heap. You can not be given a tile.");
                        }
                    }

                    if (choice == 1) {
                        // if the user chose to play with his own tiles, or if he chose to take a
                        // tile from the heap, but he was unable to be given one (heap contained 2 or less tiles).

                        do {
                            System.out.println("> Choose the tile you want to play with (1-" + gameInstance.getPlayingNowObj().getPlayerTilesAmount() + ").");
                            System.out.printf("%n");
                            choice = intChoiceValidityCheck(1, gameInstance.getPlayingNowObj().getPlayerTilesAmount());

                            // choice number accepted.
                            ArrayList<PossibleMove> result;
                            Tile chosenTile = gameInstance.getPlayingNowObj().chooseTile(choice);
                            result = gameInstance.checkTileChoice(chosenTile);
                            System.out.println("PIRA result me size: " + result.size());

                            if (result.size() == 0) {
                                //there is no possible move with the chosen tile.
                                System.out.printf("%n");
                                System.out.println("> There is no possible move with the chosen tile! Try again!");
                                System.out.printf("%n");
                                //continue
                            } else if (result.size() == 1) {
                                //there is one possible move so tile is placed automatically.
                                gameInstance.humanPlays(choice, chosenTile, result.get(0).needsRotation(), result.get(0).whereToPlace());
                                break;
                            } else if (result.size() == 2) {
                                //there are more 2 possible moves 
                                //so user is asked about where to place tile
                                System.out.printf("%n");
                                System.out.println("> There are 2 possible moves with this tile.");
                                System.out.printf("%n");
                                System.out.println("> Do you want to place the tile left or right?");
                                do {
                                    System.out.printf("%n");
                                    System.out.printf("> Please enter left or right as an answer: ");
                                    answer = input.nextLine();
                                    System.out.printf("%n");

                                    if (answer.equals("left") || answer.equals("right")) {
                                        break;
                                    } else {
                                        System.out.println("> Wrong entry!");
                                    }

                                } while (true);

                                if (result.get(0).whereToPlace().equals(answer)) {
                                    gameInstance.humanPlays(choice, chosenTile, result.get(0).needsRotation(), answer);
                                    break;
                                } else if (result.get(1).whereToPlace().equals(answer)) {
                                    gameInstance.humanPlays(choice, chosenTile, result.get(0).needsRotation(), answer);
                                    break;
                                }
                            } else if (result.size() == 4) {
                                // there are 4 possible moves (cardinal tile).
                                Boolean rotate;

                                System.out.printf("%n");
                                System.out.println("> There are 4 possible moves with this tile.");
                                System.out.printf("%n");
                                System.out.println("> Do you want to rotate the tile (180 degrees) ?");
                                do {
                                    System.out.printf("%n");
                                    System.out.printf("> Please enter yes or no as an answer: ");
                                    answer = input.nextLine();
                                    System.out.printf("%n");

                                    if (answer.equals("yes")) {
                                        rotate = true;
                                        break;
                                    } else if (answer.equals("no")) {
                                        rotate = false;
                                        break;
                                    } else {
                                        System.out.println("> Wrong entry!");
                                    }

                                } while (true);

                                System.out.printf("%n%n");
                                System.out.println("> Do you want to place the tile left or right?");
                                do {
                                    System.out.printf("%n");
                                    System.out.printf("> Please enter left or right as an answer: ");
                                    answer = input.nextLine();
                                    System.out.printf("%n");

                                    if (answer.equals("left") || answer.equals("right")) {
                                        break;
                                    } else {
                                        System.out.println("> Wrong entry!");
                                    }

                                } while (true);

                                gameInstance.humanPlays(choice, chosenTile, rotate, answer);
                                break;
                            }
                        } while (true);
                    }
                }
                System.out.printf("%n");
                System.out.println("==================================================================================================================================");

                if (gameInstance.whoPlaysNext() >= 0) {
                    gameInstance.setPlayingNowPlayer(gameInstance.whoPlaysNext());
                } else {
                    // round ends
                    break;
                }

            } while (true);

            int roundPoints = gameInstance.giveRoundPoints();
            System.out.printf("%n%n%n");
            System.out.println("                                                 *** END OF ROUND! ***");
            System.out.println("                                              *** Round Winner: " + gameInstance.getWinnerPlayerName() + " ***");
            System.out.println("                                                $$$ Points given: " + roundPoints + " $$$");

            gameInstance.resetRound();

        } while (gameInstance.scoreLimitReached() == false);

        System.out.println("%n%n%n");
        System.out.println("                              *** Player " + gameInstance.getWinnerPlayerName() + " has won the game by reaching the score limit! ***");
    }
    
    @Override
    public void run() {
        try {
            executeGame();
        } catch (InterruptedException ex) {
            Logger.getLogger(HungarianGameThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
