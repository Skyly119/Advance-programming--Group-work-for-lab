package utm;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import static utm.MoveClassical.LEFT;
import static utm.MoveClassical.RIGHT;

public class Main {
    public static void main(String[] args) {
        String filename = "example-2.desc";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            HashMap<String, String> properties = new HashMap<>();
            ArrayList<String> rules = new ArrayList<>();
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
            int count = 0;
            String initialState = properties.get("initialState");
            String acceptState = properties.get("acceptState");
            String rejectState = properties.get("rejectState");
            for (String rule : rules) {
                System.out.println(rule);
                count++;
            }
            UniversalTuringMachine utm1 = new UniversalTuringMachine();
            TuringMachine machine1 = new TuringMachine(count,initialState,acceptState, rejectState);
            for (String rule : rules) {
                MoveClassical moverule = RIGHT;
                if (rule.split(",")[4].equals("RIGHT")) {
                    moverule = RIGHT;
                }
                else {
                    moverule = LEFT;
                }
                machine1.addRule(rule.split(",")[0], rule.split(",")[1].toCharArray()[0],rule.split(",")[2],
                        rule.split(",")[3].toCharArray()[0], moverule);
                System.out.println(rule.split(",")[0]+rule.split(",")[1].toCharArray()[0]+rule.split(",")[2]+
                        rule.split(",")[3].toCharArray()[0] +moverule);
            }
            utm1.loadTuringMachine(machine1);
            String inputs = "01**X";
            utm1.loadInput(inputs);
            utm1.display();
            Head head = machine1.getHead();
            while(true){
                //获取当前状态
                String currentState = head.getCurrentState();
                //获取当前符号
                char currentCell = machine1.getTape().get(head.getCurrentCell());
                //查找对应规则
                String[][] machine1Rules = machine1.getRules();
                String newState = null;
                char newCell = ' ';
                MoveClassical move = null;
                for(int i=0; i< machine1Rules.length; i++){
                    if(machine1Rules[i][0].equals(currentState) && machine1Rules[i][1].charAt(0) == currentCell){
                        newState = machine1Rules[i][2];
                        newCell = machine1Rules[i][3].charAt(0);
                        move = MoveClassical.valueOf(machine1Rules[i][4]);
                        break;
                    }
                }
                if(newState != null){
                    //规则存在
                    //更新单元格
                    utm1.writeOnCurrentCell(newCell);
                    //移动磁头
                    if(move == RIGHT){
                        utm1.moveHead(RIGHT,true);
                    }else {
                        utm1.moveHead(MoveClassical.LEFT ,true);
                    }
                    //跟新状态
                    utm1.updateHeadState(newState);
                    //检查是否停机
                    if(newState.equals(machine1.getAcceptState()) || newState.equals(machine1.getRejectState())){
                    //到达停机状态
                        utm1.displayHaltState(newState.equals(utm1.getTuringMachine().getAcceptState())
                                ? HaltState.ACCEPTED : HaltState.REJECTED);
                        break;
                    }
                }else {//没有匹配规则
                    utm1.displayHaltState(HaltState.REJECTED);
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
