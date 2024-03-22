import utm.*;

import static utm.MoveClassical.RIGHT;

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
public class ExtendedUtm extends UniversalTuringMachine {
    public static void main(String[] args) {
        UniversalTuringMachine utm1 = new UniversalTuringMachine();
        MoveClassical right = RIGHT;
        TuringMachine machine1 = new TuringMachine(3, "q0", "qa", "qr");
        machine1.addRule("q0", '0', "qa", '0', right);
        machine1.addRule("q0", '1', "qr", '1', right);
        machine1.addRule("q0", '*', "qr", '*', right);
        utm1.loadTuringMachine(machine1);
        String inputs = "01*X";
        utm1.loadInput(inputs);
        utm1.display();

        Head head = machine1.getHead();
        while (true) {
            //获取当前状态
            String currentState = head.getCurrentState();
            //获取当前符号
            char currentCell = machine1.getTape().get(head.getCurrentCell());
            //查找对应规则
            String[][] rules = machine1.getRules();
            String newState = null;
            char newCell = ' ';
            MoveClassical move = null;
            for (int i = 0; i < rules.length; i++) {
                if (rules[i][0].equals(currentState) && rules[i][1].charAt(0) == currentCell) {
                    newState = rules[i][2];
                    newCell = rules[i][3].charAt(0);
                    move = MoveClassical.valueOf(rules[i][4]);
                    break;
                }
            }

            if (newState != null) {
                //规则存在
                //更新单元格
                utm1.writeOnCurrentCell(newCell);

                //移动磁头
                if (move == right) {
                    utm1.moveHead(right, true);
                } else {
                    utm1.moveHead(MoveClassical.LEFT, true);
                }

                //跟新状态
                utm1.updateHeadState(newState);

                //检查是否停机
                if (newState.equals(machine1.getAcceptState()) || newState.equals(machine1.getRejectState())) {
                    //到达停机状态
                    utm1.displayHaltState(newState.equals(utm1.getTuringMachine().getAcceptState())
                            ? HaltState.ACCEPTED : HaltState.REJECTED);
                    break;
                }
            } else {
                //没有匹配规则
                utm1.displayHaltState(HaltState.REJECTED);
                break;
            }
        }

    }
}
