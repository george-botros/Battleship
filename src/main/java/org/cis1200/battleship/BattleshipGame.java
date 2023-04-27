package org.cis1200.battleship;

/**
 * CIS 120 HW09 - TicTacToe Demo
 * (c) University of Pennsylvania
 * Created by Bayley Tuch, Sabrina Green, and Nicolas Corona in Fall 2020.
 */

import java.io.*;

/**
 * This class is a model for Battleship.
 *
 * This model is completely independent of the view and controller.
 * This is in keeping with the concept of modularity! We can play
 * the whole game from start to finish without ever drawing anything
 * on a screen or instantiating a Java Swing object.
 * 
 * Run this file to see the main method play a game of Battleship,
 * visualized with Strings printed to the console.
 */
public class BattleshipGame {

    /*
     * player1Board and player2Board refer to the boards that the respective player
     * created.
     * So, player 1 would shoot onto player 2's board and vice-versa.
     */
    // M - Miss
    // S - Ship
    // H - Hit
    private Hittable[][] player1Board;
    private Hittable[][] player2Board;

    // -- Game Information --
    /*
     * The score of each player. Score increases each time you hit a ship and can be
     * used for the
     * leaderboard or for special bullets.
     */
    private int player1Score;
    private int player2Score;
    // Indicates whose turns it is.
    private boolean player1;
    private boolean gameOver;

    // Usernames of players for leaderboard.
    private String player1Username;
    private String player2Username;
    private boolean written;

    // -- Bullets --
    private BulletMode bulletMode;
    private StandardBullet standardBullet;
    private BombBullet bomb;
    private AirStrikeBullet airStrike;

    /*
     * An array of all the ships of each player. On the board, a 4-square long
     * ship's object
     * will occupy four cells with all the cells being referentially equal.
     * Battleship - 6 spots
     * Aircraft Carrier - 5 spots
     * Cruiser - 5 spots
     * Destroyer - 4 spots
     * Submarine - 4 spots
     * Boat - 3 spots
     */
    private Hittable[] shipsPlayer1;
    private Hittable[] shipsPlayer2;

    /**
     * Constructor sets up game state.
     */
    public BattleshipGame() {
        reset();
    }

    /**
     * reset (re-)sets the game state to start a new game.
     */
    public void reset() {
        player1Board = new Hittable[14][14];
        player2Board = new Hittable[14][14];

        shipsPlayer1 = new Hittable[] { new Battleship(), new AircraftCarrier(), new Cruiser(),
            new Destroyer(), new Submarine(), new Boat() };
        shipsPlayer2 = new Hittable[] { new Battleship(), new AircraftCarrier(), new Cruiser(),
            new Destroyer(), new Submarine(), new Boat() };

        resetBoard(true); // player 1
        resetBoard(false); // player 2

        player1Score = 0;
        player2Score = 0;
        player1 = true;
        gameOver = false;
        player1Username = "";
        player2Username = "";
        written = false;

        bulletMode = BulletMode.STANDARD_BULLET_MODE;
        standardBullet = new StandardBullet();
        bomb = new BombBullet();
        airStrike = new AirStrikeBullet();
    }

    /* Resets the board of an individual player to be a 2D array of emptyTile's. */
    public void resetBoard(boolean player) {
        Hittable[][] currBoard = player1Board;
        if (!player) {
            currBoard = player2Board;
        }

        for (int i = 0; i < currBoard.length; i++) {
            for (int j = 0; j < currBoard[i].length; j++) {
                currBoard[i][j] = new EmptyTile();
                currBoard[i][j].setRow(i);
                currBoard[i][j].setCol(j);
            }
        }
    }

    /**
     * readInputtedBoard reads a board from a text file.
     *
     * @param filePath path to the text file
     * @param player   player whose board it is. True if player 1, false if player
     *                 2.
     * @return true if successful, false if unsuccessful
     */
    public boolean readInputtedBoard(String filePath, boolean player) {
        boolean haveBattleship = false, haveAircraftCarrier = false, haveCruiser = false,
                haveDestroyer = false, haveSubmarine = false, haveBoat = false;
        resetBoard(player);

        FileLineIterator reader;
        try {
            reader = new FileLineIterator(filePath);
        } catch (IllegalArgumentException e) {
            return false;
        }

        int r = 0;
        while (reader.hasNext() && r < 14) {
            // read next line
            String curr = reader.next();
            // convert to char array
            char[] currChars = curr.toCharArray();

            // Consider switching to hashset

            // Iterate through and check if it's a ship.
            for (int c = 0; c < currChars.length && c < 14; c++) {
                switch (currChars[c]) {
                    case 'B' -> {
                        if (!haveBattleship) {
                            if (!createPromptForAddShip(r, c, 0, player, currChars, 'B')) {
                                return false;
                            }
                            haveBattleship = true;
                        }
                    }
                    case 'A' -> {
                        if (!haveAircraftCarrier) {
                            if (!createPromptForAddShip(r, c, 1, player, currChars, 'A')) {
                                return false;
                            }
                            haveAircraftCarrier = true;
                        }
                    }
                    case 'C' -> {
                        if (!haveCruiser) {
                            if (!createPromptForAddShip(r, c, 2, player, currChars, 'C')) {
                                return false;
                            }
                            haveCruiser = true;
                        }
                    }
                    case 'D' -> {
                        if (!haveDestroyer) {
                            if (!createPromptForAddShip(r, c, 3, player, currChars, 'D')) {
                                return false;
                            }
                            haveDestroyer = true;
                        }
                    }
                    case 'S' -> {
                        if (!haveSubmarine) {
                            if (!createPromptForAddShip(r, c, 4, player, currChars, 'S')) {
                                return false;
                            }
                            haveSubmarine = true;
                        }
                    }
                    case 'T' -> {
                        if (!haveBoat) {
                            if (!createPromptForAddShip(r, c, 5, player, currChars, 'T')) {
                                return false;
                            }
                            haveBoat = true;
                        }
                    }
                    default -> {
                        continue;
                    }
                }
            }
            r++;
        }

        boolean res = haveBattleship && haveAircraftCarrier && haveCruiser && haveDestroyer &&
                haveSubmarine && haveBoat;
        if (!res) {
            resetBoard(player);
        }

        return res;
    }

    /*
     * Helper function for readInputtedBoard to simplify its code.
     */
    private boolean createPromptForAddShip(
            int r, int c, int shipNumber, boolean player, char[] currChars, char shipIndicator
    ) {
        // We are going to assume it's vertical and try to disprove that assumption.
        Orientation o = Orientation.VERTICAL;
        if (c < 13 && currChars[c + 1] == shipIndicator) {
            o = Orientation.HORIZONTAL;
        }

        // Add the ship. If unsuccessful, return false.
        boolean res = addShip(r, c, o, shipNumber, player);
        if (!res) {
            resetBoard(player);
            return false;
        }
        return true;
    }

    /**
     * addShip attempts to add a ship to a player's board, checking whether it is a
     * valid placement.
     *
     * @param r          row that you want to place ship in
     * @param c          column that you want to place ship in
     * @param o          orientation of the ship. This can be either
     *                   {@code Orientation.VERTICAL} or
     *                   {@code Orientation.VERTICAL}.
     * @param shipNumber index of the ship in the player's ship array
     * @param player     player who wants to place a ship. True if player 1, false
     *                   if player 2.
     * @return whether the turn was successful
     */
    public boolean addShip(int r, int c, Orientation o, int shipNumber, boolean player) {
        if (shipNumber >= shipsPlayer1.length) {
            return false;
        }

        Hittable currShip = shipsPlayer1[shipNumber];
        Hittable[][] currBoard = player1Board;
        if (!player) {
            currShip = shipsPlayer2[shipNumber];
            currBoard = player2Board;
        }

        if (o == null) {
            return false;
        }

        currShip.setOrientation(o);

        /*
         * Input validate r and c and check that it is a valid placement with the buffer
         * requirement.
         */
        if (r < 0 || c < 0 || r >= currBoard.length || c >= currBoard.length ||
                !checkValidPlacement(r, c, currShip, currBoard)) {
            return false;
        }

        currShip.setCol(c);
        currShip.setRow(r);

        // Place the ship.
        if (o == Orientation.VERTICAL) {
            for (int row = r; row < r + currShip.getLength(); row++) {
                currBoard[row][c] = currShip;
            }
        } else {
            for (int col = c; col < c + currShip.getLength(); col++) {
                currBoard[r][col] = currShip;
            }
        }

        return true;
    }

    /**
     * checkValidPlacement checks whether a ship is validly placed. A ship is
     * validly placed if
     * (1) it doesn't go off the edge of the board.
     * (2) it doesn't touch another ship
     * (3) it doesn't intersect another ship.
     *
     * @param r         row that you want to place ship in
     * @param c         column that you want to place ship in
     * @param currShip  object of the ship you want to place
     * @param currBoard reference to the board of the player whose turn it is
     * @return whether the turn was successful
     */
    private boolean checkValidPlacement(int r, int c, Hittable currShip, Hittable[][] currBoard) {

        int colMax;
        int rowMax;
        if (currShip.getOrientation() == Orientation.HORIZONTAL) {
            rowMax = r;
            colMax = c + currShip.getLength();
        } else {
            rowMax = r + currShip.getLength();
            colMax = c;
        }

        if (colMax > currBoard[1].length || rowMax > currBoard.length || c < 0 || r < 0) {
            return false;
        }

        // We want a 1 cell buffer around the ship. Two ships can share a buffer.
        for (int row = r - 1; row < rowMax + 1; row++) {
            for (int col = c - 1; col < colMax; col++) {
                if (row < 0 || row > currBoard.length || col < 0 || col > currBoard[row].length) {
                    continue;
                }

                if (!(currBoard[row][col] instanceof EmptyTile)) {
                    return false;
                }
            }
        }

        return true;
    }

    // Switch bullet mode. Return true if successful, false if unsuccessful.
    public boolean switchBulletMode(BulletMode newMode, boolean player1ToSwitch) {

        if (newMode == BulletMode.STANDARD_BULLET_MODE) {
            bulletMode = newMode;
            return true;
        }

        if (player1ToSwitch) {

            if (newMode == BulletMode.AIRSTRIKE_MODE) {
                if (airStrike.getCost() <= player1Score) {
                    bulletMode = newMode;
                    return true;
                }
            } else if (newMode == BulletMode.BOMB_MODE) {
                if (bomb.getCost() <= player1Score) {
                    bulletMode = newMode;
                    return true;
                }
            }
        } else {
            if (newMode == BulletMode.AIRSTRIKE_MODE) {
                if (airStrike.getCost() <= player2Score) {
                    bulletMode = newMode;
                    return true;
                }
            } else if (newMode == BulletMode.BOMB_MODE) {
                if (bomb.getCost() <= player2Score) {
                    bulletMode = newMode;
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * playTurn allows players to play a turn. Returns true if the move is
     * successful and false if a player tries to play in a location that is
     * taken or after the game has ended. If the turn is successful and the game
     * has not ended, the player is changed. If the turn is unsuccessful or the
     * game has ended, the player is not changed.
     *
     * @param c column to play in
     * @param r row to play in
     * @return whether the turn was successful
     */
    public boolean playTurn(int r, int c) {

        Hittable[][] board;

        if (r >= 14 || c >= 14 || r < 0 || c < 0) {
            return false;
        }

        // Sets it referentially equal to the board the player is playing on.
        if (player1) {
            board = player2Board;
        } else {
            board = player1Board;
        }

        // Input check
        if (!board[r][c].toString(r, c).equals("-") || gameOver) {
            return false;
        }

        // Fire the bullet.
        if (bulletMode == BulletMode.STANDARD_BULLET_MODE) {
            if (player1) {
                player1Score = standardBullet.fire(r, c, player1Score, board);
            } else {
                player2Score = standardBullet.fire(r, c, player2Score, board);
            }
        } else if (bulletMode == BulletMode.AIRSTRIKE_MODE) {
            if (player1) {
                player1Score = airStrike.fire(r, c, player1Score, board);
            } else {
                player2Score = airStrike.fire(r, c, player2Score, board);
            }
        } else if (bulletMode == BulletMode.BOMB_MODE) {
            if (player1) {
                player1Score = bomb.fire(r, c, player1Score, board);
            } else {
                player2Score = bomb.fire(r, c, player2Score, board);
            }
        } else {
            return false;
        }

        /*
         * If we wanted current player to keep going if they hit a ship, we can move
         * this.
         */
        if (checkWinner() == 0) {
            player1 = !player1;
            bulletMode = BulletMode.STANDARD_BULLET_MODE;
        }

        return true;
    }

    /**
     * checkWinner checks whether the game has reached a win condition.
     * checkWinner only looks for horizontal wins.
     *
     * @return 0 if nobody has won yet, 1 if player 1 has won, and 2 if player 2
     *         has won.
     */
    public int checkWinner() {

        if (checkWinnerHelper(player2Board)) {
            // Check if player 1 won.
            if (!written) {
                Leaderboard.writeNewScoreToLeaderboard(player1Username, player1Score);
                written = true;
            }
            return 1;
        } else if (checkWinnerHelper(player1Board)) {
            // Check if player 2 won.
            if (!written) {
                Leaderboard.writeNewScoreToLeaderboard(player2Username, player2Score);
                written = true;
            }
            return 2;
        }

        return 0;
    }

    private boolean checkWinnerHelper(Hittable[][] currBoard) {
        for (int i = 0; i < currBoard.length; i++) {
            for (int j = 0; j < currBoard[i].length; j++) {
                if (!currBoard[i][j].isSunk()) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * printGameState prints the current game state
     * for debugging.
     */
    public void printGameState() {
        Hittable[][] currBoard;

        if (player1) {
            System.out.println("Player 1's turn");
            currBoard = player2Board;
        } else {
            System.out.println("Player 2's turn");
            currBoard = player1Board;
        }

        for (int i = 0; i < currBoard.length; i++) {
            for (int j = 0; j < currBoard[i].length; j++) {
                System.out.print(currBoard[i][j].toString(i, j));
            }
            System.out.println();
        }
    }

    /**
     * getCurrentPlayer is a getter for the player
     * whose turn it is in the game.
     * 
     * @return true if it's Player 1's turn,
     *         false if it's Player 2's turn.
     */
    public boolean getCurrentPlayer() {
        return player1;
    }

    /**
     * getCellOfTurn is a getter for the contents of the cell specified by the
     * method
     * arguments of the board that the current player is playing on.
     *
     * @param c column to retrieve
     * @param r row to retrieve
     * @return a char denoting the contents of the corresponding cell on the
     *         game board. 'S' = ship, 'M' = miss, 'H' = hit, null = unplayed.
     */
    public Hittable getCellOfTurn(int r, int c) {
        if (player1) {
            return getPlayer2Cell(r, c);
        } else {
            return getPlayer1Cell(r, c);
        }
    }

    /**
     * getPlayer1Cell is a getter for the contents of the cell specified by the
     * method
     * arguments of the board of player 1.
     *
     * @param c column to retrieve
     * @param r row to retrieve
     * @return a char denoting the contents of the corresponding cell on the
     *         game board. 'S' = ship, 'M' = miss, 'H' = hit, null = unplayed.
     */
    public Hittable getPlayer1Cell(int r, int c) {
        return player1Board[r][c];
    }

    /**
     * getPlayer2Cell is a getter for the contents of the cell specified by the
     * method
     * arguments of the board of player 1.
     *
     * @param c column to retrieve
     * @param r row to retrieve
     * @return a char denoting the contents of the corresponding cell on the
     *         game board. 'S' = ship, 'M' = miss, 'H' = hit, null = unplayed.
     */
    public Hittable getPlayer2Cell(int r, int c) {
        return player2Board[r][c];
    }

    /*
     * Returns player 1's score.
     */
    public int getPlayer1Score() {
        return player1Score;
    }

    /*
     * Returns player 2's score.
     */
    public int getPlayer2Score() {
        return player2Score;
    }

    /*
     * Returns score of the player whose turn it is.
     */
    public int getCurrentPlayerScore() {
        if (player1) {
            return player1Score;
        }

        return player2Score;
    }

    /*
     * Sets the score of player 1. Used for testing purposes.
     */
    public void setPlayer1Score(int player1Score) {
        this.player1Score = player1Score;
    }

    /*
     * Sets the score of player 2. Used for testing purposes.
     */
    public void setPlayer2Score(int player2Score) {
        this.player2Score = player2Score;
    }

    /*
     * Sets the username of the given player.
     */
    public void setUsername(String username, boolean player) {
        if (player) {
            player1Username = username;
        } else {
            player2Username = username;
        }
    }

    /**
     * This main method illustrates how the model is completely independent of
     * the view and controller. We can play the game from start to finish
     * without ever creating a Java Swing object.
     *
     * This is modularity in action, and modularity is the bedrock of the
     * Model-View-Controller design framework.
     *
     * Run this file to see the output of this method in your console.
     */
    public static void main(String[] args) {
        BattleshipGame t = new BattleshipGame();
        t.addShip(0, 0, Orientation.HORIZONTAL, 5, true);
        t.addShip(0, 0, Orientation.HORIZONTAL, 5, false);

        t.printGameState();

        t.playTurn(1, 1);
        t.printGameState();

        t.playTurn(0, 0);
        t.printGameState();

        t.playTurn(0, 2);
        t.printGameState();

        System.out.println();
        System.out.println();
        System.out.println("Winner is: " + t.checkWinner());
    }
}
