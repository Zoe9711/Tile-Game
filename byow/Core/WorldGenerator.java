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
    private LinkedList<Portal> portals;
    private HashMap<GameCharacter, Position> charToPositions;
    private WeightedDirectedGraph aStarGraph;
    private TETile type;
    private Random portalRand;
    private Integer steps;
    private int portalNum;

    public WorldGenerator(int w, int h, long seed, TETile type) {
        this.width = w;
        this.height = h;
        this.seed = seed;
        this.roomMap = new HashMap<>();
        this.hallwayList = new LinkedList<>();
        this.hallGenerator = new HallwayGenerator(new Random(seed));
        this.player = new Player(new Position(0, 0));
        this.enemies = new LinkedList<>();
        this.charToPositions = new HashMap<>();
        this.type = type;
        this.portals = new LinkedList<>();
        this.portalRand = new Random(seed + 1);
        this.steps = 0;
        this.portalNum = 0;

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
        this.aStarGraph = new WeightedDirectedGraph(this, w, h);

        addPlayers();
        addEnemies(4);
        addPortals(5);
    }

    public void addAStep() {
        this.steps += 1;
    }

    public String getSteps() {
        return this.steps.toString();
    }

    public void setPortals(LinkedList<Portal> portals) { this.portals = portals; }

    public TETile[][] getTeTile() {
        return this.world;
    }

    public void setTeTile(TETile[][] world) { this.world = world; }

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

    public Portal getOtherRandomPortal(Portal curr) {
        Integer randomIndex = portalIndex(curr.getPosition());
        while (randomIndex.equals(portalIndex(curr.getPosition()))) {
            randomIndex = portalRand.nextInt(portalNum);
        }
        return portals.get(randomIndex);
    }

    private int portalIndex(Position n) {
        int i = 0;
        for (Portal p : portals) {
            if (p.equals(new Portal(n))) {
                return i;
            }
            i += 1;
        }
        return 0;
    }

    public void removePortals(Position avatarFinalPos) {
        for (Portal p : portals) {
            if (p.getPosition() != avatarFinalPos) {
                world[p.getStartX()][p.getStartY()] = Tileset.FLOOR;
            }
        }
        this.portals.clear();
    }

    public void addPortals(int number) {
        this.portalNum = number;
        ArrayList<Position> occupied = new ArrayList<>();
        occupied.add(getPlayer());
        for (Enemy e : enemies) {
            occupied.add(e.getPosition());
        }
        int portalsAddedCorrectly = 0;
        while (portalsAddedCorrectly != number) {
            Room ranRm = roomList.get(portalRand.nextInt(roomList.size()));
            Position portalP = ranRm.ranPosInRoom(portalRand);
            if (!occupied.contains(portalP)) {
                Portal portalToAdd = new Portal(portalP);
                portalToAdd.addOnMap(this.world, portalP, null);
                this.portals.add(portalToAdd);
//                this.charToPositions.put(portalToAdd, portalP);
                occupied.add(portalP);
                portalsAddedCorrectly += 1;
            }
        }
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
//        System.out.println("Rooms: " + j);
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
        player.addOnMap(this.world, playerP, this.type);
        this.player = new Player(playerP);
        this.charToPositions.put(this.player, playerP);
    }

    public void addEnemies(int amt) {
        Random random = new Random(seed());
        random.nextInt(); //get rid of player's place
        ArrayList<Position> occupied = new ArrayList<>();
        int enemiesAddedCorrectly = 0;
        while (enemiesAddedCorrectly != amt) {
            Room ranRm = roomList.get(random.nextInt(roomList.size()));
            Position enemyP = ranRm.ranPosInRoom(random);
            if (!occupied.contains(enemyP) && !enemyP.equals(player.getPosition())) {
                occupied.add(enemyP);
                Enemy enemyToAdd = new Enemy(enemyP, enemiesAddedCorrectly);
                enemyToAdd.addOnMap(this.world, enemyP, Tileset.FLOWER);
                this.enemies.add(enemyToAdd);
                enemiesAddedCorrectly += 1;
                this.charToPositions.put(enemyToAdd, enemyP);
            }

        }
    }

    public Enemy getEnemyAt(Position mPos) {
        for (Enemy e : enemies) {
            if (mPos.equals(e.getPosition())) {
                return e;
            }
        }
        return null;
    }

    //AStar on each enemy, move that way and update positions +
    // keep moving for about 3 or less spots before calling this method again
    public void moveEnemies() {
        for (Enemy enemy : enemies) {
//            System.out.println(enemy.getStartX() + ", "
//                    + enemy.getStartY() + " BEFORE ASTAR POSITION");
            enemy.move(world, enemy.getPosition(), enemy.getNextMove(), Tileset.FLOWER, null);
            this.charToPositions.put(enemy, enemy.getNextMove());

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
                if (!world[p.x()][p.y()].equals(Tileset.WALL)
                        && !world[p.x()][p.y()].equals(Tileset.FLOWER)
                        && !world[p.x()][p.y()].equals(Tileset.LOCKED_DOOR)) {
                    wasdP.add(p);
                }
            }

            // checks the 4 positions using graph
            // full of map's floors only and calculate nearest to player position
            HashMap<Position, Double> movesFour = new HashMap<>();
            for (Position p : wasdP) {
                ShortestPathsSolver<Position> solver =
                        new AStarSolver<>(aStarGraph, p, getPlayer(), 20);
                movesFour.put(p, solver.solutionWeight());
            }

            // Compare for  each "moves needed" and go for that position
            Position bestPos = wasdP.get(0);
            for (Position p : wasdP) {
                if (movesFour.get(p) < movesFour.get(bestPos)) {
                    bestPos = p;
                }
            }
//            System.out.println(bestPos.x() + ", " + bestPos.y() + " AFTER ASTAR POSITION");
            enemy.setNextMove(bestPos);
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
