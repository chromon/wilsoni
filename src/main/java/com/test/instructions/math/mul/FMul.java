package com.test.instructions.math.mul;

import com.test.dataarea.Frame;
import com.test.dataarea.OperandStack;
import com.test.instructions.base.NoOperandsInstruction;

// float 乘法指令
public class FMul extends NoOperandsInstruction {
    @Override
    public void Execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        float f2 = stack.popFloat();
        float f1 = stack.popFloat();
        float res = f1 * f2;
        stack.pushFloat(res);
    }
}
