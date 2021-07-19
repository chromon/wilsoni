package com.test.instructions.stack.pop;

import com.test.dataarea.Frame;
import com.test.instructions.base.NoOperandsInstruction;

// 将操作数栈栈顶变量弹出
public class Pop2 extends NoOperandsInstruction {
    // POP2 指令可以用于弹出 double 和 long 类型占用两个操作数栈位置的变量
    @Override
    public void Execute(Frame frame) {
        frame.getOperandStack().popSlot();
        frame.getOperandStack().popSlot();
    }
}
