package org.cis1200.battleship;

public class AircraftCarrier extends Hittable {

    public AircraftCarrier() {
        super(5);
    }

    @Override
    public int getRewardPoints() {
        return 25;
    }

    @Override
    public String getFileName() {
        if (getOrientation() == Orientation.HORIZONTAL) {
            return "files/aircraft_carrier_horizontal.png";
        }
        return "files/aircraft_carrier_vertical.png";
    }
}
