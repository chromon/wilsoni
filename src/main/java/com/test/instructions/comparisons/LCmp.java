package com.test.instructions.comparisons;

import com.test.dataarea.Frame;
import com.test.dataarea.OperandStack;
import com.test.instructions.base.NoOperandsInstruction;

// 比较指令可以分为两类：
// 一类将比较结果推入操作数栈顶，一类根据比较结果跳转。
// 比较指令是编译器实现 if-else、for、while 等语句的基石

// lcmp 指令用于比较 long 变量
public class LCmp extends NoOperandsInstruction {
    // 弹出，比较，将最终结果推入栈顶
    @Override
    public void Execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        long l2 = stack.popLong();
        long l1 = stack.popLong();
        if (l1 > l2) {
            stack.pushInt(1);
        } else if (l1 == l2) {
            stack.pushInt(0);
        }
        stack.pushInt(-1);
    }
}
