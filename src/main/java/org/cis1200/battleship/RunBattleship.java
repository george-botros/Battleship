package org.cis1200.battleship;


import javax.swing.*;
import java.awt.*;

/**
 * This class sets up the top-level frame and widgets for the GUI.
 */
public class RunBattleship implements Runnable {
    public void run() {
        // NOTE: the 'final' keyword denotes immutability even for local variables.

        // Top-level frame in which game components live
        final JFrame frame = new JFrame("Battleship");
        frame.setLocation(600, 600);

        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Setting up...");
        final JLabel player1Pt = new JLabel("P1 Points: 0\t\t\t\t\t\t");
        final JLabel player2Pt = new JLabel("\t\t\t\t\t\tP2 Points: 0");
        status_panel.add(player1Pt);
        status_panel.add(status);
        status_panel.add(player2Pt);

        // Game board
        final GameBoard board = new GameBoard(status, player1Pt, player2Pt);
        frame.add(board, BorderLayout.CENTER);

        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);
        control_panel.setLayout(new GridLayout(3, 5));

        // Diplay player 1 board button
        final JButton player1 = new JButton("Player 1");
        player1.addActionListener(e -> {
            board.changeDrawMode(GameBoard.DrawMode.PLAYER1_MODE);
            board.repaint();
            board.changeStatusToCurrentPlayer();
        });
        control_panel.add(player1);

        // Display player 2 board button
        final JButton player2 = new JButton("Player 2");
        player2.addActionListener(e -> {
            board.changeDrawMode(GameBoard.DrawMode.PLAYER2_MODE);
            board.repaint();
            board.changeStatusToCurrentPlayer();
        });
        control_panel.add(player2);

        // Launch Air Strike button
        final JButton airStrikeBullet = new JButton("Launch Air Strike");
        airStrikeBullet.addActionListener(e -> {
            board.launchAirStrikeIfAllowed();
        });
        control_panel.add(airStrikeBullet);

        // Drop Bomb button
        final JButton bombBullet = new JButton("Drop Bomb");
        bombBullet.addActionListener(e -> {
            board.dropBombIfAllowed();
        });
        control_panel.add(bombBullet);

        ImageIcon uploadIcon = new ImageIcon("files/but_classic.png");
        ImageIcon errorIcon = new ImageIcon("files/but_not.png");
        // Upload player 1's board button
        final JButton uploadP1 = new JButton("Load Player 1 Board");
        uploadP1.addActionListener(e -> {
            JTextField username = new JTextField();
            JTextField filePath = new JTextField();

            Object[] fields = { "Username", username,
                "Type the location of the text file of your board.", filePath };
            int clicked = JOptionPane.showConfirmDialog(
                    null, fields, "Load Player 1 Board",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    uploadIcon
            );

            if (clicked == JOptionPane.OK_OPTION) {
                if (filePath.getText() != null) {
                    boolean successfulUpload = board.setUpFromFile(filePath.getText(), true);
                    boolean successfulUser = board.setUsername(username.getText(), true);
                    if (!successfulUpload) {
                        JOptionPane.showMessageDialog(
                                frame,
                                "Cannot load file " + filePath.getText() + ".",
                                "Alert",
                                JOptionPane.ERROR_MESSAGE,
                                errorIcon
                        );
                    }
                    if (!successfulUser) {
                        JOptionPane.showMessageDialog(
                                frame,
                                "Username must be between 1 and 25 characters and" +
                                    " cannot contain any colons.",
                                "Alert",
                                JOptionPane.ERROR_MESSAGE,
                                errorIcon
                        );
                    }
                }
            }
        });
        control_panel.add(uploadP1);

        // Upload player 2's board button
        final JButton uploadP2 = new JButton("Load Player 2 Board");
        uploadP2.addActionListener(e -> {
            JTextField username = new JTextField();
            JTextField filePath = new JTextField();

            Object[] fields = { "Username", username,
                "Type the location of the text file of your board.", filePath };
            int clicked = JOptionPane.showConfirmDialog(
                    null, fields, "Load Player 2 Board",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    uploadIcon
            );

            if (clicked == JOptionPane.OK_OPTION) {
                if (filePath.getText() != null) {
                    boolean successfulUpload = board.setUpFromFile(filePath.getText(), false);
                    boolean successfulUser = board.setUsername(username.getText(), false);
                    if (!successfulUpload) {
                        JOptionPane.showMessageDialog(
                                frame,
                                "Cannot load file " + filePath.getText() + ".",
                                "Alert",
                                JOptionPane.ERROR_MESSAGE,
                                errorIcon
                        );
                    }
                    if (!successfulUser) {
                        JOptionPane.showMessageDialog(
                                frame,
                                "Username must be between 1 and 25 characters.",
                                "Alert",
                                JOptionPane.ERROR_MESSAGE,
                                errorIcon
                        );
                    }
                }
            }
        });
        control_panel.add(uploadP2);

        // display leaderboard button
        final JButton leaderboard = new JButton("Leaderboard");
        leaderboard.addActionListener(e -> {
            JOptionPane.showMessageDialog(
                    frame,
                    Leaderboard.getLeaderboard(),
                    "Leaderboard",
                    JOptionPane.PLAIN_MESSAGE
            );
        });
        control_panel.add(leaderboard);

        // Instructions button
        final JButton instructions = new JButton("Instructions");
        instructions.addActionListener(e -> {
            board.changeDrawMode(GameBoard.DrawMode.INSTRUCTION_MODE);
            board.repaint();
        });
        control_panel.add(instructions);

        // Reset button
        final JButton reset = new JButton("Reset");
        reset.addActionListener(e -> board.reset());
        control_panel.add(reset);

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start the game
        board.reset();
    }
}