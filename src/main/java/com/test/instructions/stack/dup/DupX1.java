package com.test.instructions.stack.dup;

import com.test.dataarea.Frame;
import com.test.dataarea.OperandStack;
import com.test.dataarea.Slot;
import com.test.instructions.base.NoOperandsInstruction;

/*
bottom -> top
[...][c][b][a]
          __/
         |
         V
[...][c][a][b][a]
*/
// 复制操作数栈栈顶变量，并插入到操作数栈第二个变量后面
public class DupX1 extends NoOperandsInstruction {
    @Override
    public void Execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        Slot slot1 = stack.popSlot();
        Slot slot2 = stack.popSlot();
        stack.pushSlot(slot1);
        stack.pushSlot(slot2);
        stack.pushSlot(slot1);

    }
}
