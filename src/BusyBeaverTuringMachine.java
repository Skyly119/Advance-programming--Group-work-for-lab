import utm.*;

/**
 * This class represents a Busy Beaver Turing Machine.
 */
public class BusyBeaverTuringMachine extends UniversalTuringMachine {

    /**
     * Constructs a Busy Beaver Turing Machine and initializes it.
     *
     * @param machine the Turing machine loaded in the Busy Beaver Turing Machine
     */
    public BusyBeaverTuringMachine(TuringMachine machine) {
        super();
        // Load the given Turing machine
        this.loadTuringMachine(machine);
        // Load a default input tape with 20 zeros
        this.loadInput("00000000000000000000");
        // Move the tape head 10 positions to the right
        for (int i = 0; i < 10; i++) {
            this.moveHead(MoveClassical.RIGHT, false);
        }
    }
}