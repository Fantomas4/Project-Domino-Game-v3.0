/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominogamev3;

import java.util.ArrayList;
import java.util.HashSet;
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
public class HeapTest {
    
    public HeapTest() {
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
     * Test of getHeap method, of class Heap.
     */
    @Test
    public void testGetHeap() {
        System.out.println("getHeap");
        Heap instance = new Heap();
        int expResultRows = 4;
        int expResultColumns = 7;
        ArrayList<ArrayList<Tile>> resultArrayList = instance.getHeap();
        int result = resultArrayList.size();
        assertEquals(expResultRows, result);
        result = 7;
        for (ArrayList<Tile> ar : resultArrayList) {
            if (ar.size() != 7) {
                result = ar.size();
            }
        }
        assertEquals(expResultColumns, result);
    }

    /**
     * Test of removeHeapTile method, of class Heap.
     */
    @Test
    public void testRemoveHeapTile() {
        System.out.println("removeTile");
        Heap instance = new Heap();
        int choice = 1;
        ArrayList<Tile> row = instance.getHeap().get(choice);
        Tile expResult = row.get(5);
        instance.removeHeapTile(choice + 1);
        Tile result = instance.getHeap().get(choice).get(row.size()-1);
        assertEquals(expResult, result);
    }

    /**
     * Test of chooseTile method, of class Heap.
     */
    @Test
    public void testChooseTile() {
        System.out.println("chooseTile");
        int choice = 0;
        Heap instance = new Heap();
        Tile result = instance.chooseTile(choice+1); // choice 1 (1-4) is index 0 (arraylist).
        ArrayList<ArrayList<Tile>> currentHeap = instance.getHeap();
        ArrayList<Tile> wantedRow = currentHeap.get(choice);
        Tile expResult = wantedRow.get(wantedRow.size()-1);
        assertEquals(expResult, result);
    }

    /**
     * Test of pickRandomTile method, of class Heap.
     */
    @Test
    public void testPickRandomTile() {
        System.out.println("pickRandomTile");
        Heap instance = new Heap();
        boolean result = true;
        boolean expResult = true;
        Tile testTile = instance.pickRandomTile();
        for (Tile piece: instance.getAllTiles()) {
            if (piece.getNum1() == testTile.getNum1() && piece.getNum2() == testTile.getNum2()) {
                result = false;
            }
        }
        assertEquals(expResult, result);
    }

    /**
     * Test of setAllTiles method, of class Heap.
     */
    @Test
    public void testSetAllTiles() {
        System.out.println("setAllTiles");
        Heap instance = new Heap();
        instance.setAllTiles();
    }

    /**
     * Test of getAllTiles method, of class Heap.
     */
    @Test
    public void testGetAllTiles() {
        System.out.println("getAllTiles");
        Heap instance = new Heap();
        HashSet<Tile> uniqueTiles = new HashSet<>();
        int expResult = 28;
        ArrayList<Tile> returnedTileList = instance.getAllTiles();
        for (Tile piece : returnedTileList) {
            uniqueTiles.add(piece);
        }
        int result = uniqueTiles.size();
        assertEquals(expResult, result);
    }
    
}
