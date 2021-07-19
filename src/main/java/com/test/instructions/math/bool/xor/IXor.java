package com.test.instructions.math.bool.xor;

import com.test.dataarea.Frame;
import com.test.dataarea.OperandStack;
import com.test.instructions.base.NoOperandsInstruction;

// 按位异或
// 按位异或运算只能操作 int 和 long 变量

// int 型按位异或运算
public class IXor extends NoOperandsInstruction {
    @Override
    public void Execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        int i2 = stack.popInt();
        int i1 = stack.popInt();
        int res = i1 ^ i2;
        stack.pushInt(res);
    }
}
