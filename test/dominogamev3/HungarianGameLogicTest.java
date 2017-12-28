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
public class HungarianGameLogicTest {
    
    public HungarianGameLogicTest() {
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
     * Test of giveRoundPoints method, of class HungarianGameLogic.
     */
    @Test
    public void testGiveRoundPoints() {
        System.out.println("giveRoundPoints");
        int mode = 2;
        HungarianGameLogic instance = new HungarianGameLogic(mode,"User");
        int humanPoints = instance.getPlayerOrderedList().get(0).getRemainingTilePoints();
        //System.out.println(humanPoints);
        int botPoints = instance.getPlayerOrderedList().get(1).getRemainingTilePoints();
        //System.out.println(botPoints)
        int expPointsResult = humanPoints;
        String expWinnerResult = "User";
        if (botPoints < humanPoints) {
            expWinnerResult = "Bot1";
        }
        if (botPoints > expPointsResult) {
            expPointsResult = botPoints;
        }
        int pointsResult = instance.giveRoundPoints();
        String winnerResult = instance.getWinnerPlayerName();
        assertEquals(expPointsResult, pointsResult);
        assertEquals(expWinnerResult, winnerResult);
    }

    /**
     * Test of possibleMoveExists method, of class HungarianGameLogic.
     */
    @Test
    public void testPossibleMoveExists() {
       System.out.println("possibleMoveExists");
        int mode = 2;
        HungarianGameLogic instance = new HungarianGameLogic(mode,"User");
        instance.setPlayingNowPlayer(0);
        Player subject = instance.getPlayingNowObj();
        boolean expResult = true;
        boolean result = instance.possibleMoveExists(subject);
        assertEquals(expResult, result);
    }

    /**
     * Test of checkTileChoice method, of class HungarianGameLogic.
     */
    @Test
    public void testCheckTileChoice() {
       System.out.println("checkTileChoice");
        int mode = 2;
        HungarianGameLogic instance = new HungarianGameLogic(mode,"User");
        instance.setPlayingNowPlayer(1);
        instance.botPlays();
        ArrayList<Tile> tableTiles = instance.getTable().getTable();
        Tile tablePiece = tableTiles.get(0);
        ArrayList<PossibleMove> result = new ArrayList<>();
        ArrayList<PossibleMove> expResult = new ArrayList<>();
        if (tablePiece.getNum1() == tablePiece.getNum2()) {
            if (tablePiece.getNum2() == 0) {
                Tile testTile = new Tile(tablePiece.getNum2(), tablePiece.getNum2() + 1);
                expResult.add(new PossibleMove(true, "left"));
                expResult.add(new PossibleMove(false, "right"));
                result = instance.checkTileChoice(testTile);
                // 2 possible moves exist and we check both.
                assertEquals(expResult.get(0).needsRotation(), result.get(0).needsRotation());
                assertEquals(expResult.get(0).whereToPlace(), result.get(0).whereToPlace());
                assertEquals(expResult.get(1).needsRotation(), result.get(1).needsRotation());
                assertEquals(expResult.get(1).whereToPlace(), result.get(1).whereToPlace());
            } else {
                Tile testTile = new Tile(tablePiece.getNum2(), tablePiece.getNum2() - 1);
                expResult.add(new PossibleMove(true, "left"));
                expResult.add(new PossibleMove(false, "right"));
                result = instance.checkTileChoice(testTile);
                // 2 possible moves exist and we check both.
                assertEquals(expResult.get(0).needsRotation(), result.get(0).needsRotation());
                assertEquals(expResult.get(0).whereToPlace(), result.get(0).whereToPlace());

                assertEquals(expResult.get(1).needsRotation(), result.get(1).needsRotation());
                assertEquals(expResult.get(1).whereToPlace(), result.get(1).whereToPlace());
            }
        } else {
            Tile testTile = new Tile(tablePiece.getNum2(), tablePiece.getNum2());
            result = instance.checkTileChoice(testTile);
            expResult.add(new PossibleMove(false, "right"));
            assertEquals(expResult.get(0).needsRotation(), result.get(0).needsRotation());
            assertEquals(expResult.get(0).whereToPlace(), result.get(0).whereToPlace());
    }
    }

    /**
     * Test of whoPlaysNext method, of class HungarianGameLogic.
     */
    @Test
    public void testWhoPlaysNext() {
        System.out.println("whoPlaysNext");
        int mode = 2;
        HungarianGameLogic instance = new HungarianGameLogic(mode,"User");
        int expResult;
        instance.setPlayingNowPlayer(1);
        if (instance.possibleMoveExists(instance.getPlayingNowObj())) {
            expResult = 1;
        } else {
            expResult = 0;
        }
        int result = instance.whoPlaysNext();
        assertEquals(expResult, result);
    }

    /**
     * Test of botPlays method, of class HungarianGameLogic.
     */
    @Test
    public void testBotPlays() {
        System.out.println("botPlays");
        int mode = 2;
        HungarianGameLogic instance = new HungarianGameLogic(mode,"User");
        int index = 1;
        instance.setPlayingNowPlayer(index);
        instance.botPlays();
        int expResult = 1;
        int result = instance.getTable().getSize();
        assertEquals(expResult, result);
    }
    
}
