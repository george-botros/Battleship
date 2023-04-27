package org.cis1200.battleship;

public class Submarine extends Hittable {
    public Submarine() {
        super(4);
    }

    public int getRewardPoints() {
        return 30;
    }

    @Override
    public String getFileName() {
        if (getOrientation() == Orientation.HORIZONTAL) {
            return "files/submarine_horizontal.png";
        }
        return "files/submarine_vertical.png";
    }
}
