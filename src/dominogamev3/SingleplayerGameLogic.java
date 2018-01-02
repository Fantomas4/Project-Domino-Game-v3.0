/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominogamev3;

/**
 * Class containing the common logic and methods used by all singleplayer type games.
 * @author Sierra Kilo
 */
public class SingleplayerGameLogic extends BasicGameLogic {
    
    public SingleplayerGameLogic() {
        super();
    }
    
    
    /**
     * Called in order to execute a specific move for the human user, using
     * a specific tile (chosen by the user) from the game heap.
     * 
     * @param choice a number (1 - 4) representing the row of the heap from which
     * the last tile that is going to be used for the player's move will be taken.
     * @param chosenTile a Tile object representing the tile taken from the heap for the user's move.
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
        heap.removeHeapTile(choice);

    }
    
}
