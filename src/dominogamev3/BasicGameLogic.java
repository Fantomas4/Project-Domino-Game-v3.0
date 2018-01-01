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
public class BasicGameLogic {
    
    protected Heap heap;
    protected Table table;
    
    /**
     * Constructs and initializes a BasicGameLogic object
     * by creating and initializing the Heap and Table objects it uses.
     */
    public BasicGameLogic() {
        heap = new Heap();
        table = new Table();
    }
    
    /**
     * Returns the heap of the game in its current state.
     * @return a Heap object representing the heap of the game in its current state.
     */
    public Heap getHeap() {
        return heap;
    }
    
    /**
     * Used to get the table of the game in its current state.
     * @return a Table object representing the table of the game 
     * in its current state.
     */
    public Table getTable() {
        return table;
    }
    
}
