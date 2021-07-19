package com.test.instructions.constants.lconstx;

import com.test.dataarea.Frame;
import com.test.instructions.base.NoOperandsInstruction;

// 将 long 类型 1 压入操作数栈
public class LConst1 extends NoOperandsInstruction {
    @Override
    public void Execute(Frame frame) {
        frame.getOperandStack().pushLong(1L);
    }
}