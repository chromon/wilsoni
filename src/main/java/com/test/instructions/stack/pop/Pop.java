package com.test.instructions.stack.pop;

import com.test.dataarea.Frame;
import com.test.instructions.base.NoOperandsInstruction;

// 将操作数栈栈顶变量弹出
public class Pop extends NoOperandsInstruction {
    // POP 指令只能用于弹出 int、float 等占用一个操作数栈位置的变量
    @Override
    public void Execute(Frame frame) {
        frame.getOperandStack().popSlot();
    }
}
