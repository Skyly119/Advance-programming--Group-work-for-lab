import utm.*;

public class BusyBeaverTuringMachine extends UniversalTuringMachine {
    public BusyBeaverTuringMachine(TuringMachine machine) {
        super();
        this.loadTuringMachine(machine);
        this.loadInput("00000000000000000000");
        for (int i = 0; i < 10; i++) {
            this.moveHead(MoveClassical.RIGHT, false);
        }
    }
}