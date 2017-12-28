/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominogamev3;

import java.util.ArrayList;

/**
 *
 * @author Sierra Kilo
 */
public class AllSevenGameThread {
    
    private AllSevenGameLogic gameInstance;
    private AllSevenGameJFrame gameFrame;
    ArrayList<Player> playerList; 
    
    private static Object sharedLock;
    
    public AllSevenGameThread(int gamemode, String username, AllSevenGameJFrame gameFrame, Object sharedLock) {
        gameInstance = new AllSevenGameLogic(gamemode, username);
        playerList = gameInstance.getPlayerOrderedList();
        this.gameFrame = gameFrame;
        this.sharedLock = sharedLock;
    }
    
    public AllSevenGameLogic getGameInstance() {
        return gameInstance;
    }
    
}
