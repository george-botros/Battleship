package org.cis1200.battleship;

public class Battleship extends Hittable {

    public Battleship() {
        super(6);
    }

    public int getRewardPoints() {
        return 20;
    }

    @Override
    public String getFileName() {
        if (getOrientation() == Orientation.HORIZONTAL) {
            return "files/battleship_horizontal.png";
        }
        return "files/battleship_vertical.png";
    }
}
