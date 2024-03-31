import utm.*;

/**
 * Represents the types of movement behavior of Turing machines with extended functionality.
 * These movements include moving the tape head to the right, left, or resetting its position.
 */
public enum ExtendedMoveClassical implements Move {

    /**
     * Represents the movement of the tape head to the right.
     */
    RIGHT,

    /**
     * Represents the movement of the tape head to the left.
     */
    LEFT,

    /**
     * Represents the resetting of the tape head's position.
     */
    RESET;
}

