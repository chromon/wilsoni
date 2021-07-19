package com.test.instructions.conversions.f2x;

import com.test.dataarea.Frame;
import com.test.dataarea.OperandStack;
import com.test.instructions.base.NoOperandsInstruction;

// 将 float 类型强制转换为 double 类型
public class F2D extends NoOperandsInstruction {
    @Override
    public void Execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        float f = stack.popFloat();
        stack.pushDouble(f);
    }
}
