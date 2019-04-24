package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.Random;

public class Room {
    private Position p; //bottom left corner of the room, wall
    private int width;  //including the wall
    private int height; //including the wall
    private int maxSize; // height/width = 6

    public Room(Position p, int w, int h) {
        this.p = p;
        this.width = w;
        this.height = h;
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


    public void addRoom(TETile[][] world, Position p, int w, int h) {
        for (int i = 0; i < w; i++ ) {
            world[p.x() + i][p.y()] = Tileset.WALL;
            world[p.x() + i][p.y() + h - 1] = Tileset.WALL;
        }

        for (int j = 0; j < h; j++) {
            world[p.x()][p.y() + j] = Tileset.WALL;
            world[p.x() + w - 1][p.y() + j] = Tileset.WALL;
        }
    }

    private ArrayList<Position> roomSpace(Room r) {
        ArrayList<Position> space = new ArrayList<>();
        for (int i = 1; i < r.width - 1; i++) {
            for (int j = 1; j < r.height - 1; j++) {
                space.add(new Position(r.p.x() + i, r.p.y() + j));
            }
        }
        return space;
    }

    public Position ranPosInRoom(Room r) {
        Random random = new Random();
        int ranX = random.nextInt(r.width - 2) + r.p.x() + 1;
        int ranY = random.nextInt(r.height - 2) + r.p.y() + 1;
        return new Position(ranX, ranY);
    }

    public ArrayList<Room> sortedList(ArrayList<Room> list) {
        ArrayList<Room> newList = new ArrayList<>();

        return newList;
    }

    public void fillAll(TETile[][] world, ArrayList<Room> roomList) {
        for (Room r : roomList) {
            for (Position p : roomSpace(r)) {
                world[p.x()][p.y()] = Tileset.FLOOR;
            }
        }
    }

}
