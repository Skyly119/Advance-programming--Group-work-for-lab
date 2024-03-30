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
 * 1. loadRulesFromFile: This method reads data from a file via an input stream, stores properties as key-value pairs using properties.getProperty() method,
 * and splits rules  and saves them. Finally, it returns the type of the Turing machine read and the MachineType judgment.
 * 2. createTuringMachine: This method configures the initial, accept, and reject states of the Turing machine, as well as the running rules.
 * It retrieves the initial, accept, and reject states from properties.getProperty() method and configures the rules of the Turing machine
 * by iterating through the Rules string array.
 * 3. runTuringMachine: This method runs the UniversalTuringMachine and plays the animation.
 * It iterates through Turing machine rules, finds update modes based on input via a switch statement, and updates the Turing machine and its animation.
 */
public class TuringMachineHelper {

    /**
     * Properties associated with the Turing machine configuration.
     * This can include various parameters and settings.
     */
    public Properties properties = new Properties();
    /**
     * Rules defining the behavior of the Turing machine.
     * Each rule represents a transition from one state to another based on the current symbol.
     */
    public ArrayList<String> rules = new ArrayList<>();

    /**
     * This method reads data from a file via an input stream, stores properties as key-value pairs using properties.getProperty() method,
     * and splits rules and saves them. Finally, it returns the type of the Turing machine read and the MachineType judgment.
     * @param fileName the file name to read data from
     * @return the type of Turing machine read
     * @throws IOException if an I/O error occurs
     */
    public MachineType loadRulesFromFile(String fileName) throws IOException {
        try {
            properties.load(new FileInputStream(fileName));
        } catch (IOException e) {
            System.out.println();
        }
        String[] ruleParts = properties.getProperty("rules").split("<>");
        rules.addAll(Arrays.asList(ruleParts));
        // TODO: Return the type of the Turing machine read
        return MachineType.convertStringToType(properties.getProperty("type"));
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
            Move moveRule = switch (rule.split(",")[4]) {
                case "RIGHT" -> MoveClassical.RIGHT;
                case "LEFT" -> MoveClassical.LEFT;
                case "RESET" -> ExtendedMoveClassical.RESET;
                default -> throw new IllegalArgumentException("Invalid move rule: " + rule.split(",")[4]);
            };
            machine.addRule(rule.split(",")[0], rule.split(",")[1].toCharArray()[0], rule.split(",")[2],
                    rule.split(",")[3].toCharArray()[0], moveRule);
        }
        return machine;
    }

    /**
     * This method runs the UniversalTuringMachine and plays the animation.
     * It iterates through Turing machine rules, finds update modes based on input via a switch statement,
     * and updates the Turing machine and its animation.
     * @param utm        the UniversalTuringMachine for animation demonstration
     * @param isAnimated controls whether the Turing machine animation is played
     */
    public void runTuringMachine(UniversalTuringMachine utm, boolean isAnimated) {
        TuringMachine machine = utm.getTuringMachine();
        Head head = machine.getHead();
        if (isAnimated) {
            utm.display();
        }
        while (true) {
            // Get the current state
            String currentState = head.getCurrentState();
            // Get the current symbol
            char currentCell = machine.getTape().get(head.getCurrentCell());
            // Look up corresponding rules
            String[][] machineRules = machine.getRules();
            String newState = null;
            char newCell = ' ';
            ExtendedMoveClassical move = null;
            for (String[] machineRule : machineRules) {
                if (machineRule[0].equals(currentState) && machineRule[1].charAt(0) == currentCell) {
                    newState = machineRule[2];
                    newCell = machineRule[3].charAt(0);
                    switch (machineRule[4]) {
                        case "RIGHT" -> move = ExtendedMoveClassical.RIGHT;
                        case "LEFT" -> move = ExtendedMoveClassical.LEFT;
                        case "RESET" -> move = ExtendedMoveClassical.RESET;
                        default -> throw new IllegalArgumentException("Invalid machine rule: " + machineRule[4]);
                    }
                    break;
                }
            }
            if (newState != null) {
                // Rule exists
                // Update the cell
                utm.writeOnCurrentCell(newCell);
                // Move the head
                switch (move) {
                    case RIGHT -> utm.moveHead(MoveClassical.RIGHT, isAnimated);
                    case LEFT -> utm.moveHead(MoveClassical.LEFT, isAnimated);
                    case RESET -> utm.moveHead(ExtendedMoveClassical.RESET, isAnimated);
                    default -> throw new IllegalArgumentException("Invalid move : " + move);
                }
                // Update the state
                utm.updateHeadState(newState);
                // Check for halting
                if (newState.equals(machine.getAcceptState()) || newState.equals(machine.getRejectState())) {
                    // Reached halting state
                    utm.displayHaltState(newState.equals(utm.getTuringMachine().getAcceptState())
                            ? HaltState.ACCEPTED : HaltState.REJECTED);
                    break;
                }
            } else {// No matching rule
                utm.displayHaltState(HaltState.REJECTED);
                break;
            }
        }
    }
}


