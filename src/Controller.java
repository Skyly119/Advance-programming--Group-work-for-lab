import utm.TuringMachine;
import utmeditor.UTMController;

import java.io.IOException;

/**
 * The Controller class is used to load the file and run different Turing machines according to the different machine types,
 * It implements the UTMController, which means that it can be set to the editor and control the animation by the UTM editor
 * This class implements two methods:
 * 1. loadTuringMachineFrom
 * 2. runUTM
 */
public class Controller implements UTMController {
    private boolean isAnimated;
    private TuringMachine machine;
    private TuringMachineHelper helper;
    private MachineType machineType;


    public Controller(boolean isAnimated, TuringMachine machine) {
        this.isAnimated = isAnimated;
        this.machine = machine;
    }

    /**
     * This method reads data from a file by using the encapsulated method loadRulesFromFile in the TuringMachineHelper class
     *
     * @param fileName the file name to read data from
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void loadTuringMachineFrom(String fileName) {
        try {
            // Load rules from the file and determine the type of machine
            helper = new TuringMachineHelper();
            machineType = helper.loadRulesFromFile(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method runs the UniversalTuringMachine according to the inputs by using the encapsulated method runTuringMachine in the TuringMachineHelper class
     * It can run different Turing machines by different machine types
     *
     * @param inputs Initial value of tape
     */
    @Override
    public void runUTM(String inputs) {
        machine = helper.createTuringMachine();
        // Run the appropriate type of Turing machine based on the machine type
        switch (machineType) {
            case LR -> helper.runTuringMachine(new LeftResetTuringMachine(machine, inputs), isAnimated);
            case BB -> helper.runTuringMachine(new BusyBeaverTuringMachine(machine), isAnimated);
            case U -> helper.runTuringMachine(new ClassicTuringMachine(machine, inputs), isAnimated);
        }
    }
}
