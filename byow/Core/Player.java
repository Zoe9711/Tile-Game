package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.io.Serializable;

public class Player implements Serializable, GameCharacter {
    private Position p;
    private int startX;
    private int startY;
    private TETile type;
    private Position prevPos;

    public Player(Position p) {
        this.p = p;
        this.startX = p.x();
        this.startY = p.y();
        this.prevPos = p;
//        this.endX = startX + width + 3;
//        this.startY = p.y() + 1;
//        this.endY = startY + height - 3;
    }

    @Override
    public Position getPosition() {
        return this.p;
    }


    public void setNewPosition(Position np) {
        this.p = np;
        this.startX = np.x();
        this.startY = np.y();
    }

    public Position getPrevPos() {
        return this.prevPos;
    }

    public void setPrevPos(Position np) {
        this.prevPos = np;
    }

    @Override
    public int getStartX() {
        return this.startX;
    }

    @Override
    public int getStartY() {
        return this.startY;
    }

    @Override
    public void addOnMap(TETile[][] world, Position pn, TETile aType) {
        world[pn.x()][pn.y()] = aType;
        this.p = pn;
    }

    @Override
    public void move(TETile[][] world, Position o, Position n, TETile t, TETile aType) {
        if (t.equals(Tileset.FLOOR)) {
            world[o.x()][o.y()] = Tileset.FLOOR;
            world[n.x()][n.y()] = aType;
        }

        if (t.equals(Tileset.FLOWER)) {
            world[o.x()][o.y()] = Tileset.FLOOR;
            world[n.x()][n.y()] = Tileset.FLOWER;
        }

        this.p = n;
        this.startX = n.x();
        this.startY = n.y();
    }
}
