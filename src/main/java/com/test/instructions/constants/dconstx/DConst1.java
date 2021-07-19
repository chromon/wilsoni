package com.test.instructions.constants.dconstx;

import com.test.dataarea.Frame;
import com.test.instructions.base.NoOperandsInstruction;

// 向操作数栈中压入 double 类型 1
public class DConst1 extends NoOperandsInstruction {
    @Override
    public void Execute(Frame frame) {
        frame.getOperandStack().pushDouble(1.0);
    }
}
