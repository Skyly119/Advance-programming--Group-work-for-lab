import utm.TuringMachine;
import utm.UniversalTuringMachine;
import utmeditor.UTMController;

import java.io.IOException;

/**
 * The Controller class is used to load the file and run different Turing machines according to the different machine types,
 * It implements the UTMController, which means that it can be set to the editor and control the animation by the UTM editor
 * 1. loadTuringMachineFrom
 * 2. runUTM
 */
public class Controller implements UTMController {
    /**
     * Instance of the TuringMachine class
     */
    TuringMachine machine = null;
    /**
     * Instance of the UniversalTuringMachine class
     */
    UniversalTuringMachine utm = null;
    /**
     * local isAnimated variable
     */
    private boolean isAnimated;
    /**
     * the instance of TuringMachineHelper class
     */
    private TuringMachineHelper helper;
    /**
     * machine type which is used to specify the correct type of machine
     */
    private MachineType machineType;

    /**
     * this is the constructor
     *
     * @param isAnimated whether we should have animation given the parameter
     */
    public Controller(boolean isAnimated) {
        this.isAnimated = isAnimated;
    }

    /**
     * This method reads data from a file and initializes the Turing Machine accordingly.
     * It utilizes the encapsulated method loadRulesFromFile in the TuringMachineHelper class
     * to load rules from the specified file and determine the type of machine.
     *
     * @param fileName the name of the file to read data from
     */
    @Override
    public void loadTuringMachineFrom(String fileName) {
        try {
            // Load rules from the file and determine the type of machine
            helper = new TuringMachineHelper();
            machineType = helper.loadRulesFromFile(fileName);
            machine = helper.createTuringMachine();
            switch (machineType) {
                case LR -> utm = new LeftResetTuringMachine(machine);
                case BB -> utm = new BusyBeaverTuringMachine(machine);
                case U -> utm = new ClassicTuringMachine(machine);
            }
            if (isAnimated) {
                utm.display();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method runs the Universal Turing Machine (UTM) with the given inputs.
     * It utilizes the encapsulated method runTuringMachine in the TuringMachineHelper class
     * to execute the UTM or specific Turing machines based on their types.
     *
     * @param inputs the initial values of the tape
     */
    @Override
    public void runUTM(String inputs) {
        // Run the appropriate type of Turing machine based on the machine type
        switch (machineType) {
            case LR, U -> {
                utm.loadInput(inputs);
                helper.runTuringMachine(utm, isAnimated);
            }
            case BB -> helper.runTuringMachine(utm, isAnimated);
        }
    }
}
