package com.test.instructions.math.add;

import com.test.dataarea.Frame;
import com.test.dataarea.OperandStack;
import com.test.instructions.base.NoOperandsInstruction;

// double 加法指令
public class DAdd extends NoOperandsInstruction {
    @Override
    public void Execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        double d2 = stack.popDouble();
        double d1 = stack.popDouble();
        double res = d1 + d2;
        stack.pushDouble(res);
    }
}
