package byow.Core;

import byow.TileEngine.TETile;

import java.util.LinkedList;
import java.util.List;

public class HallwayGenerator {
    private List<Hallway> hallwayList; //list of hallways for cleaning

    public HallwayGenerator() {
        this.hallwayList = new LinkedList<>();
    }

    public List<Hallway> getHallwayList() {
        return this.hallwayList;
    }


    /**
     * Adds either ONE hallway for vertical/horizontal rooms, or TWO for diagonal rooms.
     *
     * Note 1: Diagonal hallways consist of two hallways for the cleaning process.
     * Note 2: Diagonal hallways will need to have an extra block added in the outer corner
     * of the two joint hallways. May be ignored when cleaning.
     * Note 3: This will only work if method is called iteratively on sorted list, due
     * to start and end Positions.
     * */
    public void addHallwayPath(TETile[][] world, Room start, Room end) {
        int roomOrientation = roomOrientation(start, end);

        if (roomOrientation == 0) { //horizontal rooms
            if (start.getStartX() < end.getStartX()) { //start room is on the left

            } else { //end room is on the left

            }
//            Hallway hallToAdd = new Hallway(startPos, endPos, length, true);
//            hallwayList.add(hallToAdd);
        } else if (roomOrientation == 1) { //vertical rooms

        } else { //diagonal rooms
            if (starterRoom(start, end) == 0) { //start is above

            } else { //end is above

            }
        }
    }

    /**
     * Returns
     *
     * 0: rooms are horizontally aligned to each other.
     * 1: rooms are vertically aligned to each other.
     * -1: rooms are diagonal to each other. (Use starterRoom() to find horizontal, then vertical orientation.)
     *
     * Note: "a" should be "start", "b" should be "end" for consistency.
     * */
    private int roomOrientation(Room a, Room b) {
        if (a.getEndY() >= b.getStartY() && a.getStartY() <= b.getEndY()) {
            return 0;
        } else if (a.getEndX() >= b.getStartX() && a.getStartX() <= b.getEndX()) {
            return 1;
        } else {
            return -1;
        }
    }


    /**
     * Finds which room is the start room to make the "upside-down L shaped pathway";
     * returns the higher room.
     *
     * Returns
     *
     * 0: a is the start, and on the left.
     * 1: a is the start, and on the right.
     * 2: b is the start, and on the left.
     * 3: b is the start, and on the right.
     *
     * Note: "a" should be "start", "b" should be "end" for consistency.
     * */
    private int starterRoom(Room a, Room b) {

    }
}
