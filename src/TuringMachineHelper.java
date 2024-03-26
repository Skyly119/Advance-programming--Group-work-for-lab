import utm.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import static utm.MoveClassical.LEFT;
import static utm.MoveClassical.RIGHT;

public class TuringMachineHelper {

    public HashMap<String, String> properties = new HashMap<>();
    public ArrayList<String> rules = new ArrayList<>();

    public void loadRulesFromFile(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("rules=")) {
                String[] ruleParts = line.substring(6).split("<>");
                for (String rule : ruleParts) {
                    rules.add(rule);
                }
            }
            else if (line.startsWith(" ")||line.startsWith("#")){
            }
            else if (line.startsWith("initialState")||line.startsWith("acceptState")||line.startsWith("rejectState")){
                String[] parts = line.split("=");
                properties.put(parts[0], parts[1]);
            }
        }
        reader.close();
    }

    public TuringMachine createTuringMachine() {
        int count = rules.size();
        String initialState = properties.get("initialState");
        String acceptState = properties.get("acceptState");
        String rejectState = properties.get("rejectState");
        TuringMachine machine = new TuringMachine(count,initialState,acceptState, rejectState);
        for (String rule : rules) {
            Move moveRule;
            if (rule.split(",")[4].equals("RIGHT")) {
                moveRule = MoveClassical.RIGHT;
            } else if (rule.split(",")[4].equals("LEFT")) {
                moveRule = MoveClassical.LEFT;
            } else if (rule.split(",")[4].equals("RESET")) {
                moveRule = ExtendedMoveClassical.RESET;
            } else {
                throw new IllegalArgumentException("Invalid move rule: " + rule.split(",")[4]);
            }
            machine.addRule(rule.split(",")[0], rule.split(",")[1].toCharArray()[0],rule.split(",")[2],
                    rule.split(",")[3].toCharArray()[0], moveRule);
        }
        return machine;
    }

    //classical turing machine
    public void runTuringMachine(UniversalTuringMachine utm, TuringMachine machine, String inputs) {
        utm.loadTuringMachine(machine);
        utm.loadInput(inputs);
        utm.display();
        Head head = machine.getHead();
        while(true){
            //获取当前状态
            String currentState = head.getCurrentState();
            //获取当前符号
            char currentCell = machine.getTape().get(head.getCurrentCell());
            //查找对应规则
            String[][] machineRules = machine.getRules();
            String newState = null;
            char newCell = ' ';
            Move move = null;
            for(int i=0; i< machineRules.length; i++){
                if(machineRules[i][0].equals(currentState) && machineRules[i][1].charAt(0) == currentCell){
                    newState = machineRules[i][2];
                    newCell = machineRules[i][3].charAt(0);
                    if (machineRules[i][4].equals("RESET")) {
                        move = ExtendedMoveClassical.RESET;
                    } else {
                        move = MoveClassical.valueOf(machineRules[i][4]);
                    }
                    break;
                }
            }
            if(newState != null){
                //规则存在
                //更新单元格
                utm.writeOnCurrentCell(newCell);
                //移动磁头
                if(move == RIGHT){
                    utm.moveHead(RIGHT,true);
                }else {
                    utm.moveHead(MoveClassical.LEFT ,true);
                }
                //跟新状态
                utm.updateHeadState(newState);
                //检查是否停机
                if(newState.equals(machine.getAcceptState()) || newState.equals(machine.getRejectState())){
                    //到达停机状态
                    utm.displayHaltState(newState.equals(utm.getTuringMachine().getAcceptState())
                            ? HaltState.ACCEPTED : HaltState.REJECTED);
                    break;
                }
            }else {//没有匹配规则
                utm.displayHaltState(HaltState.REJECTED);
                break;
            }
        }
    }
    //left reset turing machine

    public void runTuringMachine(LeftResetTuringMachine lrtm, TuringMachine machine, String inputs) {
        lrtm.loadTuringMachine(machine);
        lrtm.loadInput(inputs);
        lrtm.display();
        Head head = machine.getHead();
        while(true){
            //获取当前状态
            String currentState = head.getCurrentState();
            //获取当前符号
            char currentCell = machine.getTape().get(head.getCurrentCell());
            //查找对应规则
            String[][] machineRules = machine.getRules();
            String newState = null;
            char newCell = ' ';
            Move move = null;
            for(int i=0; i< machineRules.length; i++){
                if(machineRules[i][0].equals(currentState) && machineRules[i][1].charAt(0) == currentCell){
                    newState = machineRules[i][2];
                    newCell = machineRules[i][3].charAt(0);
                    if (machineRules[i][4].equals("RESET")) {
                        move = ExtendedMoveClassical.RESET;
                    } else {
                        move = MoveClassical.valueOf(machineRules[i][4]);
                    }
                    break;
                }
            }
            if(newState != null){
                //规则存在
                //更新单元格
                lrtm.writeOnCurrentCell(newCell);
                //移动磁头
                if(move == ExtendedMoveClassical.RESET){
                    lrtm.moveHead(ExtendedMoveClassical.RESET,true);
                }else if(move == MoveClassical.RIGHT){
                    lrtm.moveHead(MoveClassical.RIGHT,true);
                }
                //跟新状态
                lrtm.updateHeadState(newState);
                //检查是否停机
                if(newState.equals(machine.getAcceptState()) || newState.equals(machine.getRejectState())){
                    //到达停机状态
                    lrtm.displayHaltState(newState.equals(lrtm.getTuringMachine().getAcceptState())
                            ? HaltState.ACCEPTED : HaltState.REJECTED);
                    break;
                }
            }else {//没有匹配规则
                lrtm.displayHaltState(HaltState.REJECTED);
                break;
            }
        }
    }
    //busy beaver turing machine

    public void runTuringMachine(BusyBeaverTuringMachine bbtm, TuringMachine machine, String inputs){
        bbtm.loadTuringMachine(machine);
        bbtm.loadInput(inputs);
        bbtm.display();
        Head head = machine.getHead();
        // 初始化纸带
        bbtm.loadInput("00000000000000000000");
        // 每次开始新的执行周期时，将磁头重新设置到第10个单元格
        for (int i = 0; i < 10; i++) {
            bbtm.moveHead(MoveClassical.RIGHT, false);
        }
        while(true){
            //获取当前状态
            String currentState = head.getCurrentState();
            //获取当前符号
            char currentCell = machine.getTape().get(head.getCurrentCell());
            //查找对应规则
            String[][] machineRules = machine.getRules();
            String newState = null;
            char newCell = ' ';
            Move move = null;
            for(int i=0; i< machineRules.length; i++){
                if(machineRules[i][0].equals(currentState) && machineRules[i][1].charAt(0) == currentCell){
                    newState = machineRules[i][2];
                    newCell = machineRules[i][3].charAt(0);
                    if (machineRules[i][4].equals("RESET")) {
                        move = ExtendedMoveClassical.RESET;
                    } else {
                        move = MoveClassical.valueOf(machineRules[i][4]);
                    }
                    break;
                }
            }
            if(newState != null){
                //规则存在
                //更新单元格
                bbtm.writeOnCurrentCell(newCell);
                //移动磁头
                if(move == RIGHT){
                    bbtm.moveHead(RIGHT,true);
                }else {
                    bbtm.moveHead(MoveClassical.LEFT ,true);
                }
                //跟新状态
                bbtm.updateHeadState(newState);
                //检查是否停机
                if(newState.equals(machine.getAcceptState()) || newState.equals(machine.getRejectState())){
                    //到达停机状态
                    bbtm.displayHaltState(newState.equals(bbtm.getTuringMachine().getAcceptState())
                            ? HaltState.ACCEPTED : HaltState.REJECTED);
                    break;
                }
            }else {//没有匹配规则
                bbtm.displayHaltState(HaltState.REJECTED);
                break;
            }
        }
    }
}