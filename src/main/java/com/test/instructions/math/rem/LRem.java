package com.test.instructions.math.rem;

import com.test.dataarea.Frame;
import com.test.dataarea.OperandStack;
import com.test.instructions.base.NoOperandsInstruction;

// long 类型求余
public class LRem extends NoOperandsInstruction {
    @Override
    public void Execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        long l2 = stack.popLong();
        long l1 = stack.popLong();
        if (l2 == 0) {
            throw new RuntimeException("java.lang.ArithmeticException: / by zero");
        }
        long res = l1 % l2;
        stack.pushLong(res);
    }
}
