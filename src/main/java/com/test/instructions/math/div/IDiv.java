package com.test.instructions.math.div;

import com.test.dataarea.Frame;
import com.test.dataarea.OperandStack;
import com.test.instructions.base.NoOperandsInstruction;

// int 除法指令
public class IDiv extends NoOperandsInstruction {
    @Override
    public void Execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        int i2 = stack.popInt();
        int i1 = stack.popInt();
        if (i2 == 0) {
            throw new RuntimeException("java.lang.ArithmeticException: / by zero");
        }
        int res = i1 / i2;
        stack.pushInt(res);
    }
}
