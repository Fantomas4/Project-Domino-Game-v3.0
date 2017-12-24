/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominogamev3;;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * This class contains the necessary logic code and methods that are used
 * to create and manage Heap objects. A Heap object represents the game heap
 * from which the player either chooses a tile to play with (Solo1 game) or is 
 * given an amount of random tiles to fill his game hand (Hungarian game).
 * 
 * @author  Stefanos Karamperas / Athanasios Tsakmakis
 */
public class Heap {

    private ArrayList<Tile> allTiles; //contains all the 28 pieces of the domino.
    private ArrayList<ArrayList<Tile>> heapTiles; //4x7 table of all the heap tiles.
    private int pos = 0;

    /**
     * Constructs and initializes a new heap object.
     */
    public Heap() {
        
        setAllTiles(); // fills the "allTiles" list with the 28 unique domino tiles of the game.

        heapTiles = new ArrayList<>();

        //Creating 4x7 heap Table.
        for (int i = 0; i < 4; i++) {
            ArrayList<Tile> row = new ArrayList<>();
            for (int j = 0; j < 7; j++) {
                row.add(allTiles.get(pos));
                pos++;
            }
            heapTiles.add(row);
        }
    }

    /**
     * Used to get the tiles that the heap contains in its current state.
     * @return an ArrayList containing 4 ArrayLists (rows) each of size == 7 that contain Tile objects.
     */
    public ArrayList<ArrayList<Tile>> getHeap() {
        return heapTiles;
    }

    /**
     * Removes the last tile of a specific row (1-4).
     * @param choice the row number (1-4) from which the last tile will be removed.
     */
    public void removeHeapTile(int choice) {
        ArrayList<Tile> row;
        System.out.println("DIAG: CHOICE IN PROBLEM IS: " + choice);
        row = heapTiles.get(choice - 1);
        row.remove(row.size() - 1);
        
//        // we check if the row from which we removed the last tile
//        // has now become empty. In case it is now empty, we delete the row
//        // from the row list ArrayList.
//        if (row.isEmpty()) {
//            heapTiles.remove(choice - 1);
//        }

    }

    /**
     * Returns the last tile of a specific row (1-4).
     * @param choice the number of the row (1-4) from which the last tile will be returned.
     * @return a Tile object representing the last tile of the chosen row, or null if the
     * chosen row is empty.
     */
    public Tile chooseTile(int choice) {
        //choice: 1-4
        ArrayList<Tile> row;
        row = heapTiles.get(choice - 1);
        
        if (row.size() > 0) {
            // the row is not empty so we return its last Tile
            return row.get(row.size() - 1);
        } else {
            // the row is empty, so null is returned
            return null;
        }
        

    }

    /**
     * Picks a random tile from the collection of the 28 unique domino tiles, returns
     * it and removes it from the collection.
     * @return a Tile object representing the random tile picked from the collection.
     */
    public Tile pickRandomTile() {
        Random r1 = new Random();
        int tileChoice = r1.nextInt(allTiles.size());
        Tile pickedTile = allTiles.get(tileChoice);
        allTiles.remove(tileChoice);

        return pickedTile;

    }

    /**
     * Creates the allTiles collection that contains the 28 unique domino tiles.
     */
    public void setAllTiles() {
        allTiles = new ArrayList<>();
        for (int i = 0; i <= 6; i++) {
            //fills up the AllTiles ArrayList with all the existing domino pieces.
            for (int j = i; j <= 6; j++) {
                Tile Piece = new Tile(i, j);
                allTiles.add(Piece);
            }
        }
        Collections.shuffle(allTiles); //shuffles the AllTiles list (randomize).

    }
    
    /**
     * Called to get the allTiles collection at its current state.
     * @return an ArrayList containing Tiles, representing the current state of the allTiles list.
     */
    public ArrayList<Tile> getAllTiles() {
        return allTiles;
    }

}
