import utm.*;

/**
 * This enumeration represents the extended movements that a Turing machine can perform.
 * It implements the Move interface, which means it provides the specific implementations for the types of moves a Turing machine can make.
 * By implementing the Move interface, this enumeration is ensuring that it adheres to a specific contract of behaviors that any "Move" should have.
 * This is beneficial as it provides a clear and consistent structure for different types of moves, and allows for the possibility of adding more types of moves in the future.
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

