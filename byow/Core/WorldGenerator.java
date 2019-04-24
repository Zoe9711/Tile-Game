package byow.Core;

import byow.TileEngine.TETile;
import java.util.List;
import java.util.HashMap;
import java.util.Random;

public class WorldGenerator {
    private int width;
    private int height;
    private int seed;
    private TETile[][] world;
    private List<Room> roomList;
    private HashMap<Integer, Room> roomMap;
    private List<Hallway> hallwayList;
    RoomGenerator roomGenerator = new RoomGenerator();
    HallwayGenerator hallGenerator = new HallwayGenerator();

    public WorldGenerator(int w, int h, int seed) {
        this.width = w;
        this.height = h;
        this.seed = seed;
        this.world = new TETile[width][height];
    }

    public TETile[][] getTeTile() {
        return this.world;
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

    public void addRooms(int NumOfRoom) {
        Random random = new Random(seed());
        for (int i = 0; i < NumOfRoom; i++) {
            int w = random.nextInt(8) + 1;
            int h = random.nextInt(8) + 1;
            int posX = random.nextInt(width() - w);
            int posY = random.nextInt(height() - h);
            roomGenerator.addRoom(this.world, new Position(posX, posY), w, h);
            this.roomList = roomGenerator.getRoomList();
            this.roomMap = roomGenerator.getMap();
        }
    }

    public List<Room> roomList() {
        return this.roomList;
    }

    public void addHallways() {
        List<Room> newList = roomGenerator.sortedList();
        for (int i = 0; i < newList.size() - 1; i++) {
            List<Hallway> twoHalls = hallGenerator.addHallwayPath(this.world, newList.get(i), newList.get(i + 1));
            hallwayList.add(twoHalls.get(0));
            hallwayList.add(twoHalls.get(1));
        }
    }

}
