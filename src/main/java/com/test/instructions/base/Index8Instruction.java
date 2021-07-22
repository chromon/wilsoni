package com.test.instructions.base;

import com.test.dataarea.Frame;

// 存储和加载类指令需要根据索引存取局部变量表，索引由单节操作数给出
// 这类指令抽象为 Index8Instruction ，用 index 字段表示局部变量表索引
public class Index8Instruction implements Instruction {
    // 局部变量表索引
    private int index;

    @Override
    public void FetchOperands(BytecodeReader reader) {
        this.index = reader.readByte();
        index &= 0xFF;
    }

    @Override
    public void Execute(Frame frame) {}

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
