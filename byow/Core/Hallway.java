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

    public void drawHallway(TETile[][] world) {
        for (int i = start.y(); i < start.y() - 2; i++) {
            for (int j = start.x(); j < length; i++) {

            }
        }
    }
}