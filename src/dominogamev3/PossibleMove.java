/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominogamev3;;

/**
 * This class is used by the checkTileChoice() methods. Its objects are used to store
 * the details of a possible move found for a given tile by the checkTileChoice() methods.
 * These details describe: 
 * 
 * 1) Whether the given tile needs to be rotated before placed on the
 * game table 
 * 
 * 2) On which side of the game table (left - front or right - end) the given tile
 * is supposed to be placed for the possible move found.
 * 
 * @author  Stefanos Karamperas / Athanasios Tsakmakis
 */
public class PossibleMove {
    
    private boolean rotation;   // 0: Tile needs to be rotated before placing it 
                                // 1: Tile does not need to be rotated before placing it
    
    private String side; //left or right
    
    /**
     * Creates and initializes a PossibleMove object.
     * PossibleMove objects are used to save the details
     * of possible moves that are found for a specific tile.
     * @param rotation states whether the tile needs to be rotated for the given possible move.
     * @param side states on which side of the table (left - front or right - end) the tile must
     * be placed for the given possible move.
     */
    public PossibleMove(boolean rotation, String side) {
        this.rotation = rotation;
        this.side = side;
    }
    
    /**
     * Used to check whether the tile needs to be rotated for the given possible move.
     * @return a boolean value that is true if the tile must be rotated, or false if it does not.
     */
    public boolean needsRotation() {
        return rotation;
    }
    
    /**
     * Used to check on which side of the table (left - front or right - end) the tile must be placed.
     * @return a String that is either "left", representing the left table side,
     * or "right", representing the right table side.
     */
    public String whereToPlace() {
        return side;
    }
    
}

