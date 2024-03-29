import utm.*;
import java.io.IOException;


/*
A file describing a TM that decides whether 0 is the first symbol of an input string:
UTM=“在输入为<TM,w>的情况下，其中TM是一个图灵机，w是一个输入字符串：

将w加载到TM的磁带上。
将TM的头移动到w的最左边的符号。
将TM的状态设置为q0（即在TM的描述中指定的初始状态）。
在每个时间步骤中，执行TM如下：
(1) TM的头从当前指向的单元格（即当前单元格）读取符号。
(2) 查找与当前状态和读取的符号相关联的规则。这些规则在TM的描述中指定。
(3) TM的头用被触发规则指定的符号覆盖当前单元格。
(4) TM的头根据被触发规则指定的移动向右移动一个单元格或向左移动一个单元格。
(5) 根据被触发规则更新TM的状态。
(6) 重复(1)-(5)直到TM的状态为qa或qr。状态qa和qr分别是接受和拒绝状态，这些状态在TM的描述中指定。
如果TM最终处于qa，则TM已经停机并接受了输入w。如果TM最终处于qr，则TM已经停机并拒绝了w。如果没有达到这些状态中的任何一个，则TM将永远循环。
检查TM的状态。如果TM停机，则UTM停机；否则，UTM将永远执行下去。
 */
public class Main extends UniversalTuringMachine{
    public static void main(String[] args) {
        MachineType machineType;
        String filename = "src\\Example\\bb-2.desc";
        try {
            TuringMachineHelper helper = new TuringMachineHelper();
            helper.loadRulesFromFile(filename);
            TuringMachine machine = helper.createTuringMachine();
            machineType = MachineType.BB;
            String inputs = "01*X";
            switch (machineType){
                case LR -> helper.runTuringMachine(new LeftResetTuringMachine(machine,inputs));
                case BB -> helper.runTuringMachine(new BusyBeaverTuringMachine(machine));
                case U -> {
                    UniversalTuringMachine universalTuringMachine = new UniversalTuringMachine();
                    universalTuringMachine.loadTuringMachine(machine);
                    universalTuringMachine.loadInput(inputs);
                    helper.runTuringMachine(universalTuringMachine);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
