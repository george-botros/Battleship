package org.cis1200.battleship;

public class Destroyer extends Hittable {
    public Destroyer() {
        super(4);
    }

    public int getRewardPoints() {
        return 30;
    }

    @Override
    public String getFileName() {
        if (getOrientation() == Orientation.HORIZONTAL) {
            return "files/destroyer_horizontal.png";
        }
        return "files/destroyer_vertical.png";
    }
}
