package byow.Core;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;

public class Hallway {
    private Position start; //left or top of hall: one pixel away from the last wall
    private Position end; //right or bottom of hall: one pixel away from the last wall
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
        if (isHorizontal) {
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < length; i++) {
                    //Do stuff :/
                }
            }
        } else {
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < length; i++) {
                    //Do stuff :/
                }
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

