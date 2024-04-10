import utm.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

/**
 * The TuringMachineHelper class assists in building Turing machines, including reading data,
 * configuring Turing machines, and running UniversalTuring machines.
 * This class contains three methods:
 * 1. loadRulesFromFile
 * 2. createTuringMachine
 * 3. runTuringMachine
 */
public class TuringMachineHelper {

    /**
     * Properties associated with the Turing machine configuration.
     * This can include various parameters and settings.
     */
    private Properties properties = new Properties();
    /**
     * Rules defining the behavior of the Turing machine.
     * Each rule represents a transition from one state to another based on the current symbol.
     */
    private ArrayList<String> rules = new ArrayList<>();

    /**
     * This method reads data from a file via an input stream, stores properties as key-value pairs using properties.getProperty() method,
     * and splits rules and saves them. Finally, it returns the type of the Turing machine read and the MachineType judgment.
     *
     * @param fileName the file name to read data from
     * @return the type of Turing machine read in MachineType
     * @throws IOException if an I/O error occurs
     */
    public MachineType loadRulesFromFile(String fileName) throws IOException {
        try {
            properties.load(new FileInputStream(fileName));
        } catch (IOException e) {
            System.err.println();
        }
        String[] ruleParts = properties.getProperty("rules").split("<>");
        rules.addAll(Arrays.asList(ruleParts));
        return MachineType.convertStringToType(properties.getProperty("variant"));
    }

    /**
     * This method configures the initial, accept, and reject states of the Turing machine, as well as the running rules.
     * It retrieves the initial, accept, and reject states from properties.getProperty() method and configures the rules of the Turing machine
     * by iterating through the Rules string array.
     *
     * @return the configured Turing machine object
     */
    public TuringMachine createTuringMachine() {
        int count = rules.size();
        String initialState = properties.getProperty("initialState");
        String acceptState = properties.getProperty("acceptState");
        String rejectState = properties.getProperty("rejectState");
        TuringMachine machine = new TuringMachine(count, initialState, acceptState, rejectState);
        for (String rule : rules) {
            Move moveRule = switchMoveTypeHelper(rule.split(",")[4]);
            machine.addRule(rule.split(",")[0], rule.split(",")[1].toCharArray()[0], rule.split(",")[2],
                    rule.split(",")[3].toCharArray()[0], moveRule);
        }
        return machine;
    }

    /**
     * This method runs the UniversalTuringMachine and plays the animation if needed.
     * It iterates through Turing machine rules, finds update modes based on input via a switch statement,
     * and updates the Turing machine and its animation.
     *
     * @param utm        the UniversalTuringMachine for animation demonstration
     * @param isAnimated controls whether the Turing machine animation is played
     */
    public void runTuringMachine(UniversalTuringMachine utm, boolean isAnimated) {
        TuringMachine machine = utm.getTuringMachine();
        Head head = machine.getHead();
        while (true) {
            // Get the current state
            String currentState = head.getCurrentState();
            // Get the current symbol
            char currentCell = machine.getTape().get(head.getCurrentCell());
            // Initialize variables to hold the new state, new cell symbol, and move direction
            String[][] machineRules = machine.getRules();
            String newState = null;
            char newCell = ' ';
            Move move = null;
            // Iterate over the rules to find a matching rule
            for (String[] machineRule : machineRules) {
                // If the current state and symbol match a rule
                if (machineRule[0].equals(currentState) && machineRule[1].charAt(0) == currentCell) {
                    // Get the new state, new cell symbol, and move direction from the rule
                    newState = machineRule[2];
                    newCell = machineRule[3].charAt(0);
                    // Determine the move direction based on the rule
                    move = switchMoveTypeHelper(machineRule[4]);
                    break;
                }
            }
            // If a matching rule was found
            if (newState != null) {
                // Write the new symbol on the current cell
                utm.writeOnCurrentCell(newCell);
                // Move the head of the Turing machine
                utm.moveHead(move, isAnimated);
                // Update the state
                utm.updateHeadState(newState);
                // Check for halting
                if (newState.equals(machine.getAcceptState()) || newState.equals(machine.getRejectState())) {
                    // Reached halting state
                    utm.displayHaltState(newState.equals(utm.getTuringMachine().getAcceptState())
                            ? HaltState.ACCEPTED : HaltState.REJECTED);
                    break;
                }
            } else {
                // No matching rule
                utm.displayHaltState(HaltState.REJECTED);
                break;
            }
        }
    }

    /**
     * this is the helper function, switch-cases exists in above programs multiple times
     *
     * @param string the input string
     * @return move return a specific Move type that can be handled
     */
    private Move switchMoveTypeHelper(String string) {
        Move move;
        switch (string) {
            case "RIGHT" -> move = MoveClassical.RIGHT;
            case "LEFT" -> move = MoveClassical.LEFT;
            case "RESET" -> move = ExtendedMoveClassical.RESET;
            default -> throw new IllegalArgumentException("Invalid type: " + string);
        }
        return move;
    }
}


