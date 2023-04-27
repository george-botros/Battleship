package org.cis1200.battleship;

public class Boat extends Hittable {
    public Boat() {
        super(3);
    }

    public int getRewardPoints() {
        return 40;
    }

    @Override
    public String getFileName() {
        if (getOrientation() == Orientation.HORIZONTAL) {
            return "files/boat_horizontal.png";
        }
        return "files/boat_vertical.png";
    }
}
