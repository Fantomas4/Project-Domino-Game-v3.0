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
public class AllSevenGameLogicTest {

    public AllSevenGameLogicTest() {
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
     * Test of giveRoundPoints method, of class AllSevenGameLogic.
     */
    @Test
    public void testGiveRoundPoints() {
        System.out.println("giveRoundPoints");
        int mode = 2;
        AllSevenGameLogic instance = new AllSevenGameLogic(mode,"User");
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
     * Test of possibleMoveExists method, of class AllSevenGameLogic.
     */
    @Test
    public void testPossibleMoveExists() {
        System.out.println("possibleMoveExists");
        int mode = 2;
        AllSevenGameLogic instance = new AllSevenGameLogic(mode,"User");
        instance.setPlayingNowPlayer(0);
        Player subject = instance.getPlayingNowObj();
        boolean expResult = true;
        boolean result = instance.possibleMoveExists(subject);
        assertEquals(expResult, result);
    }

    /**
     * Test of checkTileChoice method, of class AllSevenGameLogic.
     */
    @Test
    public void testCheckTileChoice() {
        System.out.println("checkTileChoice");
        int mode = 2;
        AllSevenGameLogic instance = new AllSevenGameLogic(mode,"User");
        instance.setPlayingNowPlayer(1);
        instance.botPlays();
        ArrayList<Tile> tableTiles = instance.getTable().getTable();
        Tile tablePiece = tableTiles.get(0);
        if (tablePiece.getNum1() == tablePiece.getNum2()) {
            if (tablePiece.getNum2() == 0) {
                Tile testTile = new Tile(3, 5);
                int expResult = 0;
                int result = instance.checkTileChoice(testTile).size();  // gia to an prostethei cardinal tile elegxetai pio katw ( line 103-117 ) 
                assertEquals(expResult, result);
            } else {
                ArrayList<PossibleMove> result = new ArrayList<>();
                ArrayList<PossibleMove> expResult = new ArrayList<>();
                Tile testTile = new Tile(7 - tablePiece.getNum2(), 6 - tablePiece.getNum2());
                if (testTile.getNum1() + testTile.getNum2() == 7) {
                    expResult.add(new PossibleMove(false, "left"));
                    expResult.add(new PossibleMove(false, "right"));
                    expResult.add(new PossibleMove(true, "left"));
                    expResult.add(new PossibleMove(true, "right"));
                    result = instance.checkTileChoice(testTile);
                    assertEquals(expResult.get(0).needsRotation(), result.get(0).needsRotation());
                    assertEquals(expResult.get(0).whereToPlace(), result.get(0).whereToPlace());
                    assertEquals(expResult.get(1).needsRotation(), result.get(1).needsRotation());
                    assertEquals(expResult.get(1).whereToPlace(), result.get(1).whereToPlace());
                    assertEquals(expResult.get(2).needsRotation(), result.get(2).needsRotation());
                    assertEquals(expResult.get(2).whereToPlace(), result.get(2).whereToPlace());
                    assertEquals(expResult.get(3).needsRotation(), result.get(3).needsRotation());
                    assertEquals(expResult.get(3).whereToPlace(), result.get(3).whereToPlace());
                } else {
                    expResult.add(new PossibleMove(true, "left"));
                    expResult.add(new PossibleMove(false, "right"));
                    result = instance.checkTileChoice(testTile);
                    assertEquals(expResult.get(0).needsRotation(), result.get(0).needsRotation());
                    assertEquals(expResult.get(0).whereToPlace(), result.get(0).whereToPlace());
                    assertEquals(expResult.get(1).needsRotation(), result.get(1).needsRotation());
                    assertEquals(expResult.get(1).whereToPlace(), result.get(1).whereToPlace());
                }
            }
        } else {
            if (tablePiece.getNum1() == 0) {
                ArrayList<PossibleMove> result = new ArrayList<>();
                ArrayList<PossibleMove> expResult = new ArrayList<>();
                Tile testTile = new Tile(7 - tablePiece.getNum2(), 7 - tablePiece.getNum2());
                expResult.add(new PossibleMove(false, "right"));
                result = instance.checkTileChoice(testTile);
                assertEquals(expResult.get(0).needsRotation(), result.get(0).needsRotation());
                assertEquals(expResult.get(0).whereToPlace(), result.get(0).whereToPlace());
            } else {
                ArrayList<PossibleMove> result = new ArrayList<>();
                ArrayList<PossibleMove> expResult = new ArrayList<>();
                Tile testTile = new Tile(7 - tablePiece.getNum1(), 7 - tablePiece.getNum1());
                result = instance.checkTileChoice(testTile);
                expResult.add(new PossibleMove(true, "left"));
                assertEquals(expResult.get(0).needsRotation(), result.get(0).needsRotation());
                assertEquals(expResult.get(0).whereToPlace(), result.get(0).whereToPlace());
                ArrayList<PossibleMove> result2 = new ArrayList<>();
                ArrayList<PossibleMove> expResult2 = new ArrayList<>();
                Tile testTile2 = new Tile(7 - tablePiece.getNum1(), 7 - tablePiece.getNum2());
                result2 = instance.checkTileChoice(testTile2);
                expResult2.add(new PossibleMove(true, "left"));
                expResult2.add(new PossibleMove(true, "right"));
                assertEquals(expResult2.get(0).needsRotation(), result2.get(0).needsRotation());
                assertEquals(expResult2.get(0).whereToPlace(), result2.get(0).whereToPlace());
                assertEquals(expResult2.get(1).needsRotation(), result2.get(1).needsRotation());
                assertEquals(expResult2.get(1).whereToPlace(), result2.get(1).whereToPlace());
            }
        }
    }

    /**
     * Test of whoPlaysNext method, of class AllSevenGameLogic.
     */
    @Test
    public void testWhoPlaysNext() {
        System.out.println("whoPlaysNext");
        int mode = 2;
        AllSevenGameLogic instance = new AllSevenGameLogic(mode,"User");
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
     * Test of botPlays method, of class AllSevenGameLogic.
     */
    @Test
    public void testBotPlays() {
        System.out.println("botPlays");
        int mode = 2;
        AllSevenGameLogic instance = new AllSevenGameLogic(mode,"User");
        int index = 1;
        instance.setPlayingNowPlayer(index);
        instance.botPlays();
        int expResult = 1;
        int result = instance.getTable().getSize();
        assertEquals(expResult, result);
    }

}
