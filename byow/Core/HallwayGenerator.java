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
        Room right = start;
        Room left = end;
        //Room above = start;
        Room below = end;
        if (end.getStartX() > start.getStartX()) { //Start room is actually on left; adjust for math.
            right = end;
            left = start;
        }
        if (end.getStartY() > start.getStartY()) { //Start room is actually below; adjust for math.
            //above = end;
            below = start;
        }

        //draw horizontal hallway first
        Position startH = left.getPosition(); //the smaller x coordinate
        Position endH = new Position(right.getPosition().x(), left.getPosition().y()); //the larger x coordinate
        Hallway hallHToAdd = new Hallway(startH, endH, endH.x() - startH.x(), true);
        hallHToAdd.addHallway(world);
        hallwayList.add(hallHToAdd);

        //draw vertical
        Position startV = below.getPosition(); //the smaller y coordinate
        Position endV = endH; //the larger y coordinate
        Hallway hallYToAdd = new Hallway(startV, endV, endV.y() - startV.y(), false);
        hallYToAdd.addHallway(world);
        hallwayList.add(hallYToAdd);




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
    }

//    private void addHorizontalHallway(TETile[][] world, Room start, Room end) {
//        //Start room is on the right of End room
//        Room right = start;
//        Room left = end;
//        if (end.getStartX() > start.getStartX()) { //Start room is actually on left; adjust for math.
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
//        if (end.getStartY() > start.getStartY()) { //Start room is actually below; adjust for math.
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
//        if (end.getStartX() > start.getStartX()) { //Start room is actually on left; adjust for math.
//            right = end;
//            left = start;
//        }
//        int horizontalLength = getRandomFromRange(above, below);;
//
//        Room above = start;
//        Room below = end;
//        if (end.getStartY() > start.getStartY()) { //Start room is actually below; adjust for math.
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
}
