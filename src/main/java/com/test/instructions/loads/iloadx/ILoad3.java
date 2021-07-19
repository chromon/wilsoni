package com.test.instructions.loads.iloadx;

import com.test.dataarea.Frame;
import com.test.instructions.base.NoOperandsInstruction;
import com.test.instructions.loads.LoadManager;

// 加载指令从局部变量表获取变量，然后推入操作数栈顶

// 从局部变量表中获取变量 int 型变量 3，压入操作数栈顶
public class ILoad3 extends NoOperandsInstruction {

    @Override
    public void Execute(Frame frame) {
        LoadManager.iload(frame, 3);
    }
}
