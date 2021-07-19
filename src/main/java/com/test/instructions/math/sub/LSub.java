package com.test.instructions.math.sub;

import com.test.dataarea.Frame;
import com.test.dataarea.OperandStack;
import com.test.instructions.base.NoOperandsInstruction;

// long 减法指令
public class LSub extends NoOperandsInstruction {
    @Override
    public void Execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        long l2 = stack.popLong();
        long l1 = stack.popLong();
        long res = l1 - l2;
        stack.pushLong(res);
    }
}
