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
     * Note: A diagonal hallways must be identified as one single hallway instance, but
     * consist of two hallways for the cleaning process.
     * */
    public void addHallwayPath(TETile[][] world, Room start, Room end) {
        int roomOrientation = roomOrientation(start, end);

        if (roomOrientation == 0) { //horizontal rooms

        } else if (roomOrientation == 1) { //vertical rooms

        } else { //diagonal rooms
            if (starterRoom(start, end) == 0) { //start is above

            } else { //end is above

            }

        }
    }

    /** Returns
     *
     * 0: rooms are horizontally aligned to each other.
     * 1: rooms are vertically aligned to each other.
     * -1: rooms are diagonal to each other. (Use starterRoom() to find horizontal, then vertical orientation.)
     *
     * Note: "a" should be "start", "b" should be "end" for consistency.
     * */
    private int roomOrientation(Room a, Room b) {
        //Room A ranges
        int xARoomStartRange = a.getPosition().x();
        int xARoomEndRange = xARoomStartRange + a.getHeight() - 1;
        int yARoomStartRange = a.getPosition().y();
        int yARoomEndRange = yARoomStartRange + a.getWidth() - 1;

        //Room B ranges
        int xBRoomStartRange = b.getPosition().x();
        int xBRoomEndRange = xBRoomStartRange + b.getHeight() - 1;
        int yBRoomStartRange = b.getPosition().y();
        int yBRoomEndRange = yBRoomStartRange + b.getWidth() - 1;

        if (yBRoomEndRange >= yARoomStartRange && yBRoomStartRange <= yARoomEndRange) {
            return 0;
        } else if (xBRoomEndRange >= xARoomStartRange && xBRoomStartRange <= xARoomEndRange) {
            return 1;
        } else {
            return -1;
        }
    }

    /** Finds which room is the start room to make the "upside-down L shaped pathway";
     * returns the higher room.
     *
     * Returns
     *
     * 0: a is the start.
     * 1: b is the start.
     *
     * Note: "a" should be "start", "b" should be "end" for consistency.
     * */
    private int starterRoom(Room a, Room b) {
        return 0;
    }
}
