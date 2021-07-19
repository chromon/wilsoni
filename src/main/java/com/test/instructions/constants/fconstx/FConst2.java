package com.test.instructions.constants.fconstx;

import com.test.dataarea.Frame;
import com.test.instructions.base.NoOperandsInstruction;

// 将 float 类型 2 压入操作数栈
public class FConst2 extends NoOperandsInstruction {
    @Override
    public void Execute(Frame frame) {
        frame.getOperandStack().pushFloat(2.0f);
    }
}
