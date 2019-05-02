package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.*;
import java.lang.invoke.MethodHandle;
import java.util.ArrayList;
import java.util.Random;


public class Engine {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    private static final int WIDTH = 80;
    private static final int HEIGHT = 30;
    private static final int MWIDTH = 60;
    private static final int MHEIGHT = 40;
    private String savedWorld = "";
    private boolean gameRunning = false;
    private boolean seedInputRunning = false;
    private boolean menuRunning = true;
    private WorldGenerator newWorld;
    TETile[][] renderTiles = null;

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        Character last = new Character(' ');
        String seed = "";

        //ter.initialize(WIDTH, HEIGHT);
        drawCanvas();
        drawMenu();

        while (!(last.equals('q'))) {
            if (gameRunning) {
                gameRunning();
            }

            if (StdDraw.hasNextKeyTyped()) { //runs below if key is pressed.
                last = StdDraw.nextKeyTyped();
                if (menuRunning) {
                    switch (last) {
                        case ('N'):
                        case ('n'): {
                            System.out.print("case n");
                            savedWorld = "";
                            savedWorld += last.toString();
                            seedMenu("");
                            seedInputRunning = true;
                            menuRunning = false;
                            break;
                        }
                        case ('L'):
                        case ('l'): {
                            System.out.print("case l");
                            String loadString = load();
                            ter.initialize(WIDTH, HEIGHT);
                            renderTiles = interactWithInputString(loadString);
                            ter.renderFrame(renderTiles);
                            savedWorld += last;
                            menuRunning = false;
                            seedInputRunning = false;
                            gameRunning = true;
                            break;
                        }
                        case ('Q'):
                        case ('q'): {
                            System.exit(0);
                        }
                    }
                }

             else if (seedInputRunning) {
                    if ((!Character.isDigit(last) && !last.equals('s')) || (!Character.isDigit(last) && !last.equals('S'))) {
                        warning();
                    }
                if ((!seed.equals("") && last.equals('s')) || (!seed.equals("") && last.equals('S'))) {
                    System.out.println("seedInputRunning else case 1");
                    savedWorld += last.toString();
                    ter.initialize(WIDTH, HEIGHT);
                    System.out.println(savedWorld);
                    this.renderTiles = interactWithInputString(savedWorld);
                    ter.renderFrame(renderTiles);
                    seedInputRunning = false;
                    gameRunning = true;
                }

                if ((seed.isEmpty() && last.equals('s')) || (seed.isEmpty() && last.equals('S'))) {
                    System.out.println("seedInputRunning else case 2");
                    warning();
                    }

                if (Character.isDigit(last)) {
                    savedWorld += last.toString();
                    seed += last.toString();
                    seedMenu(seed);
                    System.out.println(seed);
                }
            }

                last = new Character(' ');

            }
        }

    }

    private void gameRunning() {
        while (gameRunning) {
            mouse(newWorld);
            if (StdDraw.hasNextKeyTyped()) {
                Character last = StdDraw.nextKeyTyped();
                WASD(last);
                int i = savedWorld.length() - 2;
                System.out.println("last: " + last);
                if (savedWorld.charAt(i) == ':' && (savedWorld.charAt(i + 1) == 'q'
                        || savedWorld.charAt(i + 1) == 'Q')) {
                    System.out.println("quit");
                    String savedString = savedWorld.substring(0, savedWorld.length() - 3);
                    save(savedString);
                    drawCanvas();
                    notification("Saved");
                    StdDraw.pause(5000);
                    gameRunning = false;
                    System.exit(0);
                }

                tryGameOver(); // after your move -- for when player is next to enemy before moving to it
                newWorld.moveEnemies();
                tryGameOver(); //after their move -- for when player doesn't move or enemy reaches you first
                ter.renderFrame(newWorld.getTeTile());
            }
        }
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, both of these calls:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.

//        ter.initialize(WIDTH, HEIGHT);

        TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                finalWorldFrame[x][y] = Tileset.NOTHING;
            }
        }
        if (!(input.isEmpty())) {
            ArrayList<Character> charArray = new ArrayList<>();
            for (char ch : input.toCharArray()) {
                charArray.add(ch);
            }

            int startIndexWASD = 0;
            if (charArray.get(0).equals('n') || charArray.get(0).equals('N')
                    || Character.isDigit(charArray.get(1))) {
                String numbersToParse = "";
                for (int i = 1; i < charArray.size(); i++) {
                    Character c = charArray.get(i);
                    if (!Character.isDigit(c)) {
                        startIndexWASD = i;
                        if (!(c.equals('s') || c.equals('S'))) {
                            return finalWorldFrame;
                        }
                        break;
                    } else {
                        numbersToParse += c;
                    }
                }

                //System.out.println(numbersToParse);
                if (numbersToParse.length() > 19) {
                    return finalWorldFrame;
                }

                long seed = Long.valueOf(numbersToParse);

                this.newWorld = new WorldGenerator(WIDTH, HEIGHT, seed);
                finalWorldFrame = newWorld.getTeTile();
                moveCharactersN(startIndexWASD, charArray);


            }

            if (charArray.get(0).equals('l') || charArray.get(0).equals('L')) {
                String loadString = load();



                this.newWorld = interactWithKeyboard(loadString);

                moveCharactersL();

            }


        }


//        ter.renderFrame(finalWorldFrame);
        return finalWorldFrame;
    }


    private void moveCharactersN(int wasdIndex, ArrayList<Character> charArray) {

        for (int i = wasdIndex; i < charArray.size(); i++) {
            Character c = charArray.get(i);
            WASD(c);
            if (charArray.get(i) == ':' && (charArray.get(i + 1) == 'q'
                    || charArray.get(i + 1) == 'Q')) {



                String savedString = savedWorld.substring(0, savedWorld.length() - 3);
                save(savedString);
                drawCanvas();
                notification("Saved");
                StdDraw.pause(5000);
                gameRunning = false;
                System.exit(0);
            }
        }
    }

    private void moveCharactersL(int wasdIndex, ArrayList<Character> charArray) {
        for (int i = wasdIndex; i < charArray.size(); i++) {
            Character c = charArray.get(i);
            WASD(c);
            if (charArray.get(i) == ':' && (charArray.get(i + 1) == 'q'
                    || charArray.get(i + 1) == 'Q')) {
                System.out.println("quit");
                String savedString = savedWorld.substring(0, savedWorld.length() - 3);
                save(savedString);
                drawCanvas();
                notification("Saved");
                StdDraw.pause(5000);
                gameRunning = false;
                System.exit(0);
            }
        }
    }

    private void WASD(Character key) {
        TETile up = newWorld.getTeTile()[newWorld.getPlayer().x()][newWorld.getPlayer().y() + 1];
        TETile down = newWorld.getTeTile()[newWorld.getPlayer().x()][newWorld.getPlayer().y() - 1];
        TETile left = newWorld.getTeTile()[newWorld.getPlayer().x() - 1][newWorld.getPlayer().y()];
        TETile right = newWorld.getTeTile()[newWorld.getPlayer().x() + 1][newWorld.getPlayer().y()];
        switch (key) {
            case ('W'):
            case ('w'): {
                if (up.equals(Tileset.FLOOR) || up.equals(Tileset.FLOWER)) {
                    newWorld.thePlayer().setNewPosition(newWorld.thePlayer().moveUp(newWorld.getTeTile(), newWorld.getPlayer()));
                    //newWorld.setPlayer(new Player(newWorld.thePlayer().moveUp(newWorld.getTeTile(), newWorld.getPlayer())));
                }

                savedWorld += key.toString();
                break;
            }
            case ('S'):
            case ('s'): {
                if (down.equals(Tileset.FLOOR) || down.equals(Tileset.FLOWER)) {
                    newWorld.thePlayer().setNewPosition(newWorld.thePlayer().moveDown(newWorld.getTeTile(), newWorld.getPlayer()));
                    //newWorld.setPlayer(new Player(newWorld.thePlayer().moveDown(newWorld.getTeTile(), newWorld.getPlayer())));
                }
                savedWorld += key.toString();
                break;
            }
            case ('A'):
            case ('a'): {
                if (left.equals(Tileset.FLOOR) || left.equals(Tileset.FLOWER)) {
                    newWorld.thePlayer().setNewPosition(newWorld.thePlayer().moveLeft(newWorld.getTeTile(), newWorld.getPlayer()));
                    //newWorld.setPlayer(new Player(newWorld.thePlayer().moveLeft(newWorld.getTeTile(), newWorld.getPlayer())));
                }
                savedWorld += key.toString();
                break;
            }
            case ('D'):
            case ('d'): {
                if (right.equals(Tileset.FLOOR) || right.equals(Tileset.FLOWER)) {
                    newWorld.thePlayer().setNewPosition(newWorld.thePlayer().moveRight(newWorld.getTeTile(), newWorld.getPlayer()));
                    //newWorld.setPlayer(new Player(newWorld.thePlayer().moveRight(newWorld.getTeTile(), newWorld.getPlayer())));
                }
                savedWorld += key.toString();
                break;
            }
            case (':'): {
                savedWorld += key.toString();
                break;
            }
            case ('q'):
            case ('Q'): {
                savedWorld += key.toString();
                break;
            }
        }
        System.out.println("Player Location: (" + newWorld.getPlayer().x() + ", " + newWorld.getPlayer().y() + ")");
    }

    private void tryGameOver() {
        if (newWorld.isPlayerKilled()) {
            //make menu of getting killed lmao "You died" -- no save needed
            System.exit(0); //placeholder for now
        }
    }

    private void notification(String s) {
        StdDraw.clear(Color.BLACK);
        Font title = new Font("Times New Roman", Font.BOLD, 40);
        StdDraw.setFont(title);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(MWIDTH / 2, MHEIGHT / 2, s);
        StdDraw.show();
    }

    private void warning() {
        StdDraw.clear(Color.BLACK);
        StdDraw.text(MWIDTH / 2, MHEIGHT /2, "Put a number");
        StdDraw.show();
    }


    private void drawMenu() {

        Font title = new Font("Times New Roman", Font.BOLD, 40);
        StdDraw.setFont(title);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(MWIDTH / 2, MHEIGHT * 2 / 3, "My World");
        Font other = new Font("Times New Roman", Font.PLAIN, 24);
        StdDraw.setFont(other);
        StdDraw.text(MWIDTH / 2, MHEIGHT / 2, "New Game (N)");
        StdDraw.text(MWIDTH / 2, MHEIGHT * 2 / 5, "Load Game (L)");
        StdDraw.text(MWIDTH / 2, MHEIGHT * 3 / 10, "Quit (Q)");
        StdDraw.show();
    }

    private void seedMenu(String seed) {
        StdDraw.clear(Color.BLACK);
        StdDraw.text(MWIDTH / 2, MHEIGHT / 2, "Input: " + seed);
        StdDraw.show();
    }

    private void drawCanvas() {
        StdDraw.setCanvasSize(MWIDTH * 16, MHEIGHT * 16);
        Font font = new Font("Times New Roman", Font.BOLD, 100);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, MWIDTH);
        StdDraw.setYscale(0, MHEIGHT);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
    }

    private void mouse(WorldGenerator wg) {
        StdDraw.clear(Color.BLACK);
        if ((int) StdDraw.mouseX() < WIDTH && (int) StdDraw.mouseY() < HEIGHT) {
            TETile mPos = wg.getTeTile()[(int) StdDraw.mouseX()][(int) StdDraw.mouseY()];
            ter.renderFrame(newWorld.getTeTile());
            StdDraw.enableDoubleBuffering();
            StdDraw.setPenColor(Color.white);
            if (mPos.equals(Tileset.AVATAR)) {
                StdDraw.text(WIDTH / 2, HEIGHT - 1, "You");
            } else if (mPos.equals(Tileset.WALL)) {
                StdDraw.text(WIDTH / 2, HEIGHT - 1, "Wall");
            } else if (mPos.equals(Tileset.FLOOR)) {
                StdDraw.text(WIDTH / 2, HEIGHT - 1, "Floor");
            } else if (mPos.equals(Tileset.FLOWER)) {
                StdDraw.text(WIDTH / 2, HEIGHT - 1, "Flower");
            } else {
                StdDraw.text(WIDTH / 2, HEIGHT - 1, "Nothing");
            }
            StdDraw.show();
        }
    }

    private void save(String s) {
        File f = new File("./save_data.txt");
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            FileOutputStream fs = new FileOutputStream(f);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(s);
        }  catch (FileNotFoundException e) {
            System.out.println("file not found");
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        }
    }

    private static String load() {
        File f = new File("./save_data.txt");
        if (f.exists()) {
            try {
                FileInputStream fs = new FileInputStream(f);
                ObjectInputStream os = new ObjectInputStream(fs);
                return (String) os.readObject();
            } catch (FileNotFoundException e) {
                System.out.println("file not found");
                System.exit(0);
            } catch (IOException e) {
                System.out.println(e);
                System.exit(0);
            } catch (ClassNotFoundException e) {
                System.out.println("class not found");
                System.exit(0);
            }
        }
        System.exit(0);
        return "";
    }
}
