package com.test.instructions.comparisons.ifacmp;

import com.test.dataarea.Frame;
import com.test.dataarea.OperandStack;
import com.test.dataarea.heap.XObject;
import com.test.instructions.base.BranchInstruction;

// if_acmp 指令把栈顶的两个引用弹出，根据引用是否相同进行跳转

// 栈顶的两个引用相同
public class IFACmpEQ extends BranchInstruction {
    @Override
    public void Execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        XObject obj2 = stack.popRef();
        XObject obj1 = stack.popRef();
        if (obj1 == obj2) {
            super.branch(frame, super.getOffset());
        }
    }
}
