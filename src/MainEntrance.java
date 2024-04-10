import utm.*;
import utmeditor.UTMEditor;

/**
 * This is the main class of the application which extends the UniversalTuringMachine class.
 * By extending the UniversalTuringMachine, this class inherits all its methods and properties,
 * and can leverage its functionality to run a Turing machine.
 */
public class MainEntrance extends UniversalTuringMachine {
    /**
     * MainEntrance method to execute a Turing machine.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        boolean isAnimated;
        Controller controller;
        if (args.length == 0) {
            // If args.length == 0, executing Mode 3, a TM is executed with the UTM Editor and with graphical animation on the UTM GUI
            controller = new Controller(true);
            UTMEditor utmEditor = new UTMEditor();
            utmEditor.setUTMController(controller);
            return;
        } else {
            if (args.length != 3) {
                throw new IllegalArgumentException("Incorrect number of parameters. Expected 3, got " + args.length);
            }
            // If args.length == 3, executing Mode 1 and 2
            // Mode1: A TM is executed without the UTM Editor and with no graphical animation
            // Mode2: A TM is executed without the UTM Editor and with graphical animation
            if (args[2].equals("--animation")) {
                isAnimated = true;
            } else if (args[2].equals("--noanimation")) {
                isAnimated = false;
            } else {
                throw new IllegalArgumentException("Unknown argument: " + args[2]);
            }
            String fileName = args[0];
            String inputs = args[1];
            controller = new Controller(isAnimated);
            controller.loadTuringMachineFrom(fileName);
            controller.runUTM(inputs);
        }
        // make sure program end fast in no-animation mode
        if (!isAnimated) {
            System.exit(0);
        }
    }
}
