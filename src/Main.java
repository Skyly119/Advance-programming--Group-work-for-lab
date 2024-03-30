import utm.*;

import java.io.IOException;


/*
A file describing a TM that decides whether 0 is the first symbol of an input string:
UTM= Given an input <TM,w>, where TM is a Turing machine and w is an input string:

Load w onto the tape of TM.

Move the head of TM to the leftmost symbol of w.

Set the state of TM to q0 (i.e., the initial state specified in the description of TM).

At each time step, execute TM as follows:
(1) Read the symbol from the current cell pointed by the head of TM.
(2) Look up the rules associated with the current state and the symbol read. These rules are specified in the description of TM.
(3) Replace the current cell with the symbol specified by the triggered rule.
(4) Move the head of TM one cell to the right or left based on the movement specified by the triggered rule.
(5) Update the state of TM according to the triggered rule.
(6) Repeat steps (1)-(5) until the state of TM is qa or qr. States qa and qr are acceptance and rejection states, respectively, specified in the description of TM.

If TM ends in state qa, it halts and accepts the input w. If TM ends in state qr, it halts and rejects w. If neither of these states is reached, TM will loop indefinitely.

Check the state of TM. If TM halts, UTM halts; otherwise, UTM will continue indefinitely.
*/
/**
 * Main class to run a Turing machine.
 */
public class Main extends UniversalTuringMachine {

    /**
     * Main method to execute a Turing machine.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        // Define file name, input string, and animation flag
        String fileName = "src\\Example\\bb-2.desc";
        String inputs = "01*X";
        boolean isAnimated = false;

        try {
            // Load rules from the file and determine the type of machine
            TuringMachineHelper helper = new TuringMachineHelper();
            MachineType machineType = helper.loadRulesFromFile(fileName);
            TuringMachine machine = helper.createTuringMachine();

            // Run the appropriate type of Turing machine based on the machine type
            switch (machineType) {
                case LR -> helper.runTuringMachine(new LeftResetTuringMachine(machine, inputs), isAnimated);
                case BB -> helper.runTuringMachine(new BusyBeaverTuringMachine(machine), isAnimated);
                case U -> {
                    UniversalTuringMachine universalTuringMachine = new UniversalTuringMachine();
                    universalTuringMachine.loadTuringMachine(machine);
                    universalTuringMachine.loadInput(inputs);
                    helper.runTuringMachine(universalTuringMachine, isAnimated);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
