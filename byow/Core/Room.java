package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.HashMap;


public class Room {
    private HashMap<Integer, Room> roomMap = new HashMap<>();
    private Position p; //bottom left corner of the room, wall
    private int width;  //including the wall
    private int height; //including the wall
    private int maxSize; // height/width = 6

    //Room (x,y) ranges, not including wall
    private int startX;
    private int startY;
    private int endX;
    private int endY;

    public Room(Position p, int w, int h) {
        this.p = p;
        this.width = w;
        this.height = h;

        this.startX = p.x() + 1;
        this.endX = startX + width + 3;
        this.startY = p.y() + 1;
        this.endY = startY + height - 3;;
    }

    public Position getPosition() {
        return this.p;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getStartX() {
        return this.startX;
    }

    public int getStartY() {
        return this.startY;
    }

    public int getEndX() {
        return this.endX;
    }

    public int getEndY() {
        return this.endY;
    }


    public void addRoom(TETile[][] world, Position p, int w, int h, ArrayList<Room> roomList, HashMap<Integer, Room> roomMap) {
        for (int i = 0; i < w; i++ ) {
            world[p.x() + i][p.y()] = Tileset.WALL;
            world[p.x() + i][p.y() + h - 1] = Tileset.WALL;
        }

        for (int j = 0; j < h; j++) {
            world[p.x()][p.y() + j] = Tileset.WALL;
            world[p.x() + w - 1][p.y() + j] = Tileset.WALL;
        }

        Room r = new Room(p, w, h);
        roomList.add(r);

        roomMap.put(r.getStartX(), r);
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

    public Position ranPosInRoom(Room r) {
        Random random = new Random();
        int ranX = random.nextInt(r.getWidth() - 2) + r.getPosition().x() + 1;
        int ranY = random.nextInt(r.getHeight() - 2) + r.getPosition().y() + 1;
        return new Position(ranX, ranY);
    }

    public ArrayList<Room> sortedList(ArrayList<Room> roomList) {
        ArrayList<Room> newList = new ArrayList<>();
        ArrayList<Integer> listX = new ArrayList<>();
        for (Room r : roomList) {
            listX.add(r.getStartX());
        }
        while (listX.size() != 0) {
            int smallEst = minIndex(listX);
            newList.add(roomMap.get(smallEst));
            listX.remove(smallEst);
        }
        return newList;
    }

    private int minIndex(ArrayList<Integer> listX) {
        return listX.indexOf(Collections.min(listX));
    }

    public void fillAll(TETile[][] world, ArrayList<Room> roomList) {
        for (Room r : roomList) {
            for (Position p : roomSpace(r)) {
                world[p.x()][p.y()] = Tileset.FLOOR;
            }
        }
    }

}
