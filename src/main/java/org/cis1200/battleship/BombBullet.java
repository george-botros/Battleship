package org.cis1200.battleship;

public class BombBullet implements Bullet {
    private static final int COST = 50;

    /**
     * fire calls the hit method on a 3x3 area, centered at {@param r} and
     * {@param c}.
     *
     * @param r         row selected by user
     * @param c         column selected by user
     * @param currBoard the board that the user is playing on
     */
    public int fire(int r, int c, int userPoints, Hittable[][] currBoard) {
        if (userPoints >= COST) {
            userPoints = userPoints - COST;

            for (int i = r - 1; i <= r + 1; i++) {

                for (int j = c - 1; j <= c + 1; j++) {
                    if (i >= 0 && j >= 0 && i < currBoard.length && j < currBoard.length) {
                        userPoints += currBoard[i][j].hit(i, j);
                    }
                }
            }
        }

        return userPoints;
    }

    public int getCost() {
        return COST;
    }

}
