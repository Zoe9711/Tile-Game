package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.io.Serializable;

public class Player implements Serializable, GameCharacter {
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

    @Override
    public Position getPosition() {
        return this.p;
    }


    public void setNewPosition(Position np) {
        this.p = np;
        this.startX = np.x();
        this.startY = np.y();
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
    public void addOnMap(TETile[][] world, Position p) {
        world[p.x()][p.y()] = Tileset.AVATAR;
        this.p = p;
    }

    @Override
    public void move(TETile[][] world, Position o, Position n, TETile t) {
        if (t.equals(Tileset.FLOOR)) {
            world[o.x()][o.y()] = Tileset.FLOOR;
            world[n.x()][n.y()] = Tileset.AVATAR;
        }

        if (t.equals(Tileset.FLOWER)) {
            world[o.x()][o.y()] = Tileset.FLOOR;
            world[n.x()][n.y()] = Tileset.FLOWER;
        }
        this.p = n;
    }


}
