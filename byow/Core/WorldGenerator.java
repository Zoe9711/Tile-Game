package byow.Core;

import byow.TileEngine.TETile;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class WorldGenerator {
    private int width;
    private int height;
    private int seed;
    private ArrayList<Room> roomList;

    public WorldGenerator(int w, int h, int seed) {
        this.width = w;
        this.height = h;
        this.seed = seed;
    }

    public int width() {
        return this.width;
    }

    public int height() {
        return this.height;
    }

    public int seed() {
        return this.seed;
    }

    public void addRoom(TETile[][] world, int NumOfRoom) {
        Random random = new Random(seed());

    }

    public void addHallWays(TETile[][] world, Room room1, Room room2) {

        //randomly selecting two positions in the two rooms respectively
        Position r1 = null;
        Position r2 = null;

        //draw horizontal hallway first?
        Position startH = null; //the smaller x coordinate
        Position endH = null; //the larger x coordinate
        //hallway.addhorizontalhallway(world, start, end)

        //draw vertical
        Position startV = null; //the smaller y coordinate
        Position endV = null; //the larger y coordinate
        //hallway.addverticalhallway(world, start, end)

        //add both to the list of hallways

    }


}
