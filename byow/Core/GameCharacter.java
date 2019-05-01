package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public interface GameCharacter {
    public Position getPosition();

    public int getStartX();

    public int getStartY();

    public void addOnMap(TETile[][] world, Position p);

    public void move(TETile[][] world, Position o, Position n);

    default Position moveUp(TETile[][] world, Position playerPos) {
        Position n = new Position(getStartX(), getStartY() + 1);
        move(world, playerPos, n);
        return n;

    }

    default Position moveDown(TETile[][] world, Position playerPos) {
        Position n = new Position(getStartX(), getStartY() - 1);
        move(world, playerPos, n);
        return n;
    }

    default Position moveRight(TETile[][] world, Position playerPos) {
        Position n = new Position(getStartX() + 1, getStartY());
        move(world, playerPos, n);
        return n;

    }

    default Position moveLeft(TETile[][] world, Position playerPos) {
        Position n = new Position(getStartX() - 1, getStartY());
        move(world, playerPos, n);
        return n;

    }

}