package com.test.instructions.conversions.l2x;

import com.test.dataarea.Frame;
import com.test.dataarea.OperandStack;
import com.test.instructions.base.NoOperandsInstruction;

// 将 long 类型强制转换为 double 类型
public class L2D extends NoOperandsInstruction {
    @Override
    public void Execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        long l = stack.popLong();
        stack.pushDouble(l);
    }
}
