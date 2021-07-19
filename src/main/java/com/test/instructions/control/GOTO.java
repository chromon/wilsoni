package com.test.instructions.control;

import com.test.dataarea.Frame;
import com.test.instructions.base.BranchInstruction;

// goto 指令进行无条件跳转
public class GOTO extends BranchInstruction {
    @Override
    public void Execute(Frame frame) {
        super.branch(frame, super.getOffset());
    }
}
