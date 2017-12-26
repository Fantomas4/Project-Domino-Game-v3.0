/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominogamev3;

import static dominogamev3.DominoGameMain.intChoiceValidityCheck;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JRadioButton;

/**
 *
 * @author Sierra Kilo
 */
public class HungarianGameThread extends Thread {

    private HungarianGameLogic gameInstance;
    private HungarianGameJFrame gameFrame;

    public HungarianGameThread(int gamemode, HungarianGameJFrame gameFrame) {
        gameInstance = new HungarianGameLogic(gamemode);
        this.gameFrame = gameFrame;
    }

    private void updateGUI() {

        // Set the GUI label that shows who plays now:
        System.out.println("DIAG: updateguielements CHECKPOINT 1");
        gameFrame.getPlayingNowLabel().setText("Player " + gameInstance.getPlayingNowObj().getPlayerName() + " plays now");


        // Get the table of the game and show in on the GUI:
        Table tableObj = gameThread.getGameInstance().getTable();
        ArrayList<Tile> tableTiles = tableObj.getTable();
        String tableText = "";

        for (Tile piece : tableTiles) {
            tableText += "|" + piece.getNum1() + " " + piece.getNum2() + "| ";
        }

        jTableLabel.setText(tableText);

        // Get the hand of the player and show it on the GUI using the JRadioButtons as choices:
        // reset all the radio buttons by emptying their text and disabling them.
        for (JRadioButton button : choiceRadioButtons) {
            button.setText("");
            button.setEnabled(false);
        }

        int pos = 0; // position indicator used to traverse the choiceRadioButtons array
        ArrayList<Tile> playerHand = gameThread.getGameInstance().getPlayingNowObj().getPlayerTiles();

        for (int i = 0; i < playerHand.size(); i++) {
            choiceRadioButtons[pos].setText("|" + playerHand.get(i).getNum1() + " " + playerHand.get(i).getNum2() + "|");
            choiceRadioButtons[pos].setEnabled(true);
            pos++;
        }

        // for diagnostic purposes, print the hand to console
    }

    @Override
    public synchronized void run() {
        do {
            System.out.println("DIAG: THREAD CHECKPOINT 1");
            gameInstance.setPlayingNowPlayer(gameInstance.firstPlayerIndex());

            do {
                System.out.printf("%n%n%n%n%n%n%n%n%n%n%n");
                System.out.println("==================================================================================================================================");

//                //show all players' hand.
//                System.out.println("!!!!!!!!!!!!! DIAG !!!!!!!!!!!!!!!!!!!!");
//                for (Player player : gameInstance.getPlayerOrderedList()) {
//
//                    //prints each player's hand at the beginning of each move.
//                    System.out.printf(player.getPlayerName() + " player's hand: ");
//
//                    ArrayList<Tile> playerTiles = player.getPlayerTiles();
//
//                    for (Tile piece : playerTiles) {
//                        System.out.printf("|%d %d|", piece.getNum1(), piece.getNum2());
//                    }
//
//                    System.out.printf("%n");
//                }
//                System.out.println("!!!!!!!!!!!!! DIAG !!!!!!!!!!!!!!!!!!!!");
                //System.out.printf("%n");
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

                    // suspend the thread until a notify() call from the GUI part of the program is executed
                    // to indicate that the user has finished his move.
                    try {
                        wait();
                    } catch (InterruptedException e) {

                    }

//                    do {
//                        System.out.println("> Choose the tile you want to play with (1-" + gameInstance.getPlayingNowObj().getPlayerTilesAmount() + ").");
//                        System.out.printf("%n");
//                        choice = intChoiceValidityCheck(1, gameInstance.getPlayingNowObj().getPlayerTilesAmount());
//
//                        // choice number accepted.
//                        ArrayList<PossibleMove> result;
//                        Tile chosenTile = gameInstance.getPlayingNowObj().chooseTile(choice);
//                        result = gameInstance.checkTileChoice(chosenTile);
//
//                        if (result.size() == 0) {
//                            //there is no possible move with the chosen tile.
//                            System.out.printf("%n");
//                            System.out.println("> There is no possible move with the chosen tile! Try again!");
//                            System.out.printf("%n");
//                            //continue
//                        } else if (result.size() == 1) {
//                            //there is one possible move so tile is placed automatically.
//                            gameInstance.humanPlays(choice, chosenTile, result.get(0).needsRotation(), result.get(0).whereToPlace());
//                            break;
//                        } else {
//                            //there are more 2 possible moves 
//                            //so user is asked about where to place tile
//                            System.out.printf("%n");
//                            System.out.println("> There are 2 possible moves with this tile.");
//                            System.out.printf("%n");
//                            System.out.println("> Do you want to place the tile left or right?");
//                            do {
//                                System.out.printf("%n");
//                                System.out.printf("> Please enter left or right as an answer: ");
//                                answer = input.nextLine();
//                                System.out.printf("%n");
//
//                                if (answer.equals("left") || answer.equals("right")) {
//                                    break;
//                                } else {
//                                    System.out.println("> Wrong entry!");
//                                }
//
//                            } while (true);
//
//                            if (result.get(0).whereToPlace().equals(answer)) {
//                                gameInstance.humanPlays(choice, chosenTile, result.get(0).needsRotation(), answer);
//                                break;
//                            } else if (result.get(1).whereToPlace().equals(answer)) {
//                                gameInstance.humanPlays(choice, chosenTile, result.get(0).needsRotation(), answer);
//                                break;
//                            }
//                        }
//
//                    } while (true);
                }
                System.out.printf("%n");
                System.out.println("==================================================================================================================================");

                if (gameInstance.whoPlaysNext() >= 0) {
                    gameInstance.setPlayingNowPlayer(gameInstance.whoPlaysNext());
                } else {
                    // round ends
                    break;
                }

                notifyAll();

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

}
