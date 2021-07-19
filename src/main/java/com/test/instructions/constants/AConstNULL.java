package com.test.instructions.constants;

import com.test.dataarea.Frame;
import com.test.instructions.base.NoOperandsInstruction;

// const 指令把隐含在操作码中的常量值推入操作数栈顶

// 向操作数栈中压入 null
public class AConstNULL extends NoOperandsInstruction {
    @Override
    public void Execute(Frame frame) {
        frame.getOperandStack().pushRef(null);
    }
}
