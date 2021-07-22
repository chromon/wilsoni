package com.test.instructions.references;

import com.test.dataarea.Frame;
import com.test.instructions.base.Index16Instruction;

public class InvokeSpecial extends Index16Instruction {
    @Override
    public void Execute(Frame frame) {
        frame.getOperandStack().popRef();
    }
}
