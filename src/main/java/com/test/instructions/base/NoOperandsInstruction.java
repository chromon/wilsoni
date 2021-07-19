package com.test.instructions.base;

import com.test.dataarea.Frame;

// 没有操作数的指令
public class NoOperandsInstruction implements Instruction {
    // 由于没有操作数，所以没有任何字段，方法实现也为空
    @Override
    public void FetchOperands(BytecodeReader reader) {}

    @Override
    public void Execute(Frame frame) {}
}
