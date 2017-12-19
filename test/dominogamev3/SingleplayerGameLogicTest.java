/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominogamev3;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author pbach
 */
public class SingleplayerGameLogicTest {
    
    public SingleplayerGameLogicTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of humanPlays method, of class SingleplayerGameLogic.
     */
    @Test
    public void testHumanPlays() {
        System.out.println("userPlays");
        SingleplayerGameLogic instance = new SingleplayerGameLogic();
        int choice = 1;
        Tile chosenTile = instance.getHeap().getHeap().get(choice - 1).get(6);
        // the new expected last tile of the row is going to be the tile currently located before the present last tile.
        Tile expHeapResult = instance.getHeap().getHeap().get(choice - 1).get(5);
        boolean needsRotation = false;
        String whereToPlace = "left";
        instance.humanPlays(choice, chosenTile, needsRotation, whereToPlace);
        // result is the current last tile of the row
        Tile heapResult = instance.getHeap().getHeap().get(choice - 1).get(instance.getHeap().getHeap().get(0).size()-1);
        assertEquals(expHeapResult.getNum1(), heapResult.getNum1());
        assertEquals(expHeapResult.getNum2(), heapResult.getNum2());
        Tile expTableResult = chosenTile;
        Tile tableResult = instance.getTable().getFirstTile();
        assertEquals(expTableResult.getNum1(), tableResult.getNum1());
        assertEquals(expTableResult.getNum2(), tableResult.getNum2());
    }
    
}
