package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import java.util.Random;

public class Room {
    private Position p; //upper left corner of the room
    private int width;
    private int height;

    public Room(Position p, int w, int h) {
        this.p = p;
        this.width = w;
        this.height = h;
    }

    public Position p() {
        return this.p;
    }

    public int h() {
        return this.height;
    }

    public int w() {
        return this.width;
    }

    public void addRoom(TETile[][] world, Position p, int w, int h) {

    }

    public void roomFloor(TETile[][] world, Room r) {

    }
}
