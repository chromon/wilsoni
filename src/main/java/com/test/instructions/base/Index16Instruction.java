package com.test.instructions.base;

import com.test.dataarea.Frame;

// 一些指令需要访问运行时常量池，常量池索引由两字节操作数给出。
// 把这类指令抽象成 Index16Instruction，用 Index 字段表示常量池索引
public class Index16Instruction implements Instruction {

    // 常量池索引
    private int index;

    @Override
    public void FetchOperands(BytecodeReader reader) {
        this.index = reader.readU2();
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
