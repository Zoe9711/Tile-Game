package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.algs4.Queue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class RoomGenerator {
    private ArrayList<Room> roomList; //list of hallways for cleaning
    private HashMap<Integer, Room> roomMap;

    public RoomGenerator() {
        this.roomList = new ArrayList<>();
        this.roomMap = new HashMap<>();
    }

    public HashMap<Integer, Room> getMap() {
        return this.roomMap;
    }

    /** Returns list of rooms generated. */
    public ArrayList<Room> getRoomList() {
        return this.roomList;
    }

    public void addRoom(TETile[][] world, Position p, int w, int h, int id) {
        Room roomToAdd = new Room(p, w, h, id);
        this.roomMap.put(roomToAdd.getStartX() + roomToAdd.getStartY(), roomToAdd);
        this.roomList.add(roomToAdd);
        roomToAdd.addRoom(world, w, h);
    }

    public ArrayList<Room> sortedList() {
//        ArrayList<Room> newList = new ArrayList<>();
//        ArrayList<Integer> listX = new ArrayList<>();
//        for (Room r : this.roomList) {
//            listX.add(r.getStartX() + r.getStartY());
//        }
//        while (listX.size() != 0) {
//            int smallEst = minIndex(listX);
//            int smalleSt = listX.get(smallEst);
//            newList.add(this.roomMap.get(smalleSt));
//            System.out.println(this.roomMap.get(smalleSt).getId());
//            listX.remove(smallEst);
//        }
//        return newList;
        Queue<Room> items = new Queue<>();

        for (Room i: this.getRoomList()) {
            items.enqueue(i);
        }

        items = QuickSort.quickSort(items);

        ArrayList<Room> newList = new ArrayList<>();
        while (!items.isEmpty()) {
            newList.add(items.dequeue());
        }
        System.out.println(newList.size());
        for (Room i: newList) {
            System.out.println(i.getId());
        }
        return newList;
    }

    private ArrayList<Position> roomSpace(Room r) {
        ArrayList<Position> space = new ArrayList<>();
        for (int i = 1; i < r.getWidth() - 1; i++) {
            for (int j = 1; j < r.getHeight() - 1; j++) {
                space.add(new Position(r.getPosition().x() + i, r.getPosition().y() + j));
            }
        }
        return space;
    }

    public void fillAll(TETile[][] world) {
        for (Room r : this.roomList) {
            for (Position p : roomSpace(r)) {
                world[p.x()][p.y()] = Tileset.FLOOR;
            }
        }
    }

    private static int minIndex(List<Integer> listX) {
        return listX.indexOf(Collections.min(listX));
    }
/*
    public static void main(String[] args) {
        Position p1 = new Position(9, 9);
        Room r1 = new Room(p1, 2, 2);
        Position p2 = new Position(3, 3);
        Room r2 = new Room(p2, 2, 2);
        Position p3 = new Position(3, 5);
        Room r3 = new Room(p3, 2, 2);
        ArrayList<Room> roomList = new ArrayList<>();
        roomList.add(r1);
        roomList.add(r2);
        roomList.add(r3);
        HashMap<Integer, Room> roomMap = new HashMap<>();
        roomMap.put(9, r1);
        roomMap.put(3, r2);
        roomMap.put(3, r3);
        List<Room> newList = sortedList(roomList, roomMap);
        System.out.println(newList);
    }
    */
}
