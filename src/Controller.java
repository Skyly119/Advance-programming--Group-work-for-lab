import utm.TuringMachine;
import utm.UniversalTuringMachine;
import utmeditor.UTMController;

import java.io.IOException;

/**
 * @author xue
 * @date 2024/4/7 21:33:09
 */
public class Controller implements UTMController {
    // 需要从外界传进来isAnimated, machine
    public Controller(boolean isAnimated, TuringMachine machine) {
        this.isAnimated = isAnimated;
        this.machine = machine;
    }

    private boolean isAnimated;
    private TuringMachine machine;
    private TuringMachineHelper helper;
    private MachineType machineType;

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
