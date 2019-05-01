package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.io.Serializable;

public class Player implements Serializable {
    private Position p;
    private int startX;
    private int startY;

    public Player(Position p) {
        this.p = p;
        this.startX = p.x();
        this.startY = p.y();
//        this.endX = startX + width + 3;
//        this.startY = p.y() + 1;
//        this.endY = startY + height - 3;;
    }

    public Position getPosition() {
        return this.p;
    }

    public int getStartX() {
        return this.startX;
    }

    public int getStartY() {
        return this.startY;
    }

    public void addPlayer(TETile[][] world, Position p) {
        world[p.x()][p.y()] = Tileset.AVATAR;
        this.p = p;
    }

    public void move(TETile[][] world, Position o, Position n) {
        world[o.x()][o.y()] = Tileset.FLOOR;
        world[n.x()][n.y()] = Tileset.AVATAR;
        this.p = n;
    }


}
