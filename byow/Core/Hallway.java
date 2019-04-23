package byow.Core;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;

public class Hallway {
    private Position start;
    private Position end;
    private int length;

    public Hallway(Position s, Position e, int l) {
        this.start = s;
        this.end = e;
        this.length = l;
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

    public void addHallway() {

    }

}
