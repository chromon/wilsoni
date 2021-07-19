package com.test.instructions.loads.aloadx;

import com.test.dataarea.Frame;
import com.test.instructions.base.NoOperandsInstruction;
import com.test.instructions.loads.LoadManager;

// 加载指令从局部变量表获取变量，然后推入操作数栈顶

// 从局部变量表获取引用变量 1，然后推入操作数栈顶
public class ALoad1 extends NoOperandsInstruction {
    @Override
    public void Execute(Frame frame) {
        LoadManager.aload(frame, 1);
    }
}
