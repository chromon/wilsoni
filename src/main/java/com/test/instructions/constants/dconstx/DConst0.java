package com.test.instructions.constants.dconstx;

import com.test.dataarea.Frame;
import com.test.instructions.base.NoOperandsInstruction;

// 向操作数栈中压入 double 类型 0
public class DConst0 extends NoOperandsInstruction {
    @Override
    public void Execute(Frame frame) {
        frame.getOperandStack().pushDouble(0.0);
    }
}
