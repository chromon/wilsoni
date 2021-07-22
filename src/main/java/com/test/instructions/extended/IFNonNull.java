package com.test.instructions.extended;

import com.test.dataarea.Frame;
import com.test.dataarea.heap.XObject;
import com.test.instructions.base.BranchInstruction;

// 根据引用是否是 null 进行跳转

// 引用不是 null
public class IFNonNull extends BranchInstruction {
    @Override
    public void Execute(Frame frame) {
        XObject obj = frame.getOperandStack().popRef();
        if (obj != null) {
            super.branch(frame, super.getOffset());
        }
    }
}
