import utm.*;

/**
 * This class represents a Left Reset Turing Machine, which is a type of Universal Turing Machine.
 * In a Left Reset Turing Machine, the head of the machine resets to the leftmost position whenever a move other than a right move is performed.
 * The reason for extending the UniversalTuringMachine class is that only needs to override the part of head movement.
 * This reduces code duplication and improves code maintainability and readability.
 */
public class LeftResetTuringMachine extends UniversalTuringMachine {
    /**
     * Constructs a Left Reset Turing Machine and initializes it.
     *
     * @param machine the Turing machine bound to this Left Reset Turing Machine
     */
    public LeftResetTuringMachine(TuringMachine machine) {
        loadTuringMachine(machine);
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
        super.moveHead(move, isAnimated);
        if (move.equals(ExtendedMoveClassical.RESET)) {
            getTuringMachine().getHead().reset();
        }
        if (move.equals(MoveClassical.LEFT)) {
            throw new IllegalArgumentException("Unexpected move: can not move left");
        }
    }
}


