/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominogamev3;

/**
 *
 * @author Sierra Kilo
 */
public class Human extends Player {
    
    public Human(String name, int tilesAmount, Heap heap) {
        
        super();

        this.name = name;
        this.heap = heap;

        givePlayerTiles(tilesAmount); //gives player the needed amount of tiles
                                      //for the game (tilesAmount).
    }
    
}
