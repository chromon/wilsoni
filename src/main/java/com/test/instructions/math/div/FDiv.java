package com.test.instructions.math.div;

import com.test.dataarea.Frame;
import com.test.dataarea.OperandStack;
import com.test.instructions.base.NoOperandsInstruction;

// float 除法
public class FDiv extends NoOperandsInstruction {
    @Override
    public void Execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        float f2 = stack.popFloat();
        float f1 = stack.popFloat();
        float res = f1 / f2;
        stack.pushFloat(res);
    }
}
