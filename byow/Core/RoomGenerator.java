package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.*;

public class RoomGenerator {
    private List<Room> roomList; //list of hallways for cleaning
    private HashMap<Integer, Room> roomMap;

    public RoomGenerator() {
        this.roomList = new LinkedList<>();
        this.roomMap = new HashMap<>();
    }

    public HashMap<Integer, Room> getMap() {
        return this.roomMap;
    }

    /** Returns list of rooms generated. */
    public List<Room> getRoomList() {
        return this.roomList;
    }

    public void addRoom(TETile[][] world, Position p, int w, int h) {
        Room roomToAdd = new Room(p, w, h);
        this.roomMap.put(roomToAdd.getStartX(), roomToAdd);
        this.roomList.add(roomToAdd);
        roomToAdd.addRoom(world, p, w, h);
    }

    public List<Room> sortedList() {
        ArrayList<Room> newList = new ArrayList<>();
        ArrayList<Integer> listX = new ArrayList<>();
        for (Room r : this.roomList) {
            listX.add(r.getStartX());
        }
        while (listX.size() != 0) {
            int smallEst = minIndex(listX);
            newList.add(this.roomMap.get(smallEst));
            listX.remove(smallEst);
        }
        return newList;
    }

    private ArrayList<Position> roomSpace(Room r) {
        ArrayList<Position> space = new ArrayList<>();
        for (int i = 1; i < r.getWidth() - 1; i++) {
            for (int j = 1; j < r.getHeight() - 1; j++) {
                space.add(new Position(r.getPosition().x() + i, r.getPosition().y() + j));
            }
        }
        return space;
    }

    public void fillAll(TETile[][] world) {
        for (Room r : this.roomList) {
            for (Position p : roomSpace(r)) {
                world[p.x()][p.y()] = Tileset.FLOOR;
            }
        }
    }

    private static int minIndex(List<Integer> listX) {
        return listX.indexOf(Collections.min(listX));
    }
}
