package com.test.instructions.comparisons.ifcond;

import com.test.dataarea.Frame;
import com.test.dataarea.OperandStack;
import com.test.instructions.base.BranchInstruction;

// if<cond> 指令把操作数栈顶的 int 变量弹出，然后跟 0 进行比较，满足条件则跳转

// 当弹出的栈顶变量等于 0 时跳转
public class IFEQ extends BranchInstruction {
    @Override
    public void Execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        int val = stack.popInt();
        if (val == 0) {
            super.branch(frame, super.getOffset());
            // BranchInstruction.branch(frame, super.getOffset());
        }
    }
}
