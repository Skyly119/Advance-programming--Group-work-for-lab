import utm.TuringMachine;
import utm.UniversalTuringMachine;

/**
 * This class represents a typical version of Turing Machine
 * By extending the UniversalTuringMachine class, this class inherits all its methods and properties
 */
public class ClassicTuringMachine extends UniversalTuringMachine {

    /**
     * Constructs a typical Turing Machine and initializes it.
     *
     * @param machine the Turing machine given
     */
    public ClassicTuringMachine(TuringMachine machine) {
        this.loadTuringMachine(machine);
    }
}
