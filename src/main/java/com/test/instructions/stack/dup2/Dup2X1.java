package com.test.instructions.stack.dup2;

import com.test.dataarea.Frame;
import com.test.dataarea.OperandStack;
import com.test.dataarea.Slot;
import com.test.instructions.base.NoOperandsInstruction;

/*
bottom -> top
[...][c][b][a]
       _/ __/
      |  |
      V  V
[...][b][a][c][b][a]
*/
// 将操作数栈前两个元素看做整体并复制，然后将这一整体插入到操作数栈第三个元素后面
// (注：前两个元素看做整体时，可以理解为插入到第二个元素后面)
public class Dup2X1 extends NoOperandsInstruction {
    @Override
    public void Execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        Slot slot1 = stack.popSlot();
        Slot slot2 = stack.popSlot();
        Slot slot3 = stack.popSlot();
        stack.pushSlot(slot2);
        stack.pushSlot(slot1);
        stack.pushSlot(slot3);
        stack.pushSlot(slot2);
        stack.pushSlot(slot1);
    }
}
