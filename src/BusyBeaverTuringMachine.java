import utm.*;

/**
 * This class represents a Busy Beaver Turing Machine, which is a type of Universal Turing Machine.
 * A Busy Beaver Turing Machine is a specific kind of Turing machine that is designed to maximize the number of steps taken or symbols printed before it halts.
 * By extending the UniversalTuringMachine class, this class inherits all its methods and properties, and can leverage its functionality to run a Busy Beaver Turing Machine.
 */
public class BusyBeaverTuringMachine extends UniversalTuringMachine {

    /**
     * Constructs a Busy Beaver Turing Machine and initializes it.
     * Load a default input tape with 20 zeros, and Move the tape head 10 positions to the right
     *
     * @param machine the Turing machine loaded in the Busy Beaver Turing Machine
     */
    public BusyBeaverTuringMachine(TuringMachine machine) {
        super();
        // Load the given Turing machine
        this.loadTuringMachine(machine);
        this.loadInput("00000000000000000000");
        for (int i = 0; i < 10; i++) {
            this.moveHead(MoveClassical.RIGHT, false);
        }
    }
}
