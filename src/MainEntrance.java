import org.junit.Test;
import utm.*;

import java.io.IOException;

import static org.junit.Assert.assertEquals;


/**
 * This is the main class of the application which extends the UniversalTuringMachine class.
 * By extending the UniversalTuringMachine, this class inherits all its methods and properties,
 * and can leverage its functionality to run a Turing machine.
 */
public class MainEntrance extends UniversalTuringMachine {
    static TuringMachine machine;
    static String dir = System.getProperty("user.dir");
    static String examplePath = dir + "/src/Example/";

    /**
     * MainEntrance method to execute a Turing machine.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("Incorrect number of parameters. Expected 3, got " + args.length);
        }
        String fileName = args[0];
        String inputs = args[1];
        boolean isAnimated;
        if (args[2].equals("--animation")) {
            isAnimated = true;
        } else if (args[2].equals("--noanimation")) {
            isAnimated = false;
        } else {
            throw new IllegalArgumentException("Unknown argument: " + args[2]);
        }

        try {
            // Load rules from the file and determine the type of machine
            TuringMachineHelper helper = new TuringMachineHelper();
            MachineType machineType = helper.loadRulesFromFile(fileName);
            machine = helper.createTuringMachine();

            // Run the appropriate type of Turing machine based on the machine type
            switch (machineType) {
                case LR -> helper.runTuringMachine(new LeftResetTuringMachine(machine, inputs), isAnimated);
                case BB -> helper.runTuringMachine(new BusyBeaverTuringMachine(machine), isAnimated);
                case U -> helper.runTuringMachine(new ClassicTuringMachine(machine, inputs), isAnimated);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!isAnimated) {
            System.exit(0);
        }
    }

    @Test
    public void TestLrAll1() {
        MainEntrance.main(new String[]{
                examplePath + "lr-all1.desc",
                "11110",
                "--animation"});
        Tape t = new Tape("XXXX0");
        assertEquals(t, machine.getTape());
    }
}
