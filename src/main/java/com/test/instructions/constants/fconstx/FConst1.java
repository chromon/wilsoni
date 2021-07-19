package com.test.instructions.constants.fconstx;

import com.test.dataarea.Frame;
import com.test.instructions.base.NoOperandsInstruction;

// 将 float 类型 1 压入操作数栈
public class FConst1 extends NoOperandsInstruction {
    @Override
    public void Execute(Frame frame) {
        frame.getOperandStack().pushFloat(1.0f);
    }
}
