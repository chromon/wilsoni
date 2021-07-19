package com.test.instructions.constants.iconstx;

import com.test.dataarea.Frame;
import com.test.instructions.base.NoOperandsInstruction;

// 将 int 类型 2 压入操作数栈
public class IConst2 extends NoOperandsInstruction {
    @Override
    public void Execute(Frame frame) {
        frame.getOperandStack().pushInt(2);
    }
}
