package org.cis1200.battleship;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * This class instantiates a BattleshipGame object, which is the model for the
 * game.
 * As the user clicks the game board, the model is updated. Whenever the model
 * is updated, the game board repaints itself and updates its status JLabel to
 * reflect the current state of the model.
 *
 * This game adheres to a Model-View-Controller design framework.
 */
@SuppressWarnings("serial")
public class GameBoard extends JPanel {

    private BattleshipGame gameModel; // model for the game
    private JLabel status; // current status text

    // Point labels for player 1 and player 2.
    private JLabel p1PtLabel;
    private JLabel p2PtLabel;

    private boolean player1Ready;
    private boolean player2Ready;

    // Game constants
    // Battleship is on a 14x14 grid. So, each cell is then 50 px.
    public static final int BOARD_WIDTH = 1400 / 2;
    public static final int BOARD_HEIGHT = 1400 / 2;

    public enum DrawMode {
        INSTRUCTION_MODE, PLAYER1_MODE, PLAYER2_MODE
    }

    DrawMode currDrawMode;

    /**
     * Initializes the game board.
     */
    public GameBoard(JLabel statusInit, JLabel player1Pt, JLabel player2Pt) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Enable keyboard focus on the court area. When this component has the
        // keyboard focus, key events are handled by its key listener.
        setFocusable(true);

        gameModel = new BattleshipGame(); // initializes model for the game
        status = statusInit; // initializes the status JLabel
        p1PtLabel = player1Pt;
        p2PtLabel = player2Pt;

        currDrawMode = DrawMode.PLAYER1_MODE;

        /*
         * Listens for mouseclicks. Updates the model, then updates the game
         * board based off of the updated model.
         */
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (player1Ready && player2Ready) {
                    // Must be on the board of the current player in order for clicks to update
                    // model.
                    if ((gameModel.getCurrentPlayer() && currDrawMode == DrawMode.PLAYER1_MODE) ||
                            (!gameModel.getCurrentPlayer()
                                    && currDrawMode == DrawMode.PLAYER2_MODE)) {

                        Point p = e.getPoint();

                        // updates the model given the coordinates of the mouseclick
                        boolean successful = gameModel.playTurn(p.y / 50, p.x / 50);

                        if (successful) {
                            updateStatus(); // updates the status JLabel
                        }
                        repaint(); // repaints the game board
                    }
                }
            }
        });
    }

    /**
     * (Re-)sets the game to its initial state.
     */
    public void reset() {
        gameModel.reset();
        changeDrawMode(DrawMode.INSTRUCTION_MODE);
        status.setText("Please upload your boards.");
        p1PtLabel.setText("P1 Points: 0\t\t\t\t\t\t");
        p2PtLabel.setText("\t\t\t\t\t\tP2 Points: 0");
        player1Ready = false;
        player2Ready = false;
        repaint();

        // Makes sure this component has keyboard/mouse focus
        requestFocusInWindow();
    }

    public void changeStatusToCurrentPlayer() {
        if (player1Ready && player2Ready) {
            if (gameModel.checkWinner() == 0) {
                if (gameModel.getCurrentPlayer()) {
                    status.setText("Player 1's Turn");
                } else {
                    status.setText("Player 2's Turn");
                }
            }
        }
    }

    /**
     * Updates the JLabel to reflect the current state of the game.
     */
    private void updateStatus() {
        status.setText("Nice shot! Please pass computer to the other player.");
        p1PtLabel.setText("P1 Points: " + gameModel.getPlayer1Score() + "\t\t\t\t\t\t");
        p2PtLabel.setText("\t\t\t\t\t\tP2 Points: " + gameModel.getPlayer2Score());

        int winner = gameModel.checkWinner();
        if (winner == 1) {
            status.setText("Player 1 wins!");
        } else if (winner == 2) {
            status.setText("Player 2 wins!");
        }
    }

    /**
     * Draws the game board.
     *
     * There are many ways to draw a game board. This approach
     * will not be sufficient for most games, because it is not
     * modular. All of the logic for drawing the game board is
     * in this method, and it does not take advantage of helper
     * methods. Consider breaking up your paintComponent logic
     * into multiple methods or classes, like Mushroom of Doom.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (currDrawMode == DrawMode.INSTRUCTION_MODE) {
            drawInstructions(g);
            return;
        }

        // Draws board grid
        drawBlankBoard(g);

        if (currDrawMode == DrawMode.PLAYER1_MODE) {
            drawPlayer1Board(g);
        } else if (currDrawMode == DrawMode.PLAYER2_MODE) {
            drawPlayer2Board(g);
        }
    }

    public void drawInstructions(Graphics g) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("files/instructions.png"));
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
        g.drawImage(img, 0, 0, 1400 / 2, 1400 / 2, null);
    }

    public void drawBlankBoard(Graphics g) {
        // Draws board grid
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("files/GameGrid.png"));
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
        g.drawImage(img, 0, 0, 1400 / 2, 1400 / 2, null);
    }

    public void drawPlayer1Board(Graphics g) {
        g.setColor(Color.RED);
        // Draw hit state
        for (int i = 0; i < 14; i++) {
            for (int j = 0; j < 14; j++) {
                // We want the cell of the opposite player.
                Hittable curr = gameModel.getPlayer2Cell(i, j);
                curr.draw(j * 50, i * 50, i, j, g);
            }
        }
    }

    public void drawPlayer2Board(Graphics g) {
        g.setColor(Color.RED);
        // Draw hit state
        for (int i = 0; i < 14; i++) {
            for (int j = 0; j < 14; j++) {
                Hittable curr = gameModel.getPlayer1Cell(i, j);
                curr.draw(j * 50, i * 50, i, j, g);
            }
        }
    }

    public void changeDrawMode(DrawMode newMode) {
        currDrawMode = newMode;
    }

    public void launchAirStrikeIfAllowed() {
        gameModel.switchBulletMode(BulletMode.AIRSTRIKE_MODE, gameModel.getCurrentPlayer());
    }

    public void dropBombIfAllowed() {
        gameModel.switchBulletMode(BulletMode.BOMB_MODE, gameModel.getCurrentPlayer());
    }

    public boolean setUpFromFile(String filePath, boolean player) {
        boolean res = gameModel.readInputtedBoard(filePath, player);
        if (player) {
            player1Ready = res;
        } else {
            player2Ready = res;
        }

        return res;
    }

    public boolean setUsername(String username, boolean player) {
        if (username.length() > 25 || username.length() == 0 || username.contains(":")) {
            if (player) {
                player1Ready = false;
            } else {
                player2Ready = false;
            }
            return false;
        }

        gameModel.setUsername(username, player);
        return true;
    }

    /**
     * Returns the size of the game board.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    }
}
