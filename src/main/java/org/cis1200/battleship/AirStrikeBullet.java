package org.cis1200.battleship;

public class AirStrikeBullet implements Bullet {

    private static final int COST = 100;

    /**
     * fire calls a column of cell's hit method.
     *
     * @param r         row selected by user
     * @param c         column selected by user
     * @param currBoard the board that the user is playing on
     */
    public int fire(int r, int c, int userPoints, Hittable[][] currBoard) {
        if (userPoints >= COST) {
            userPoints = userPoints - COST;
            for (int i = currBoard.length - 1; i >= 0; i--) {
                userPoints += currBoard[i][c].hit(i, c);
            }
        }

        return userPoints;
    }

    public int getCost() {
        return COST;
    }
}
