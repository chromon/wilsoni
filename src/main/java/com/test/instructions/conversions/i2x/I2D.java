package com.test.instructions.conversions.i2x;

import com.test.dataarea.Frame;
import com.test.dataarea.OperandStack;
import com.test.instructions.base.NoOperandsInstruction;

// 将 int 类型强制转换为 double 类型
public class I2D extends NoOperandsInstruction {
    @Override
    public void Execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        int i = stack.popInt();
        float f = Float.intBitsToFloat(i);
        stack.pushDouble(f);
    }
}
