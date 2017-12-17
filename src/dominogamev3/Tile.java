/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominogamev3;;

/**
 * This class contains the necessary logic code and methods that are used
 * to create and manage Tile objects, each representing one of the 28 unique tiles 
 * used in the game.
 * 
 * @author  Stefanos Karamperas / Athanasios Tsakmakis
 */
public class Tile {
    
    private int num1;
    private int num2;
    
    /**
     * Constructs and initializes a Tile.
     * @param num1 the first number on the tile.
     * @param num2 the second number on the tile.
     */
    public Tile(int num1, int num2){
        this.num1 = num1;
        this.num2 = num2;
    
    }
    
    /**
     * Returns the first number written on the Tile.
     * @return integer representing the first number of the tile.
     */
    public int getNum1() {
        return num1;
        
    }
    
    /**
     * Returns the second number written on the Tile
     * @return integer representing the second number of the tile.
     */
    public int getNum2() {
        return num2;
    }
    
    /**
     * Rotates the present tile 180 degrees.
     */
    public void rotateTile() {
        int temp = num1;
        num1 = num2;
        num2 = temp;
    }
    
    
}

