package byow.Core;

import byow.TileEngine.TETile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class WorldGenerator {
    private int width;
    private int height;
    private int seed;
    private TETile[][] world;
    private ArrayList<Room> roomList;
    private HashMap<Integer, Room> roomMap;
    private ArrayList<Hallway> hallwayList;

    public WorldGenerator(int w, int h, int seed) {

        this.width = w;
        this.height = h;
        this.seed = seed;
        this.world = new TETile[width][height];
    }

    public int width() {
        return this.width;
    }

    public int height() {
        return this.height;
    }

    private int seed() {
        return this.seed;
    }

    public void addRoom(int NumOfRoom) {
        Random random = new Random(seed());
        for (int i = 0; i < NumOfRoom; i++) {
            int w = random.nextInt(8) + 1;
            int h = random.nextInt(8) + 1;
            int posX = random.nextInt(width() - w);
            int posY = random.nextInt(height() - h);
            Room.addRoom(this.world, new Position(posX, posY), w, h, this.roomList, this.roomMap);
        }

    }

    public ArrayList<Room> roomList() {
        return this.roomList;
    }

    public void addHallway() {
        ArrayList<Room> newList = Room.sortedList(this.roomList, this.roomMap);
        for (int i = 0; i < newList.size() - 1; i++) {
            HallwayGenerator.addHallwayPath(this.world, newList.get(i), newList.get(i + 1), this.hallwayList);
        }
    }

}
