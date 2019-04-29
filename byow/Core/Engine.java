package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Engine {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    private static final int WIDTH = 80;
    private static final int HEIGHT = 30;
    private static final int MWIDTH = 60;
    private static final int MHEIGHT = 40;
    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        drawMenu();

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

        ter.initialize(WIDTH, HEIGHT);
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

            if (charArray.get(0).equals('n') || charArray.get(0).equals('N')
                    || Character.isDigit(charArray.get(1))) {
                String numbersToParse = "";
                int startIndexWASD;
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
//                for (int i = numbersToParse.length() - 1; i >= 0; i--) {
//                    String c = Character.toString(numbersToParse.charAt(i));
//                    seed += Integer.parseInt(c) * (int) Math.pow(10, i);
//                }

                //System.out.println(seed);
                WorldGenerator newWorld = new WorldGenerator(WIDTH, HEIGHT, seed);
                Random random = new Random(seed);
                //make up to 35 random rooms
                newWorld.addRooms(RandomUtils.uniform(random, 35) + 1);
                newWorld.addHallways();
                newWorld.cleanAndFill();
                newWorld.addPlayer();
                finalWorldFrame = newWorld.getTeTile();
            }
        }
        ter.renderFrame(finalWorldFrame);
        return finalWorldFrame;
    }

    private void WASD(WorldGenerator wg, char key) {
        TETile up = wg.getTeTile()[wg.getPlayer().x()][wg.getPlayer().y() + 1];
        TETile down = wg.getTeTile()[wg.getPlayer().x()][wg.getPlayer().y() - 1];
        TETile left = wg.getTeTile()[wg.getPlayer().x() - 1][wg.getPlayer().y()];
        TETile right = wg.getTeTile()[wg.getPlayer().x() + 1][wg.getPlayer().y()];
        switch (key) {
            case ('w'): {
                if (up.equals(Tileset.FLOOR)) {
                    wg.getTeTile()[wg.getPlayer().x()][wg.getPlayer().y() + 1] = Tileset.AVATAR;
                    wg.getTeTile()[wg.getPlayer().x()][wg.getPlayer().y()] = Tileset.FLOOR;
                }
                wg.modPlayer(new Position(wg.getPlayer().x(), wg.getPlayer().y() + 1));
            }

            case ('s'): {
                if (down.equals(Tileset.FLOOR)) {
                    wg.getTeTile()[wg.getPlayer().x()][wg.getPlayer().y() - 1] = Tileset.AVATAR;
                    wg.getTeTile()[wg.getPlayer().x()][wg.getPlayer().y()] = Tileset.FLOOR;
                }
                wg.modPlayer(new Position(wg.getPlayer().x(), wg.getPlayer().y() - 1));

            }

            case ('a'): {
                if (left.equals(Tileset.FLOOR)) {
                    wg.getTeTile()[wg.getPlayer().x() - 1][wg.getPlayer().y()] = Tileset.AVATAR;
                    wg.getTeTile()[wg.getPlayer().x()][wg.getPlayer().y()] = Tileset.FLOOR;
                }
                wg.modPlayer(new Position(wg.getPlayer().x() - 1, wg.getPlayer().y()));
            }

            case ('d'): {
                if (right.equals(Tileset.FLOOR)) {
                    wg.getTeTile()[wg.getPlayer().x() + 1][wg.getPlayer().y()] = Tileset.AVATAR;
                    wg.getTeTile()[wg.getPlayer().x()][wg.getPlayer().y()] = Tileset.FLOOR;
                }
                wg.modPlayer(new Position(wg.getPlayer().x() + 1, wg.getPlayer().y()));
            }
        }
    }

    private void drawMenu() {
        Font title = new Font("Times New Roman", Font.BOLD, 20);
        StdDraw.setFont(title);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(MWIDTH / 2, MHEIGHT * 2 / 3, "My World");
        Font other = new Font("Times New Roman", Font.PLAIN, 12);
        StdDraw.setFont(other);
        StdDraw.text(MWIDTH / 2, MHEIGHT / 2, "New Game (N)");
        StdDraw.text(MWIDTH / 2, MHEIGHT * 2 / 5, "Load Game (L)");
        StdDraw.text(MWIDTH / 2, MHEIGHT * 3 / 10, "Quit (Q)");
        StdDraw.show();
    }

    private void mouse(WorldGenerator wg) {
        TETile mPos = wg.getTeTile()[(int) StdDraw.mouseX()][(int) StdDraw.mouseY()];
        if (mPos.equals(Tileset.AVATAR)) {
            ter.renderFrame(wg.getTeTile());
            StdDraw.enableDoubleBuffering();
            StdDraw.setPenColor(Color.white);
            StdDraw.text(WIDTH / 2, HEIGHT, "You");
        } else if (mPos.equals(Tileset.WALL)) {
            ter.renderFrame(wg.getTeTile());
            StdDraw.enableDoubleBuffering();
            StdDraw.setPenColor(Color.white);
            StdDraw.text(WIDTH / 2, HEIGHT, "Wall");
        }
        else if (mPos.equals(Tileset.FLOOR)) {
            ter.renderFrame(wg.getTeTile());
            StdDraw.enableDoubleBuffering();
            StdDraw.setPenColor(Color.white);
            StdDraw.text(WIDTH / 2, HEIGHT, "Floor");
        } else {
            ter.renderFrame(wg.getTeTile());
            StdDraw.enableDoubleBuffering();
            StdDraw.setPenColor(Color.white);
            StdDraw.text(WIDTH / 2, HEIGHT, "Nothing");
        }
        StdDraw.show();
    }

}
