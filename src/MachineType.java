/**
 * Represents the types of turning machines.
 */
public enum MachineType {
    LR,
    BB,
    U;

    /**
     * Converts the given string representation of a machine type into the corresponding enum value.
     *
     * @param string The string representation of the machine type (e.g., "LR", "BB", or "U").
     * @return The enum value corresponding to the given string representation.
     * @throws IllegalArgumentException If the given string does not match any acceptable type.
     */
    public static MachineType convertStringToType(String string) {
        return switch (string) {
            case "LR" -> LR;
            case "BB" -> BB;
            case "U" -> U;
            default -> throw new IllegalArgumentException("Error: None acceptable type");
        };
    }
}
