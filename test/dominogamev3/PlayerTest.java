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
public class PlayerTest {
    
    public PlayerTest() {
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
     * Test of getScore method, of class Player.
     */
    @Test
    public void testGetScore() {
        System.out.println("getScore");
        Heap heap = new Heap();
        Player instance = new Player();
        int expResult = 0;
        int result = instance.getScore();
        assertEquals(expResult, result);
    }

    /**
     * Test of getPlayerName method, of class Player.
     */
    @Test
    public void testGetPlayerName() {
        System.out.println("getPlayerName");
        Heap heap = new Heap();
        ArrayList<Player> players = new ArrayList<>();
        Human human = new Human("User", 12 , heap);
        players.add(human);
        String expResult = "User";
        String result = players.get(0).getPlayerName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getPlayerTiles method, of class Player.
     */
    @Test
    public void testGetPlayerTiles() {
        System.out.println("getPlayerTiles");
        Heap heap = new Heap();
        int amount = 12;
        ArrayList<Player> players = new ArrayList<>();
        Human human = new Human("User", 12 , heap);
        players.add(human);
        players.get(0).givePlayerTiles(amount);
        int expResult = amount;
        int result = players.get(0).getPlayerTiles().size();
        assertEquals(expResult, result);
    }

    /**
     * Test of getRemainingTilePoints method, of class Player.
     */
    @Test
    public void testGetRemainingTilePoints() {
        System.out.println("getRemainingTilePoints");
        Heap heap = new Heap();
        ArrayList<Player> players = new ArrayList<>();
        Human human = new Human("User", 12 , heap);
        players.add(human);
        ArrayList<Tile> playerTiles = players.get(0).getPlayerTiles();
        int expResult=0;
        for (Tile piece : playerTiles) {
            expResult += piece.getNum1() + piece.getNum2();
        }
        int result = players.get(0).getRemainingTilePoints();
        assertEquals(expResult, result);
    }

    /**
     * Test of givePlayerTiles method, of class Player.
     */
    @Test
    public void testGivePlayerTiles() {
        System.out.println("givePlayerTiles");
        Heap heap = new Heap();
        ArrayList<Player> players = new ArrayList<>();
        Human human = new Human("User", 12 , heap);
        players.add(human);
        int expResult = 12;
        int result = players.get(0).getPlayerTilesAmount();
        assertEquals(expResult, result);
    }

    /**
     * Test of increaseScore method, of class Player.
     */
    @Test
    public void testIncreaseScore() {
        System.out.println("increaseScore");
        Heap heap = new Heap();
        ArrayList<Player> players = new ArrayList<>();
        Human human = new Human("User", 12 , heap);
        players.add(human);
        int expResult = 15;
        int points = 15;
        players.get(0).increaseScore(points);
        int result = players.get(0).getScore();
        assertEquals(expResult, result);
    }

    /**
     * Test of getHighestTile method, of class Player.
     */
    @Test
    public void testGetHighestTile() {
        System.out.println("getHighestTile");
        Heap heap = new Heap();
        ArrayList<Player> players = new ArrayList<>();
        Human human = new Human("User", 12 , heap);
        players.add(human);
        ArrayList<Tile> playerTiles = players.get(0).getPlayerTiles();
        Tile maxTile = new Tile(-1, -1);
        for (Tile piece : playerTiles) {
            if (piece.getNum1() == piece.getNum2()) {
                if (piece.getNum1() > maxTile.getNum1()) {
                    maxTile = piece;
                }
            }
        }
        Tile expResult = maxTile;
        Tile result = players.get(0).getHighestTile();
        assertEquals(expResult.getNum1(), result.getNum1());
    }

    /**
     * Test of getPlayerTilesAmount method, of class Player.
     */
    @Test
    public void testGetPlayerTilesAmount() {
        System.out.println("getPlayerTilesAmount");
        Heap heap = new Heap();
        ArrayList<Player> players = new ArrayList<>();
        Human human = new Human("User", 12 , heap);
        players.add(human);
        int expResult = 12;
        int result = players.get(0).getPlayerTilesAmount();
        assertEquals(expResult, result);
    }

    /**
     * Test of chooseTile method, of class Player.
     */
    @Test
    public void testChooseTile() {
        System.out.println("chooseTile");   
        Heap heap = new Heap();
        ArrayList<Player> players = new ArrayList<>();
        Human human = new Human("User", 12 , heap);
        players.add(human);
        int choice = 3; 
        ArrayList<Tile> playerTiles = players.get(0).getPlayerTiles();
        Tile expResult = playerTiles.get(2);
        Tile result = players.get(0).chooseTile(choice);
        assertEquals(expResult, result);
    }

    /**
     * Test of removePlayerTile method, of class Player.
     */
    @Test
    public void testRemovePlayerTile() {
        System.out.println("removePlayerTile");
        Heap heap = new Heap();
        ArrayList<Player> players = new ArrayList<>();
        Human human = new Human("User", 12 , heap);
        players.add(human);
        ArrayList<Tile> playerTiles = players.get(0).getPlayerTiles();
        int choice = 5;
        Tile expResult = playerTiles.get(choice+1);
        players.get(0).removePlayerTile(choice);
        Tile result = playerTiles.get(choice);
        assertEquals(expResult,result);
    }

    /**
     * Test of addTileToPlayer method, of class Player.
     */
    @Test
    public void testAddTileToPlayer() {
        System.out.println("addTileToPlayer");
        Heap heap = new Heap();
        ArrayList<Player> players = new ArrayList<>();
        Human human = new Human("User", 12 , heap);
        players.add(human);
        ArrayList<Tile> playerTiles = players.get(0).getPlayerTiles();
        int expResult = playerTiles.size() + 1;
        players.get(0).addTileToPlayer(new Tile(3,5));
        int result = playerTiles.size();
        assertEquals(expResult,result);
    }
    
}
