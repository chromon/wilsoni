package com.test.instructions.loads.dloadx;

import com.test.dataarea.Frame;
import com.test.instructions.base.Index8Instruction;
import com.test.instructions.loads.LoadManager;


// 从局部变量表中获取变量 double 型变量，压入操作数栈顶
public class DLoad extends Index8Instruction {
    @Override
    public void Execute(Frame frame) {
        LoadManager.dload(frame, super.getIndex());
    }
}
