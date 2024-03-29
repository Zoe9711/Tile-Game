package byow.Core;

import byow.TileEngine.TETile;
import java.io.Serializable;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class HallwayGenerator implements Serializable {
    private List<Hallway> hallwayList; //list of hallways for cleaning
    private Random randomGenerator;

    public HallwayGenerator(Random rand) {
        this.hallwayList = new LinkedList<>();
        this.randomGenerator = rand;
    }


    /** Returns list of hallways generated. */
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
        //randomly selecting two positions in the two rooms respectively
        Position r1 = start.ranPosInRoom(randomGenerator);
        Position r2 = end.ranPosInRoom(randomGenerator);

        if (r1.x() > r2.x()) { //ensure r1 has smaller x coordinate
            Position rTemp = r1;
            r1 = r2;
            r2 = rTemp;
        }

        //draw horizontal hallway first
        Position startH = r1; //the smaller x coordinate
        Position endH = new Position(r2.x(), r1.y()); //the larger x coordinate
        Hallway hallHToAdd = new Hallway(startH, endH, endH.x() - startH.x(), true);
        hallHToAdd.addHallway(world);
        this.hallwayList.add(hallHToAdd);

        if (endH.y() > r2.y()) { //ensure r1 has smaller y coordinate
            Position rTemp = endH;
            endH = r2;
            r2 = rTemp;
        }

        //draw vertical
        Position startV = new Position(endH.x(), endH.y() - 1); //the smaller y coordinate
        Position endV = new Position(startV.x(), r2.y() + 1); //the larger y coordinate
        Hallway hallVToAdd = new Hallway(startV, endV, endV.y() - startV.y(), false);
        hallVToAdd.addHallway(world);
        this.hallwayList.add(hallVToAdd);
    }

    public void fillAll(TETile[][] world) {
        for (Hallway h : this.hallwayList) {
            h.clean(world);
            //NOTE: do stuff here with this.world, make sure about the orientations!
        }
    }















//        /** Other Solution: "Please Bear With Mae." */
//        int roomOrientation = roomOrientation(start, end);
//
//        if (roomOrientation == 0) { //horizontal rooms
//            addHorizontalHallway(world, start, end);
//        } else if (roomOrientation == 1) { //vertical rooms
//            addVerticalHallway(world, start, end);
//        } else { //diagonal rooms
//            addDiagonalHallway(world, start, end);
//        }
//
//    private void addHorizontalHallway(TETile[][] world, Room start, Room end) {
//        //Start room is on the right of End room
//        Room right = start;
//        Room left = end;
//        if (end.getStartX() > start.getStartX()) {
//          //Start room is actually on left; adjust for math.
//            right = end;
//            left = start;
//        }
//        int length = right.getStartX() - left.getEndX();
//        int randomYRange = getRandomFromRange(right, left);
//        Hallway hallToAdd = new Hallway(new Position(left.getEndX(), randomYRange),
//                new Position(right.getStartX(), randomYRange), length, true);
//        hallToAdd.addHallway(world);
//        hallwayList.add(hallToAdd);
//
//    }
//
//    private void addVerticalHallway(TETile[][] world, Room start, Room end) {
//        //Start room is above the End room
//        Room above = start;
//        Room below = end;
//        if (end.getStartY() > start.getStartY()) {
//              //Start room is actually below; adjust for math.
//            above = end;
//            below = start;
//        }
//        Position aboveRandPos = above.ranPosInRoom();
//        Position belowRandPos = below.ranPosInRoom();
//        int length = above.getStartY() - below.getEndY();
//        Hallway hallToAdd = new Hallway(aboveRandPos,
//                belowRandPos, length, false);
//        hallToAdd.addHallway(world);
//        hallwayList.add(hallToAdd);
//    }
//
//    private void addDiagonalHallway(TETile[][] world, Room start, Room end) {
//        Room right = start;
//        Room left = end;
//        if (end.getStartX() > start.getStartX()) {
//              Start room is actually on left; adjust for math.
//            right = end;
//            left = start;
//        }
//        int horizontalLength = getRandomFromRange(above, below);;
//
//        Room above = start;
//        Room below = end;
//        if (end.getStartY() > start.getStartY()) {
//              //Start room is actually below; adjust for math.
//            above = end;
//            below = start;
//        }
//        int verticalLength = ;
//
//
//        int length = right.getStartX() - left.getEndX();
//        int randomYRange = getRandomFromRange(right, left);
//        Hallway hallToAdd = new Hallway(new Position(left.getEndX(), randomYRange),
//                new Position(right.getStartX(), randomYRange), length, true);
//
//        Hallway verticalHallToAdd = new Hallway(new Position(randomXRange, above.getStartY()),
//                new Position(randomXRange, below.getEndY()), length, false);
//        Hallway horizontalHallToAdd = new Hallway(new Position(randomXRange, above.getStartY()),
//                new Position(randomXRange, below.getEndY()), length, false);
//        verticalHallToAdd.addHallway(world);
//        horizontalHallToAdd.addHallway(world);
//        hallwayList.add(hallToAdd);
//    }
//
//    /**
//     * Returns x or y of a pixel picked randomly from an x or y range.
//     * Assumes start is UP/RIGHT and end is DOWN/LEFT.
//     * */
//    private int getRandomFromRange(Room start, Room end) {
//
//    }
//
//    /**
//     * Returns
//     *
//     * 0: rooms are horizontally aligned to each other.
//     * 1: rooms are vertically aligned to each other.
//     * -1: rooms are diagonal to each other.
//     * For Diagonals: (Use starterRoom() to find horizontal, then vertical orientation.)
//     *
//     * Note: "a" should be "start", "b" should be "end" for consistency.
//     * */
//    private int roomOrientation(Room a, Room b) {
//        if (a.getEndY() >= b.getStartY() && a.getStartY() <= b.getEndY()) {
//            return 0;
//        } else if (a.getEndX() >= b.getStartX() && a.getStartX() <= b.getEndX()) {
//            return 1;
//        } else {
//            return -1;
//        }
//    }
}
