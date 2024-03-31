import javadoc.ClassicTuringMachine;
import utm.*;

import java.io.IOException;


/**
 * This is the main class of the application which extends the UniversalTuringMachine class.
 * By extending the UniversalTuringMachine, this class inherits all its methods and properties,
 * and can leverage its functionality to run a Turing machine.
 */
public class Main extends UniversalTuringMachine {

    /**
     * Main method to execute a Turing machine.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
/*        if (args.length != 3) {
            System.out.println("Hey man, input three parameters");
            return;
        }
        String fileName = args[0];
        String inputs = args[1];
        boolean isAnimated;
        isAnimated = args[2].equals("--animation");*/
        String fileName = "/Users/skyly/Documents/TheSixSemester/Advanced Programming Lab/Lab1_XWT_Answer/src/Example/lr-convert.desc";
        String inputs = "01010000";
        boolean isAnimated = true;

        try {
            // Load rules from the file and determine the type of machine
            TuringMachineHelper helper = new TuringMachineHelper();
            MachineType machineType = helper.loadRulesFromFile(fileName);
            TuringMachine machine = helper.createTuringMachine();

            // Run the appropriate type of Turing machine based on the machine type
            switch (machineType) {
                case LR -> helper.runTuringMachine(new LeftResetTuringMachine(machine, inputs), isAnimated);
                case BB -> helper.runTuringMachine(new BusyBeaverTuringMachine(machine), isAnimated);
                case U -> helper.runTuringMachine(new ClassicTuringMachine(machine, inputs), isAnimated);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
