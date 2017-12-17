/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominogamev3;;

import java.util.ArrayList;

/**
 *
 * @author Sierra Kilo
 */
public class AllSevenGameLogic extends MultiplayerGameLogic {

    public AllSevenGameLogic(int mode) {
        
        super();
        
        gamemode = mode;
        scoreLimit = 100;
        playerOrderedList = new ArrayList<>();

        if (gamemode == 2) {
            tilesAmount = 7;
        } else if (gamemode == 3) {
            tilesAmount = 5;
        } else if (gamemode == 4) {
            tilesAmount = 5;
        }

        playerOrderedList.add(new Human("User", tilesAmount, heap));

        for (int i = 1; i < gamemode; i++) {
            //adds as many bots to playerlist as the chosen game mode needs.
            playerOrderedList.add(new Bot("Bot" + i, tilesAmount, heap));
        }
    }
    
    public int giveRoundPoints() {

        //increases player points in player object and returns total points added.
        int totalPoints = 0;
        int minPoints = playerOrderedList.get(0).getRemainingTilePoints();
        int minPlayerIndex = 0;
        boolean tie = true; // used to check if all the players have the same 
        // amount of points in their hand. Initial value is set to true.

        // checks if there is a tie of points between all players.
        for (int i = 1; i < playerOrderedList.size(); i++) {
            if (playerOrderedList.get(i - 1).getRemainingTilePoints() != playerOrderedList.get(i).getRemainingTilePoints()) {
                tie = false;
                break;
            }
        }

        // calculates the total sum of points of the tiles still in every player's hand.
        for (int i = 0; i < playerOrderedList.size(); i++) {

            totalPoints += playerOrderedList.get(i).getRemainingTilePoints();

            if (playerOrderedList.get(i).getRemainingTilePoints() < minPoints) {
                minPoints = playerOrderedList.get(i).getRemainingTilePoints();
                minPlayerIndex = i;
            }

        }

        if (tie == false) {
            totalPoints -= playerOrderedList.get(minPlayerIndex).getRemainingTilePoints(); //removes the points of the player that has the fewest points
            //in his hand and is about to receive the totalPoints sum.
            playerOrderedList.get(minPlayerIndex).increaseScore(totalPoints); // gives accumulated points to the
            // player who has the fewest points. 
            winnerName = playerOrderedList.get(minPlayerIndex).getPlayerName(); // saves the object of the player who won the round to the winner object field.

        } else {
            // tie == true means that there is a point tie between all players.
            // in this case, no points are given to any player.
            totalPoints = 0;

            winnerName = "There is a tie, no winner occurred.";
        }

        return totalPoints;

    }
    
    public boolean possibleMoveExists(Player subject) {
        ArrayList<PossibleMove> result;
        ArrayList<Tile> playerTiles;

        playerTiles = subject.getPlayerTiles();

        for (Tile piece : playerTiles) {
            result = checkTileChoice(piece);
            if (result.size() > 0) {
                //there is at least one possible move 
                return true;
            }
        }
        return false;
    }

    public ArrayList<PossibleMove> checkTileChoice(Tile piece) {

        ArrayList<PossibleMove> result = new ArrayList<>();

        if (table.getSize() == 0) {
            // table is empty and we are placing the first tile.
            result.add(new PossibleMove(false, "left")); // no rotation needed, tile can be placed on both sides
        } else {
            // table is not empty.

            if (piece.getNum1() + piece.getNum2() == 7 || (piece.getNum1() == 0 && piece.getNum2() == 0)) {
                // if the picked tile is a cardinal tile (num1 + num2 == 7 or num1 ==0 and num2 == 0 ).
                System.out.printf("DIAG: EKANA 4 POSSIBLE MOVES");
                result.add(new PossibleMove(false, "left"));
                result.add(new PossibleMove(false, "right"));
                result.add(new PossibleMove(true, "left"));
                result.add(new PossibleMove(true, "right"));

            } else {
                // if the picked tile is NOT a cardinal tile.
                if (piece.getNum1() + table.getFirstTile().getNum1() == 7) {
                    result.add(new PossibleMove(true, "left"));

                } else if (piece.getNum2() + table.getFirstTile().getNum1() == 7) {
                    result.add(new PossibleMove(false, "left"));

                }

                if (piece.getNum1() + table.getLastTile().getNum2() == 7) {
                    result.add(new PossibleMove(false, "right"));
                } else if (piece.getNum2() + table.getLastTile().getNum2() == 7) {
                    result.add(new PossibleMove(true, "right"));
                }
            }
        }
        return result;
    }

    public int whoPlaysNext() {

        // returns the index of playerOrderedList for the player that is to play next 
        // or -1 if the round must end (when no player has a possible move or a player has played all his tiles).
        
        int resultIndex = -1;
        
        for (Player player : playerOrderedList) {
            if (player.getPlayerTiles().size() == 0) {
                return -1; // a player has played all his tile, and the round must end.
            }
        }

        if (possibleMoveExists(playerOrderedList.get(playingNowIndex)) == true) {
            //first we check if the player who plays now has a possible move.
            //System.out.println("===================Mpika 1");
            resultIndex = playingNowIndex;
        } else {
            //System.out.println("===================Mpika 2");
            //System.out.println("===================DIAG: before playingNowIndex: " + playingNowIndex);
            int indexLimit = playerOrderedList.size() - 1; // last index number
            int pos = playingNowIndex + 1; // we begin from the immediate next index from the index of the player playing now.
            //then we search in the order of the playerOrderedList for the first player that has a possible move.
            //System.out.println("===================DIAG: after playingNowIndex: " + playingNowIndex);
            int loops = 0; // counter - number of times the do-while loop below has been executed

            do {
                //System.out.println("===================POS IS: " + pos);
                if (pos > indexLimit) {
                    //System.out.println("===================Mpika 3");
                    // if we reach the last true index number, we reset the pos to 0 index.
                    pos = 0;
                }

                if (possibleMoveExists(playerOrderedList.get(pos)) == true) {
                    //System.out.println("===================Mpika 4");
                    resultIndex = pos;
                    break;
                } else {
                    //System.out.println("===================Mpika 5");
                    pos++;
                }

                loops++;

            } while (loops < playerOrderedList.size() - 1); // we check playerOrderedList.size() - 1 times since we have already 
            //checked whether the playingNowIndex player had a move using the initial if statement at the beginning of the function.               
        }

        //System.out.println("DIAG: the resultIndex return by function is: " + resultIndex);
        return resultIndex;

    }

    public void botPlays() {
        ArrayList<Tile> botTiles = playingNowObj.getPlayerTiles();
        ArrayList<PossibleMove> botResult;
        Tile botChosenTile;

        for (int i = 0; i < botTiles.size(); i++) {

            botChosenTile = botTiles.get(i);
            botResult = checkTileChoice(botChosenTile);
            if (botResult.size() > 0) {

                // if at least one move exists for the selected bot tile.
                if (botResult.get(0).needsRotation() == true) {
                    botChosenTile.rotateTile();
                }
                table.addTile(botChosenTile, botResult.get(0).whereToPlace());
                playingNowObj.removePlayerTile(i + 1); //removes tile from player's hand.
                break;
            }
        }
    }

    //NOTE: All methods except: checkTileChoice(), whoPlaysNext() are common for all game types.
}
