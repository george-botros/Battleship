package org.cis1200.battleship;

public class Cruiser extends Hittable {
    public Cruiser() {
        super(5);
    }

    public int getRewardPoints() {
        return 25;
    }

    @Override
    public String getFileName() {
        if (getOrientation() == Orientation.HORIZONTAL) {
            return "files/cruiser_horizontal.png";
        }
        return "files/cruiser_vertical.png";
    }
}
