package com.test.instructions.control;

import com.test.dataarea.Frame;
import com.test.instructions.base.BranchInstruction;
import com.test.instructions.base.BytecodeReader;

// Java 语言中的 switch-case 语句有两种实现方式:
// lookupswitch 和 tableswitch

// lookupswitch 内部存放着各个离散的 case-offset 对，
// 每次执行都要搜索全部的 case-offset 对，找到匹配的 case 值，
// 并根据对应的 offset 计算跳转地址，因此效率较低
public class LookUpSwitch extends BranchInstruction {

    // 对应默认情况下执行跳转所需的字节码偏移量
    private int defaultOffset;
    private int nPairs;
    // 类似于 Map，key 是 case 值，value 是跳转偏移量
    private int[] matchOffsets;

    @Override
    public void FetchOperands(BytecodeReader reader) {
        // 指令操作码的后面有 0 ~ 3 字节的 padding，
        // 以保证 defaultOffset 在字节码中的地址是 4 的倍数
        reader.skipPadding();
        this.defaultOffset = reader.readU4Int();
        this.nPairs = reader.readU4Int();
        int length = 2 * this.nPairs;
        this.matchOffsets = reader.readU4Ints(length);
    }

    @Override
    public void Execute(Frame frame) {
        // 操作数栈中弹出一个 int 变量 key
        int key = frame.getOperandStack().popInt();
        for (int i = 0; i < 2 * this.nPairs; i += 2) {
            // 由 key 查找 matchOffsets，看是否能找到匹配的 key
            if (this.matchOffsets[i] == key) {
                // 找到匹配的 key，按照 value 给出的偏移量跳转
                int offset = matchOffsets[i + 1];
                super.branch(frame, offset);
                return;
            }
        }
        // 否则使用默认的 defaultOffset 跳转
        super.branch(frame, this.defaultOffset);
    }
}
