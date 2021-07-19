package com.test.instructions.control;

import com.test.dataarea.Frame;
import com.test.instructions.base.BranchInstruction;
import com.test.instructions.base.BytecodeReader;

// Java 语言中的 switch-case 语句有两种实现方式:
// lookupswitch 和 tableswitch

// 由于 tableswitch 的 case 值是连续的，因此只需要记录最低值和最高值，
// 以及每一项对应的 offset 偏移量，根据给定的 index 值通过简单的计算即可直接定位到 offset
public class TableSwitch extends BranchInstruction {

    // 对应默认情况下执行跳转所需的字节码偏移量
    private int defaultOffset;
    // case 的取值范围
    private int low;
    private int high;
    // 索引表，保存 high - low + 1 个 int 值
    // 对应各种 case 情况下，执行跳转所需要的字节码偏移量
    private int[] jumpOffsets;

    @Override
    public void FetchOperands(BytecodeReader reader) {
        // tableswitch 指令操作码的后面有 0 ~ 3 字节的 padding，
        // 以保证 defaultOffset 在字节码中的地址是 4 的倍数
        reader.skipPadding();
        this.defaultOffset = reader.readU4Int();
        this.low = reader.readU4Int();
        this.high = reader.readU4Int();
        int jumpOffsetsCount = this.high - this.low + 1;
        this.jumpOffsets = reader.readU4Ints(jumpOffsetsCount);
    }

    @Override
    public void Execute(Frame frame) {
        // 从操作数栈中弹出一个 int 变量
        int index = frame.getOperandStack().popInt();
        int offset;
        // 是否是在 low 和 high 给定的范围内
        if (index >= this.low && index <= this.high) {
            // 从 jumpOffsets 表中查出偏移量进行跳转
            offset = this.jumpOffsets[index - this.low];
        } else {
            // 使用 defaultOffset 跳转
            offset = this.defaultOffset;
        }
        super.branch(frame, offset);
    }
}
