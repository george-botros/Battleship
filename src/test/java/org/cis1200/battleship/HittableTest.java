package org.cis1200.battleship;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class HittableTest {

    // hit test
    @Test
    public void testHit1() {
        Boat b = new Boat();
        b.setRow(0);
        b.setCol(0);
        b.setOrientation(Orientation.HORIZONTAL);
        b.hit(0, 0);
        assertTrue(b.getHitPoints(0));
        assertFalse(b.getHitPoints(1));
        assertFalse(b.getHitPoints(2));
    }

    @Test
    public void testHit2() {
        Boat b = new Boat();
        b.setRow(0);
        b.setCol(0);
        b.setOrientation(Orientation.HORIZONTAL);
        b.hit(0, 1);
        assertFalse(b.getHitPoints(0));
        assertTrue(b.getHitPoints(1));
        assertFalse(b.getHitPoints(2));
    }

    @Test
    public void testHit3() {
        Boat b = new Boat();
        b.setRow(0);
        b.setCol(0);
        b.setOrientation(Orientation.HORIZONTAL);
        b.hit(0, 2);
        assertFalse(b.getHitPoints(0));
        assertFalse(b.getHitPoints(1));
        assertTrue(b.getHitPoints(2));
    }

    @Test
    public void testHit4() {
        Boat b = new Boat();
        b.setRow(0);
        b.setCol(0);
        b.setOrientation(Orientation.VERTICAL);
        b.hit(0, 0);
        assertTrue(b.getHitPoints(0));
        assertFalse(b.getHitPoints(1));
        assertFalse(b.getHitPoints(2));
    }

    @Test
    public void testHit5() {
        Boat b = new Boat();
        b.setRow(0);
        b.setCol(0);
        b.setOrientation(Orientation.VERTICAL);
        b.hit(1, 0);
        assertFalse(b.getHitPoints(0));
        assertTrue(b.getHitPoints(1));
        assertFalse(b.getHitPoints(2));
    }

    @Test
    public void testHit6() {
        Boat b = new Boat();
        b.setRow(0);
        b.setCol(0);
        b.setOrientation(Orientation.VERTICAL);
        b.hit(2, 0);
        assertFalse(b.getHitPoints(0));
        assertFalse(b.getHitPoints(1));
        assertTrue(b.getHitPoints(2));
    }

    @Test
    public void testHitOnEmptyTile() {
        EmptyTile b = new EmptyTile();
        b.setRow(0);
        b.setCol(0);
        b.hit(0, 0);
        assertTrue(b.getHitPoints(0));
    }

    // isSunk Tests
    @Test
    public void testEmptyTileIsSunk() {
        EmptyTile b = new EmptyTile();
        b.setRow(0);
        b.setCol(0);
        assertTrue(b.isSunk());
    }

    @Test
    public void testIsSunk1() {
        Boat b = new Boat();
        b.setRow(0);
        b.setCol(0);
        b.setOrientation(Orientation.VERTICAL);
        b.hit(0, 0);
        assertFalse(b.isSunk());
        b.hit(1, 0);
        assertFalse(b.isSunk());
        b.hit(2, 0);
        assertTrue(b.isSunk());
    }

    @Test
    public void testIsSunk2() {
        Boat b = new Boat();
        b.setRow(0);
        b.setCol(0);
        b.setOrientation(Orientation.HORIZONTAL);
        b.hit(0, 0);
        assertFalse(b.isSunk());
        b.hit(0, 1);
        assertFalse(b.isSunk());
        b.hit(0, 2);
        assertTrue(b.isSunk());
    }

    // toString Tests
    @Test
    public void testToStringOnShip() {
        Boat b = new Boat();
        b.setRow(0);
        b.setCol(0);
        b.setOrientation(Orientation.HORIZONTAL);
        assertEquals("-", b.toString(0, 0));
        assertEquals("-", b.toString(0, 1));
        assertEquals("-", b.toString(0, 2));
        b.hit(0, 0);
        assertEquals("H", b.toString(0, 0));
        assertEquals("-", b.toString(0, 1));
        assertEquals("-", b.toString(0, 2));
        b.hit(0, 1);
        assertEquals("H", b.toString(0, 0));
        assertEquals("H", b.toString(0, 1));
        assertEquals("-", b.toString(0, 2));
        b.hit(0, 2);
        assertTrue(b.isSunk());
        assertEquals("S", b.toString(0, 0));
        assertEquals("S", b.toString(0, 1));
        assertEquals("S", b.toString(0, 2));
    }

    @Test
    public void testToStringOnEmptyTile() {
        EmptyTile b = new EmptyTile();
        b.setRow(0);
        b.setCol(0);
        assertEquals("-", b.toString(0, 0));
        b.hit(0, 0);
        assertEquals("M", b.toString(0, 0));
    }
}
