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
public class MultiplayerGameLogicTest {
    
    public MultiplayerGameLogicTest() {
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
     * Test of firstPlayerIndex method, of class MultiplayerGameLogic.
     */
    @Test
    public void testFirstPlayerIndex() {
        System.out.println("firstPlayerIndex");
        int mode = 2;
        HungarianGameLogic instance = new HungarianGameLogic(mode);
        int expResult = 0;
        if (instance.getPlayerOrderedList().get(1).getHighestTile().getNum1() > instance.getPlayerOrderedList().get(0).getHighestTile().getNum1()) {
            expResult = 1;
        }
        int result = instance.firstPlayerIndex();
        assertEquals(expResult, result);
    }

    /**
     * Test of humanPlays method, of class MultiplayerGameLogic.
     */
    @Test
    public void testHumanPlays() {
        System.out.println("humanPlays");
        int mode = 2;
        HungarianGameLogic instance = new HungarianGameLogic(mode);
        int index = 0;
        instance.setPlayingNowPlayer(index);
        ArrayList<Tile> playerHand = instance.getPlayingNowObj().getPlayerTiles();
        int choice = 1;
        Tile chosenTile = playerHand.get(1);
        instance.humanPlays(choice, chosenTile, false, "left");
        int expResult = 1;
        int result = instance.getTable().getSize();
        assertEquals(expResult, result);
    }

    /**
     * Test of scoreLimitReached method, of class MultiplayerGameLogic.
     */
    @Test
    public void testScoreLimitReached() {
        System.out.println("scoreLimitReached");
        int mode = 2;
        HungarianGameLogic instance = new HungarianGameLogic(mode);
        boolean expResult = false;
        boolean result = instance.scoreLimitReached();
        assertEquals(expResult, result);
    }

    /**
     * Test of resetRound method, of class MultiplayerGameLogic.
     */
    @Test
    public void testResetRound() {
        System.out.println("resetRound");
        int mode = 2;
        HungarianGameLogic instance = new HungarianGameLogic(mode);
        instance.setPlayingNowPlayer(1);
        instance.botPlays();
        instance.resetRound();
        int resultTable = instance.getTable().getSize();
        int resultAllTiles = instance.getHeap().getAllTiles().size();
        int resultPlayerTiles = 12;
        for (Player player : instance.getPlayerOrderedList()) {
            if (player.getPlayerTilesAmount() != 12) {
                resultPlayerTiles = player.getPlayerTilesAmount();
            }
        }
        int expResultTable = 0;
        int expResultAllTiles = 4; // after each one of the 2 players is given 12 tiles,
                                   // 4 tiles should still remain in the allTiles collection (28-24).
        int expResultPlayerTiles = 12;
        assertEquals(expResultTable, resultTable);
        assertEquals(expResultAllTiles, resultAllTiles);
        assertEquals(expResultPlayerTiles, resultPlayerTiles);
    }

    /**
     * Test of setPlayingNowPlayer method, of class MultiplayerGameLogic.
     */
    @Test
    public void testSetPlayingNowPlayer() {
        System.out.println("setPlayingNowPlayer");
        int mode = 2;
        HungarianGameLogic instance = new HungarianGameLogic(mode);
        int index = 1;
        instance.setPlayingNowPlayer(index);
        int expResult = index;
        int result = instance.getPlayingNowIndex();
        assertEquals(expResult, result);
    }

    /**
     * Test of getPlayerOrderedList method, of class MultiplayerGameLogic.
     */
    @Test
    public void testGetPlayerOrderedList() {
        System.out.println("getPlayerOrderedList");
        int mode = 2;
        HungarianGameLogic instance = new HungarianGameLogic(mode);
        int expResult = 2;
        int result = instance.getPlayerOrderedList().size();
        assertEquals(expResult, result);
    }

    /**
     * Test of getPlayingNowIndex method, of class MultiplayerGameLogic.
     */
    @Test
    public void testGetPlayingNowIndex() {
        System.out.println("getPlayingNowIndex");
        int mode = 2;
        HungarianGameLogic instance = new HungarianGameLogic(mode);
        instance.setPlayingNowPlayer(instance.firstPlayerIndex());
        int expResult = instance.firstPlayerIndex();
        int result = instance.getPlayingNowIndex();
        assertEquals(expResult, result);
    }

    /**
     * Test of getPlayingNowObj method, of class MultiplayerGameLogic.
     */
    @Test
    public void testGetPlayingNowObj() {
        System.out.println("getPlayingNowObj");
        int mode = 2;
        HungarianGameLogic instance = new HungarianGameLogic(mode);
        instance.setPlayingNowPlayer(instance.firstPlayerIndex());
        int index = instance.firstPlayerIndex();
        Player expResult = instance.getPlayerOrderedList().get(index);
        Player result = instance.getPlayingNowObj();
        assertEquals(expResult, result);
    }

    /**
     * Test of getWinnerPlayerName method, of class MultiplayerGameLogic.
     */
    @Test
    public void testGetWinnerPlayerName() {
        System.out.println("getWinnerPlayerName");
        int mode = 2;
        HungarianGameLogic instance = new HungarianGameLogic(mode);
        ArrayList<Player> playerOrderedList = instance.getPlayerOrderedList();
        playerOrderedList.get(1).increaseScore(101);
        String expResult = playerOrderedList.get(1).getPlayerName();
        instance.scoreLimitReached();
        String result = instance.getWinnerPlayerName();
        assertEquals(expResult, result);
    }
    
}
