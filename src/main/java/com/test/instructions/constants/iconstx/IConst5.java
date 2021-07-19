package com.test.instructions.constants.iconstx;

import com.test.dataarea.Frame;
import com.test.instructions.base.NoOperandsInstruction;

// 将 int 类型 5 压入操作数栈
public class IConst5 extends NoOperandsInstruction {
    @Override
    public void Execute(Frame frame) {
        frame.getOperandStack().pushInt(5);
    }
}
