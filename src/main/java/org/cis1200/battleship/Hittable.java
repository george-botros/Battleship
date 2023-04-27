package org.cis1200.battleship;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class Hittable {

    private int objectRow;
    private int objectCol;
    private int length;
    private boolean[] hitPoints;
    private Orientation orientation;
    private boolean isSunk;

    public Hittable(int length) {
        this.length = length;
        this.hitPoints = new boolean[length];
        this.orientation = Orientation.HORIZONTAL;
        this.isSunk = false;
    }

    public int getObjectRow() {
        return objectRow;
    }

    public int getObjectCol() {
        return objectCol;
    }

    public int getLength() {
        return length;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public boolean getHitPoints(int index) {
        return hitPoints[index];
    }

    public boolean isSunk() {
        return isSunk;
    }

    public void setRow(int objectRow) {
        this.objectRow = objectRow;
    }

    public void setCol(int objectCol) {
        this.objectCol = objectCol;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int hit(int r, int c) {
        int index = c - objectCol;
        if (orientation == Orientation.VERTICAL) {
            index = r - objectRow;
        }

        if (hitPoints[index]) {
            return 0;
        } else {
            hitPoints[index] = true;
            updateIsSunk();
            return getRewardPoints();
        }
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    private void updateIsSunk() {
        if (isSunk) {
            return;
        } else {
            isSunk = true;
        }

        for (Boolean shot : hitPoints) {
            if (!shot) {
                isSunk = false;
                return;
            }
        }
    }

    public abstract int getRewardPoints();

    public String toString(int r, int c) {
        int index = c - objectCol;
        if (orientation == Orientation.VERTICAL) {
            index = r - objectRow;
        }

        if (isSunk) {
            return "S";
        } else if (hitPoints[index]) {
            return "H";
        } else {
            return "-";
        }
    }

    public abstract String getFileName();

    public void draw(int x, int y, int r, int c, Graphics g) {

        // cell hit condition draws fire
        if (toString(r, c).equals("H")) {
            BufferedImage img = null;
            try {
                img = ImageIO.read(new File("files/fire2.png"));
            } catch (IOException e) {
                System.out.println("Internal Error:" + e.getMessage());
            }

            g.drawImage(img, x, y, 45, 45, null);

            // ship sunk condition
        } else if (toString(r, c).equals("S")) {

            BufferedImage img = null;
            try {
                img = ImageIO.read(new File(getFileName()));
            } catch (IOException e) {
                System.out.println("Internal Error:" + e.getMessage());
            }

            /*
             * There is inefficiency here, since it draws the image a getLength() amount
             * of times with each image above the last, instead of just once at the head and
             * then
             * skipping. This could be improved (but with possibly more complex and less
             * readable
             * code) by checking if it's sunk, and then checking if the i, j is its
             * objectCol and
             * objectRow.
             */
            if (getOrientation() == Orientation.HORIZONTAL) {
                g.drawImage(img, getObjectCol() * 50, y, 50 * getLength(), 45, null);
            } else {
                g.drawImage(img, x, getObjectRow() * 50, 45, 50 * getLength(), null);
            }
        }
    }
}
