import utm.*;

import static utm.MoveClassical.RIGHT;

/**
 * ExtendedUtm class represents an extended Universal Turing Machine.
 * This class demonstrates the behavior of a Turing machine described in a file.
 * It loads the machine rules, initializes the machine, and runs it step by step.
 */
public class ExtendedUtm extends UniversalTuringMachine {

    /**
     * The main method to demonstrate the behavior of the Turing machine.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        // Create a new Universal Turing Machine
        UniversalTuringMachine utm1 = new UniversalTuringMachine();
        MoveClassical right = RIGHT;

        // Define the Turing machine and its rules
        TuringMachine machine1 = new TuringMachine(3, "q0", "qa", "qr");
        machine1.addRule("q0", '0', "qa", '0', right);
        machine1.addRule("q0", '1', "qr", '1', right);
        machine1.addRule("q0", '*', "qr", '*', right);

        // Load the Turing machine and its input
        utm1.loadTuringMachine(machine1);
        String inputs = "01*X";
        utm1.loadInput(inputs);

        // Display the initial state of the Turing machine
        utm1.display();

        // Run the Turing machine step by step
        Head head = machine1.getHead();
        while (true) {
            // Get the current state and symbol
            String currentState = head.getCurrentState();
            char currentCell = machine1.getTape().get(head.getCurrentCell());

            // Look up the corresponding rule
            String[][] rules = machine1.getRules();
            String newState = null;
            char newCell = ' ';
            MoveClassical move = null;
            for (String[] rule : rules) {
                if (rule[0].equals(currentState) && rule[1].charAt(0) == currentCell) {
                    newState = rule[2];
                    newCell = rule[3].charAt(0);
                    move = MoveClassical.valueOf(rule[4]);
                    break;
                }
            }

            if (newState != null) {
                // Rule exists
                // Update the cell
                utm1.writeOnCurrentCell(newCell);

                // Move the head
                if (move == right) {
                    utm1.moveHead(right, true);
                } else {
                    utm1.moveHead(MoveClassical.LEFT, true);
                }

                // Update the state
                utm1.updateHeadState(newState);

                // Check for halting
                if (newState.equals(machine1.getAcceptState()) || newState.equals(machine1.getRejectState())) {
                    // Reached halting state
                    utm1.displayHaltState(newState.equals(utm1.getTuringMachine().getAcceptState())
                            ? HaltState.ACCEPTED : HaltState.REJECTED);
                    break;
                }
            } else {
                // No matching rule
                utm1.displayHaltState(HaltState.REJECTED);
                break;
            }
        }
    }
}
