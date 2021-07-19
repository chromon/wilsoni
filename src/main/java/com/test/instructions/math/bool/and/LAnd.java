package com.test.instructions.math.bool.and;

import com.test.dataarea.Frame;
import com.test.dataarea.OperandStack;
import com.test.instructions.base.NoOperandsInstruction;

// 按位与
// 按位与运算只能操作 int 和 long 变量

// long 型按位与运算
public class LAnd extends NoOperandsInstruction {
    @Override
    public void Execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        long l2 = stack.popLong();
        long l1 = stack.popLong();
        long res = l1 & l2;
        stack.pushLong(res);
    }
}
