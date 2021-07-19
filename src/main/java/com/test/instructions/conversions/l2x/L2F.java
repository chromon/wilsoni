package com.test.instructions.conversions.l2x;

import com.test.dataarea.Frame;
import com.test.dataarea.OperandStack;
import com.test.instructions.base.NoOperandsInstruction;

// 将 long 类型强制转换为 float 类型
public class L2F extends NoOperandsInstruction {
    @Override
    public void Execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        long l = stack.popLong();
        stack.pushFloat(l);
    }
}
