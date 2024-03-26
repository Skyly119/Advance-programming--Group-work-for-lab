import utm.*;
public enum ExtendedMoveClassical implements Move{
    RIGHT(MoveClassical.RIGHT),
    LEFT(MoveClassical.LEFT),
    RESET;

    private MoveClassical moveClassical;

    ExtendedMoveClassical(MoveClassical moveClassical) {
        this.moveClassical = moveClassical;
    }

    ExtendedMoveClassical() {
    }

    public MoveClassical getMoveClassical() {
        return moveClassical;
    }
}
