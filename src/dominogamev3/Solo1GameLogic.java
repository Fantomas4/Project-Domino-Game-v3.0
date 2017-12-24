/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominogamev3;;

import java.util.ArrayList;

/**
 * This class holds the logic code and necessary methods used for the Solo1 game.
 * 
 * @author  Stefanos Karamperas / Athanasios Tsakmakis
 */
public class Solo1GameLogic extends SingleplayerGameLogic {

    /**
     * Constructs and initializes a Solo1GameLogic object
     * by calling the constructor of the parent BasicGameLogic Class.
     */
    public Solo1GameLogic() {
        super();
    }


    /**
     * Used to check whether any possible move exists for the player, by comparing
     * the 4 tile options the player is currently given with the tiles on the table.
     * @return a boolean value that is true if at least one possible move exists, or false
     * if there is no possible move.
     */
    public boolean possibleMoveExists() {
        ArrayList<PossibleMove> result = new ArrayList<>();
        Tile testTile;
        
        for (int i = 1; i < 5; i++) {
            testTile = heap.chooseTile(i);
            
            if (testTile != null) {
                // testTile != null means a tile was returned from the heap
                result = checkTileChoice(testTile); //choose tile accepts int in range 1-4.
            }
            

            if (result.size() > 0) { //if a list containing at least one possible move is returned
                return true;
            }
        }
        return false;
    }

    /**
     * Called to check whether there are any possible moves with a specific tile taking
     * into consideration the current state of the table. The details of any possible move
     * found are saved using PossibleMove objects (each possible move found is described in a separate
     * PossibleMove object). All the PossibleMove objects created for the possible moves found for the
     * specific tile are saved in an ArrayList and returned. If no possible moves were found, an empty 
     * ArrayList is returned.
     * @param piece the specific tile for which we want to check whether any possible moves exist, and
     * if they do exist, save their details.
     * @return an ArrayList containing PossibleMove objects, each describing the details of a possible move
     * found. If no possible moves are found, an empty ArrayList will be returned.
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
     * Used to check whether the current state of the game results in a gameover, a win, or leads to the game 
     * still being in progress.
     * @return an integer value that is 0 if the current state is found to result in a gameover,
     * 2 if the current state is found to result in a win for the player, or 0 if the current state of
     * the game indicates it still is in progress.
     */
    public int gameStatus() {
        //0 == gameover, 1 = in progress, 2 = win
        
        int result = 0;
        if (table.getSize() == 28) {
            result = 2;
        } else if (table.getSize() < 28 && possibleMoveExists() == true) {
            result = 1;
        } else if (table.getSize() < 28 && possibleMoveExists() == false) {
            result = 0;
        }
        return result;
    }

}
