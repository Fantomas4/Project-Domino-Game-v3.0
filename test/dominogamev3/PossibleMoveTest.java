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
public class PossibleMoveTest {
    
    public PossibleMoveTest() {
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
     * Test of needsRotation method, of class PossibleMove.
     */
    @Test
    public void testNeedsRotation() {
        System.out.println("needsRotation");
        PossibleMove instance = new PossibleMove(true, "left");
        boolean expResult = true;
        boolean result = instance.needsRotation();
        assertEquals(expResult, result);
    }

    /**
     * Test of whereToPlace method, of class PossibleMove.
     */
    @Test
    public void testWhereToPlace() {
        System.out.println("whereToPlace");
        PossibleMove instance = new PossibleMove(false, "right");
        String expResult = "right";
        String result = instance.whereToPlace();
        assertEquals(expResult, result);
    }
    
}
