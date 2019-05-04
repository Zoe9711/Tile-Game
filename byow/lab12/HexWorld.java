package byow.lab12;
import org.junit.Test;
import static org.junit.Assert.*;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

import byow.Core.Position;



/**
 * Draws a world consisting of hexagonal regions.
 * @source Josh Hug
 */
public class HexWorld {

/*

    public static int rowWidth(int s, int i) {
        int effectiveI = i;
        if (i >= s) {
            effectiveI = 2 * s - 1 - effectiveI;
        }
        return s + 2 * effectiveI;
    }

    public static int rowOffset(int s, int i) {
        int effectiveI = i;
        if (i >= s) {
            effectiveI = 2 * s - 1 - effectiveI;
        }
        return -effectiveI;
    }


    sample hexagon
           xxxx     s = 4
          xxxxxx
         xxxxxxxx
        xxxxxxxxxx  i = 4
        xxxxxxxxxx  i = 3
         xxxxxxxx   i = 2
          xxxxxx    i = 1
           xxxx     i = 0


    public static void addHexagon(TETile[][] world, Position p, int s, TETile t) {
        if (s < 2) {
            throw new IllegalArgumentException("Hexagon must be at least size 2.");
        }

        for (int rowNum = 0; rowNum < 2 * s; rowNum++) {
            int rowY = p.y() + rowNum;
            int rowX = p.x() + rowOffset(s, rowNum);
            Position rowStartP = new Position(rowX, rowY);
            int rowWidth = rowWidth(s, rowNum);
            addRow(world, rowStartP, rowWidth, t);
        }
    }

    private void addRow(TETile[][] world, Position p, int width, TETile t) {
        for (int tileNum = 0; tileNum < width; tileNum += 1) {
            int xCoord = p.x() + tileNum;
            int yCoord = p.y();
            Random RANDOM = new Random(100);
            world[xCoord][yCoord] = TETile.colorVariant(t, 32, 32, 32, RANDOM);
        }
    }
*/

}
