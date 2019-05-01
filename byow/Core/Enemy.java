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
        this.startX = p.x();
        this.startY = p.y();
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
    public void addOnMap(TETile[][] world, Position p) {
        world[p.x()][p.y()] = Tileset.FLOWER;
        this.p = p;
    }

    @Override
    public void move(TETile[][] world, Position o, Position n, TETile t) {
        world[o.x()][o.y()] = Tileset.FLOOR;
        world[n.x()][n.y()] = Tileset.FLOWER;
        this.p = n;
        if (t.equals(Tileset.FLOWER)) {
            world[n.x()][n.y()] = Tileset.AVATAR;
        }
    }


}
