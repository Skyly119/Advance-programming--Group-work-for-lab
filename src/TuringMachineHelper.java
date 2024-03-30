import utm.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

public class TuringMachineHelper {
    public Properties properties = new Properties();
    public ArrayList<String> rules = new ArrayList<>();

    // 这个方法同时返回判断的machineType种类
    public MachineType loadRulesFromFile(String fileName) throws IOException {
        try {
            properties.load(new FileInputStream(fileName));
        } catch (IOException e) {
            System.out.println();
        }
        String[] ruleParts = properties.getProperty("rules").split("<>");
        rules.addAll(Arrays.asList(ruleParts));
        // TODO:返回判断的machineType种类
        return MachineType.convertStringToType(properties.getProperty("type"));
    }

    public TuringMachine createTuringMachine() {
        int count = rules.size();
        String initialState = properties.getProperty("initialState");
        String acceptState = properties.getProperty("acceptState");
        String rejectState = properties.getProperty("rejectState");
        TuringMachine machine = new TuringMachine(count, initialState, acceptState, rejectState);
        for (String rule : rules) {
            Move moveRule = switch (rule.split(",")[4]) {
                case "RIGHT" -> MoveClassical.RIGHT;
                case "LEFT" -> MoveClassical.LEFT;
                case "RESET" -> ExtendedMoveClassical.RESET;
                default -> throw new IllegalArgumentException("Invalid move rule: " + rule.split(",")[4]);
            };
            machine.addRule(rule.split(",")[0], rule.split(",")[1].toCharArray()[0], rule.split(",")[2],
                    rule.split(",")[3].toCharArray()[0], moveRule);
        }
        return machine;
    }

    //classical turing machine
    public void runTuringMachine(UniversalTuringMachine utm, boolean isAnimated) {
        TuringMachine machine = utm.getTuringMachine();
        Head head = machine.getHead();
        utm.display();
        while (true) {
            //获取当前状态
            String currentState = head.getCurrentState();
            //获取当前符号
            char currentCell = machine.getTape().get(head.getCurrentCell());
            //查找对应规则
            String[][] machineRules = machine.getRules();
            String newState = null;
            char newCell = ' ';
            ExtendedMoveClassical move = null;
            for (String[] machineRule : machineRules) {
                if (machineRule[0].equals(currentState) && machineRule[1].charAt(0) == currentCell) {
                    newState = machineRule[2];
                    newCell = machineRule[3].charAt(0);
                    switch (machineRule[4]) {
                        case "RIGHT" -> move = ExtendedMoveClassical.RIGHT;
                        case "LEFT" -> move = ExtendedMoveClassical.LEFT;
                        case "RESET" -> move = ExtendedMoveClassical.RESET;
                        default -> throw new IllegalArgumentException("Invalid machine rule: " + machineRule[4]);
                    }
                    break;
                }
            }
            if (newState != null) {
                //规则存在
                //更新单元格
                utm.writeOnCurrentCell(newCell);
                //移动磁头
                switch (move) {
                    case RIGHT -> utm.moveHead(MoveClassical.RIGHT, isAnimated);
                    case LEFT -> utm.moveHead(MoveClassical.LEFT, isAnimated);
                    case RESET -> utm.moveHead(ExtendedMoveClassical.RESET, isAnimated);
                    default -> throw new IllegalArgumentException("Invalid move : " + move);
                }
                //跟新状态
                utm.updateHeadState(newState);
                //检查是否停机
                if (newState.equals(machine.getAcceptState()) || newState.equals(machine.getRejectState())) {
                    //到达停机状态
                    utm.displayHaltState(newState.equals(utm.getTuringMachine().getAcceptState())
                            ? HaltState.ACCEPTED : HaltState.REJECTED);
                    break;
                }
            } else {//没有匹配规则
                utm.displayHaltState(HaltState.REJECTED);
                break;
            }
        }
    }
}
