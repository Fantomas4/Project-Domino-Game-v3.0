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
public class Bot extends Player {

    /**
     * This class contains the necessary logic code and methods that are used to
     * create and manage Bot Player objects, each representing one of the Bot players
     * participating in a game session.
     *
     * @param name the name of the bot player
     * @param tilesAmount the amount of tiles to be given to the bot at the start of the game.
     * @param heap the object of the heap the game uses.
     */
    public Bot(String name, int tilesAmount, Heap heap) {

        super();

        this.name = name;
        this.heap = heap;

        givePlayerTiles(tilesAmount); //gives player the needed amount of tiles
        //for the game (tilesAmount).
    }

}
