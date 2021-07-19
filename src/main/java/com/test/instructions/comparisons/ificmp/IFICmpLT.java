package com.test.instructions.comparisons.ificmp;

import com.test.dataarea.Frame;
import com.test.dataarea.OperandStack;
import com.test.instructions.base.BranchInstruction;

// if_icmp<cond> 指令把栈顶的两个 int 变量弹出，然后进行比较，满足条件则跳转

// 当弹出的栈顶比下一个元素大时跳转
public class IFICmpLT extends BranchInstruction {
    @Override
    public void Execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        int i2 = stack.popInt();
        int i1 = stack.popInt();
        if (i1 < i2) {
            super.branch(frame, super.getOffset());
        }
    }
}
