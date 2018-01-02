/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominogamev3;

import java.util.ArrayList;

/**
 *
 * @author Sierra Kilo
 */
public class MultiplayerGameLogic extends BasicGameLogic {

    /**
     * Integer the value of which determines the current game mode. The meaning
     * of its values is: 2: two players (one human, one bot), 3: 3 players (one
     * human, two bots) etc.
     */
    protected int gamemode;

    /**
     * ArrayList containing Player objects that represent each one of the
     * players participating in the game. The list is ordered to reflect the
     * order in which the players should take turns. The first position of the
     * list contains the player that is to play now.
     */
    protected ArrayList<Player> playerOrderedList;

    /**
     * holds the Player object of the player who currently plays.
     */
    protected Player playingNowObj;

    /**
     * Integer that holds the index of the Player object in the
     * playerOrderedList for the player who currently plays.
     */
    protected int playingNowIndex;

    /**
     * String that holds the name of the winner of the round or the final winner
     * of the game, set by giveRoundPoints or scoreLimitReached() function
     * accordingly.
     */
    protected String winnerName;

    /**
     * Integer representing how many tiles each player is supposed to be given.
     * Depends on type of game (2,3 or 4 players). value is initialized for
     * gamemode == 2 (2 players).
     */
    protected int tilesAmount;

    /**
     * Integer representing the amount of points that when reached by a player,
     * leads to the end of the game.
     */
    protected int scoreLimit;

    /**
     * Integer used to count the number of rounds for a multiplayer game
     * instance.
     */
    protected int roundCount;

    /**
     * Constructs and initializes the MultiplayerGameLogic class object.
     */
    public MultiplayerGameLogic() {
        super();
        roundCount = 1;
    }

    /**
     * Used to find which player should play first at the beginning of a new
     * round.
     *
     * @return an integer representing the index of the player object in the
     * playerOrderedList for the player that is to play first.
     */
    public int firstPlayerIndex() {

        Player firstPlayer = playerOrderedList.get(0); //gets the first player ( the Human) for the 
        //ordered player list and initializes the firstPlayer with him.
        int index = 0; // position of the player playing first in the playerOrderedList

        for (int i = 1; i < playerOrderedList.size(); i++) {
            if (playerOrderedList.get(i).getHighestTile().getNum1() > firstPlayer.getHighestTile().getNum1()) {
                firstPlayer = playerOrderedList.get(i);
                index = i;
            }
        }
        return index;

    }

    /**
     * Called in order to execute a specific move for the human player playing
     * now, using a specific tile in the human player's hand.
     *
     * @param choice a number (1 - amount of tiles in player's hand)
     * representing the tile in the player's hand that will be used for the
     * move.
     * @param chosenTile a Tile object representing the tile that will be used
     * for the move.
     * @param needsRotation a boolean value that is true is the tile used for
     * the move needs to be rotated 180 degrees before placing it on the table,
     * or false if the tile does not need to be rotated for the move.
     * @param whereToPlace a String that is either "left" or "right", describing
     * the side of the table on which the tile must be placed for the move.
     */
    public void humanPlays(int choice, Tile chosenTile, boolean needsRotation, String whereToPlace) {
        if (needsRotation == true) {
            chosenTile.rotateTile();
        }
        table.addTile(chosenTile, whereToPlace);
        playingNowObj.removePlayerTile(choice); //removes tile from player's hand.

    }

    /**
     * Used to check whether the score limit to end the game has been reached by
     * a player.
     *
     * @return a boolean value that is true if the score limit has been reached
     * or false if the score limit has not been reached yet.
     */
    public boolean scoreLimitReached() {
        for (Player obj : playerOrderedList) {
            if (obj.getScore() >= scoreLimit) {
                winnerName = obj.getPlayerName();
                return true;
            }
        }
        return false;

    }

    /**
     * Called to reset the table, heap and player's hand to their initial state
     * before the beginning of a new round.
     */
    public void resetRound() {

        //clear game table for next round.
        table.clearTable();
        //reset 28 unique tiles heap collection (from where players are given their tiles).
        heap.setAllTiles();
        //give new tiles to players for next round.
        for (Player player : playerOrderedList) {
            player.givePlayerTiles(tilesAmount);
        }
    }

    /**
     * Receives an index and sets the player at this index of the
     * playerOrderedList as the player playing now.
     *
     * @param index the index of the Player object in the playerOrderedList we
     * want to set as the player playing now.
     */
    public void setPlayingNowPlayer(int index) {
        playingNowIndex = index;
        playingNowObj = playerOrderedList.get(playingNowIndex);
    }

    /**
     * Called to get the playerOrderedList containing all the players' objects.
     *
     * @return an ArrayList containing the Player objects of all the players
     * participating in the game.
     */
    public ArrayList<Player> getPlayerOrderedList() {
        return playerOrderedList;
    }

    /**
     * Called to get the index representing the position of the playing now
     * player's Player object in the playerOrderedList.
     *
     * @return an integer representing the index number of the playing now
     * player's Player object in the playerOrderedList.
     */
    public int getPlayingNowIndex() {
        return playingNowIndex;
    }

    /**
     * Called to get the Player object of the player currently playing.
     *
     * @return a Player object representing the Player object of the player
     * currently playing.
     */
    public Player getPlayingNowObj() {
        return playingNowObj;
    }

    /**
     * Called to get the name of the player who won the round or the game
     * (called by giveRoundPoints() or scoreLimitReached() accordingly).
     *
     * @return a String representing the name of the player who won the round or
     * the game.
     */
    public String getWinnerPlayerName() {
        return winnerName;
    }

    /**
     * Called to increase the roundCount counter variable by 1.
     */
    public void incRoundCount() {
        roundCount++;
    }

    /**
     * Called to get the current value of the roundCount counter variable,
     * representing the current round of the game.
     *
     * @return an integer representing the current value of the roundCount
     * variable.
     */
    public int getRoundCount() {
        return roundCount;
    }
}
