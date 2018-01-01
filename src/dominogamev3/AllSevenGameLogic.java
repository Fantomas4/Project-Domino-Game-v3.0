/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominogamev3;

;

import java.util.ArrayList;

/**
 * This class holds the logic code and necessary methods used for the AllSeven
 * game.
 *
 * @author Sierra Kilo
 */


public class AllSevenGameLogic extends MultiplayerGameLogic {

    /**
     * Constructs and initializes an AllSevenGameLogic object. The accepted
     * range of the variable is 2-4.
     *
     * @param mode the amount of players participating in the game.
     * @param username a String representing the name of the human player of the
     * game.
     */
    public AllSevenGameLogic(int mode, String username) {

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

        playerOrderedList.add(new Human(username, tilesAmount, heap));

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
        boolean tie = false; // used to check if at least two of the participating players have the same 
        // amount of points in their hand. In this case, the round is considered a TIE and no points are awarded.

        // checks if there is a tie
        for (int i = 0; i < playerOrderedList.size(); i++) {
            for (int j = i + 1; j < playerOrderedList.size(); j++) {
                if (playerOrderedList.get(i).getRemainingTilePoints() == playerOrderedList.get(j).getRemainingTilePoints()) {
                    tie = true;
                    break;
                }
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

    /**
     * Called immediately after a player's move to check whose turn is to play.
     *
     * @return an integer representing the index of the Player object in the
     * playerOrderedList of the player whose turn is to play now.
     */
    public int whoPlaysNext() {

        int resultIndex;
        boolean playersHavePossibleMove = false;

        // returns the index of playerOrderedList for the player that is to play next 
        // or a negative number if the round must end (-1 a player has played all his tiles or -2 when no player has a possible move).
        for (Player player : playerOrderedList) {
            if (player.getPlayerTiles().size() == 0) {
                return -1; // a player has played all his tile, and the round must end.
            }

            if (possibleMoveExists(player) == true) {
                playersHavePossibleMove = true;
            }
        }

        if (playersHavePossibleMove == false && heap.getAllTiles().size() == 2) {
            // if no player was found to have a possible move and the heap has reached its minimum
            // size, the round must end
            return -2;
        }

        int indexLimit = playerOrderedList.size() - 1; // last index number
        resultIndex = playingNowIndex + 1; // we begin from the immediate next index from the index of the player playing now.

        if (resultIndex > indexLimit) {
            //System.out.println("===================Mpika 3");
            // if we reach the last true index number, we reset the pos to 0 index.
            resultIndex = 0;
        }

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
