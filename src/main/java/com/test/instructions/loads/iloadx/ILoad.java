package com.test.instructions.loads.iloadx;

import com.test.dataarea.Frame;
import com.test.instructions.base.Index8Instruction;
import com.test.instructions.loads.LoadManager;

// 加载指令从局部变量表获取变量，然后推入操作数栈顶

// 从局部变量表中获取变量 int 型变量，压入操作数栈顶
public class ILoad extends Index8Instruction {

    @Override
    public void Execute(Frame frame) {
        LoadManager.iload(frame, super.getIndex());
    }
}
