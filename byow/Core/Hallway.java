package byow.Core;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class Hallway {
    private Position start; //left or bottom of hall: one pixel away from the last wall
    private Position end; //right or top of hall: one pixel away from the last wall
    private int length; //does not include start and end points
    private boolean isHorizontal; //vertical or horizontal hallway

    public Hallway(Position s, Position e, int l, boolean h) {
        this.start = s;
        this.end = e;
        this.length = l;
        this.isHorizontal = h;
    }

    public Position start() {
        return this.start;
    }

    public Position end() {
        return this.end;
    }

    public int length() {
        return this.length;
    }

    public boolean isHorizontal() {
        return this.isHorizontal;
    }

    public void addHallway(TETile[][] world) {
        //NOTE: UNCOMMENTING BELOW--sometimes--RESULTS IN BLANK WORLD (idk why lmao, plz send help)
        if (isHorizontal) {
            for (int i = start.x(); i < end.x(); i++) {
                world[i][start.y() + 1] = Tileset.WALL;
                world[i][start.y() - 1] = Tileset.WALL;
            }
        } else {
            for (int i = start.y(); i < end.y(); i++) {
                world[start.x() + 1][i] = Tileset.WALL;
                world[start.x() - 1][i] = Tileset.WALL;
            }
        }
    }

}

    //Ignore for now.
//    /**
//     * Finds which room is the start room to make the "upside-down L shaped pathway";
//     * returns the higher room.
//     *
//     * Returns
//     *
//     * 0: a is the start, and on the left.
//     * 1: a is the start, and on the right.
//     * 2: b is the start, and on the left.
//     * 3: b is the start, and on the right.
//     *
//     * Note: "a" should be "start", "b" should be "end" for consistency.
//     * */
//    private int starterRoom(Room a, Room b) {
//        return 0;
//    }

