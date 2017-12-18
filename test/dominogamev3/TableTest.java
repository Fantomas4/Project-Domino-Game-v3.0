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
public class TableTest {
    
    public TableTest() {
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
     * Test of getSize method, of class Table.
     */
    @Test
    public void testGetSize() {
        System.out.println("getSize");
        Table instance = new Table();
        instance.addTile(new Tile(3,5), "left");
        instance.addTile(new Tile(5,2),"right");
        int expResult = 2;
        int result = instance.getSize();
        assertEquals(expResult, result);
    }

    /**
     * Test of getFirstTile method, of class Table.
     */
    @Test
    public void testGetFirstTile() {
        System.out.println("getFirstTile");
        Table instance = new Table();
        instance.addTile(new Tile(3,5), "left");
        instance.addTile(new Tile(5,2),"right");
        Tile expResult = new Tile(3,5);
        Tile result = instance.getFirstTile();
        assertEquals(expResult.getNum1(),result.getNum1());
        assertEquals(expResult.getNum2(),result.getNum2());
    }

    /**
     * Test of getLastTile method, of class Table.
     */
    @Test
    public void testGetLastTile() {
        System.out.println("getLastTile");
        Table instance = new Table();
        instance.addTile(new Tile(3,5), "left");
        instance.addTile(new Tile(5,2),"right");
        Tile expResult = new Tile(5,2);
        Tile result = instance.getLastTile();
        assertEquals(expResult.getNum1(),result.getNum1());
        assertEquals(expResult.getNum2(),result.getNum2());
    }

    /**
     * Test of addTile method, of class Table.
     */
    @Test
    public void testAddTile() {
        System.out.println("addTile");
        Table instance = new Table();
        instance.addTile(new Tile(3,5), "left");
        instance.addTile(new Tile(5,2),"right");
        Tile piece = new Tile(2,1);
        String side = "right";
        instance.addTile(piece, side);
        Tile expResult = piece;
        Tile result = instance.getLastTile();
        assertEquals(expResult.getNum1(),result.getNum1());
        assertEquals(expResult.getNum2(),result.getNum2());
    }

    /**
     * Test of clearTable method, of class Table.
     */
    @Test
    public void testClearTable() {
        System.out.println("clearTable");
        Table instance = new Table();
        instance.addTile(new Tile(3,5), "left");
        instance.addTile(new Tile(5,2), "right");
        instance.addTile(new Tile(2,1), "right");
        instance.clearTable();
        int expResult = 0;
        int result = instance.getTable().size();
        assertEquals(expResult,result);
    }

    /**
     * Test of getTable method, of class Table.
     */
    @Test
    public void testGetTable() {
        System.out.println("getTable");
        Table instance = new Table();
        int expResult = 0;
        int result = instance.getTable().size();
        assertEquals(expResult, result);
    }
    
}
