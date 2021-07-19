package com.test.instructions.constants.lconstx;

import com.test.dataarea.Frame;
import com.test.instructions.base.NoOperandsInstruction;

// 将 long 类型 0 压入操作数栈
public class LConst0 extends NoOperandsInstruction {
    @Override
    public void Execute(Frame frame) {
        frame.getOperandStack().pushLong(0L);
    }
}