import static java.lang.System.exit;

public enum MachineType {
    LR,
    BB,
    U;

    public static MachineType convertStringToType(String string) {
        return switch (string) {
            case "LR" -> LR;
            case "BB" -> BB;
            case "U" -> U;
            default -> throw new IllegalArgumentException("Error: None acceptable type");
        };
    }
}
