package org.cis1200.battleship;

public class StandardBullet implements Bullet {

    /**
     * fire calls a cell's hit method.
     *
     * @param r         row selected by user
     * @param c         column selected by user
     * @param currBoard the board that the user is playing on
     */
    public int fire(int r, int c, int userPoints, Hittable[][] currBoard) {
        userPoints += currBoard[r][c].hit(r, c);
        return userPoints;
    }

    public int getCost() {
        return 0;
    }
}
