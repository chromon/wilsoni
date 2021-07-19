package com.test.instructions.base;

import com.test.dataarea.Frame;

// 跳转指令
public class BranchInstruction implements Instruction {

    // 存放跳转偏移量
    private int offset;

    @Override
    public void FetchOperands(BytecodeReader reader) {
        // 从字节码中提取操作数
        this.offset = reader.readU2();
    }

    // 分支跳转
    public void branch(Frame frame, int offset) {
        int pc = frame.getXThread().getPC();
        int nextPC = pc + offset;
        frame.setNextPC(nextPC);
    }

    @Override
    public void Execute(Frame frame) {}

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
