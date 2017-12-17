/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominogamev3;

;

import java.util.ArrayList;

/**
 * This class holds the logic code and necessary methods used for the Hungarian
 * game.
 *
 * @author Stefanos Karamperas / Athanasios Tsakmakis
 */
public class HungarianGameLogic extends MultiplayerGameLogic {

    /**
     * Constructs and initializes a HungarianGameLogic object.
     *
     * @param mode the amount of players participating in the game.
     */
    public HungarianGameLogic(int mode) {
        
        super();
        
        gamemode = mode;
        scoreLimit = 100;
        playerOrderedList = new ArrayList<>();

        if (gamemode == 2) {
            tilesAmount = 12;
        } else if (gamemode == 3) {
            tilesAmount = 8;
        } else if (gamemode == 4) {
            tilesAmount = 6;
        }

        playerOrderedList.add(new Human("User", tilesAmount, heap));

        for (int i = 1; i < gamemode; i++) {
            //adds as many bots to playerlist as the chosen game mode needs.
            playerOrderedList.add(new Bot("Bot" + i, tilesAmount, heap));
        }

    }

    /**
     * Called at the end of every round to calculate the total of the round
     * points and give it to the appropriate player. Also saves the object of
     * the player who won the round to the winner class field.
     *
     * @return an integer value representing the total round points that have
     * been given to the appropriate player.
     */
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
    
    /**
     * Checks whether there are any possible moves for a specific player by
     * comparing the tiles in his hand with the current state of the game table.
     *
     * @param subject the Player object of the player for who we want to check
     * if any possible moves exist.
     * @return a boolean value that is true if at least one possible move was
     * found for the given player (Player object) or false if no possible moves
     * where found.
     */
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
    
    /**
     * Checks for the possible moves with a specific tile based on the current
     * state of the table. Any possible move found is saved in a PossibleMove
     * object that describes its details. All the PossibleMove objects created
     * (each describing a possible move) are then added to an ArrayList that is
     * returned. If no possible moves are found, an empty ArrayList is returned.
     *
     * @param piece the Tile object for which we want to check what possible
     * moves exist based on the current state of the game table.
     * @return an ArrayList containing PossibleMove objects, each describing the
     * details of a possible move. If no possible move exists for the given
     * tile, an empty ArrayList is returned.
     */
    public ArrayList<PossibleMove> checkTileChoice(Tile piece) {

        ArrayList<PossibleMove> result = new ArrayList<>();

        if (table.getSize() == 0) {
            //table is empty and we are placing the first tile.
            result.add(new PossibleMove(false, "left")); // no rotation needed, tile can be placed on both sides
        } else {

            if (piece.getNum1() == table.getFirstTile().getNum1()) {
                result.add(new PossibleMove(true, "left"));

            } else if (piece.getNum2() == table.getFirstTile().getNum1()) {
                result.add(new PossibleMove(false, "left"));

            }

            if (piece.getNum1() == table.getLastTile().getNum2()) {
                result.add(new PossibleMove(false, "right"));
            } else if (piece.getNum2() == table.getLastTile().getNum2()) {
                result.add(new PossibleMove(true, "right"));
            }
        }
        return result;
    }

    /**
     * Called immediately after a player's move to check whose turn is to play.
     *
     * @return an integer representing the index of the Player object in the
     * playerOrderedList of the player whose turn is to play now.
     */
    public int whoPlaysNext() {

        // returns index of playerOrderedList for the player that is to play next or -1 if no player has a possible move.
        int resultIndex = -1;

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

    /**
     * Called to find and execute a move for the bot player playing now.
     */
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
}
