package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.io.Serializable;
import java.util.Random;

public class Room implements Comparable, Serializable {
    private Position p; //bottom left corner of the room, wall
    private int width;  //including the wall
    private int height; //including the wall
    private int maxSize; // height/width = 8

//    //Room (x,y) ranges, not including wall
    private int startX;
    private int startY;
    private int id;
    private int endX;
    private int endY;

    public Room(Position p, int w, int h, int id) {
        this.p = p;
        this.width = w;
        this.height = h;
        this.id = id;

        this.startX = p.x();
        this.startY = p.y();
        this.endX = startX + width + 3;
        this.startY = p.y() + 1;
        this.endY = startY + height - 3;;
    }

    public int getId() {
        return this.id;
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

    public void addRoom(TETile[][] world, int w, int h) {
        for (int i = 0; i < w; i++) {
            world[p.x() + i][p.y()] = Tileset.WALL;
            world[p.x() + i][p.y() + h - 1] = Tileset.WALL;
        }

        for (int j = 0; j < h; j++) {
            world[p.x()][p.y() + j] = Tileset.WALL;
            world[p.x() + w - 1][p.y() + j] = Tileset.WALL;
        }
    }

    public Position ranPosInRoom(Random random) {
        int ranX = random.nextInt(this.getWidth() - 3) + this.getPosition().x() + 1;
        int ranY = random.nextInt(this.getHeight() - 3) + this.getPosition().y() + 1;
        return new Position(ranX, ranY);
    }

    @Override
    public int compareTo(Object room) {
        if (room.getClass().equals(this.getClass())) {
            return this.startX - ((Room) room).startX;
        } else {
            return 0;
        }
    }

}
