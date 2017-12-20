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
public class Solo1GameLogicTest {
    
    public Solo1GameLogicTest() {
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
     * Test of possibleMoveExists method, of class Solo1GameLogic.
     */
    @Test
    public void testPossibleMoveExists() {
        System.out.println("possibleMoveExists");
        Solo1GameLogic instance = new Solo1GameLogic();
        boolean expResult = true;
        boolean result = instance.possibleMoveExists();
        assertEquals(expResult, result);
    }

    /**
     * Test of checkTileChoice method, of class Solo1GameLogic.
     */
    @Test
    public void testCheckTileChoice() {
        System.out.println("checkTileChoice");
        Solo1GameLogic instance = new Solo1GameLogic();
        PossibleMove result = instance.checkTileChoice(new Tile(6,6)).get(0);
        PossibleMove expResult = new PossibleMove(false, "left");
        assertEquals(expResult.needsRotation(), result.needsRotation());
        assertEquals(expResult.whereToPlace(), result.whereToPlace());
    }

    /**
     * Test of gameStatus method, of class Solo1GameLogic.
     */
    @Test
    public void testGameStatus() {
        System.out.println("gameStatus");
        Solo1GameLogic instance = new Solo1GameLogic();
        int expResult = 1;
        int result = instance.gameStatus();
        assertEquals(expResult, result);
    }
    
}
