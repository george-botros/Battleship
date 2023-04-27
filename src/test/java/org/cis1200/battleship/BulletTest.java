package org.cis1200.battleship;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class BulletTest {

    // Standard bullet
    @Test
    public void testStandardBullet() {
        BattleshipGame model = new BattleshipGame();
        model.addShip(0, 0, Orientation.HORIZONTAL, 5, true);
        model.addShip(0, 0, Orientation.HORIZONTAL, 5, false);
        model.setPlayer1Score(100);
        model.playTurn(0, 0);
        Hittable b1 = model.getPlayer2Cell(0, 0);
        Hittable b2 = model.getPlayer1Cell(0, 0);
        assertTrue(b1.getHitPoints(0));
        assertFalse(b1.getHitPoints(1));
        assertFalse(b1.getHitPoints(2));
        assertFalse(b2.getHitPoints(0));
        assertFalse(b2.getHitPoints(1));
        assertFalse(b2.getHitPoints(2));
        assertEquals(100 + b1.getRewardPoints(), model.getPlayer1Score());
    }

    // Air Strike bullet
    @Test
    public void testAirStrikeBullet1() {
        BattleshipGame model = new BattleshipGame();
        model.addShip(0, 0, Orientation.HORIZONTAL, 5, true);
        model.addShip(0, 0, Orientation.HORIZONTAL, 5, false);
        model.setPlayer1Score(100);
        model.switchBulletMode(BulletMode.AIRSTRIKE_MODE, true);
        // Selected top edge
        model.playTurn(0, 0);
        Hittable b1 = model.getPlayer2Cell(0, 0);
        Hittable b2 = model.getPlayer1Cell(0, 0);
        for (int i = 0; i < 13; i++) {
            Hittable curr1 = model.getPlayer2Cell(i, 0);
            Hittable curr2 = model.getPlayer1Cell(i, 0);
            assertTrue(curr1.getHitPoints(0));
            assertFalse(curr2.getHitPoints(0));
        }
        assertFalse(b1.getHitPoints(1));
        assertFalse(b1.getHitPoints(2));
        assertFalse(b2.getHitPoints(0));
        assertFalse(b2.getHitPoints(1));
        assertFalse(b2.getHitPoints(2));
        assertEquals(0 + b1.getRewardPoints(), model.getPlayer1Score());
    }

    @Test
    public void testAirStrikeBullet2() {
        BattleshipGame model = new BattleshipGame();
        model.addShip(0, 0, Orientation.HORIZONTAL, 5, true);
        model.addShip(0, 0, Orientation.HORIZONTAL, 5, false);
        model.setPlayer1Score(100);
        model.switchBulletMode(BulletMode.AIRSTRIKE_MODE, true);
        // Selected bottom edge.
        model.playTurn(13, 0);
        Hittable b1 = model.getPlayer2Cell(0, 0);
        Hittable b2 = model.getPlayer1Cell(0, 0);
        for (int i = 0; i < 13; i++) {
            Hittable curr1 = model.getPlayer2Cell(i, 0);
            Hittable curr2 = model.getPlayer1Cell(i, 0);
            assertTrue(curr1.getHitPoints(0));
            assertFalse(curr2.getHitPoints(0));
        }
        assertFalse(b1.getHitPoints(1));
        assertFalse(b1.getHitPoints(2));
        assertFalse(b2.getHitPoints(0));
        assertFalse(b2.getHitPoints(1));
        assertFalse(b2.getHitPoints(2));
        assertEquals(0 + b1.getRewardPoints(), model.getPlayer1Score());
    }

    @Test
    public void testAirStrikeBullet3() {
        BattleshipGame model = new BattleshipGame();
        model.addShip(0, 0, Orientation.HORIZONTAL, 5, true);
        model.addShip(0, 0, Orientation.HORIZONTAL, 5, false);
        model.setPlayer1Score(100);
        model.switchBulletMode(BulletMode.AIRSTRIKE_MODE, true);
        // Selected middle
        model.playTurn(5, 0);
        Hittable b1 = model.getPlayer2Cell(0, 0);
        Hittable b2 = model.getPlayer1Cell(0, 0);
        for (int i = 0; i < 13; i++) {
            Hittable curr1 = model.getPlayer2Cell(i, 0);
            Hittable curr2 = model.getPlayer1Cell(i, 0);
            assertTrue(curr1.getHitPoints(0));
            assertFalse(curr2.getHitPoints(0));
        }
        assertFalse(b1.getHitPoints(1));
        assertFalse(b1.getHitPoints(2));
        assertFalse(b2.getHitPoints(0));
        assertFalse(b2.getHitPoints(1));
        assertFalse(b2.getHitPoints(2));
        assertEquals(0 + b1.getRewardPoints(), model.getPlayer1Score());
    }

    @Test
    public void testAirStrikeBulletNotEnoughPoints1() {
        BattleshipGame model = new BattleshipGame();
        model.addShip(0, 0, Orientation.HORIZONTAL, 5, true);
        model.addShip(0, 0, Orientation.HORIZONTAL, 5, false);
        model.setPlayer1Score(99);
        model.switchBulletMode(BulletMode.AIRSTRIKE_MODE, true);
        model.playTurn(0, 0);
        Hittable b1 = model.getPlayer2Cell(0, 0);
        Hittable b2 = model.getPlayer1Cell(0, 0);
        for (int i = 1; i < 13; i++) {
            Hittable curr1 = model.getPlayer2Cell(i, 0);
            Hittable curr2 = model.getPlayer1Cell(i, 0);
            assertFalse(curr1.getHitPoints(0));
            assertFalse(curr2.getHitPoints(0));
        }
        assertTrue(b1.getHitPoints(0));
        assertFalse(b2.getHitPoints(0));
        assertFalse(b1.getHitPoints(1));
        assertFalse(b1.getHitPoints(2));
        assertFalse(b2.getHitPoints(0));
        assertFalse(b2.getHitPoints(1));
        assertFalse(b2.getHitPoints(2));
        assertEquals(99 + b1.getRewardPoints(), model.getPlayer1Score());
    }

    @Test
    public void testAirStrikeBulletNotEnoughPoints2() {
        BattleshipGame model = new BattleshipGame();
        model.addShip(0, 0, Orientation.HORIZONTAL, 5, true);
        model.addShip(0, 0, Orientation.HORIZONTAL, 5, false);
        model.setPlayer1Score(99);
        model.switchBulletMode(BulletMode.AIRSTRIKE_MODE, true);
        model.playTurn(5, 0);
        Hittable b1 = model.getPlayer2Cell(0, 0);
        Hittable b2 = model.getPlayer1Cell(0, 0);
        for (int i = 0; i < 13; i++) {
            Hittable curr1 = model.getPlayer2Cell(i, 0);
            Hittable curr2 = model.getPlayer1Cell(i, 0);
            if (i == 5) {
                assertTrue(curr1.getHitPoints(0));
                assertFalse(curr2.getHitPoints(0));
                continue;
            }

            assertFalse(curr1.getHitPoints(0));
            assertFalse(curr2.getHitPoints(0));
        }
        assertFalse(b1.getHitPoints(1));
        assertFalse(b1.getHitPoints(2));
        assertFalse(b2.getHitPoints(0));
        assertFalse(b2.getHitPoints(1));
        assertFalse(b2.getHitPoints(2));
        assertEquals(99, model.getPlayer1Score());
    }

    // Bomb tests

    @Test
    public void testBombBulletOnEdge1() {
        BattleshipGame model = new BattleshipGame();
        model.addShip(0, 0, Orientation.HORIZONTAL, 5, true);
        model.addShip(0, 0, Orientation.HORIZONTAL, 5, false);
        model.setPlayer1Score(50);
        model.switchBulletMode(BulletMode.BOMB_MODE, true);
        // Selected top left edge
        model.playTurn(0, 0);

        Hittable b1 = model.getPlayer2Cell(0, 0);
        Hittable b2 = model.getPlayer1Cell(0, 0);
        assertTrue(b1.getHitPoints(0));
        assertTrue(b1.getHitPoints(1));
        assertTrue(model.getPlayer2Cell(1, 0).getHitPoints(0));
        assertTrue(model.getPlayer2Cell(1, 1).getHitPoints(0));

        assertFalse(b1.getHitPoints(2));
        assertFalse(b2.getHitPoints(0));
        assertFalse(b2.getHitPoints(1));
        assertFalse(b2.getHitPoints(2));
        assertEquals(0 + b1.getRewardPoints() * 2, model.getPlayer1Score());
    }

    @Test
    public void testBombBulletOnEdge2() {
        BattleshipGame model = new BattleshipGame();
        model.addShip(0, 0, Orientation.HORIZONTAL, 5, true);
        model.addShip(0, 0, Orientation.HORIZONTAL, 5, false);
        model.setPlayer1Score(50);
        model.switchBulletMode(BulletMode.BOMB_MODE, true);
        // Selected bottom right edge
        model.playTurn(13, 13);

        Hittable b1 = model.getPlayer2Cell(0, 0);
        Hittable b2 = model.getPlayer1Cell(0, 0);

        assertTrue(model.getPlayer2Cell(13, 13).getHitPoints(0));
        assertTrue(model.getPlayer2Cell(12, 13).getHitPoints(0));
        assertTrue(model.getPlayer2Cell(13, 12).getHitPoints(0));
        assertTrue(model.getPlayer2Cell(12, 12).getHitPoints(0));

        assertFalse(b1.getHitPoints(0));
        assertFalse(b1.getHitPoints(1));
        assertFalse(b1.getHitPoints(2));
        assertFalse(b2.getHitPoints(0));
        assertFalse(b2.getHitPoints(1));
        assertFalse(b2.getHitPoints(2));
        assertEquals(0, model.getPlayer1Score());
    }

    @Test
    public void testBombBulletOnEdge3() {
        BattleshipGame model = new BattleshipGame();
        model.addShip(0, 0, Orientation.HORIZONTAL, 5, true);
        model.addShip(0, 0, Orientation.HORIZONTAL, 5, false);
        model.setPlayer1Score(50);
        model.switchBulletMode(BulletMode.BOMB_MODE, true);
        // Selected mid-top edge
        model.playTurn(0, 3);

        Hittable b1 = model.getPlayer2Cell(0, 0);
        Hittable b2 = model.getPlayer1Cell(0, 0);
        assertTrue(b1.getHitPoints(2));
        assertTrue(model.getPlayer2Cell(0, 3).getHitPoints(0));
        assertTrue(model.getPlayer2Cell(0, 4).getHitPoints(0));
        assertTrue(model.getPlayer2Cell(1, 2).getHitPoints(0));
        assertTrue(model.getPlayer2Cell(1, 3).getHitPoints(0));
        assertTrue(model.getPlayer2Cell(1, 4).getHitPoints(0));

        assertFalse(b1.getHitPoints(0));
        assertFalse(b1.getHitPoints(1));
        assertFalse(b2.getHitPoints(0));
        assertFalse(b2.getHitPoints(1));
        assertFalse(b2.getHitPoints(2));
        assertEquals(0 + b1.getRewardPoints(), model.getPlayer1Score());
    }

    @Test
    public void testBombBulletOnEdge4() {
        BattleshipGame model = new BattleshipGame();
        model.addShip(0, 0, Orientation.HORIZONTAL, 5, true);
        model.addShip(0, 0, Orientation.HORIZONTAL, 5, false);
        model.setPlayer1Score(50);
        model.switchBulletMode(BulletMode.BOMB_MODE, true);
        // Selected mid-right edge
        model.playTurn(5, 13);

        Hittable b1 = model.getPlayer2Cell(0, 0);
        Hittable b2 = model.getPlayer1Cell(0, 0);
        assertTrue(model.getPlayer2Cell(4, 13).getHitPoints(0));
        assertTrue(model.getPlayer2Cell(5, 13).getHitPoints(0));
        assertTrue(model.getPlayer2Cell(6, 13).getHitPoints(0));
        assertTrue(model.getPlayer2Cell(4, 12).getHitPoints(0));
        assertTrue(model.getPlayer2Cell(5, 12).getHitPoints(0));
        assertTrue(model.getPlayer2Cell(6, 12).getHitPoints(0));

        assertFalse(b1.getHitPoints(0));
        assertFalse(b1.getHitPoints(1));
        assertFalse(b1.getHitPoints(2));
        assertFalse(b2.getHitPoints(0));
        assertFalse(b2.getHitPoints(1));
        assertFalse(b2.getHitPoints(2));
        assertEquals(0, model.getPlayer1Score());
    }

    @Test
    public void testBombBullet() {
        BattleshipGame model = new BattleshipGame();
        model.addShip(0, 0, Orientation.HORIZONTAL, 5, true);
        model.addShip(0, 0, Orientation.HORIZONTAL, 5, false);
        model.setPlayer1Score(50);
        model.switchBulletMode(BulletMode.BOMB_MODE, true);
        // Selected mid-right edge
        model.playTurn(1, 1);

        Hittable b1 = model.getPlayer2Cell(0, 0);
        Hittable b2 = model.getPlayer1Cell(0, 0);
        assertTrue(b1.getHitPoints(0));
        assertTrue(b1.getHitPoints(1));
        assertTrue(b1.getHitPoints(2));
        assertTrue(b1.isSunk());
        assertTrue(model.getPlayer2Cell(1, 0).getHitPoints(0));
        assertTrue(model.getPlayer2Cell(1, 1).getHitPoints(0));
        assertTrue(model.getPlayer2Cell(1, 2).getHitPoints(0));
        assertTrue(model.getPlayer2Cell(2, 0).getHitPoints(0));
        assertTrue(model.getPlayer2Cell(2, 1).getHitPoints(0));
        assertTrue(model.getPlayer2Cell(2, 2).getHitPoints(0));

        assertFalse(b2.getHitPoints(0));
        assertFalse(b2.getHitPoints(1));
        assertFalse(b2.getHitPoints(2));
        assertEquals(0 + b1.getRewardPoints() * 3, model.getPlayer1Score());
    }

    @Test
    public void testBombBulletNotEnoughPoints1() {
        BattleshipGame model = new BattleshipGame();
        model.addShip(0, 0, Orientation.HORIZONTAL, 5, true);
        model.addShip(0, 0, Orientation.HORIZONTAL, 5, false);
        model.setPlayer1Score(49);
        model.switchBulletMode(BulletMode.BOMB_MODE, true);
        model.playTurn(5, 0);
        Hittable b1 = model.getPlayer2Cell(0, 0);
        Hittable b2 = model.getPlayer1Cell(0, 0);

        assertTrue(model.getPlayer2Cell(5, 0).getHitPoints(0));

        assertFalse(b1.getHitPoints(0));
        assertFalse(b1.getHitPoints(1));
        assertFalse(b1.getHitPoints(2));
        assertFalse(b2.getHitPoints(0));
        assertFalse(b2.getHitPoints(1));
        assertFalse(b2.getHitPoints(2));
        assertEquals(49, model.getPlayer1Score());
    }

    @Test
    public void testBombBulletNotEnoughPoints2() {
        BattleshipGame model = new BattleshipGame();
        model.addShip(0, 0, Orientation.HORIZONTAL, 5, true);
        model.addShip(0, 0, Orientation.HORIZONTAL, 5, false);
        model.setPlayer1Score(49);
        model.switchBulletMode(BulletMode.BOMB_MODE, true);
        model.playTurn(0, 0);
        Hittable b1 = model.getPlayer2Cell(0, 0);
        Hittable b2 = model.getPlayer1Cell(0, 0);

        assertTrue(b1.getHitPoints(0));

        assertFalse(b1.getHitPoints(1));
        assertFalse(b1.getHitPoints(2));
        assertFalse(b2.getHitPoints(0));
        assertFalse(b2.getHitPoints(1));
        assertFalse(b2.getHitPoints(2));
        assertEquals(49 + b1.getRewardPoints(), model.getPlayer1Score());
    }

}
