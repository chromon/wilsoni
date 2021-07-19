package com.test.instructions.stack.dup;

import com.test.dataarea.Frame;
import com.test.dataarea.Slot;
import com.test.instructions.base.NoOperandsInstruction;

/*
bottom -> top
[...][c][b][a]
             \_
               |
               V
[...][c][b][a][a]
*/
// dup 指令用于复制栈顶元素并插入操作数栈中
// 为了与其他 dup 命令统一，可以理解为：
// 复制栈顶变量并插入到栈顶变量的后面（其实是自身，并不需要这么做）
public class Dup extends NoOperandsInstruction {
    @Override
    public void Execute(Frame frame) {
        Slot slot = frame.getOperandStack().popSlot();
        frame.getOperandStack().pushSlot(slot);
        frame.getOperandStack().pushSlot(slot);
    }
}
