/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominogamev3;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * The main class of the Domino Game, responsible for printing the initial game
 * menu and based on the choice of the user, initializing the proper UI and game
 * structure, starting a new game session.
 *
 * @author Stefanos Karamperas / Athanasios Tsakmakis
 */
public class DominoGameMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArrayList<Integer> userChoices;

        do {
            userChoices = menuChoice();

            if (userChoices.get(0) == 1) {
                runSolo1UI();
            } else if (userChoices.get(0) == 2) {
                // starts a Hungarian game with the amount of bots
                // the user has chosen and that is represented with the integer
                // value at the pos 1 of the userChoices ArrayList.
                runHungarianUI(userChoices.get(1) + 1); // because the userChoices pos 1 contains the amount of bots for the game,
                // we add 1 to that amount to represent the human player.
            } else if (userChoices.get(0) == 3) {
                System.out.println("DIAG &&& ALL SEVEN GAME MODE!");
                System.out.println("DIAG: AMOUNT OF BOTS: " + userChoices.get(1));
                runAllSevenUI(userChoices.get(1) + 1); // because the userChoices pos 1 contains the amount of bots for the game,
                // we add 1 to that amount to represent the human player.
            }

        } while (userChoices.get(0) != 0);

        System.out.printf("%n%n%n                     ===== Thank you for playing ===== %n%n%n");

    }

    /**
     * Prints the game menu and reads a user choice that represents a menu
     * option
     *
     * @return an integer value that represents the menu option the user has
     * chosen.
     */
    public static ArrayList<Integer> menuChoice() {

        int menuChoice;
        ArrayList<Integer> userChoices = new ArrayList<>(); // ArrayList that holds an integer representing the 
        // user's menu choice (pos 0), and when needed (Hungarian or All 7)
        // a second integer representing the amount of bots the user
        // wants for the game (pos 1).

        System.out.printf("%n%n%n%n%n%n%n%n%n%n%n%n");
        System.out.println("=========================================================== Welcome to the Domino game ===========================================================");
        System.out.printf("%n%n%n");
        System.out.println("0. Exit program.");
        System.out.println("1. Start a new Solo 1 game.");
        System.out.println("2. Start a new Hungarian game.");
        System.out.println("3. Start a new All 7 game.");
        System.out.printf("%n%n");

        System.out.println("> Please select one of the given options (0-3).");
        System.out.printf("%n");

        menuChoice = intChoiceValidityCheck(0, 3);
        userChoices.add(menuChoice);

        if (menuChoice == 2 || menuChoice == 3) {

            System.out.printf("%n");
            System.out.println("> How many bots should be added as opponents?");
            System.out.println("> Please enter the number of bots you wish to add (maximum 3 bots per game): ");
            System.out.printf("%n");

            userChoices.add(intChoiceValidityCheck(1, 3));

        }

        System.out.printf("%n%n%n%n");
        return userChoices;

    }

    /**
     * Used to check whether the input choice of a user is a number within the
     * specific bounds stated by the game, and in case of error, ask the user to
     * enter a correct choice. This is repeated until a proper choice is given
     * by the user, that is returned by the method as an integer value.
     *
     * @param startValue the beginning of the bound of integer values accepted.
     * @param endValue the end of the bound of integer values accepted.
     * @return an integer value representing a correct choice number given by
     * the user, within the bounds set by startValue and endValue.
     */
    public static int intChoiceValidityCheck(int startValue, int endValue) {
        Scanner input = new Scanner(System.in);
        int choice;
        do {
            System.out.printf("> Enter your choice: ");
            while (!input.hasNextInt()) {
                System.out.printf("%n");
                System.out.printf("> Wrong entry! Please enter a number: ");
                input.next();
            }
            choice = input.nextInt();
            System.out.printf("%n");

            if (choice < startValue || choice > endValue) {
                System.out.println("> Wrong entry! Choice out of bounds.");
                System.out.printf("%n");
            }

        } while (choice < startValue || choice > endValue);

        // choice is accepted and returned
        return choice;
    }
    
    /**
     * The main structure for the Solo1 game that also displays the User
     * Interface (UI) of the Solo1 game.
     */
    public static void runSolo1UI() {

        Solo1GameLogic gameInstance = new Solo1GameLogic();

        Scanner input = new Scanner(System.in);
        String answer;
        int choice;
        Tile chosenTile;
        ArrayList<PossibleMove> result;

        do {
            System.out.println("Heap: ");
            System.out.printf("%n");

            // show game heap
            Heap heapObj = gameInstance.getHeap();
            ArrayList<ArrayList<Tile>> heapTiles = heapObj.getHeap();
            int rowsAmount = 0; // the amount of rows the heap displayed has.

            for (int i = 0; i < heapTiles.size(); i++) {
                ArrayList<Tile> row = heapTiles.get(i);
                if (row.size() > 0) {
                    rowsAmount++;
                }
                for (int j = 0; j < row.size(); j++) {
                    Tile piece = row.get(j);
                    System.out.printf("|%d %d| ", piece.getNum1(), piece.getNum2());
                }
                System.out.printf("%n%n");
            }

            System.out.printf("%n%n%n");

            do {
                System.out.println("> Enter the row from which you want to take the Tile (1-" + rowsAmount + ").");
                System.out.printf("%n");
                choice = intChoiceValidityCheck(1, rowsAmount);
                System.out.printf("%n");

                chosenTile = gameInstance.getHeap().chooseTile(choice);
                result = gameInstance.checkTileChoice(chosenTile);

                if (result.size() == 0) {
                    // there is no possible move with the chosen tile.
                    System.out.println("> There is no possible move with the chosen tile! Try again!");
                    // continue
                } else if (result.size() == 1) {
                    // there is one possible move so tile is placed automatically
                    gameInstance.humanPlays(choice, chosenTile, result.get(0).needsRotation(), result.get(0).whereToPlace());
                    break;
                } else {
                    // there are more 2 possible moves 
                    // so the user is asked about where to place tile.
                    System.out.println("> There are 2 possible moves with this tile.");
                    System.out.println("> Do you want to place the tile left or right?");
                    do {
                        answer = input.nextLine();
                        if (answer.equals("left") || answer.equals("right")) {
                            break;
                        } else {
                            System.out.println("> Please enter left or right as an answer.");
                        }
                    } while (true);

                    if (result.get(0).whereToPlace().equals(answer)) {
                        gameInstance.humanPlays(choice, chosenTile, result.get(0).needsRotation(), answer);
                        break;
                    } else if (result.get(1).whereToPlace().equals(answer)) {
                        gameInstance.humanPlays(choice, chosenTile, result.get(1).needsRotation(), answer);
                        break;
                    }
                }

            } while (true);

            System.out.println("Table: ");

            // show game Table
            Table tableObj = gameInstance.getTable();
            ArrayList<Tile> tableTiles = tableObj.getTable();

            for (Tile piece : tableTiles) {
                System.out.printf("|%d %d|", piece.getNum1(), piece.getNum2());
            }

            System.out.printf("%n%n%n");

        } while (gameInstance.gameStatus() == 1); // 1: game in progress.

        System.out.printf("%n%n%n%n");
        if (gameInstance.gameStatus() == 0) {
            System.out.println("                            *** Game over! There are no possible moves left! ***");
        } else if (gameInstance.gameStatus() == 2) {
            System.out.println("                            *** Congratulations! You have won! ***");
        }

    }

    /**
     * The main structure for the Hungarian game that also displays the User
     * Interface (UI) of the Hungarian game.
     *
     * @param gamemode determines the amount of players participating in the
     * Hungarian game session.
     */
    public static void runHungarianUI(int gamemode) {

        HungarianGameLogic gameInstance = new HungarianGameLogic(gamemode);
        Scanner input = new Scanner(System.in);
        String answer;
        int choice;

        do {

            gameInstance.setPlayingNowPlayer(gameInstance.firstPlayerIndex());

            do {
                System.out.printf("%n%n%n%n%n%n%n%n%n%n%n");
                System.out.println("==================================================================================================================================");

//                //show all players' hand.
//                System.out.println("!!!!!!!!!!!!! DIAG !!!!!!!!!!!!!!!!!!!!");
//                for (Player player : gameInstance.getPlayerOrderedList()) {
//
//                    //prints each player's hand at the beginning of each move.
//                    System.out.printf(player.getPlayerName() + " player's hand: ");
//
//                    ArrayList<Tile> playerTiles = player.getPlayerTiles();
//
//                    for (Tile piece : playerTiles) {
//                        System.out.printf("|%d %d|", piece.getNum1(), piece.getNum2());
//                    }
//
//                    System.out.printf("%n");
//                }
//                System.out.println("!!!!!!!!!!!!! DIAG !!!!!!!!!!!!!!!!!!!!");
                //System.out.printf("%n");
                System.out.println("*** Player  > " + gameInstance.getPlayingNowObj().getPlayerName() + " <  is playing now. ***");
                System.out.printf("%n");

                // if the player playing now is a human, print his tiles (hand).
                if (gameInstance.getPlayingNowObj() instanceof Human) {
                    System.out.printf("*** Player tiles: ");

                    ArrayList<Tile> playerTiles = gameInstance.getPlayingNowObj().getPlayerTiles();

                    for (Tile piece : playerTiles) {
                        System.out.printf("|%d %d|", piece.getNum1(), piece.getNum2());
                    }
                    System.out.printf(" ***");
                    System.out.printf("%n");
                }

                System.out.println("==================================================================================================================================");

                System.out.printf("%n");
                System.out.printf("*** Table: ");

                ArrayList<Tile> tableTiles = gameInstance.getTable().getTable();

                for (Tile piece : tableTiles) {
                    System.out.printf("|%d %d|", piece.getNum1(), piece.getNum2());
                }

                System.out.printf(" ***");
                System.out.printf("%n%n");

                if (gameInstance.getPlayingNowObj() instanceof Bot) {
                    //the player that plays now is a bot

                    gameInstance.botPlays();

                } else {
                    //the player that plays now is Human

                    do {
                        System.out.println("> Choose the tile you want to play with (1-" + gameInstance.getPlayingNowObj().getPlayerTilesAmount() + ").");
                        System.out.printf("%n");
                        choice = intChoiceValidityCheck(1, gameInstance.getPlayingNowObj().getPlayerTilesAmount());

                        // choice number accepted.
                        ArrayList<PossibleMove> result;
                        Tile chosenTile = gameInstance.getPlayingNowObj().chooseTile(choice);
                        result = gameInstance.checkTileChoice(chosenTile);

                        if (result.size() == 0) {
                            //there is no possible move with the chosen tile.
                            System.out.printf("%n");
                            System.out.println("> There is no possible move with the chosen tile! Try again!");
                            System.out.printf("%n");
                            //continue
                        } else if (result.size() == 1) {
                            //there is one possible move so tile is placed automatically.
                            gameInstance.humanPlays(choice, chosenTile, result.get(0).needsRotation(), result.get(0).whereToPlace());
                            break;
                        } else {
                            //there are more 2 possible moves 
                            //so user is asked about where to place tile
                            System.out.printf("%n");
                            System.out.println("> There are 2 possible moves with this tile.");
                            System.out.printf("%n");
                            System.out.println("> Do you want to place the tile left or right?");
                            do {
                                System.out.printf("%n");
                                System.out.printf("> Please enter left or right as an answer: ");
                                answer = input.nextLine();
                                System.out.printf("%n");

                                if (answer.equals("left") || answer.equals("right")) {
                                    break;
                                } else {
                                    System.out.println("> Wrong entry!");
                                }

                            } while (true);

                            if (result.get(0).whereToPlace().equals(answer)) {
                                gameInstance.humanPlays(choice, chosenTile, result.get(0).needsRotation(), answer);
                                break;
                            } else if (result.get(1).whereToPlace().equals(answer)) {
                                gameInstance.humanPlays(choice, chosenTile, result.get(1).needsRotation(), answer); //fixed bug caused by wrong result.get(0)
                                break;
                            }
                        }

                    } while (true);
                }
                System.out.printf("%n");
                System.out.println("==================================================================================================================================");

                if (gameInstance.whoPlaysNext() >= 0) {
                    gameInstance.setPlayingNowPlayer(gameInstance.whoPlaysNext());
                } else {
                    // round ends
                    break;
                }

            } while (true);

            int roundPoints = gameInstance.giveRoundPoints();
            System.out.printf("%n%n%n");
            System.out.println("                                                 *** END OF ROUND! ***");
            System.out.println("                                              *** Round Winner: " + gameInstance.getWinnerPlayerName() + " ***");
            System.out.println("                                                $$$ Points given: " + roundPoints + " $$$");

            gameInstance.resetRound();

        } while (gameInstance.scoreLimitReached() == false);

        System.out.println("%n%n%n");
        System.out.println("                              *** Player " + gameInstance.getWinnerPlayerName() + " has won the game by reaching the score limit! ***");
    }

    public static void runAllSevenUI(int gamemode) {

        AllSevenGameLogic gameInstance = new AllSevenGameLogic(gamemode);
        Scanner input = new Scanner(System.in);
        String answer;
        int choice;

        do {

            gameInstance.setPlayingNowPlayer(gameInstance.firstPlayerIndex());

            do {
                System.out.printf("%n%n%n%n%n%n%n%n%n%n%n");
                System.out.println("==================================================================================================================================");

                System.out.println("*** Player  > " + gameInstance.getPlayingNowObj().getPlayerName() + " <  is playing now. ***");
                System.out.printf("%n");

                // if the player playing now is a human, print his tiles (hand).
                if (gameInstance.getPlayingNowObj() instanceof Human) {
                    System.out.printf("*** Player tiles: ");

                    ArrayList<Tile> playerTiles = gameInstance.getPlayingNowObj().getPlayerTiles();

                    for (Tile piece : playerTiles) {
                        System.out.printf("|%d %d|", piece.getNum1(), piece.getNum2());
                    }
                    System.out.printf(" ***");
                    System.out.printf("%n");
                }

                System.out.println("==================================================================================================================================");

                System.out.printf("%n");
                System.out.printf("*** Table: ");

                ArrayList<Tile> tableTiles = gameInstance.getTable().getTable();

                for (Tile piece : tableTiles) {
                    System.out.printf("|%d %d|", piece.getNum1(), piece.getNum2());
                }

                System.out.printf(" ***");
                System.out.printf("%n%n");

                if (gameInstance.getPlayingNowObj() instanceof Bot) {
                    //the player that plays now is a bot

                    gameInstance.botPlays();

                } else {
                    //the player that plays now is Human

                    System.out.println("0. Take a random tile from the heap.");
                    System.out.println("1. Play using one of your hand's tiles.");
                    System.out.printf("%n");
                    choice = intChoiceValidityCheck(0, 1);

                    if (choice == 0) {
                        // user chose to take a random tile from the heap;
                        System.out.println("DIAG: getAllTiles size: " + gameInstance.getHeap().getAllTiles().size());
                        if (gameInstance.getHeap().getAllTiles().size() > 2) {
                            // if the heap contains more than 2 pieces, one can be given to the user.
                            gameInstance.getPlayingNowObj().addTileToPlayer(gameInstance.getHeap().pickRandomTile());

                        } else {
                            System.out.printf("> Two or less tiles are left in the heap. You can not be given a tile.");
                        }
                    }

                    if (choice == 1) {
                        // if the user chose to play with his own tiles, or if he chose to take a
                        // tile from the heap, but he was unable to be given one (heap contained 2 or less tiles).

                        do {
                            System.out.println("> Choose the tile you want to play with (1-" + gameInstance.getPlayingNowObj().getPlayerTilesAmount() + ").");
                            System.out.printf("%n");
                            choice = intChoiceValidityCheck(1, gameInstance.getPlayingNowObj().getPlayerTilesAmount());

                            // choice number accepted.
                            ArrayList<PossibleMove> result;
                            Tile chosenTile = gameInstance.getPlayingNowObj().chooseTile(choice);
                            result = gameInstance.checkTileChoice(chosenTile);
                            System.out.println("PIRA result me size: " + result.size());

                            if (result.size() == 0) {
                                //there is no possible move with the chosen tile.
                                System.out.printf("%n");
                                System.out.println("> There is no possible move with the chosen tile! Try again!");
                                System.out.printf("%n");
                                //continue
                            } else if (result.size() == 1) {
                                //there is one possible move so tile is placed automatically.
                                gameInstance.humanPlays(choice, chosenTile, result.get(0).needsRotation(), result.get(0).whereToPlace());
                                break;
                            } else if (result.size() == 2) {
                                //there are more 2 possible moves 
                                //so user is asked about where to place tile
                                System.out.printf("%n");
                                System.out.println("> There are 2 possible moves with this tile.");
                                System.out.printf("%n");
                                System.out.println("> Do you want to place the tile left or right?");
                                do {
                                    System.out.printf("%n");
                                    System.out.printf("> Please enter left or right as an answer: ");
                                    answer = input.nextLine();
                                    System.out.printf("%n");

                                    if (answer.equals("left") || answer.equals("right")) {
                                        break;
                                    } else {
                                        System.out.println("> Wrong entry!");
                                    }

                                } while (true);

                                if (result.get(0).whereToPlace().equals(answer)) {
                                    gameInstance.humanPlays(choice, chosenTile, result.get(0).needsRotation(), answer);
                                    break;
                                } else if (result.get(1).whereToPlace().equals(answer)) {
                                    gameInstance.humanPlays(choice, chosenTile, result.get(0).needsRotation(), answer);
                                    break;
                                }
                            } else if (result.size() == 4) {
                                // there are 4 possible moves (cardinal tile).
                                Boolean rotate;

                                System.out.printf("%n");
                                System.out.println("> There are 4 possible moves with this tile.");
                                System.out.printf("%n");
                                System.out.println("> Do you want to rotate the tile (180 degrees) ?");
                                do {
                                    System.out.printf("%n");
                                    System.out.printf("> Please enter yes or no as an answer: ");
                                    answer = input.nextLine();
                                    System.out.printf("%n");

                                    if (answer.equals("yes")) {
                                        rotate = true;
                                        break;
                                    } else if (answer.equals("no")) {
                                        rotate = false;
                                        break;
                                    } else {
                                        System.out.println("> Wrong entry!");
                                    }

                                } while (true);

                                System.out.printf("%n%n");
                                System.out.println("> Do you want to place the tile left or right?");
                                do {
                                    System.out.printf("%n");
                                    System.out.printf("> Please enter left or right as an answer: ");
                                    answer = input.nextLine();
                                    System.out.printf("%n");

                                    if (answer.equals("left") || answer.equals("right")) {
                                        break;
                                    } else {
                                        System.out.println("> Wrong entry!");
                                    }

                                } while (true);

                                gameInstance.humanPlays(choice, chosenTile, rotate, answer);
                                break;
                            }
                        } while (true);
                    }
                }
                System.out.printf("%n");
                System.out.println("==================================================================================================================================");

                if (gameInstance.whoPlaysNext() >= 0) {
                    gameInstance.setPlayingNowPlayer(gameInstance.whoPlaysNext());
                } else {
                    // round ends
                    break;
                }

            } while (true);

            int roundPoints = gameInstance.giveRoundPoints();
            System.out.printf("%n%n%n");
            System.out.println("                                                 *** END OF ROUND! ***");
            System.out.println("                                              *** Round Winner: " + gameInstance.getWinnerPlayerName() + " ***");
            System.out.println("                                                $$$ Points given: " + roundPoints + " $$$");

            gameInstance.resetRound();

        } while (gameInstance.scoreLimitReached() == false);

        System.out.println("%n%n%n");
        System.out.println("                              *** Player " + gameInstance.getWinnerPlayerName() + " has won the game by reaching the score limit! ***");

    }

}
