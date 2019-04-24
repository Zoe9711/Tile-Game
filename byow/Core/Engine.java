package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.Random;

public class Engine {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
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

            if (charArray.get(0).equals('n') || charArray.get(0).equals('N') || Character.isDigit(charArray.get(1))) {
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
                Integer seed = Integer.valueOf(numbersToParse);
                WorldGenerator newWorld = new WorldGenerator(WIDTH, HEIGHT, seed);
                Random random = new Random(seed);
                //make up to 50 random rooms
                newWorld.addRooms(RandomUtils.uniform(random, 50) + 1);
                newWorld.addHallways();
                newWorld.cleanAndFill();
                finalWorldFrame = newWorld.getTeTile();
            }
        }
        ter.renderFrame(finalWorldFrame);
        return finalWorldFrame;
    }

//    /**
//     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
//     * @Source: CS61B and Josh Hug
//     * @param s Input string.
//     * @return Cleaned string.
//     */
//    private static String cleanString(String s) {
//        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
//    }
}
