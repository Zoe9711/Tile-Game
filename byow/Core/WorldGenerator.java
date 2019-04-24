package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.*;

public class WorldGenerator {
    private int width;
    private int height;
    private int seed;
    private TETile[][] world;
    private ArrayList<Room> roomList;
    private HashMap<Integer, Room> roomMap;
    private List<Hallway> hallwayList;
    private RoomGenerator roomGenerator = new RoomGenerator();
    private HallwayGenerator hallGenerator = new HallwayGenerator();

    public WorldGenerator(int w, int h, int seed) {
        this.width = w;
        this.height = h;
        this.seed = seed;
        this.roomMap = new HashMap<>();
        this.hallwayList = new LinkedList<>();

        this.world = new TETile[width][height];
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
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
            int w = random.nextInt(8) + 3;
            int h = random.nextInt(8) + 3;
            int posX = random.nextInt(width() - w);
            int posY = random.nextInt(height() - h);
            this.roomGenerator.addRoom(this.world, new Position(posX, posY), w, h);
        }
        this.roomList = roomGenerator.getRoomList();
        this.roomMap = roomGenerator.getMap();
    }

    public void addHallways() {
        //List<Room> newList = roomGenerator.sortedList(); //fix this - Null pointer Exception
        ArrayList<Room> newList = roomGenerator.sortedList(); //Alternate List for now.
        for (int i = 0; i < newList.size() - 1; i++) {
            hallGenerator.addHallwayPath(this.world, newList.get(i), newList.get(i + 1));
        }
        this.hallwayList = hallGenerator.getHallwayList();
    }

    public void cleanAndFill() {
        roomGenerator.fillAll(this.world);

        hallGenerator.fillAll(this.world); //NOTE: IMPLEMENT THIS IN HALLWAYGENERATOR CLASS
    }

}
