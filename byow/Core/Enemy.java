package byow.Core;


import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.io.Serializable;

public class Enemy implements Serializable, GameCharacter {
    private Position p;
    private int startX;
    private int startY;

    public Enemy(Position p) {
        this.p = p;
    }

    @Override
    public Position getPosition() {
        return this.p;
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
    public void addOnMap(TETile[][] world, Position pn, TETile type) {
        world[pn.x()][pn.y()] = type;
        this.p = pn;
        this.startX = pn.x();
        this.startY = pn.y();
    }

    @Override
    public void move(TETile[][] world, Position o, Position n, TETile t, TETile type) {
        world[o.x()][o.y()] = Tileset.NOTHING;
        world[n.x()][n.y()] = Tileset.FLOWER;
        this.p = n;
        this.startX = n.x();
        this.startY = n.y();
//        if (t.equals(Tileset.FLOWER)) {
//            world[n.x()][n.y()] = Tileset.AVATAR;
//        }
    }


}
