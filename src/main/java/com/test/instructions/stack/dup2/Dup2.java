package com.test.instructions.stack.dup2;

import com.test.dataarea.Frame;
import com.test.dataarea.OperandStack;
import com.test.dataarea.Slot;
import com.test.instructions.base.NoOperandsInstruction;

/*
bottom -> top
[...][c][b][a]____
          \____   |
               |  |
               V  V
[...][c][b][a][b][a]
*/
// 将操作数栈前两个元素看做整体并复制，然后将复制的元素插入的这一整体的后面
public class Dup2 extends NoOperandsInstruction {
    @Override
    public void Execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        Slot slot1 = stack.popSlot();
        Slot slot2 = stack.popSlot();
        stack.pushSlot(slot2);
        stack.pushSlot(slot1);
        stack.pushSlot(slot2);
        stack.pushSlot(slot1);
    }
}
