package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.LinkedList;
import java.io.Serializable;

public class WorldGenerator implements Serializable {
    private int width;
    private int height;
    private long seed;
    private TETile[][] world;
    private ArrayList<Room> roomList;
    private HashMap<Integer, Room> roomMap;
    private List<Hallway> hallwayList;
    private RoomGenerator roomGenerator = new RoomGenerator();
    private HallwayGenerator hallGenerator;
    private Player player;
    private LinkedList<Enemy> enemies;
    private HashMap<GameCharacter, Position> charToPositions;
    private AStarGraph<Position> aStarGraph;

    public WorldGenerator(int w, int h, long seed) {
        this.width = w;
        this.height = h;
        this.seed = seed;
        this.roomMap = new HashMap<>();
        this.hallwayList = new LinkedList<>();
        this.hallGenerator = new HallwayGenerator(new Random(seed));
        this.player = new Player(new Position(0, 0));
        this.enemies = new LinkedList<>();
        this.charToPositions = new HashMap<>();

        this.world = new TETile[width][height];
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        Random random = new Random(seed);
        //make up to 35 random rooms
        addRooms(RandomUtils.uniform(random, 35) + 1);
        addHallways();
        cleanAndFill();
        //this.aStarGraph = new WeightedDirectedGraph(this, w, h);
        addPlayers();
        addEnemies();
    }

    public TETile[][] getTeTile() {
        return this.world;
    }

    public Position getPlayer() {
        return this.player.getPosition();
    }

    public Player thePlayer() {
        return this.player;
    }

    public void setPlayer(Player newPlayer) {
        this.player = newPlayer;
    }

    public LinkedList<Enemy> getEnemies() { return this.enemies; }

    public int width() {
        return this.width;
    }

    public int height() {
        return this.height;
    }

    private long seed() {
        return this.seed;
    }

    public void addRooms(int numOfRoom) {
        int j = 0;
        Random random = new Random(seed());
        for (int i = 0; i < numOfRoom; i++) {
            int w = random.nextInt(8) + 4;
            int h = random.nextInt(8) + 4;
            int posX = random.nextInt(width() - w);
            int posY = random.nextInt(height() - h);
            this.roomGenerator.addRoom(this.world, new Position(posX, posY), w, h, i);
            j += 1;
        }
        System.out.println("Rooms: " + j);
        this.roomList = roomGenerator.getRoomList();
        this.roomMap = roomGenerator.getMap();
    }

    public void addHallways() {
        //System.out.println("Amount of rooms: " + roomGenerator.getRoomList().size());
        ArrayList<Room> newList = roomGenerator.sortedList(); //Alternate List for now.
        //ArrayList<Room> newList = roomGenerator.getRoomList(); //Alternate List for now.
        //System.out.println("Amount of rooms after sorted: " + newList.size());
        for (int i = 0; i < newList.size() - 1; i++) {
            hallGenerator.addHallwayPath(this.world, newList.get(i), newList.get(i + 1));
        }
        this.hallwayList = hallGenerator.getHallwayList();
    }

    public void addPlayers() {
        Random random = new Random(seed());
        Room ranRm = roomList.get(random.nextInt(roomList.size()));
        Position playerP = ranRm.ranPosInRoom(random);
        player.addOnMap(this.world, playerP);
        this.player = new Player(playerP);
        this.charToPositions.put(this.player, playerP);
    }

    public void addEnemies() {
        Random random = new Random(seed());
        random.nextInt(); //get rid of player's place
        ArrayList<Position> occupied = new ArrayList<>();
        int enemiesAddedCorrectly = 0;
        while (enemiesAddedCorrectly != 4) {
            Room ranRm = roomList.get(random.nextInt(roomList.size()));
            Position enemyP = ranRm.ranPosInRoom(random);
            if (!occupied.contains(enemyP) && !enemyP.equals(player.getPosition())) {
                occupied.add(enemyP);
                Enemy enemyToAdd = new Enemy(enemyP);
                enemyToAdd.addOnMap(this.world, enemyP);
                this.enemies.add(enemyToAdd);
                enemiesAddedCorrectly += 1;
                this.charToPositions.put(enemyToAdd, enemyP);
            }

        }

    }

    //AStar on each enemy, move that way and update positions +
    // keep moving for about 3 or less spots before calling this method again
    public void moveEnemies() {
        for (Enemy enemy : enemies) {
            Position up = new Position(enemy.getStartX(), enemy.getStartY() + 1);
            Position down = new Position(enemy.getStartX(), enemy.getStartY() - 1);
            Position left = new Position(enemy.getStartX() - 1, enemy.getStartY());
            Position right = new Position(enemy.getStartX() + 1, enemy.getStartY());
            ArrayList<Position> wasd = new ArrayList<>();
            wasd.add(up);
            wasd.add(down);
            wasd.add(left);
            wasd.add(right);

            ArrayList<Position> wasdP = new ArrayList<>();
            //get rid of any walls, can't go there! Don't estimate it.
            for (Position p : wasd) {
                if (!world[p.x()][p.y()].equals(Tileset.WALL)) {
                    wasdP.add(p);
                }
            }

            // checks the 4 positions using graph
            // full of map's floors only and calculate nearest to player position
            HashMap<Position, Double> movesFour = new HashMap<>();
            for (Position p : wasdP) {
                ShortestPathsSolver<Position> solver = new AStarSolver<>(aStarGraph, p, getPlayer(), 20);
                movesFour.put(p, solver.solutionWeight());
            }

            // Compare for  each "moves needed" and go for that position
            Position bestPos = up;
            for (Position p : wasdP) {
                if (movesFour.get(p) < movesFour.get(bestPos)) {
                    bestPos = p;
                }
            }
            this.charToPositions.put(enemy, bestPos);
            enemy.move(world, enemy.getPosition(), bestPos, Tileset.FLOWER);
            charToPositions.put(enemy, bestPos);
        }
    }

    public boolean isPlayerKilled() {
        for (Enemy enemy : enemies) {
            if (charToPositions.get(enemy).equals(player.getPosition())) {
                return true;
            }
        }
        return false;

    }

    public void cleanAndFill() {
        //System.out.println("Amount of hallways: " + hallGenerator.getHallwayList().size());
        hallGenerator.fillAll(this.world);
        roomGenerator.fillAll(this.world);

    }

}
