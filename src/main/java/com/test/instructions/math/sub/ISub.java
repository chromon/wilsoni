package com.test.instructions.math.sub;

import com.test.dataarea.Frame;
import com.test.dataarea.OperandStack;
import com.test.instructions.base.NoOperandsInstruction;

// int 减法指令
public class ISub extends NoOperandsInstruction {
    @Override
    public void Execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        int i2 = stack.popInt();
        int i1 = stack.popInt();
        int res = i1 - i2;
        stack.pushInt(res);
    }
}
