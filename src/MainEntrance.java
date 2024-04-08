import org.junit.Test;
import utm.*;
import utmeditor.UTMController;
import utmeditor.UTMEditor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import static org.junit.Assert.assertEquals;


/**
 * This is the main class of the application which extends the UniversalTuringMachine class.
 * By extending the UniversalTuringMachine, this class inherits all its methods and properties,
 * and can leverage its functionality to run a Turing machine.
 */
public class MainEntrance extends UniversalTuringMachine {
    static TuringMachine machine;
    static String dir = System.getProperty("user.dir");
    static String examplePath = dir + "/src/Example/";

    /**
     * MainEntrance method to execute a Turing machine.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        boolean isAnimated;
        if (args.length == 0) {
            Controller controller1 = new Controller(true,machine);
            UTMEditor utmEditor = new UTMEditor();
            utmEditor.setUTMController(controller1);
            return;
        }else{
            if (args.length != 3) {
                throw new IllegalArgumentException("Incorrect number of parameters. Expected 3, got " + args.length);
            }
            if (args[2].equals("--animation")) {
                isAnimated = true;
            } else if (args[2].equals("--noanimation")) {
                isAnimated = false;
            } else {
                throw new IllegalArgumentException("Unknown argument: " + args[2]);
            }
            Controller controller2 = new Controller(isAnimated,machine);
            String fileName = args[0];
            String inputs = args[1];
            controller2.loadTuringMachineFrom(fileName);
            controller2.runUTM(inputs);
        }
        if (!isAnimated) {
            System.exit(0);
        }
    }

    @Test
    public void TestLrAll1() {
        MainEntrance.main(new String[]{
                examplePath + "bb-2.desc",
                "111110",
                "--animation"
        });
//        Tape t = new Tape("011110");
//        assertEquals(t, machine.getTape());
    }
}
