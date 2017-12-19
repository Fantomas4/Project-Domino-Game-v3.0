/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominogamev3;

import java.util.ArrayList;
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
public class BasicGameLogicTest {
    
    public BasicGameLogicTest() {
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
     * Test of getHeap method, of class BasicGameLogic.
     */
    @Test
    public void testGetHeap() {
        System.out.println("getHeap");
        BasicGameLogic instance = new BasicGameLogic();
        int expResult = 28;
        Heap heap = instance.getHeap();
        ArrayList<ArrayList<Tile>> heapRowsList = heap.getHeap();
        int result = 0;
        for (ArrayList<Tile> heapRow : heapRowsList) {
            result += heapRow.size();
        }
        assertEquals(expResult, result);
    }

    /**
     * Test of getTable method, of class BasicGameLogic.
     */
    @Test
    public void testGetTable() {
        System.out.println("getTable");
        BasicGameLogic instance = new BasicGameLogic();
        int expResult = 0;
        int result = instance.getTable().getSize();
        assertEquals(expResult, result);
    }
    
}
