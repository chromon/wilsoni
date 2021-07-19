package com.test.instructions.constants.push;

import com.test.dataarea.Frame;
import com.test.instructions.base.BytecodeReader;
import com.test.instructions.base.Instruction;

// 从操作数中获取一个 byte 型正数，扩展为 int 型，后压入栈顶
public class BiPush implements Instruction {

    private int value;

    @Override
    public void FetchOperands(BytecodeReader reader) {
        this.value = reader.readByte();
    }

    @Override
    public void Execute(Frame frame) {
        frame.getOperandStack().pushInt(this.value);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
