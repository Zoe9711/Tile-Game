package byow.Core;

import byow.Core.GameCharacter;
import byow.Core.Position;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.io.Serializable;

public class Portal implements Serializable, GameCharacter {
    private Position p;
    private int startX;
    private int startY;

    public Portal(Position p) {
        this.p = p;
    }

    @Override
    public Position getPosition() {
        return this.p;
    }

    public void setNewPosition(Position n) {
        this.p = n;
        this.startX = n.x();
        this.startY = n.y();
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
        world[pn.x()][pn.y()] = Tileset.LOCKED_DOOR;
        this.p = pn;
        this.startX = pn.x();
        this.startY = pn.y();
    }

    @Override
    public void move(TETile[][] world, Position o, Position n, TETile t, TETile type) {

    }

    @Override
    public boolean equals(Object o) {
        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }

        Portal c = (Portal) o;

        return this.getPosition().equals(c.getPosition());
    }


}