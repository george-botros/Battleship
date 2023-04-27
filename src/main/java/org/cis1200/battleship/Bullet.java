package org.cis1200.battleship;

public interface Bullet {

    // Command to shoot a bullet. Returns new userPoints value after decrement of
    // cost of bullet.
    public int fire(int r, int c, int userPoints, Hittable[][] currBoard);

    // Cost of a bullet.
    public int getCost();
}
