package com.test.instructions.constants.push;

import com.test.dataarea.Frame;
import com.test.instructions.base.BytecodeReader;
import com.test.instructions.base.Instruction;

// 从操作数中获取一个 short 型整数，扩展成 int 型，然后推入栈顶
public class SiPush implements Instruction {

    private int value;

    @Override
    public void FetchOperands(BytecodeReader reader) {
        this.value = reader.readU2();
    }

    @Override
    public void Execute(Frame frame) {
        frame.getOperandStack().pushInt(this.value);
    }
}
