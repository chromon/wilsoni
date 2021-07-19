package com.test.instructions.base;

import com.test.dataarea.Frame;

// 指令接口
public interface Instruction {
    // 从字节码中提取操作数
    void FetchOperands(BytecodeReader reader);
    // 执行指令逻辑
    void Execute(Frame frame);
}
