package com.test.instructions.extended;

import com.test.dataarea.Frame;
import com.test.instructions.base.BranchInstruction;

// 根据引用是否是 null 进行跳转

// 引用是 null
public class IFNull extends BranchInstruction {
    @Override
    public void Execute(Frame frame) {
        Object obj = frame.getOperandStack().popRef();
        if (obj == null) {
            super.branch(frame, super.getOffset());
        }
    }
}
