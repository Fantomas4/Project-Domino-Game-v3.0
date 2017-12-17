/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominogamev3;;

import java.util.ArrayList;

/**
 * This class contains the necessary logic code and methods that are used
 * to create and manage Player objects, each representing one of the players 
 * participating in a game session.
 * 
 * @author  Stefanos Karamperas / Athanasios Tsakmakis
 */
public class Player {

    protected ArrayList<Tile> playerTiles;
    protected int score;
    protected String name;
    protected Heap heap;

    /**
     * Creates and initializes a player object.
     */
    public Player() {
        score = 0;
    }

    /**
     * Used to get the player's total score.
     * @return an integer representing the total score of the player.
     */
    public int getScore() {
        return score;
    }

    /**
     * Used to get the player's name.
     * @return a String representing the player's name.
     */
    public String getPlayerName() {
        return name;
    }

    /**
     * Used to get all the tiles the player currently holds in his hand.
     * @return an ArrayList containing the Tile objects of the player's tiles.
     */
    public ArrayList<Tile> getPlayerTiles() {
        return playerTiles;
    }

    /**
     * Used to get the sum of the points from the tiles the player currently has in his hand.
     * @return an integer representing the total amount of points from the tiles in the player's hand.
     */
    public int getRemainingTilePoints() {
        int sum = 0;

        for (Tile piece : playerTiles) {
            sum += piece.getNum1() + piece.getNum2();
        }
        return sum;
    }

    /**
     * Used to fill the player's hand with the necessary amount of tiles.
     * @param tilesAmount the amount of tiles the player's hand should receive.
     */
    public void givePlayerTiles(int tilesAmount) {
        playerTiles = new ArrayList<>();

        for (int i = 1; i <= tilesAmount; i++) {
            playerTiles.add(heap.pickRandomTile());
        }

    }

    /**
     * Used to increase the player's score by a given amount of points.
     * @param points the amount of points that should be added to the current player's score.
     */
    public void increaseScore(int points) {
        score += points;

    }

    /**
     * Used to get the highest double tile (number 1 == number 2) currently in the player's hand.
     * @return a Tile object representing the highest double tile in the player's hand.
     */
    public Tile getHighestTile() {
        // (-1,-1) is a tile representing that the player has no tiles with num1 == num2
        Tile maxTile = new Tile(-1, -1);

        for (Tile piece : playerTiles) {
            if (piece.getNum1() == piece.getNum2()) {
                if (piece.getNum1() > maxTile.getNum1()) {
                    maxTile = piece;
                }
            }

        }
        return maxTile;
    }

    /**
     * Used to get the amount of tiles currently in the player's hand.
     * @return an integer representing the amount of tiles in the player's hand.
     */
    public int getPlayerTilesAmount() {
        return playerTiles.size();
    }

    /**
     * Used to get a specific tile from the player's hand.
     * @param choice the index (1 - amount of tiles in the player's hand) of the tile we want to get.
     * @return a Tile object representing the tile we want to get.
     */
    public Tile chooseTile(int choice) {

        return playerTiles.get(choice - 1);
    }

    /**
     * Used to remove a specific tile from the player's hand.
     * @param choice the index (1 - amount of tiles in the player's hand) of the tile we want to remove.
     */
    public void removePlayerTile(int choice) {
        // choice index 1 - amount of player tiles
        playerTiles.remove(choice - 1);
    }

    //////////////////////////////////////////////////////////////////////////////////
    //xrisi mono apo to allsevengamelogic
    
    public void addTileToPlayer(Tile tile) {
        
        playerTiles.add(tile);
        
    }

}
