package com.test.instructions.stack;

import com.test.dataarea.Frame;
import com.test.dataarea.OperandStack;
import com.test.dataarea.Slot;
import com.test.instructions.base.NoOperandsInstruction;

/*
bottom -> top
[...][c][b][a]
          \/
          /\
         V  V
[...][c][a][b]
*/
// swap 指令交换栈顶的两个变量
public class Swap extends NoOperandsInstruction {
    @Override
    public void Execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        Slot slot1 = stack.popSlot();
        Slot slot2 = stack.popSlot();
        stack.pushSlot(slot1);
        stack.pushSlot(slot2);
    }
}
