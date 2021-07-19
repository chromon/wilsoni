package com.test.instructions.conversions.d2x;

import com.test.dataarea.Frame;
import com.test.dataarea.OperandStack;
import com.test.instructions.base.NoOperandsInstruction;

// 将 double 类型强制转换为 long 类型
public class D2L extends NoOperandsInstruction {
    @Override
    public void Execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        double d = stack.popDouble();
        long l = (long) d;
        stack.pushLong(l);
    }
}
