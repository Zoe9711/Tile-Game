package byow.Core;

/** This is the main entry point for the program. This class simply parses
 *  the command line inputs, and lets the byow.Core.Engine class take over
 *  in either keyboard or input string mode.
 */
public class Main {
    public static void main(String[] args) {
        if (args.length > 1) {
            System.out.println("Can only have one argument - the input string");
            System.exit(0);
//            System.out.print("Case1");
        } else if (args.length == 1) {
            Engine engine = new Engine();
            engine.interactWithInputString("n5197880843569031643s");
//            engine.interactWithInputString("N1234S");
            //engine.interactWithInputString(args[0]);
            System.out.println(engine.toString());
        } else {
            Engine engine = new Engine();
            engine.interactWithKeyboard();
//            System.out.print("Case 3");
        }
//        System.out.print("pls work");
    }
}
