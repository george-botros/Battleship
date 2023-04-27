package org.cis1200.battleship;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class BattleshipGameTest {

    @Test
    public void testReadInputtedBoard() {
        BattleshipGame model = new BattleshipGame();
        boolean res = model.readInputtedBoard("files/ExampleBoardInput.txt", true);

        assertTrue(res);

        Hittable bs = model.getPlayer1Cell(0, 0);
        Hittable t = model.getPlayer1Cell(0, 7);
        Hittable a = model.getPlayer1Cell(3, 0);
        Hittable d = model.getPlayer1Cell(3, 3);
        Hittable s = model.getPlayer1Cell(10, 2);
        Hittable c = model.getPlayer1Cell(13, 13);
        Hittable randomWater = model.getPlayer1Cell(0, 13);

        assertTrue(bs instanceof Battleship);
        assertTrue(t instanceof Boat);
        assertTrue(a instanceof AircraftCarrier);
        assertTrue(d instanceof Destroyer);
        assertTrue(s instanceof Submarine);
        assertTrue(c instanceof Cruiser);
        assertTrue(randomWater instanceof EmptyTile);
    }

    @Test
    public void testReadInputtedBoardTooClose() {
        BattleshipGame model = new BattleshipGame();
        boolean res = model.readInputtedBoard("files/ExampleFailingBoardInput.txt", true);

        assertFalse(res);

        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 13; j++) {
                assertTrue(model.getPlayer1Cell(i, j) instanceof EmptyTile);
            }
        }
    }

    @Test
    public void testAddShipTooClose() {
        BattleshipGame model = new BattleshipGame();
        boolean res = model.addShip(0, 0, Orientation.HORIZONTAL, 0, true);
        assertTrue(res);
        boolean res2 = model.addShip(0, 6, Orientation.HORIZONTAL, 1, true);
        assertFalse(res2);
        assertTrue(model.getPlayer1Cell(0, 6) instanceof EmptyTile);
        assertTrue(model.getPlayer1Cell(0, 0) instanceof Battleship);
    }

    @Test
    public void testAddShipIntersecting() {
        BattleshipGame model = new BattleshipGame();
        boolean res = model.addShip(0, 0, Orientation.HORIZONTAL, 0, true);
        assertTrue(res);
        boolean res2 = model.addShip(0, 2, Orientation.HORIZONTAL, 1, true);
        assertFalse(res2);
        assertTrue(model.getPlayer1Cell(0, 2) instanceof Battleship);
        assertTrue(model.getPlayer1Cell(0, 0) instanceof Battleship);
    }

    @Test
    public void testPlayTurn() {

        BattleshipGame model = new BattleshipGame();
        model.readInputtedBoard("files/ExampleBoardInput.txt", true);
        model.readInputtedBoard("files/ExampleBoardInput.txt", false);
        model.playTurn(0, 0);

        Hittable b1 = model.getPlayer2Cell(0, 0);
        Hittable b2 = model.getPlayer1Cell(0, 0);

        assertTrue(b1.getHitPoints(0));

        assertFalse(b1.getHitPoints(1));
        assertFalse(b1.getHitPoints(2));
        assertFalse(b2.getHitPoints(0));
        assertFalse(b2.getHitPoints(1));
        assertFalse(b2.getHitPoints(2));
        assertEquals(20, model.getPlayer1Score());

        model.playTurn(0, 1);
        assertTrue(b2.getHitPoints(1));

        assertFalse(b1.getHitPoints(1));
        assertFalse(b1.getHitPoints(2));
        assertFalse(b2.getHitPoints(0));
        assertFalse(b2.getHitPoints(2));
        assertEquals(20, model.getPlayer2Score());
    }

    @Test
    public void testPlayTurnInvalid() {

        BattleshipGame model = new BattleshipGame();
        model.readInputtedBoard("files/ExampleBoardInput.txt", true);
        model.readInputtedBoard("files/ExampleBoardInput.txt", false);
        boolean res = model.playTurn(14, 0);
        assertFalse(res);

        // Should still be player 1's turn;
        model.playTurn(0, 0);

        Hittable b1 = model.getPlayer2Cell(0, 0);
        Hittable b2 = model.getPlayer1Cell(0, 0);

        assertTrue(b1.getHitPoints(0));

        assertFalse(b1.getHitPoints(1));
        assertFalse(b1.getHitPoints(2));
        assertFalse(b2.getHitPoints(0));
        assertFalse(b2.getHitPoints(1));
        assertFalse(b2.getHitPoints(2));
        assertEquals(20, model.getPlayer1Score());

        model.playTurn(0, 1);
        assertTrue(b2.getHitPoints(1));

        assertFalse(b1.getHitPoints(1));
        assertFalse(b1.getHitPoints(2));
        assertFalse(b2.getHitPoints(0));
        assertFalse(b2.getHitPoints(2));
        assertEquals(20, model.getPlayer2Score());
    }

    @Test
    public void testWin1() {

        BattleshipGame model = new BattleshipGame();
        model.addShip(0, 0, Orientation.HORIZONTAL, 5, true);
        model.addShip(0, 0, Orientation.HORIZONTAL, 5, false);

        model.playTurn(0, 0);
        assertEquals(0, model.checkWinner());
        model.playTurn(13, 0);
        assertEquals(0, model.checkWinner());
        model.playTurn(0, 1);
        assertEquals(0, model.checkWinner());
        model.playTurn(12, 0);
        assertEquals(0, model.checkWinner());
        model.playTurn(0, 2);

        int win = model.checkWinner();
        assertEquals(1, win);
    }

    @Test
    public void testWin2() {

        BattleshipGame model = new BattleshipGame();
        model.addShip(0, 0, Orientation.HORIZONTAL, 5, true);
        model.addShip(0, 0, Orientation.HORIZONTAL, 5, false);

        model.playTurn(0, 5);
        assertEquals(0, model.checkWinner());
        model.playTurn(0, 0);
        assertEquals(0, model.checkWinner());
        model.playTurn(13, 0);
        assertEquals(0, model.checkWinner());
        model.playTurn(0, 1);
        assertEquals(0, model.checkWinner());
        model.playTurn(12, 0);
        assertEquals(0, model.checkWinner());
        model.playTurn(0, 2);

        int win = model.checkWinner();
        assertEquals(2, win);
    }

}
