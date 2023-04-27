package org.cis1200.battleship;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class EmptyTile extends Hittable {

    public EmptyTile() {
        super(1);
    }

    public EmptyTile(int length) {
        this();
    }

    public int getRewardPoints() {
        return 0;
    }

    @Override
    public String toString(int r, int c) {
        if (getHitPoints(0)) {
            return "M";
        }

        return "-";
    }

    @Override
    public String getFileName() {
        return "water.jpg";
    }

    @Override
    public boolean isSunk() {
        return true;
    }

    @Override
    public void draw(int x, int y, int r, int c, Graphics g) {
        if (toString(r, c).equals("M")) {

            BufferedImage img = null;
            try {
                img = ImageIO.read(new File("files/water.jpg"));
            } catch (IOException e) {
                System.out.println("Internal Error:" + e.getMessage());
            }
            // The getObjectCol() / 3 accounts for the separation lines
            g.drawImage(img, x + getObjectCol() / 3, y + getObjectRow() / 3, 45, 45, null);
        }
    }
}
