package byow.Core;

import byow.TileEngine.TETile;

public interface GameCharacter {
    Position getPosition();

    int getStartX();

    int getStartY();

    void addOnMap(TETile[][] world, Position p, TETile type);

    void move(TETile[][] world, Position o, Position n, TETile t, TETile type);

    default Position moveUp(TETile[][] world, Position playerPos) {
        Position n = new Position(getStartX(), getStartY() + 1);
        move(world, playerPos, n, world[getStartX()][getStartY() + 1],
                world[playerPos.x()][playerPos.y()]);
        return n;

    }

    default Position moveDown(TETile[][] world, Position playerPos) {
        Position n = new Position(getStartX(), getStartY() - 1);
        move(world, playerPos, n, world[getStartX()][getStartY() - 1],
                world[playerPos.x()][playerPos.y()]);
        return n;
    }

    default Position moveRight(TETile[][] world, Position playerPos) {
        Position n = new Position(getStartX() + 1, getStartY());
        move(world, playerPos, n, world[getStartX() + 1][getStartY()],
                world[playerPos.x()][playerPos.y()]);
        return n;

    }

    default Position moveLeft(TETile[][] world, Position playerPos) {
        Position n = new Position(getStartX() - 1, getStartY());
        move(world, playerPos, n, world[getStartX() - 1][getStartY()],
                world[playerPos.x()][playerPos.y()]);
        return n;

    }
}
