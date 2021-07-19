package com.test.instructions.extended;

import com.test.dataarea.Frame;
import com.test.instructions.base.BranchInstruction;
import com.test.instructions.base.BytecodeReader;

// goto_w 指令和 goto 指令的唯一区别就是索引从 2 字节变成了 4 字节
public class GOTOW extends BranchInstruction {

    @Override
    public void FetchOperands(BytecodeReader reader) {
        super.setOffset(reader.readU4Int());
    }

    @Override
    public void Execute(Frame frame) {
        super.branch(frame, super.getOffset());
    }
}
