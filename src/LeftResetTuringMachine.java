import utm.*;

/**
 * This class represents a Left Reset Turing Machine.
 */
public class LeftResetTuringMachine extends UniversalTuringMachine implements Move {
    /**
     * Constructs a Left Reset Turing Machine and initializes it.
     *
     * @param machine the Turing machine bound to this Left Reset Turing Machine
     * @param inputs  the input string for the Turing machine
     */
    public LeftResetTuringMachine(TuringMachine machine, String inputs) {
        super();
        // Load the given Turing machine
        this.loadTuringMachine(machine);
        // Load the input tape with the provided inputs
        this.loadInput(inputs);
    }

    /**
     * The method moveHead is used to determine the type of movement and whether a reset operation should be performed.
     * Moves the tape head of the Turing machine according to the specified direction.
     *
     * @param move       the direction in which the Turing machine head should move
     * @param isAnimated controls whether animation should be played
     */
    @Override
    public void moveHead(Move move, boolean isAnimated) {
        // Check if the move is not a right move
        if (move != MoveClassical.RIGHT) {
            // If it's a RESET move, reset the tape head to the leftmost position
            this.getTuringMachine().getHead().reset();
        } else {
            // If it's not a RESET move, call the moveHead method of the superclass
            super.moveHead(MoveClassical.RIGHT, isAnimated);
        }
    }
}


