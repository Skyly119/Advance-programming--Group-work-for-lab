import utm.*;

//定义一个左重置图灵机,继承通用图灵机,实现Move接口

public class LeftResetTuringMachine extends UniversalTuringMachine implements Move {
    //定义一个左重置图灵机的构造函数
    public LeftResetTuringMachine() {
        super();
    }

    @Override
    public void moveHead(Move move, boolean isAnimated) {
        // 判断move是否为RESET
        if (move != MoveClassical.RIGHT) {
            // 如果是RESET，将磁头移动到磁带的最左边
            this.getTuringMachine().getHead().reset();
        }
        else {
            // 如果不是RESET，调用父类的moveHead方法
                super.moveHead(MoveClassical.RIGHT, isAnimated);
        }
    }
}


