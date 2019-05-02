package byow.Core;
import java.io.Serializable;

public class Position implements Serializable {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }
    @Override
    public boolean equals(Object o) {
        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }

        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(o instanceof Position)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        Position c = (Position) o;

        return this.x == c.x && this.y == c.y;
    }

    public int x() {
        return this.x;
    }

    public int y() {
        return this.y;
    }

}
