package com.test.instructions.math.sh;

import com.test.dataarea.Frame;
import com.test.dataarea.OperandStack;
import com.test.instructions.base.NoOperandsInstruction;

// long 右移（算数右移）
public class LShR extends NoOperandsInstruction {
    @Override
    public void Execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        int l2 = stack.popInt();
        long l1 = stack.popLong();
        // long 变量有 64 位，所以取 l2 的前 6 个比特
        int s = l2 & 0x3f;
        long res = l1 >> s;
        stack.pushLong(res);
    }
}
